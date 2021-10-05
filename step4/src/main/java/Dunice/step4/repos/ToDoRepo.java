package Dunice.step4.repos;

import Dunice.step4.modelToDo.ToDo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.*;

public interface ToDoRepo extends JpaRepository <ToDo, Integer>, PagingAndSortingRepository <ToDo, Integer> {
    Page<ToDo> findAll(Pageable pageable);
    int countByStatus (boolean status);
    List <ToDo> deleteAllByStatus(boolean status);
    boolean existsByStatus(boolean status);
    Page<ToDo> findAllByStatus(boolean status, Pageable pageable);
    List<ToDo> findAllByStatus(boolean status);
}