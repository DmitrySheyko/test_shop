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
import com.example.test_shop.user.model.User;
import com.example.test_shop.user.model.UserStatus;
import com.example.test_shop.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

/**
 * Class of service for {@link Comment} entity
 *
 * @author DmitrySheyko
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class CommentServiceImpl implements CommentService {

    private final CommentRepository repository;
    private final PurchaseRepository purchaseRepository;
    private final UserRepository userRepository;

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

    @Override
    public String delete(Long userId, Long commentId) {
        // Проверяем существует и не заблокирован ли пользователь
        User user = checkIsUserExistAndActive(userId);

        // Проверяем существует ли комментарий с требуемыми userId и commentId
        Optional<Comment> optionalComment = repository.findByIdAndUser(commentId, user);
        if (optionalComment.isEmpty()) {
            throw new NotFoundException(String.format("Comment id=%s from user id=%s not found", commentId, userId));
        }
        repository.delete(optionalComment.get());
        log.info("Comment id={} successfully deleted", commentId);
        return String.format("Comment id=%s successfully deleted", commentId);
    }

    private User checkIsUserExistAndActive(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException(String
                .format("Product didn't add. User id=%s not found", userId)));
        if (Objects.equals(user.getStatus(), UserStatus.BLOCKED)) {
            throw new ValidationException(String.format("User id=%s is blocked", userId));
        }
        return user;
    }

}
