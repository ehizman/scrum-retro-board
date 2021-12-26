package com.ehizman.scrumretroboard.comment;

import com.ehizman.scrumretroboard.data.enums.CommentType;
import com.ehizman.scrumretroboard.data.model.Comment;
import com.ehizman.scrumretroboard.data.repository.CommentRepository;
import com.ehizman.scrumretroboard.service.CommentServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.sql.Timestamp;
import java.time.LocalDate;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DataJpaTest
public class CommentServiceTest {
    @Mock
    private CommentRepository commentRepository;
    @InjectMocks
    private CommentServiceImpl commentService;

    @Test
    @DisplayName(("test that all comments made today can be retrieved"))
    void testCanGetAllCommentForToday(){
        Comment comment = new Comment();
        comment.setComment("Comment");
        comment.setCreatedDate(new Timestamp(System.currentTimeMillis()));
        comment.setType(CommentType.PLUS);

        LocalDate today = LocalDate.now();
        List<Comment> comments = List.of(comment);

        when(commentRepository.findByCreatedYearAndMonthAndDay(today.getYear(), today.getMonthValue(), today.getDayOfMonth()))
                .thenReturn(Optional.of(comments));
        List<Comment> commentsForToday = commentService.getAllCommentForToday();
        assertThat(commentsForToday.get(0)).isEqualTo(comment);
        verify(commentRepository, times(1))
                .findByCreatedYearAndMonthAndDay(today.getYear(), today.getMonthValue(), today.getDayOfMonth());
    }
}
