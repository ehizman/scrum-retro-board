package com.ehizman.scrumretroboard.controller;

import com.ehizman.scrumretroboard.data.enums.CommentType;
import com.ehizman.scrumretroboard.data.model.Comment;
import com.ehizman.scrumretroboard.exception.CommentNotFoundException;
import com.ehizman.scrumretroboard.service.CommentService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.sql.Timestamp;
import java.util.Arrays;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

@WebMvcTest(CommentController.class)
public class CommentControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CommentService commentService;

    @Test
    @DisplayName("should return a Status 302")
    void saveComments() throws Exception {
        ResultActions resultActions = mockMvc.perform(post("/comment").with(csrf())
                .with(user("ehizman").roles("USER")).param("plusComment", "Test Plus"));

        resultActions
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        verify(commentService, times(1)).saveAll(anyList());
        verifyNoMoreInteractions(commentService);
    }

    @Test
    @DisplayName("should return a Status 200")
    void getComments() throws Exception {
        Comment comment = new Comment();
        comment.setComment("Test Plus");
        comment.setType(CommentType.PLUS);
        comment.setCreatedBy("Ehis Edemakhiota");
        comment.setCreatedDate(new Timestamp(System.currentTimeMillis()));

        Comment comment2 = new Comment();
        comment2.setComment("Test Star");
        comment2.setType(CommentType.STAR);
        comment2.setCreatedBy("Eseosa Edemakhiota");
        comment2.setCreatedDate(new Timestamp(System.currentTimeMillis()));

        Comment comment3 = new Comment();
        comment3.setComment("Test Delta");
        comment3.setType(CommentType.DELTA);
        comment3.setCreatedBy("Nosa Edemakhiota");
        comment3.setCreatedDate(new Timestamp(System.currentTimeMillis()));

        List<Comment> commentList = Arrays.asList(comment2, comment3, comment);
        when(commentService.getAllCommentForToday()).thenReturn(commentList);
        ResultActions resultActions = mockMvc.perform(get("/").with(user("Ehis Edemakhiota").roles("USER")));

        resultActions.andExpect(status().isOk())
                .andExpect(view().name("comment"))
                .andExpect(model().attribute("plusComments", hasSize(1)))
                .andExpect(model().attribute("plusComments", hasItem(
                        allOf(
                                hasProperty("createdBy", is("Ehis Edemakhiota")),
                                hasProperty("comment", is("Test Plus"))
                        )
                )))
                .andExpect(model().attribute("starComments", hasSize(1)))
                .andExpect(model().attribute("starComments", hasItem(
                        allOf(
                                hasProperty("createdBy", is("Eseosa Edemakhiota")),
                                hasProperty("comment", is("Test Star"))
                        )
                )))
                .andExpect((model().attribute("deltaComments", hasSize(1))))
                .andExpect(model().attribute("deltaComments", hasItem(
                        allOf(
                                hasProperty("createdBy", is("Nosa Edemakhiota")),
                                hasProperty("comment", is("Test Delta"))
                        )
                )));

        verify(commentService, times(1)).getAllCommentForToday();
        verifyNoMoreInteractions(commentService);
    }
}
