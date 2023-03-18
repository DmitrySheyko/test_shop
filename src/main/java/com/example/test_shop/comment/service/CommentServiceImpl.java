package com.example.test_shop.comment.service;

import com.example.test_shop.comment.dto.CommentDto;
import com.example.test_shop.comment.dto.NewCommentDto;
import com.example.test_shop.comment.mapper.CommentMapper;
import com.example.test_shop.comment.model.Comment;
import com.example.test_shop.comment.repository.CommentRepository;
import com.example.test_shop.exceptions.NotFoundException;
import com.example.test_shop.exceptions.ValidationException;
import com.example.test_shop.purchase.model.Purchase;
import com.example.test_shop.purchase.repository.PurchaseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class CommentServiceImpl implements CommentService {

    private final CommentRepository repository;
    private final PurchaseRepository purchaseRepository;

    @Override
    public CommentDto add(NewCommentDto commentDto, Long userId) {
        // Проверяем покупал ли пользвательданный товар
        Optional<Purchase> optionalPurchase = purchaseRepository.findFirstByBuyerIdAndProductId(userId,
                commentDto.getProductId());
        if (optionalPurchase.isEmpty()) {
            throw new NotFoundException(String.format("User id=%s couldn't add comments to product id=%s", userId,
                    commentDto.getProductId()));
        }
        // Проверяем оставлял ли пользователь комментарий данному товару
        if (repository.existsByUserIdAndProductId(userId, commentDto.getProductId())) {
            throw new ValidationException(String.format("User id=%s already add comment to product id=%s", userId,
                    commentDto.getProductId()));
        }
        // Сохраняем комментарий
        Comment newComment = CommentMapper.toComment(commentDto);
        newComment.setUser(optionalPurchase.get().getBuyer());
        newComment.setProduct(optionalPurchase.get().getProduct());
        newComment.setCreatedOn(LocalDateTime.now());
        newComment = repository.save(newComment);

        CommentDto newCommentDto = CommentMapper.toDto(newComment);
        log.info("Comment id={} successfully add", newComment.getId());
        return newCommentDto;
    }

}
