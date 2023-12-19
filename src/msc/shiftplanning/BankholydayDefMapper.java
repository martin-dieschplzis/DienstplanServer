package msc.shiftplanning;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;

public class BankholydayDefMapper implements RowMapper<BankholydayDef> {

	
	
	@Override
    public BankholydayDef mapRow(ResultSet rs, int rowNum) throws SQLException {
		LoggerFactory.getLogger(UserImpl.class).debug("BankholydayDefMapper");

		BankholydayDef elem = new BankholydayDef();

		elem.setName(rs.getString("name"));
		elem.setFixDate(rs.getDate("fix_date"));
		elem.setOffset(rs.getInt("offset"));
		elem.setReferenceOffset(rs.getString("reference_to_offset"));

        return elem;

    }

	
}
