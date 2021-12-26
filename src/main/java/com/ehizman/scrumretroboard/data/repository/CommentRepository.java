package com.ehizman.scrumretroboard.data.repository;

import java.util.List;
import java.util.Optional;

import com.ehizman.scrumretroboard.data.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query("SELECT c FROM Comment c WHERE year (c.createdDate) = ?1 AND " +
            "month (c.createdDate) = ?2 AND day (c.createdDate) = ?3")
    Optional<List<Comment>> findByCreatedYearAndMonthAndDay(int year, int month, int day);

}
