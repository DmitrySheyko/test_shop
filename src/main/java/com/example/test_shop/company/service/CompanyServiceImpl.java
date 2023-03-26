package com.example.test_shop.company.service;

import com.example.test_shop.company.dto.CompanyAdminUpdateDto;
import com.example.test_shop.company.dto.CompanyDto;
import com.example.test_shop.company.dto.NewCompanyDto;
import com.example.test_shop.company.mapper.CompanyMapper;
import com.example.test_shop.company.model.Company;
import com.example.test_shop.company.model.CompanyStatus;
import com.example.test_shop.company.repository.CompanyRepository;
import com.example.test_shop.exceptions.NotFoundException;
import com.example.test_shop.exceptions.ValidationException;
import com.example.test_shop.product.model.Product;
import com.example.test_shop.product.model.ProductStatus;
import com.example.test_shop.product.repository.ProductRepository;
import com.example.test_shop.user.model.User;
import com.example.test_shop.user.model.UserStatus;
import com.example.test_shop.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Class of service for {@link Company} entity
 *
 * @author DmitrySheyko
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class CompanyServiceImpl implements CompanyService {
    private final CompanyRepository repository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    @Override
    public CompanyDto add(NewCompanyDto companyDto, Long userId) {
        User owner = checkIsUserExistAndActive(userId);

        // Генерируем новую компанию из Dto и добавляем исходные данные
        Company newCompany = CompanyMapper.toCompany(companyDto);
        newCompany.setOwner(owner);
        newCompany.setStatus(CompanyStatus.PENDING);
        newCompany = repository.save(newCompany);

        // Возвращаем результат
        CompanyDto newCompanyDto = CompanyMapper.toDto(newCompany);
        log.info("Company id={} successfully created", newCompany.getId());
        return newCompanyDto;
    }

    @Override
    public CompanyDto delete(Long companyId) {
        Company company = repository.findById(companyId).orElseThrow(() -> new NotFoundException(String
                .format("Company didn't delete. Company id=%s not found", companyId)));

        // При удалении компании меняем ее статус на DELETED, из базы не удаляем
        company.setStatus(CompanyStatus.DELETED);
        repository.save(company);

        // Меняем статус товаров комании на DELETED, из базы не удаляем
        List<Product> productList = productRepository.findAllByCompany(company);
        productList.forEach(product -> product.setStatus(ProductStatus.DELETED));
        productRepository.saveAll(productList);

        // Возвращаем результат
        CompanyDto companyDto = CompanyMapper.toDto(company);
        log.info("Company id={} successfully deleted", companyId);
        return companyDto;
    }

    @Override
    public CompanyDto update(CompanyAdminUpdateDto companyDto, Long companyId) {
        Company companyFromRepository = repository.findById(companyId).orElseThrow(() -> new NotFoundException(String
                .format("Company didn't update. Company id=%s not found", companyId)));

        // Генерируем скидку на основании данных из Dto
        Company companyForUpdate = CompanyMapper.toCompany(companyDto);

        // Если параметры компании из Dto не равны null заменяем ими парметры компании из базы. Сохраняем обновленную компанию
        companyFromRepository.setStatus(Optional.ofNullable(companyForUpdate.getStatus()).orElse(companyFromRepository.getStatus()));
        companyFromRepository = repository.save(companyFromRepository);

        // Возвращаем результат
        CompanyDto updatedCompanyDto = CompanyMapper.toDto(companyFromRepository);
        log.info("Company id={} successfully updated", companyId);
        return updatedCompanyDto;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CompanyDto> searchCompany(Set<Long> companiesId, String name, Set<String> statusesString,
                                          Set<Long> ownersId, String description) {

        // Если в параметрах поиска задан статус компании. переводим его в String
        Set<CompanyStatus> statusesEnum;
        if (statusesString != null && !statusesString.isEmpty()) {
            statusesEnum = statusesString.stream().map(CompanyStatus::valueOf).collect(Collectors.toSet());
        } else {
            statusesEnum = null;
        }

        // Осуществяем поиск по параметрам
        List<Company> companiesList = repository.searchCompany(companiesId, name, statusesEnum, ownersId, description);

        // Возвращаем результат
        List<CompanyDto> companiesDtoList = companiesList.stream().map(CompanyMapper::toDto).toList();
        log.info("Set of companies successfully received");
        return companiesDtoList;
    }

    private User checkIsUserExistAndActive(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException(String
                .format("User didn't update. User id=%s not found", userId)));
        if (Objects.equals(user.getStatus(), UserStatus.BLOCKED)) {
            throw new ValidationException(String.format("User id=%s is blocked", userId));
        }
        return user;
    }

}