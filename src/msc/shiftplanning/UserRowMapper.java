package msc.shiftplanning;

import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;


import java.sql.ResultSet;
import java.sql.SQLException;




public class UserRowMapper implements RowMapper<User>  {
	
	
	@Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
		LoggerFactory.getLogger(UserImpl.class).debug("UserRowMapper");

		User user = new User();

		user.setUserID(rs.getInt("user_id"));
		user.setUserName(rs.getString("user_account"));
		user.setPassword(rs.getString("user_password"));
		user.setDisplayName(rs.getString("user_display_name"));

        return user;

    }


}
