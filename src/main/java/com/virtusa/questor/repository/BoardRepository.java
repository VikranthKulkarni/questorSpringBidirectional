package com.virtusa.questor.repository;

import com.virtusa.questor.model.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {

    @Query("select b from Board b where b.project.projectId = :projectId")
    List<Board> findByProjectId(Long projectId);

}