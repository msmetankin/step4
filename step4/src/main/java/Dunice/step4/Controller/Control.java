package Dunice.step4.Controller;


import Dunice.step4.modelToDo.ToDo;
import Dunice.step4.respo.RespoHand;
import Dunice.step4.service.Serv;
import Dunice.step4.val.MapValidationErrorService;
import Dunice.step4.val.ToDoVal;
import lombok.AllArgsConstructor;
import net.minidev.json.JSONObject;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.*;
import org.springframework.http.*;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.*;
@AllArgsConstructor
@org.springframework.stereotype.Controller
@CrossOrigin
@RequestMapping("/api/v1")
public class Control {
    @Autowired
    private final Serv todoService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    @PostMapping(value = "/todo", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> create( @RequestBody ToDo todo, BindingResult result) {
        try {
            ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
            ToDo newtodo = todoService.Create(todo.getText());
            if (errorMap != null) return errorMap;
            return RespoHand.generateResponse("Successfully added data!", HttpStatus.OK, newtodo);
        }
        catch (Exception e){
            return RespoHand.generateResponse(e.getMessage(), HttpStatus.MULTI_STATUS, null);
        }
    }

    @GetMapping("/todo/status")
    public ResponseEntity<Object> getTodoByStatus() {
        try {
            Integer countedT = todoService.countStat(true);
            Integer countedF = todoService.countStat(false);
            return RespoHand.generateResponse("Counted ready/notReady:", HttpStatus.OK, countedF);
        }
        catch (Exception e) {
            return RespoHand.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, null);
        }
    }

    @GetMapping(value = "/todo", params = {"page", "perPage"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> read(@RequestParam("page") int page, @RequestParam("perPage") int perPage) {
        try {
            int ready = todoService.countStat(true);
            int notReady = todoService.countStat(false);
            int numberOfElements = ready + notReady;
            Pageable pageable = PageRequest.of(page-1, perPage, Sort.by("id").ascending());
            Page<ToDo> todo = todoService.readAll(pageable);
            Map<String, Object> map = new HashMap<>();
            map.put("content", todo.getContent());
            map.put("ready", ready);
            map.put("numberOfElements", numberOfElements);
            map.put("notReady", notReady);
            return RespoHand.generateResponse2(HttpStatus.OK, map);
        }
        catch (Exception e){
            return RespoHand.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, null);
        }
    }

    @GetMapping(value = "/todo", params = {"page", "perPage", "status"})
    public ResponseEntity<Object> read(@RequestParam("page") int page, @RequestParam("perPage") int perPage, @RequestParam("status") Boolean status) {
        try {
            int ready = todoService.countStat(true);
            int notReady = todoService.countStat(false);
            int numberOfElements = ready + notReady;
            Pageable pageable = PageRequest.of(page-1, perPage, Sort.by("id").ascending());
            Page<ToDo> todo = todoService.findByActive(status, pageable);
            Map<String, Object> map = new HashMap<>();
            map.put("content", todo.getContent());
            map.put("ready", ready);
            map.put("numberOfElements", numberOfElements);
            map.put("notReady", notReady);
            return RespoHand.generateResponse2(HttpStatus.OK, map);
        }
        catch (Exception e){
            return RespoHand.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, null);
        }
    }

    private ToDoVal convertToToDTO(ToDo todo) {
        ToDoVal todoDTO = modelMapper.map(todo, ToDoVal.class);
        todoDTO.setText(todo.getText());
        todoDTO.setCreatedat(todo.getCreatedAt());
        todoDTO.setId(todo.getId());
        todoDTO.setUpdatedat(todo.getUpdatedAt());
        todoDTO.setStatus(todo.isStatus());
        return todoDTO;
    }

    @PatchMapping(value = "/todo/{id}")
    public ResponseEntity<?> update(@PathVariable(name = "id") int id, @Validated(ToDo.Update.class) @RequestBody ToDo todo) {
        try {
            String s = todoService.read(id).getText();
            final boolean updated = todoService.update(todo, id);
            return RespoHand.generateResponse("Successfully patched data!", HttpStatus.OK, updated);
        }
        catch (Exception e) {
            return RespoHand.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, null);
        }
    }

    @PatchMapping(value = "/todo/text/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateText(@PathVariable(name = "id") int id, @RequestBody JSONObject text) {
        try {
            JSONObject json = text;
            final boolean updated = todoService.updateText(id, json.getAsString("text"));
            return RespoHand.generateResponse("Successfully patched text!", HttpStatus.OK, updated);
        }
        catch (Exception e) {
            return RespoHand.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, null);
        }
    }

    @PatchMapping(value = "/todo/status/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateStatus(@PathVariable(name = "id") int id) {
        try {
            String s = todoService.read(id).getText();
            final boolean updated = todoService.updateStatus(id);
            return RespoHand.generateResponse("Successfully patched status!", HttpStatus.OK, updated);
        }
        catch (Exception e){
            return RespoHand.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, null);
        }
    }

    @PatchMapping(value = "/todo", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateStatusAll(@RequestBody JSONObject status) {
        try {
            boolean tr = status.containsValue(false);
            final boolean updated = todoService.updateStatusAll(tr);
            return RespoHand.generateResponse("Successfully patched status!", HttpStatus.OK, updated);
        }
        catch (Exception e){
            return RespoHand.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, null);
        }
    }

    @DeleteMapping(value = "/todo/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") int id) {
        try {
            final boolean deleted = todoService.delete(id);
            return RespoHand.generateResponse("Successfully deleted selected data!", HttpStatus.OK, deleted);
        }
        catch (Exception e){
            return RespoHand.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, null);
        }
    }
    @Transactional
    @DeleteMapping(value = "/todo")
    public ResponseEntity<?> deleteStat() {
        try {
            final boolean deleted = todoService.deleteStat(true);
            return RespoHand.generateResponse("Successfully deleted completed ToDos!", HttpStatus.OK, deleted);
        }
        catch (Exception e) {
            return RespoHand.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, null);
        }
    }
}