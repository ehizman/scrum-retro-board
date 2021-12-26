package com.ehizman.scrumretroboard.service;

import com.ehizman.scrumretroboard.data.model.Comment;
import com.ehizman.scrumretroboard.data.repository.CommentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
public class CommentServiceImpl implements CommentService{
    private CommentRepository commentRepository;
    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository){
        this.commentRepository = commentRepository;
    }

    @Transactional(rollbackOn = Exception.class)
    @Override
    public List<Comment> saveAll(List<Comment> comments) {
        log.info("Saving --> {}",comments);
        return commentRepository.saveAll(comments);
    }

    @Override
    public List<Comment> getAllCommentForToday() {
        LocalDate today = LocalDate.now();
        return commentRepository.findByCreatedYearAndMonthAndDay(today.getYear(), today.getMonthValue(), today.getDayOfMonth())
                .orElseThrow(()-> new ClassCastException("comment not found"));
    }
}
