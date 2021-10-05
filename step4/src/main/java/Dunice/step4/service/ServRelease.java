package Dunice.step4.service;
import Dunice.step4.modelToDo.ToDo;
import Dunice.step4.repos.ToDoRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.text.SimpleDateFormat;
import java.util.*;

public class ServRelease implements Serv{

    SimpleDateFormat datac = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault());
    private final ToDoRepo todoRepository;
    public ServRelease(ToDoRepo todoRepository) {
        this.todoRepository = todoRepository;
    }
    public int countStat(boolean status){
        return todoRepository.countByStatus(status);
    }
    public List countStatP(Page<ToDo> page){
        int ready = todoRepository.countByStatus(true);
        int notReady = todoRepository.countByStatus(false);
        int numberofElements = ready + notReady;
        List list = null;
        list.add(page);
        list.add(ready);
        list.add(notReady);
        list.add(numberofElements);
        return list;
    }
    public ToDo Create(String text) {
        Date date = new Date(System.currentTimeMillis());
        ToDo todo = new ToDo();
        todo.setText(text);
        todo.setStatus(false);
        todo.setCreatedAt(datac.format(date));
        todo.setUpdatedAt(datac.format(date));
        todoRepository.save(todo);
        return todo;
    }
    public Page<ToDo> readAll(Pageable pageable) {
        return todoRepository.findAll(pageable);
    }
    public Page<ToDo> findByActive(boolean status, Pageable pageable) {
        return todoRepository.findAllByStatus(status, pageable);
    }
    public ToDo read(int id) {
        ToDo ptodo = todoRepository.getById(id);
        return ptodo;
    }
    public boolean update(ToDo todo, int id) {
        if (todoRepository.existsById(id)) {
            Date date = new Date(System.currentTimeMillis());
            todo.setId(id);
            todo.setUpdatedAt(datac.format(date));
            todoRepository.save(todo);
            return true;
        }
        return false;
    }
    public boolean updateText(int id, String text) {
        ToDo newtodo = new ToDo();
        if (todoRepository.existsById(id)) {
            Date date = new Date(System.currentTimeMillis());
            newtodo = todoRepository.getById(id);
            newtodo.setText(text);
            newtodo.setUpdatedAt(datac.format(date));
            todoRepository.save(newtodo);
            return true;
        }
        return false;
    }
    public boolean updateStatus(int id) {
        ToDo newtodo = new ToDo();
        if (todoRepository.existsById(id)) {
            Date date = new Date(System.currentTimeMillis());
            newtodo = todoRepository.getById(id);
            newtodo.setStatus(!newtodo.isStatus());
            newtodo.setUpdatedAt(datac.format(date));
            todoRepository.save(newtodo);
            return true;
        }
        return false;
    }
    public boolean updateStatusAll(boolean status) {
        List<ToDo> res = todoRepository.findAllByStatus(status);
        res.forEach((todo) -> todo.setStatus(!todo.isStatus()));
        for (int j = 0; j<res.size(); j++) {
            todoRepository.save(res.get(j));
        }
        return true;
    }
    public boolean delete(int id) {
        if (todoRepository.existsById(id)) {
            todoRepository.deleteById(id);
            return true;
        }
        return false;
    }
    public boolean deleteStat(boolean status) {
        todoRepository.deleteAllByStatus(status);
        return true;
    }

}
