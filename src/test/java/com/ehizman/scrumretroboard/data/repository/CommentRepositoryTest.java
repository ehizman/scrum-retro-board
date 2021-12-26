package com.ehizman.scrumretroboard.data.repository;

import com.ehizman.scrumretroboard.data.enums.CommentType;
import com.ehizman.scrumretroboard.data.model.Comment;
import com.ehizman.scrumretroboard.exception.CommentNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Slf4j
class CommentRepositoryTest {
    private CommentRepository commentRepository;
    private TestEntityManager testEntityManager;
    private DataSource dataSource;

    @Autowired
    public CommentRepositoryTest(DataSource dataSource, TestEntityManager testEntityManager, CommentRepository commentRepository) {
        this.dataSource = dataSource;
        this.testEntityManager = testEntityManager;
        this.commentRepository = commentRepository;
    }


    @Test
    void testConnection(){
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            assertThat(connection).isNotNull();
            assertThat(connection.getCatalog()).isNotNull();
        }
        catch (SQLException e) {
            log.info("Exception --> {}", e.getMessage());
        }
    }

    @Test
    @DisplayName("given the year value, month value and day value, findByCreatedYearAndMonthAndDay should return a comment")
    void findByCreatedYearAndMonthAndDay() throws CommentNotFoundException {
        Comment comment = new Comment();
        comment.setComment("Test");
        comment.setType(CommentType.PLUS);
        comment.setCreatedDate(new Timestamp(System.currentTimeMillis()));

        testEntityManager.persist(comment);
        testEntityManager.flush();

        LocalDate now = LocalDate.now();
        List<Comment> comments = commentRepository.findByCreatedYearAndMonthAndDay
                (now.getYear(), now.getMonthValue(), now.getDayOfMonth()).orElseThrow(()-> new CommentNotFoundException("comment not found"));

        assertThat(comments).hasSize(1);
        assertThat(comments.get(0)).hasFieldOrPropertyWithValue("comment", "Test");
    }
}