package com.ehizman.scrumretroboard.controller;

import com.ehizman.scrumretroboard.data.enums.CommentType;
import com.ehizman.scrumretroboard.data.model.Comment;
import com.ehizman.scrumretroboard.exception.CommentNotFoundException;
import com.ehizman.scrumretroboard.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@Slf4j
public class CommentController {
    private CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping
    public String index(Model model){
        model.addAttribute("time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        List<Comment> comments = new ArrayList<>();
        try{
            comments = commentService.getAllCommentForToday();
        }
        catch (CommentNotFoundException exception){
            log.info("exception --> {}", exception.getMessage());
            model.addAttribute("message", exception.getMessage());
            return "error";
        }
        Map<CommentType, List<Comment>> groupedComments = comments.stream()
                .collect(Collectors.groupingBy(Comment::getType));
        model.addAttribute("starComments", groupedComments.get(CommentType.STAR));
        model.addAttribute("deltaComments", groupedComments.get(CommentType.DELTA));
        model.addAttribute("plusComments", groupedComments.get(CommentType.PLUS));
        return "comment";
    }

    @PostMapping("/comment")
    public String createComment(@RequestParam(name = "plusComment", required = false) String plusComment,
                                @RequestParam(name = "starComment", required = false) String starComment,
                                @RequestParam(name = "deltaComment", required = false) String deltaComment){
        List<Comment> comments = new ArrayList<>();
        if(plusComment != null){
            if (!plusComment.trim().equals("")){
                comments.add(getComment(plusComment, CommentType.PLUS));
            }
        }
        if(starComment != null){
            if (!starComment.trim().equals("")){
                comments.add(getComment(starComment, CommentType.STAR));
            }
        }
        if (deltaComment != null){
            if (!deltaComment.trim().equals("")){
                comments.add(getComment(deltaComment, CommentType.DELTA));
            }
        }

        if (!comments.isEmpty()){
            log.info("Saved --> {}", commentService.saveAll(comments));
        }
        return "redirect:/";
    }

    private Comment getComment(String comment, CommentType commentType) {
        Comment commentObject = new Comment();
        commentObject.setComment(comment);
        commentObject.setType(commentType);
        return commentObject;
    }
}
