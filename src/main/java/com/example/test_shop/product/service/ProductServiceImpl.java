package com.example.test_shop.product.service;

import com.example.test_shop.comment.dto.CommentDto;
import com.example.test_shop.comment.mapper.CommentMapper;
import com.example.test_shop.comment.model.Comment;
import com.example.test_shop.company.model.Company;
import com.example.test_shop.company.model.CompanyStatus;
import com.example.test_shop.company.repository.CompanyRepository;
import com.example.test_shop.discount.model.Discount;
import com.example.test_shop.discount.repository.DiscountRepository;
import com.example.test_shop.exceptions.NotFoundException;
import com.example.test_shop.exceptions.ValidationException;
import com.example.test_shop.product.dto.NewProductDto;
import com.example.test_shop.product.dto.ProductDto;
import com.example.test_shop.product.dto.ProductShortDto;
import com.example.test_shop.product.dto.ProductUpdateDto;
import com.example.test_shop.product.mapper.ProductMapper;
import com.example.test_shop.product.model.Product;
import com.example.test_shop.product.model.ProductStatus;
import com.example.test_shop.product.repository.ProductRepository;
import com.example.test_shop.user.model.User;
import com.example.test_shop.user.model.UserStatus;
import com.example.test_shop.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Class of service for {@link Product} entity
 *
 * @author DmitrySheyko
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;
    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;
    private final DiscountRepository discountRepository;

    @Override
    public ProductDto add(NewProductDto productDto, Long userId) {
        checkIsUserExistAndActive(userId);
        Company company = checkIsCompanyExistAndActive(productDto.getCompanyId());
        Discount discount = checkIsDiscountExist(productDto.getDiscountId());

        Product newProduct = Product.builder()
                .name(productDto.getName())
                .description(productDto.getDescription())
                .company(company)
                .price(productDto.getPrice())
                .quantity(productDto.getQuantity())
                .discount(discount)
                .keyWords(productDto.getKeyWords())
                .characteristics(productDto.getCharacteristics())
                .status(ProductStatus.ACTIVE)
                .ratesList(new ArrayList<>())
                .build();

        newProduct = repository.save(newProduct);
        ProductDto newProductDto = ProductMapper.toDto(newProduct);
        log.info("Product id={} successfully add", newProduct.getId());
        return newProductDto;
    }

    @Override
    public ProductDto update(ProductUpdateDto productDto, Long userId) {
        checkIsUserExistAndActive(userId);
        Product productFromRepository = checkAndGetProduct(productDto.getId());
        Product productForUpdate = ProductMapper.toProduct(productDto);

        productFromRepository.setName(Optional.ofNullable(productForUpdate.getName())
                .orElse(productFromRepository.getName()));
        productFromRepository.setDescription(Optional.ofNullable(productForUpdate.getDescription())
                .orElse(productFromRepository.getDescription()));
        productFromRepository.setPrice(Optional.ofNullable(productForUpdate.getPrice())
                .orElse(productFromRepository.getPrice()));
        productFromRepository.setQuantity(Optional.ofNullable(productForUpdate.getQuantity())
                .orElse(productFromRepository.getQuantity()));
        productFromRepository.setKeyWords(Optional.ofNullable(productForUpdate.getKeyWords())
                .orElse(productFromRepository.getKeyWords()));
        productFromRepository.setCharacteristics(Optional.ofNullable(productForUpdate.getCharacteristics())
                .orElse(productFromRepository.getCharacteristics()));
        productFromRepository.setStatus(Optional.ofNullable(productForUpdate.getStatus())
                .orElse(productFromRepository.getStatus()));
        if (productDto.getDiscountId() != null) {
            Discount discount = checkAndGetDiscount(productDto.getDiscountId());
            productFromRepository.setDiscount(discount);
        }

        ProductDto updatedProductDto = ProductMapper.toDto(productFromRepository);
        log.info("Product id={} successfully updated", productFromRepository.getId());
        return updatedProductDto;
    }

    @Override
    public ProductDto delete(Long userId, Long productId) {
        checkIsUserExistAndActive(userId);
        Product product = checkAndGetProduct(productId);

        // Меняестатус продукта на DELETE, из базы не удаляем
        product.setStatus(ProductStatus.DELETED);
        repository.save(product);

        ProductDto productDto = ProductMapper.toDto(product);
        log.info("Product id={} successfully deleted", productId);
        return productDto;
    }

    @Override
    public List<ProductShortDto> getAllActive(Long userId, Integer from, Integer size) {
        checkIsUserExistAndActive(userId);
        int page = from / size;
        Pageable pageable = PageRequest.of(page, size, Sort.by("id"));

        // получаем из базы товары имеющие статус Active принадлежащие компаниям имеющим статус Active
        Page<Product> productsPage = repository.findAllByStatusAndCompanyStatus(ProductStatus.ACTIVE,
                CompanyStatus.ACTIVE, pageable);
        List<ProductShortDto> productsList = productsPage.stream().map(ProductMapper::toShortDto).toList();
        log.info("List of products successfully received");
        return productsList;
    }

    @Override
    public List<CommentDto> getComments(Long userId, Long productId) {
        checkIsUserExistAndActive(userId);
        Product product = checkAndGetProduct(productId);
        List<Comment> commentList = product.getCommentsList();
        List<CommentDto> commentDtoList = commentList.stream().map(CommentMapper::toDto).collect(Collectors.toList());
        log.info("Set of comments for product id={} successfully received", productId);
        return commentDtoList;
    }

    private void checkIsUserExistAndActive(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException(String
                .format("Product didn't add. User id=%s not found", userId)));
        if (Objects.equals(user.getStatus(), UserStatus.BLOCKED)) {
            throw new ValidationException(String.format("User id=%s is blocked", userId));
        }
    }

    private Company checkIsCompanyExistAndActive(Long companyId) {
        Company company = companyRepository.findById(companyId).orElseThrow(() -> new NotFoundException(String
                .format("Product didn't add. Company id=%s not found", companyId)));
        if (Objects.equals(company.getStatus(), CompanyStatus.BLOCKED) ||
                Objects.equals(company.getStatus(), CompanyStatus.PENDING)) {
            throw new ValidationException(String.format("Company id=%s has status %s", companyId, company.getStatus().name()));
        }
        return company;
    }

    private Discount checkIsDiscountExist(Long discountId) {
        if (discountId == null) {
            return null;
        }
        return discountRepository.findById(discountId).orElseThrow(() -> new NotFoundException(String
                .format("Product didn't add. Discount id=%s not found", discountId)));
    }

    private Product checkAndGetProduct(Long productId) {
        return repository.findById(productId)
                .orElseThrow(() -> new NotFoundException(String.format("Product id=%s not found", productId)));
    }

    private Discount checkAndGetDiscount(Long discountId) {
        return discountRepository.findById(discountId)
                .orElseThrow(() -> new NotFoundException(String.format("Discount id=%s not found", discountId)));
    }

}
