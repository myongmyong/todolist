package kr.or.connect.todo.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.connect.todo.domain.TodoDto;
import kr.or.connect.todo.persistence.TodoDao;

@Service
public class TodoService {
	
	private final TodoDao dao;
	
	@Autowired
	public TodoService(TodoDao dao) {
		this.dao = dao;
	}
	
	public Collection<TodoDto> getTodos() {
		return dao.getTodos();
	}
	
	public TodoDto getTodo(Integer id){
		return dao.getTodo(id);
	}

	public Integer insertTodo(TodoDto dto) {
		Integer id = dao.insertTodo(dto);
		return id;
	}

	public Integer updateCompleted(TodoDto dto){
		int affected = dao.updateCompleted(dto);
		return affected;
	}

	public Integer deleteTodo(Integer id){
		int affected = dao.deleteTodo(id);
		return affected;
	}

	public boolean clearCompleted(Integer completed){
		int affected = dao.clearCompleted(completed);
		return affected == 1;
	}	

}
