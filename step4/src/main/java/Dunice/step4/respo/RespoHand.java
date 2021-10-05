package Dunice.step4.respo;

import Dunice.step4.repos.ToDoRepo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.HashMap;
import java.util.Map;

public class RespoHand {

    private ToDoRepo todoRepository;

    public void ServRelease(ToDoRepo todoRepository) {
        this.todoRepository = todoRepository;
    }

    public static ResponseEntity<Object> generateResponse(String message, HttpStatus status, Object responseObj) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("message", message);
        map.put("status", status.value());
        map.put("data", responseObj);
        return new ResponseEntity<Object>(map,status);
    }

    public static ResponseEntity<Object> generateResponse1(HttpStatus status, Object responseObj, int ready, int numberOfElements, int notReady)  {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("status", status.value());
        map.put("data", responseObj);
        map.put("ready", ready);
        map.put("numberOfElements", numberOfElements);
        map.put("notReady", notReady);
        return new ResponseEntity<Object>(map,status);
    }

    public static ResponseEntity<Object> generateResponse2(HttpStatus status, Object responseObj)  {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("status", status.value());
        map.put("data", responseObj);
        return new ResponseEntity<Object>(map,status);
    }
}