package kr.or.connect.todo.api;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import kr.or.connect.todo.domain.TodoDto;
import kr.or.connect.todo.service.TodoService;

@RestController
@RequestMapping("/api/todos")
public class TodoController {

	private final TodoService service;

	@Autowired
	public TodoController(TodoService service) {
		this.service = service;
	}

	@GetMapping("/getTodos")
	public Collection<TodoDto> getTodos() {
		
		return service.getTodos();
	}
	
	@PostMapping("/insertTodo")
	@ResponseBody
	public TodoDto insertTodo(@RequestBody HashMap<String, String> req) throws JsonParseException, JsonMappingException, IOException {
		
		String todo = req.get("todo");
		Date date = new Date();
		
		TodoDto dto = new TodoDto();
		dto.setTodo(todo);
		dto.setDate(date);

		
		int id = service.insertTodo(dto);
		TodoDto content = service.getTodo(id);
		
	    return content;

	}

	@PutMapping("/updateCompleted")
	public int updateCompleted(@RequestBody HashMap<String,Integer> req) throws JsonParseException, JsonMappingException, IOException {

		TodoDto dto = new TodoDto();		
		dto.setId(req.get("id"));
		dto.setCompleted(req.get("completed"));

		int result = service.updateCompleted(dto);

		return result;

	}

	@DeleteMapping("/deleteTodo")
	public int deleteTodo(@RequestBody HashMap<String,Integer> req){

		int result = service.deleteTodo(req.get("id"));	

		return result;
	}

	@DeleteMapping("/clearCompleted")
	public void clearCompleted(){
		int completed = 1;
		service.clearCompleted(completed);	
	}
}
