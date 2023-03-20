package com.example.test_shop.comment.repository;

import com.example.test_shop.comment.model.Comment;
import com.example.test_shop.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Interface of repository for {@link Comment} entity
 *
 * @author DmitrySheyko
 */
public interface CommentRepository extends JpaRepository<Comment, Long> {

    boolean existsByUserIdAndProductId(Long userId, long productId);

    Optional<Comment> findByIdAndUser(Long commentId, User user);

}