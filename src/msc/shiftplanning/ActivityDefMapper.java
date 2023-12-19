package msc.shiftplanning;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;

public class ActivityDefMapper implements RowMapper<ActivityDef> {

	
	
	
	@Override
    public ActivityDef mapRow(ResultSet rs, int rowNum) throws SQLException {
		LoggerFactory.getLogger(UserImpl.class).debug("ActivityDefMapper");

		ActivityDef elem = new ActivityDef();

		elem.setActivityID(rs.getInt("activity_id"));
		elem.setName(rs.getString("name"));
		elem.setUsage(rs.getNString("default_hours"));
		elem.setFactor(rs.getFloat("factor"));
		elem.setColour(rs.getString("colour"));
		elem.setActivityClass(rs.getString("class"));
		elem.setOrder(rs.getInt("order"));

		return elem;

    }


	
	
}
