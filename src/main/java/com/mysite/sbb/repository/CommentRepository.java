package com.mysite.sbb.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mysite.sbb.entity.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long>{

}
