package com.example.test_shop.comment.repository;

import com.example.test_shop.comment.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    boolean existsByUserIdAndProductId(Long userId, long productId);

}
