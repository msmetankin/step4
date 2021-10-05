package Dunice.step4.service;



import Dunice.step4.modelToDo.ToDo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.*;

public interface Serv {
    ToDo Create (String text);
    Page<ToDo> readAll(Pageable pageable);
    Page<ToDo> findByActive(boolean status, Pageable pageable);
    ToDo read(int id);
    boolean update(ToDo todo, int id);
    boolean updateText(int id, String text);
    boolean updateStatus(int id);
    boolean updateStatusAll(boolean status);
    boolean delete(int id);
    boolean deleteStat(boolean status);
    int countStat(boolean status);

}