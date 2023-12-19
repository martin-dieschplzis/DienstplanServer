package msc.shiftplanning;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;

public class ContractRowMapper implements RowMapper<Contract> {
	
	
	@Override
    public Contract mapRow(ResultSet rs, int rowNum) throws SQLException {
		LoggerFactory.getLogger(UserImpl.class).debug("UserRowMapper");

		Contract contract = new Contract();

		contract.setUserId(rs.getInt("user_id"));
		contract.setStartDate(rs.getDate("start_date"));
		contract.setEndDate(rs.getDate("end_date"));
		contract.setVacationentitlementYear(rs.getInt("days_vacation"));
		contract.setHoursToWork(rs.getInt("hours_working"));
		contract.setWorkingRole(rs.getInt("working_role_id"));

        return contract;

    }


}
