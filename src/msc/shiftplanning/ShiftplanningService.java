package msc.shiftplanning;

import java.sql.Date;
import java.time.LocalTime;

import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;




public interface ShiftplanningService  {

	
	public Response  login(User credential);
	

	public Response  logout(String cockieValue);

	
	public Response  addUser(User credential, String cockieValue);

	
	public Response  addUser(int userID, String cockieValue);

	
	public Response changeOwnPwd(String oldPwd, String newPwd, String cockieValue);
	

	public Response  getActUser(String cockieValue);
	

	public Response  checkUserName(int userID, String userName, String cockieValue);
	

	public Response  getListAllUsers(String cockieValue);
	
	
	public Response getListOfPerson(String listDate, String cockieValue);
	

	public Response  getPerson(int userID, String cockieValue);
	

	public Response  changePerson(User credential, String cockieValue);
	
	
	public Response getContract(int userID, java.util.Date start, String cockieValue);
	

	public Response getContracts(int userID, java.util.Date start, java.util.Date end, String cockieValue);

	
	public Response addContract(Contract contract, String cockieValue);
	
	
	public Response updateContract(Contract contract, String cockieValue);

	
	public Response deleteContract(Contract contract, String cockieValue);
	

	public Response saveDayActivity(DayEntry dayEntry, String cockieValue);
	
	
	public Response savePeriodActivity(int userId, java.util.Date start, java.util.Date end, int activityId, String cockieValue);
	
	
	public Response getListActivitiesOfMonth(int userId, java.util.Date start, String cockieValue);
	
	
	public Response getListAllowedToDos(String cockieValue);


	
	
	public String test();
	

	
}
