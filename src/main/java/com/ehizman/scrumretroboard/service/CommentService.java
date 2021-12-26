package com.ehizman.scrumretroboard.service;


import com.ehizman.scrumretroboard.data.model.Comment;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CommentService {
    List<Comment> saveAll(List<Comment> comments);
    List<Comment> getAllCommentForToday();
}
