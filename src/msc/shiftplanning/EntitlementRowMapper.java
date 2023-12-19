package msc.shiftplanning;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;

public class EntitlementRowMapper implements RowMapper<VacationEntitlement> {
	
	@Override
    public VacationEntitlement mapRow(ResultSet rs, int rowNum) throws SQLException {
		LoggerFactory.getLogger(UserImpl.class).debug("EntitlementRowMapper");

		VacationEntitlement entitlement = new VacationEntitlement();

		entitlement.setUserId(rs.getInt("user_id"));
		entitlement.setYear(rs.getInt("yearNum"));
		entitlement.setStartDate(rs.getDate("start_date"));
		entitlement.setEndDate(rs.getDate("end_date"));
		entitlement.setEntitlement(rs.getInt("entitlement"));
		entitlement.setVactionUsed(rs.getFloat("used_vacation_days"));
		entitlement.setRemainingVacation(rs.getFloat("remaining_vacation"));

        return entitlement;

    }

	
	
}
