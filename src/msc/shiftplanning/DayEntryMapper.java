package msc.shiftplanning;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;

public class DayEntryMapper implements RowMapper<DayEntry> {

	
	
	@Override
    public DayEntry mapRow(ResultSet rs, int rowNum) throws SQLException {
		LoggerFactory.getLogger(UserImpl.class).debug("DayEntryMapper");

		DayEntry elem = new DayEntry();

		elem.setUserId(rs.getInt("user_id"));
		elem.setDate(rs.getDate("day"));
		elem.setPlanedActivity(rs.getInt("planed_activity_id"));
		elem.setPlanedHours(rs.getFloat("planed_hours"));
		elem.setActualActivity(rs.getInt("actual_activity_id"));
		elem.setStartTime(rs.getTime("start_date"));
		elem.setEndTime(rs.getTime("end_date"));
		elem.setActualHours(rs.getFloat("actual_hours"));

        return elem;

    }


	
	
}
