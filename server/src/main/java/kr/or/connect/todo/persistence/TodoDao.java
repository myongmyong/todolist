package kr.or.connect.todo.persistence;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import kr.or.connect.todo.domain.TodoDto;

@Repository
public class TodoDao {
	private NamedParameterJdbcTemplate jdbc;
	private SimpleJdbcInsert insertAction;
	private RowMapper<TodoDto> rowMapper = BeanPropertyRowMapper.newInstance(TodoDto.class);
	private TodoSqls sql;
	
	@Autowired
	public TodoDao(DataSource dataSource, TodoSqls sql){
		this.jdbc = new NamedParameterJdbcTemplate(dataSource);
		this.insertAction = new SimpleJdbcInsert(dataSource)
							.withTableName("todo")
							.usingGeneratedKeyColumns("id");
		this.sql = sql;
	}

	public List<TodoDto> getTodos() {
		Map<String, Object> params = Collections.emptyMap();
		return jdbc.query(sql.GET_TODOS, params, rowMapper);
	}
	
	public TodoDto getTodo(Integer id){
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("id", id);
		return jdbc.queryForObject(sql.GET_TODO, params, rowMapper);
	}

	public Integer insertTodo(TodoDto dto) {
		SqlParameterSource params = new BeanPropertySqlParameterSource(dto);
		return insertAction.executeAndReturnKey(params).intValue();
	}

	public int updateCompleted(TodoDto dto){
		SqlParameterSource params = new BeanPropertySqlParameterSource(dto);
		return jdbc.update(sql.UPDATE_BY_ID, params);
	}

	public int deleteTodo(Integer id){
		Map<String,?> params = Collections.singletonMap("id", id);
		return jdbc.update(sql.DELETE_BY_ID,params);
	}

	public int clearCompleted(Integer completed){
		Map<String,?> params = Collections.singletonMap("completed", completed);
		return jdbc.update(sql.CLEAR_COMPLETED,params);
	}		
}
