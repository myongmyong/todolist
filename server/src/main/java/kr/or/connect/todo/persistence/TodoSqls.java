package kr.or.connect.todo.persistence;

import org.springframework.stereotype.Repository;

@Repository
public class TodoSqls {
	static final String DELETE_BY_ID = "DELETE FROM todo WHERE id= :id";
	static final String GET_TODOS = "SELECT id,todo,completed,date FROM TODO ORDER BY ID ASC";
	static final String GET_TODO = "SELECT id,todo,completed,date FROM TODO WHERE id = :id";
	static final String UPDATE_BY_ID = "UPDATE TODO SET COMPLETED = :completed WHERE ID = :id";
	static final String CLEAR_COMPLETED = "DELETE FROM TODO WHERE COMPLETED = :completed";

}
