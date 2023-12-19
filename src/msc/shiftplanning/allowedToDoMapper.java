package msc.shiftplanning;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;

public class allowedToDoMapper implements RowMapper<AllowedToDo> {

	
	
	@Override
    public AllowedToDo mapRow(ResultSet rs, int rowNum) throws SQLException {
		LoggerFactory.getLogger(UserImpl.class).debug("AllowedToDoMapper");

		AllowedToDo elem = new AllowedToDo();

		elem.setUserID(rs.getInt("user_id"));
		elem.setToDo(rs.getInt("todos_id"));

		return elem;

    }


	
	
}
