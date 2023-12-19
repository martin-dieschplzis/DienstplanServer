package msc.shiftplanning;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;

public class MonthTimeAccountMapper implements RowMapper<MonthTimeAccount> {

	
	@Override
    public MonthTimeAccount mapRow(ResultSet rs, int rowNum) throws SQLException {
		LoggerFactory.getLogger(UserImpl.class).debug("MonthTimeAccountMapper");

		MonthTimeAccount account = new MonthTimeAccount();

		account.setUserID(rs.getInt("user_id"));
		account.setMonth(rs.getDate("month"));
		account.setPlanedHours(rs.getFloat("planed_hours_account"));
		account.setActualHours(rs.getFloat("actual_hours_account"));

        return account;

    }

	

	
	
}
