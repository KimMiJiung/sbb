package com.mysite.sbb.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mysite.sbb.entity.Answer;
import com.mysite.sbb.entity.Comment;
import com.mysite.sbb.entity.Question;
import com.mysite.sbb.entity.SiteUser;
import com.mysite.sbb.repository.CommentRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CommentService {
	
	@Autowired
	private final CommentRepository commentRepository;
	
    public Comment create(Question question, Answer answer, SiteUser author, String content) {
        Comment c = new Comment();
        c.setContent(content);
        c.setCreateDate(LocalDateTime.now());
        if (question != null) {
            c.setQuestion(question);
        }

        if (answer != null) {
        	c.setAnswer(answer);
        }
        c.setAuthor(author);
        c = this.commentRepository.save(c);
        return c;
    }

    public Optional<Comment> getComment(Long id) {
        return this.commentRepository.findById(id);
    }

    public Comment modify(Comment c, String content) {
        c.setContent(content);
        c.setModifyDate(LocalDateTime.now());
        c = this.commentRepository.save(c);
        return c;
    }

    public void delete(Comment c) {
        this.commentRepository.delete(c);
    }	
}
