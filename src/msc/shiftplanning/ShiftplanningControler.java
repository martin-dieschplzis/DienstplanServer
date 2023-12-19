package msc.shiftplanning;


import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import javax.ws.rs.Consumes;
import javax.ws.rs.CookieParam;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.catalina.SessionIdGenerator;
import org.apache.commons.codec.binary.Base64;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;






@Path("/dienstplan")
//@Consumes(MediaType.APPLICATION_XML)
@Consumes( {MediaType.APPLICATION_JSON} )
@Produces( {MediaType.APPLICATION_JSON} )
public class ShiftplanningControler implements ShiftplanningService {

	
	private static String cockieRequestPath = "/";
	private static String cockieRequestDomain = "";
	private static final String cockieName = "Dienstplan";
	
	
	
	public static enum Action {
		USERADMIN(1), CONTRACTADMIN(2), SHIFTPLANNING(3);
		
		private int actionKey;
		
		public int getActionKey() {
			return this.actionKey;
		}
		
		
		private Action(int actionKey) {
			this.actionKey = actionKey;
		}
	}
	
	

	@Context
	Application application;
	
	
	@Override
	@POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
	//@Produces("application/json")
	public Response login(User credential) {
		LoggerFactory.getLogger(ShiftplanningControler.class).info("login start ....");
		UserImpl userAdministration = new UserImpl();
		String jsonString; 
		
		UserImpl loginUser = userAdministration.login(credential);
		if (loginUser != null) {
			LoggerFactory.getLogger(ShiftplanningControler.class).info("login ok");
			LoggerFactory.getLogger(ShiftplanningControler.class).info("UserID: " + loginUser.getUserID());
			jsonString = new JSONObject()
				            .put("User_ID", loginUser.getUserID())
				            .put("User_Account", loginUser.getUserName())
				            .put("User_Password", "")
				            .put("User_Display_Name", loginUser.getDisplayName())
				            .toString();
			

			NewCookie cockie = new NewCookie(cockieName, loginUser.getSessionID(), cockieRequestPath, cockieRequestDomain, "for shiftplanning", 10000000, false, true);
			return Response.ok(jsonString).cookie(cockie).build();
		} else {
			LoggerFactory.getLogger(ShiftplanningControler.class).info("login faild");
			jsonString = new JSONObject()
		            .put("User_ID", 0)
		            .put("User_Account", "")
		            .put("User_Password", "")
		            .put("User_Display_Name", "")
		            .toString();
		}

		return Response.status(Status.UNAUTHORIZED).entity(jsonString).build(); 
	}
	
	
	
	
	@Override
	@GET
    @Path("/getActUser")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
	//@Produces("application/json")
	public Response getActUser(@CookieParam(cockieName) String cockieValue) {
		LoggerFactory.getLogger(ShiftplanningControler.class).info("getActUser start ....");
		UserImpl userAdministration = new UserImpl();
		String jsonString; 
		
		if (!userAdministration.findSession(cockieValue)) {
			LoggerFactory.getLogger(ShiftplanningControler.class).info("session exipired");
			jsonString = new JSONObject()
		            .put("Result", "faild")
		            .put("Comment", "session expired")
		            .put("Contracts", "")
		            .toString();
			return Response.status(Status.UNAUTHORIZED).entity(jsonString).build();
		}
		
		if (userAdministration != null) {
			LoggerFactory.getLogger(ShiftplanningControler.class).info("user still ok");
			LoggerFactory.getLogger(ShiftplanningControler.class).info("UserID: " + userAdministration.getUserID());
			jsonString = new JSONObject()
				            .put("User_ID", userAdministration.getUserID())
				            .put("User_Account", userAdministration.getUserName())
				            .put("User_Password", "")
				            .put("User_Display_Name", userAdministration.getDisplayName())
				            .toString();
			

			return Response.ok(jsonString).build();
		} else {
			LoggerFactory.getLogger(ShiftplanningControler.class).info("login faild");
			jsonString = new JSONObject()
		            .put("User_ID", 0)
		            .put("User_Account", "")
		            .put("User_Password", "")
		            .put("User_Display_Name", "")
		            .toString();
		}

		return Response.status(Status.UNAUTHORIZED).entity(jsonString).build(); 
	}
	
	
	
	
	
	@Override
	@POST
    @Path("/logout")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
	public Response  logout(@CookieParam(cockieName) String cockieValue) {
		LoggerFactory.getLogger(ShiftplanningControler.class).info("logout");
		UserImpl userAdministration = new UserImpl();
		String jsonString; 
	
		userAdministration.logout(cockieValue);
		LoggerFactory.getLogger(ShiftplanningControler.class).info("logout ");
		jsonString = new JSONObject()
	            .put("Result", "faild")
	            .put("Comment", "logout: session expired")
	            .put("logout", "")
	            .toString();
		return Response.status(Status.UNAUTHORIZED).entity(jsonString).build();
	}
	
	
	
	
	@Override
	@POST
    @Path("/adduser")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
	public Response  addUser(User credential, 
			                 @CookieParam(cockieName) String cockieValue) {
		String jsonString = null;; 
		
		LoggerFactory.getLogger(ShiftplanningControler.class).info("login start ....");
		UserImpl userAdministration = new UserImpl();
		
		if (!userAdministration.findSession(cockieValue)) {
			LoggerFactory.getLogger(ShiftplanningControler.class).info("session not exists or not authorized");
			jsonString = new JSONObject()
		            .put("User_ID", 0)
		            .put("User_Account", "")
		            .put("User_Password", "")
		            .put("User_Display_Name", "")
		            .toString();
			return Response.status(Status.UNAUTHORIZED).entity(jsonString).build();
		}
		if (!userAdministration.checkToDo(Action.USERADMIN.getActionKey())) {
			LoggerFactory.getLogger(ShiftplanningControler.class).info("session not exists or not authorized");
			jsonString = new JSONObject()
		            .put("User_ID", 0)
		            .put("User_Account", "")
		            .put("User_Password", "")
		            .put("User_Display_Name", "")
		            .toString();
			return Response.status(Status.FORBIDDEN).entity(jsonString).build();
		}
		
		User loginUser = userAdministration.add(credential);
		
		if (loginUser != null) {
			LoggerFactory.getLogger(ShiftplanningControler.class).info("add user ok");
			LoggerFactory.getLogger(ShiftplanningControler.class).info("UserID: " + loginUser.getUserID());
			jsonString = new JSONObject()
				            .put("User_ID", loginUser.getUserID())
				            .put("User_Account", loginUser.getUserName())
				            .put("User_Password", "")
				            .put("User_Display_Name", loginUser.getDisplayName())
				            .toString();
			return Response.ok(jsonString).build();
		} 
		
		LoggerFactory.getLogger(ShiftplanningControler.class).info("create new user faild faild");
		jsonString = new JSONObject()
	            .put("User_ID", 0)
	            .put("User_Account", "")
	            .put("User_Password", "")
	            .put("User_Display_Name", "")
	            .toString();
		
		return Response.status(Status.FORBIDDEN).entity(jsonString).build();
	}


	
	@Override
	@POST
    @Path("/deluser")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
	public Response  addUser(@HeaderParam("userId") int userId, 
			                 @CookieParam(cockieName) String cockieValue) {

		String jsonString = null;; 
		
		LoggerFactory.getLogger(ShiftplanningControler.class).info("delete start ....");
		DayEntryhandling dataEntryAdministration = new DayEntryhandling();
		HoursAccountHandling hourAccountAdministration = new HoursAccountHandling();
		EntitlementHandling entitlementAdministration = new EntitlementHandling();
		AllowedToDoHandling allowedToDoAdminsitration = new AllowedToDoHandling();
		ContractHandling contratAdministration = new ContractHandling();
		UserImpl userAdministration = new UserImpl();
		
		if (!userAdministration.findSession(cockieValue)) {
			LoggerFactory.getLogger(ShiftplanningControler.class).info("session not exists or not authorized");
			jsonString = new JSONObject()
		            .put("User_ID", 0)
		            .put("User_Account", "")
		            .put("User_Password", "")
		            .put("User_Display_Name", "")
		            .toString();
			return Response.status(Status.UNAUTHORIZED).entity(jsonString).build();
		}
		if (!userAdministration.checkToDo(Action.USERADMIN.getActionKey())) {
			LoggerFactory.getLogger(ShiftplanningControler.class).info("session not exists or not authorized");
			jsonString = new JSONObject()
		            .put("User_ID", 0)
		            .put("User_Account", "")
		            .put("User_Password", "")
		            .put("User_Display_Name", "")
		            .toString();
			return Response.status(Status.FORBIDDEN).entity(jsonString).build();
		}
		
		if (dataEntryAdministration.deleteAllEntriesOfUser(userId)) {
			if (hourAccountAdministration.deleteAllAccount(userId)) {
				if (entitlementAdministration.deleteAllEntitlement(userId)) {
					if (allowedToDoAdminsitration.del(userId)) {
						if (contratAdministration.delete(userId)) {
							if (userAdministration.del(userId)) {
								LoggerFactory.getLogger(ShiftplanningControler.class).info("del user ok");
								LoggerFactory.getLogger(ShiftplanningControler.class).info("UserID: " + userId);
								jsonString = new JSONObject()
									            .put("User_ID", userId)
									            .put("User_Account", "")
									            .put("User_Password", "")
									            .put("User_Display_Name", "")
									            .toString();
								return Response.ok(jsonString).build();
							} 
						}
					}
				}
			}
		}
		
		
		
		LoggerFactory.getLogger(ShiftplanningControler.class).info("delete user faild faild");
		jsonString = new JSONObject()
	            .put("User_ID", 0)
	            .put("User_Account", "")
	            .put("User_Password", "")
	            .put("User_Display_Name", "")
	            .toString();
		
		return Response.status(Status.FORBIDDEN).entity(jsonString).build();
	}

	
	
	
	@Override
	@POST
    @Path("/changeownpwd")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
	public Response changeOwnPwd(@HeaderParam("oldpwd") String oldPwd, 
			                   @HeaderParam("newpwd") String newPwd, 
			                   @CookieParam(cockieName) String cockieValue) {
		String jsonString; 

		LoggerFactory.getLogger(ShiftplanningControler.class).info("change own password change start ....");
		UserImpl userAdministration = new UserImpl();
		if (!userAdministration.findSession(cockieValue) ) {
			LoggerFactory.getLogger(ShiftplanningControler.class).info("session not exists");
			jsonString = new JSONObject()
		            .put("Result", "faild")
		            .put("Comment", "session expired")
		            .toString();
			return Response.status(Status.UNAUTHORIZED).entity(jsonString).build();
		}

		if (userAdministration.changeOwnPwd(oldPwd, newPwd)) {
			LoggerFactory.getLogger(ShiftplanningControler.class).info("password is changed to " + newPwd + " for " + userAdministration.getUserName());
			jsonString = new JSONObject()
		            .put("Result", "ok")
		            .put("Comment", "password changed")
		            .toString();Response.ok(jsonString).build();
		} else {
			LoggerFactory.getLogger(ShiftplanningControler.class).info("password changed failt fo user " + userAdministration.getUserName());
			jsonString = new JSONObject()
		            .put("Result", "faild")
		            .put("Comment", "password not changed")
		            .toString();
		}
		return Response.status(Status.FORBIDDEN).entity(jsonString).build();
	}
	
	
	
	@Override
	@GET
    @Path("/getValidUsers")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
	public Response getListOfPerson(@HeaderParam("RequestDate") String listDate, 
			                        @CookieParam(cockieName) String cockieValue) {
		String jsonString; 
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        Date date = null;
		try {
			date = new java.sql.Date(dateFormat.parse(listDate).getTime());;
		} catch (ParseException e) {
			e.printStackTrace();
		}

		
		UserImpl userAdministration = new UserImpl();
		if (!userAdministration.findSession(cockieValue)) {
			LoggerFactory.getLogger(ShiftplanningControler.class).info("session exipired");
			jsonString = new JSONObject()
		            .put("Result", "faild")
		            .put("Comment", "session expired")
		            .put("Contracts", "")
		            .toString();
			return Response.status(Status.UNAUTHORIZED).entity(jsonString).build();
		}
		if (!userAdministration.checkToDo(Action.CONTRACTADMIN.getActionKey())) {
			LoggerFactory.getLogger(ShiftplanningControler.class).info("not authorized");
			jsonString = new JSONObject()
		            .put("Result", "faild")
		            .put("Comment", "not authorized")
		            .put("Contracts", "")
		            .toString();
			return Response.status(Status.FORBIDDEN).entity(jsonString).build();
		}
		
		UserImpl userHandling = new UserImpl();
		List<User> listOfUsers = userHandling.readAllValidUser(date);
		JSONArray arrayObject = new JSONArray();
		for (User elem : listOfUsers) {
			arrayObject.put(new JSONObject()
    				.put("userID", elem.getUserID())
    				.put("username", elem.getUserName())
    				.put("displayname", elem.getDisplayName())
       			    );
		}
		
		jsonString = new JSONObject()
	            .put("Result", "ok")
	            .put("Comment", "get list of all valid user")
	            .put("listDays", arrayObject)
	            .toString();
		return Response.ok(jsonString).build();
	}

	

	

	@Override
	@GET
    @Path("/getUser")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
	public Response  getPerson(@HeaderParam("userId") int userId,
                               @CookieParam(cockieName) String cockieValue) {

		LoggerFactory.getLogger(ShiftplanningControler.class).info("getActUser start ....");
		UserImpl userAdministration = new UserImpl();
		String jsonString; 
		
		if (!userAdministration.findSession(cockieValue)) {
			LoggerFactory.getLogger(ShiftplanningControler.class).info("session exipired");
			jsonString = new JSONObject()
		            .put("Result", "faild")
		            .put("Comment", "session expired")
		            .put("Contracts", "")
		            .toString();
			return Response.status(Status.UNAUTHORIZED).entity(jsonString).build();
		}
		

		UserImpl userHandling = new UserImpl();
		User user = userHandling.getOneUser(userId);
		jsonString = new JSONObject()
	            .put("User_ID", user.getUserID())
	            .put("User_Account", user.getUserName())
	            .put("User_Password", "")
	            .put("User_Display_Name", user.getDisplayName())
	            .toString();


		return Response.ok(jsonString).build();
	}
	

	
	

	

	@Override
	@GET
    @Path("/checkUserName")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
	public Response checkUserName(@HeaderParam("userId") int userID,
			                      @HeaderParam("userName") String userName,
                                  @CookieParam(cockieName) String cockieValue) {
		LoggerFactory.getLogger(ShiftplanningControler.class).info("check Username start ....");
		UserImpl userAdministration = new UserImpl();
		String jsonString; 
		
		if (!userAdministration.findSession(cockieValue)) {
			LoggerFactory.getLogger(ShiftplanningControler.class).info("session exipired");
			jsonString = new JSONObject()
		            .put("Result", "faild")
		            .put("Comment", "session expired")
		            .put("Contracts", "")
		            .toString();
			return Response.status(Status.UNAUTHORIZED).entity(jsonString).build();
		}
		

		UserImpl userHandling = new UserImpl();
		User user = userHandling.checkUserName(userID, userName);
		if (user == null) {
			jsonString = new JSONObject()
		            .put("User_ID", "")
		            .put("User_Account", "")
		            .put("User_Password", "")
		            .put("User_Display_Name", "")
		            .toString();
		} else {
			jsonString = new JSONObject()
		            .put("User_ID", user.getUserID())
		            .put("User_Account", user.getUserName())
		            .put("User_Password", "")
		            .put("User_Display_Name", user.getDisplayName())
		            .toString();
		}

		return Response.ok(jsonString).build();
		
	}
	
	
	
	@Override
	@GET
    @Path("/getAllUsers")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
	public Response getListAllUsers(@CookieParam(cockieName) String cockieValue) {
		String jsonString; 
		
		UserImpl userAdministration = new UserImpl();
		if (!userAdministration.findSession(cockieValue)) {
			LoggerFactory.getLogger(ShiftplanningControler.class).info("session exipired");
			jsonString = new JSONObject()
		            .put("Result", "faild")
		            .put("Comment", "session expired")
		            .put("Contracts", "")
		            .toString();
			return Response.status(Status.UNAUTHORIZED).entity(jsonString).build();
		}
		if (!userAdministration.checkToDo(Action.USERADMIN.getActionKey())) {
			LoggerFactory.getLogger(ShiftplanningControler.class).info("not authorized");
			jsonString = new JSONObject()
		            .put("Result", "faild")
		            .put("Comment", "not authorized")
		            .put("Contracts", "")
		            .toString();
			return Response.status(Status.FORBIDDEN).entity(jsonString).build();
		}
		
		UserImpl userHandling = new UserImpl();
		List<User> listOfUsers = userHandling.readAllUsers();
		JSONArray arrayObject = new JSONArray();
		for (User elem : listOfUsers) {
			arrayObject.put(new JSONObject()
    				.put("userID", elem.getUserID())
    				.put("username", elem.getUserName())
    				.put("displayname", elem.getDisplayName())
       			    );
		}
		
		jsonString = new JSONObject()
	            .put("Result", "ok")
	            .put("Comment", "get list of all valid user")
	            .put("listDays", arrayObject)
	            .toString();
		return Response.ok(jsonString).build();
	}

	

	


	
	
	@Override
	@POST
    @Path("/changeUser")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
	public Response  changePerson(User credential, 
                                  @CookieParam(cockieName) String cockieValue) {

		String jsonString = null;; 
		
		LoggerFactory.getLogger(ShiftplanningControler.class).info("login start ....");
		UserImpl userAdministration = new UserImpl();
		
		if (!userAdministration.findSession(cockieValue)) {
			LoggerFactory.getLogger(ShiftplanningControler.class).info("session not exists or not authorized");
			jsonString = new JSONObject()
		            .put("User_ID", 0)
		            .put("User_Account", "")
		            .put("User_Password", "")
		            .put("User_Display_Name", "")
		            .toString();
			return Response.status(Status.UNAUTHORIZED).entity(jsonString).build();
		}
		if (!userAdministration.checkToDo(Action.USERADMIN.getActionKey())) {
			LoggerFactory.getLogger(ShiftplanningControler.class).info("session not exists or not authorized");
			jsonString = new JSONObject()
		            .put("User_ID", 0)
		            .put("User_Account", "")
		            .put("User_Password", "")
		            .put("User_Display_Name", "")
		            .toString();
			return Response.status(Status.FORBIDDEN).entity(jsonString).build();
		}
		
		User loginUser = userAdministration.update(credential);
		
		if (loginUser != null) {
			LoggerFactory.getLogger(ShiftplanningControler.class).info("add user ok");
			LoggerFactory.getLogger(ShiftplanningControler.class).info("UserID: " + loginUser.getUserID());
			jsonString = new JSONObject()
				            .put("User_ID", loginUser.getUserID())
				            .put("User_Account", loginUser.getUserName())
				            .put("User_Password", "")
				            .put("User_Display_Name", loginUser.getDisplayName())
				            .toString();
			return Response.ok(jsonString).build();
		} 
		
		LoggerFactory.getLogger(ShiftplanningControler.class).info("create new user faild faild");
		jsonString = new JSONObject()
	            .put("User_ID", 0)
	            .put("User_Account", "")
	            .put("User_Password", "")
	            .put("User_Display_Name", "")
	            .toString();
		
		return Response.status(Status.FORBIDDEN).entity(jsonString).build();
	}
	
	
	
	@Override
	@GET
    @Path("/getContract")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
	public Response getContract(@HeaderParam("userID") int userID, 
			                   @HeaderParam("startDate") java.util.Date start, 
			                   @CookieParam(cockieName) String cockieValue) {
		String jsonString; 
		Date startDate = new java.sql.Date(start.getTime());
		
		UserImpl userAdministration = new UserImpl();
		if (!userAdministration.findSession(cockieValue)) {
			LoggerFactory.getLogger(ShiftplanningControler.class).info("session exipired");
			jsonString = new JSONObject()
		            .put("Result", "faild")
		            .put("Comment", "session expired")
		            .put("Contracts", "")
		            .toString();
			return Response.status(Status.UNAUTHORIZED).entity(jsonString).build();
		}
		if (!userAdministration.checkToDo(Action.CONTRACTADMIN.getActionKey())) {
			LoggerFactory.getLogger(ShiftplanningControler.class).info("not authorized");
			jsonString = new JSONObject()
		            .put("Result", "faild")
		            .put("Comment", "not authorized")
		            .put("Contracts", "")
		            .toString();
			return Response.status(Status.FORBIDDEN).entity(jsonString).build();
		}
		
		ContractHandling adminContracts = new ContractHandling();
		
		Contract contract = adminContracts.readContract(userID, startDate);
		
		jsonString = new JSONObject()
	            .put("Result", "ok")
	            .put("Comment", "get one contract by the start date")
	            .put("Contracts", new JSONArray()
	            		.put(new JSONObject()
	            				.put("userID", contract.getUserId())
	            				.put("startDate", contract.getStartDate())
	            				.put("endDate", contract.getEndDate())
	            				.put("vacationentitlementYear", contract.getVacationentitlementYear())
	            				.put("hoursToWork", contract.getHoursToWork())
	            				.put("workingRole", contract.getWorkingRole())
	            				))
	            .toString();
		return Response.ok(jsonString).build();
	}
	

	@Override
	@GET
    @Path("/getAllContracts")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
	public Response getContracts(@HeaderParam("userID") int userID, 
			                   @HeaderParam("startDate") java.util.Date start, 
			                   @HeaderParam("endDate") java.util.Date end, 
			                   @CookieParam(cockieName) String cockieValue) {
		String jsonString; 
		Date startDate = new java.sql.Date(start.getTime());
		Date endDate = new java.sql.Date(start.getTime());
		
		UserImpl userAdministration = new UserImpl();
		if (!userAdministration.findSession(cockieValue)) {
			LoggerFactory.getLogger(ShiftplanningControler.class).info("session exipired");
			jsonString = new JSONObject()
		            .put("Result", "faild")
		            .put("Comment", "session expired")
		            .put("Contracts", "")
		            .toString();
			return Response.status(Status.UNAUTHORIZED).entity(jsonString).build();
		}
		if (!userAdministration.checkToDo(Action.CONTRACTADMIN.getActionKey())) {
			LoggerFactory.getLogger(ShiftplanningControler.class).info("not authorized");
			jsonString = new JSONObject()
		            .put("Result", "faild")
		            .put("Comment", "not authorized")
		            .put("Contracts", "")
		            .toString();
			return Response.status(Status.FORBIDDEN).entity(jsonString).build();
		}
		
		ContractHandling adminContracts = new ContractHandling();
		
		List<Contract> listContracts = adminContracts.readContractsOfPeriod(userID, startDate, endDate);
		
		JSONArray ja =new JSONArray();
		for (Contract contract : listContracts) {
			ja.put(new JSONObject()
    				.put("userID", contract.getUserId())
    				.put("startDate", contract.getStartDate())
    				.put("endDate", contract.getEndDate())
    				.put("vacationentitlementYear", contract.getVacationentitlementYear())
    				.put("hoursToWork", contract.getHoursToWork())
    				.put("workingRole", contract.getWorkingRole()));
		}
		
		jsonString = new JSONObject()
	            .put("Result", "ok")
	            .put("Comment", "get all contracts of a given period")
	            .put("Contracts", ja)
	            .toString();
		return Response.ok(jsonString).build();
	}

	
	@Override
	@POST
    @Path("/addContract")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
	public Response addContract(Contract contract, @CookieParam(cockieName) String cockieValue) {
		String jsonString; 
		
		UserImpl userAdministration = new UserImpl();
		if (!userAdministration.findSession(cockieValue)) {
			LoggerFactory.getLogger(ShiftplanningControler.class).info("session exipired");
			jsonString = new JSONObject()
		            .put("Result", "faild")
		            .put("Comment", "session expired")
		            .put("Contracts", "")
		            .toString();
			return Response.status(Status.UNAUTHORIZED).entity(jsonString).build();
		}
		if (!userAdministration.checkToDo(Action.CONTRACTADMIN.getActionKey())) {
			LoggerFactory.getLogger(ShiftplanningControler.class).info("not authorized");
			jsonString = new JSONObject()
		            .put("Result", "faild")
		            .put("Comment", "not authorized")
		            .put("Contracts", "")
		            .toString();
			return Response.status(Status.FORBIDDEN).entity(jsonString).build();
		}
		
		ContractHandling adminContracts = new ContractHandling();
		
		Contract addContract = adminContracts.addContract(contract);
		
		if (addContract == null) {
			LoggerFactory.getLogger(UserImpl.class).debug("add a new contract faild");
		}
		
		jsonString = new JSONObject()
	            .put("Result", "ok")
	            .put("Comment", "add contract")
	            .put("Contracts", new JSONArray()
	            		.put(new JSONObject()
	            				.put("userID", addContract.getUserId())
	            				.put("startDate", addContract.getStartDate())
	            				.put("endDate", addContract.getEndDate())
	            				.put("vacationentitlementYear", addContract.getVacationentitlementYear())
	            				.put("hoursToWork", addContract.getHoursToWork())
	            				.put("workingRole", addContract.getWorkingRole())
	            				))
	            .toString();
		return Response.ok(jsonString).build();
	}

	
	
	@Override
	@PUT
    @Path("/updateContract")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
	public Response updateContract(Contract contract, @CookieParam(cockieName) String cockieValue) {
		String jsonString; 
		
		UserImpl userAdministration = new UserImpl();
		if (!userAdministration.findSession(cockieValue)) {
			LoggerFactory.getLogger(ShiftplanningControler.class).info("session exipired");
			jsonString = new JSONObject()
		            .put("Result", "faild")
		            .put("Comment", "session expired")
		            .put("Contracts", "")
		            .toString();
			return Response.status(Status.UNAUTHORIZED).entity(jsonString).build();
		}
		if (!userAdministration.checkToDo(Action.CONTRACTADMIN.getActionKey())) {
			LoggerFactory.getLogger(ShiftplanningControler.class).info("not authorized");
			jsonString = new JSONObject()
		            .put("Result", "faild")
		            .put("Comment", "not authorized")
		            .put("Contracts", "")
		            .toString();
			return Response.status(Status.FORBIDDEN).entity(jsonString).build();
		}
		
		ContractHandling adminContracts = new ContractHandling();
		
		Contract addContract = adminContracts.modifyContract(contract);
		
		if (addContract == null) {
			LoggerFactory.getLogger(UserImpl.class).debug("update of contract faild");
		}
		
		jsonString = new JSONObject()
	            .put("Result", "ok")
	            .put("Comment", "update contract")
	            .put("Contracts", new JSONArray()
	            		.put(new JSONObject()
	            				.put("userID", addContract.getUserId())
	            				.put("startDate", addContract.getStartDate())
	            				.put("endDate", addContract.getEndDate())
	            				.put("vacationentitlementYear", addContract.getVacationentitlementYear())
	            				.put("hoursToWork", addContract.getHoursToWork())
	            				.put("workingRole", addContract.getWorkingRole())
	            				))
	            .toString();
		return Response.ok(jsonString).build();
	}


	
	@Override
	@DELETE
    @Path("/deleteContract")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
	public Response deleteContract(Contract contract, @CookieParam(cockieName) String cockieValue) {
		String jsonString; 
		
		UserImpl userAdministration = new UserImpl();
		if (!userAdministration.findSession(cockieValue)) {
			LoggerFactory.getLogger(ShiftplanningControler.class).info("session exipired");
			jsonString = new JSONObject()
		            .put("Result", "faild")
		            .put("Comment", "session expired")
		            .put("Contracts", "")
		            .toString();
			return Response.status(Status.UNAUTHORIZED).entity(jsonString).build();
		}
		if (!userAdministration.checkToDo(Action.CONTRACTADMIN.getActionKey())) {
			LoggerFactory.getLogger(ShiftplanningControler.class).info("not authorized");
			jsonString = new JSONObject()
		            .put("Result", "faild")
		            .put("Comment", "not authorized")
		            .put("Contracts", "")
		            .toString();
			return Response.status(Status.FORBIDDEN).entity(jsonString).build();
		}
		
		ContractHandling adminContracts = new ContractHandling();
		
		Contract addContract = adminContracts.delContract(contract);
		
		if (addContract == null) {
			LoggerFactory.getLogger(UserImpl.class).debug("delete of contract faild");
		}
		
		jsonString = new JSONObject()
	            .put("Result", "ok")
	            .put("Comment", "delete contract")
	            .put("Contracts", new JSONArray()
	            		.put(new JSONObject()
	            				.put("userID", addContract.getUserId())
	            				.put("startDate", addContract.getStartDate())
	            				.put("endDate", addContract.getEndDate())
	            				.put("vacationentitlementYear", addContract.getVacationentitlementYear())
	            				.put("hoursToWork", addContract.getHoursToWork())
	            				.put("workingRole", addContract.getWorkingRole())
	            				))
	            .toString();
		return Response.ok(jsonString).build();
	}

	
	
	
	
	
	@Override
	@GET
    @Path("/test")
	public String test() {
		System.out.println("test start ....");
		Map<String, Object> props = application.getProperties();

		String jsonString; 
		jsonString = new JSONObject()
	            .put("User_ID", 0)
	            .put("User_Account", "")
	            .put("User_Password", "")
	            .put("User_Display_Name", "")
	            .toString();
		return jsonString;
	}

	
	
	

	@Override
	@POST
    @Path("/savedayactivity")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
	public Response saveDayActivity(DayEntry dayEntry, 
			                      @CookieParam(cockieName) String cockieValue) {
		String jsonString; 
				
		UserImpl userAdministration = new UserImpl();
		if (!userAdministration.findSession(cockieValue)) {
			LoggerFactory.getLogger(ShiftplanningControler.class).info("session exipired");
			jsonString = new JSONObject()
		            .put("Result", "faild")
		            .put("Comment", "session expired")
		            .put("Contracts", "")
		            .toString();
			return Response.status(Status.UNAUTHORIZED).entity(jsonString).build();
		}
		if (userAdministration.getUserID() != dayEntry.getUserId() && !userAdministration.checkToDo(Action.SHIFTPLANNING.getActionKey())) {
			LoggerFactory.getLogger(ShiftplanningControler.class).info("not authorized");
			jsonString = new JSONObject()
		            .put("Result", "faild")
		            .put("Comment", "not authorized")
		            .put("Contracts", "")
		            .toString();
			return Response.status(Status.FORBIDDEN).entity(jsonString).build();
		}
		
		Date today = DateUtil.today();
		if (dayEntry.getDate().after(today) && !userAdministration.checkToDo(Action.SHIFTPLANNING.getActionKey())) {
			LoggerFactory.getLogger(ShiftplanningControler.class).info("Given day is in the future");
			jsonString = new JSONObject()
		            .put("Result", "faild")
		            .put("Comment", "Given day is in the future and no admin rights exists")
		            .put("Shift planning", "")
		            .toString();
			return Response.status(Status.BAD_REQUEST).entity(jsonString).build();
		}
		
		DayEntryhandling dayHandling = new DayEntryhandling();
		MonthTimeAccount account = dayHandling.saveActivity(dayEntry);
		HoursAccountHandling accountHandler = new HoursAccountHandling();
		MonthTimeAccount actualAccount = accountHandler.readAccount(dayEntry.getUserId(), DateUtil.firstDayOfMonth(today));
		EntitlementHandling entitlementHandler = new EntitlementHandling();
		VacationEntitlement actualEntitlement = entitlementHandler.readActualEntitlement(dayEntry.getUserId(), DateUtil.getYear(today), today);
		
		/* returned the uctual account */
		jsonString = new JSONObject()
	            .put("Result", "ok")
	            .put("Comment", "save activity for one day")
	            .put("Contracts", new JSONArray()
	            		.put(new JSONObject()
	            				.put("userID", dayEntry.getUserId())
	            				.put("month", DateUtil.firstDayOfMonth(dayEntry.getDate()))
	            				.put("planHours", actualAccount.getPlanedHours() + DateUtil.workingHours(dayEntry.getUserId(), today, DateUtil.lastDayOfMonth(today)))
	            				.put("actualHours", actualAccount.getActualHours() + DateUtil.workingHours(dayEntry.getUserId(), today, DateUtil.lastDayOfMonth(today)))
	            				.put("remainingVacation", actualEntitlement.getRemainingVacation())
	            				.put("entitlementVacation", actualEntitlement.getEntitlement())
	            				.put("takenVacation", actualEntitlement.getVactionUsed())
	            				))
	            .toString();
		return Response.ok(jsonString).build();
	}
	
	
	

	@Override
	@POST
    @Path("/saveactivityperiod")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
	public Response savePeriodActivity(@HeaderParam("userId") int userId, 
			                         @HeaderParam("dayStart") java.util.Date start , 
			                         @HeaderParam("dayEnd") java.util.Date end, 
			                         @HeaderParam("activityID") int activityId, 
			                         @CookieParam(cockieName) String cockieValue) {
		
		String jsonString; 
		Date dayStart = new java.sql.Date(start.getTime());
		Date dayEnd = new java.sql.Date(end.getTime());
		
		UserImpl userAdministration = new UserImpl();
		if (!userAdministration.findSession(cockieValue)) {
			LoggerFactory.getLogger(ShiftplanningControler.class).info("session exipired");
			jsonString = new JSONObject()
		            .put("Result", "faild")
		            .put("Comment", "session expired")
		            .put("Contracts", "")
		            .toString();
			return Response.status(Status.UNAUTHORIZED).entity(jsonString).build();
		}
		if (userAdministration.getUserID() != userId && !userAdministration.checkToDo(Action.SHIFTPLANNING.getActionKey())) {
			LoggerFactory.getLogger(ShiftplanningControler.class).info("not authorized");
			jsonString = new JSONObject()
		            .put("Result", "faild")
		            .put("Comment", "not authorized")
		            .put("Contracts", "")
		            .toString();
			return Response.status(Status.FORBIDDEN).entity(jsonString).build();
		}
		
		LocalDateTime actuaDate = java.time.LocalDateTime.now();
		Date today = new java.sql.Date(Date.from(actuaDate.atZone(ZoneId.systemDefault()).toInstant()).getTime());
		if (dayStart.before(today) && !userAdministration.checkToDo(Action.SHIFTPLANNING.getActionKey())) {
			LoggerFactory.getLogger(ShiftplanningControler.class).info("Given day is in the past");
			jsonString = new JSONObject()
		            .put("Result", "faild")
		            .put("Comment", "Given day is in the past and no admin rights exists")
		            .put("Shift planning", "")
		            .toString();
			return Response.status(Status.BAD_REQUEST).entity(jsonString).build();
		}
		
		Date day = dayStart;
		BankholydayHandling chekBankholyday = new BankholydayHandling();
		DayEntryhandling dayHandling = new DayEntryhandling();
		ContractHandling adminContracts = new ContractHandling();
		EntitlementHandling entitlement = new EntitlementHandling();
		DayEntry dayEntry = new DayEntry();
		
		float countExistingVacation = 0L;
		if (activityId == 0 || activityId == 1) {
			countExistingVacation = dayHandling.readCountVacationOfPeriod(userId, dayStart, dayEnd);
		}
		dayEntry.setUserId(userId);
		dayEntry.setActualActivity(activityId);
		dayEntry.setPlanedActivity(activityId);
		MonthTimeAccount account = null;
		float count = 0;
		int year = DateUtil.getYear(day);
		Date testEnd = DateUtil.addDays(dayEnd, 1);
		while (day.before(testEnd))  {
			/* loop over the period */
			int testYear = DateUtil.getYear(day);
			if (year != testYear) {
				/* periode over new year */
				entitlement.storeTakenVacation(userId, day, count);
				count = 0;
				year = testYear;
			}
			
			if (DateUtil.getDayNumberofWeek(day) != Calendar.SATURDAY && DateUtil.getDayNumberofWeek(day) != Calendar.SUNDAY && chekBankholyday.getBankholyday(day) == null) {
				/* generate only entries of Monday until Friday which are no bankholydays */ 
				if (activityId == 0) {
					count = (float) (count + 0.5);
				} else if (activityId == 1) {
					count++;
				}
				dayEntry.setDate(day);
				Contract contract = adminContracts.readActContract(userId, day);
				int workingHoursPerWeek = contract.getHoursToWork();
				float workHoursPerDay = Math.round(workingHoursPerWeek/5);
				dayEntry.setPlanedHours(workHoursPerDay);
				dayEntry.setActualHours(workHoursPerDay);
				account = dayHandling.saveActivity(dayEntry);
			}
			day = DateUtil.addDays(day, 1);
		}
		Float takenDiff = count - countExistingVacation;
		if (takenDiff != 0L) {
		entitlement.storeTakenVacation(userId, day, takenDiff);
		}

		HoursAccountHandling accountHandler = new HoursAccountHandling();
		MonthTimeAccount actualAccount = accountHandler.readAccount(dayEntry.getUserId(), DateUtil.firstDayOfMonth(today));
		EntitlementHandling entitlementHandler = new EntitlementHandling();
		VacationEntitlement actualEntitlement = entitlementHandler.readActualEntitlement(dayEntry.getUserId(), DateUtil.getYear(today), today);
		
		/* returned the actual account */
		jsonString = new JSONObject()
	            .put("Result", "ok")
	            .put("Comment", "save activity period")
	            .put("Contracts", new JSONArray()
	            		.put(new JSONObject()
	            				.put("userID", dayEntry.getUserId())
	            				.put("month", DateUtil.firstDayOfMonth(dayEntry.getDate()))
	            				.put("planHours", actualAccount.getPlanedHours() + DateUtil.workingHours(dayEntry.getUserId(), today, DateUtil.lastDayOfMonth(today)))
	            				.put("actualHours", actualAccount.getActualHours() + DateUtil.workingHours(dayEntry.getUserId(), today, DateUtil.lastDayOfMonth(today)))
	            				.put("remainingVacation", actualEntitlement.getRemainingVacation())
	            				.put("entitlementVacation", actualEntitlement.getEntitlement())
	            				.put("takenVacation", actualEntitlement.getVactionUsed())
	            				))
	            .toString();
		return Response.ok(jsonString).build();
	}
	
	
	
	
	@Override
	@GET
    @Path("/getmonthactivities")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
	public Response getListActivitiesOfMonth(@HeaderParam("userId") int userId, 
			                                 @HeaderParam("dayStart") java.util.Date start, 
			                                 @CookieParam(cockieName) String cockieValue) {
		
		String jsonString;
		Date dayStart = new java.sql.Date(start.getTime());
		
		UserImpl userAdministration = new UserImpl();
		if (!userAdministration.findSession(cockieValue)) {
			LoggerFactory.getLogger(ShiftplanningControler.class).info("session exipired");
			jsonString = new JSONObject()
		            .put("Result", "faild")
		            .put("Comment", "session expired")
		            .put("Contracts", "")
		            .toString();
			return Response.status(Status.UNAUTHORIZED).entity(jsonString).build();
		}
		if (userAdministration.getUserID() != userId && !userAdministration.checkToDo(Action.SHIFTPLANNING.getActionKey())) {
			LoggerFactory.getLogger(ShiftplanningControler.class).info("not authorized");
			jsonString = new JSONObject()
		            .put("Result", "faild")
		            .put("Comment", "not authorized")
		            .put("Contracts", "")
		            .toString();
			return Response.status(Status.FORBIDDEN).entity(jsonString).build();
		}
		

		DayEntryhandling dayHandling = new DayEntryhandling();
		List<DayEntry> listOfActivities = dayHandling.readActivitiesOfPeriod(userId, dayStart, DateUtil.lastDayOfMonth(dayStart));
		JSONArray arrayObject = new JSONArray();
		for (DayEntry elem : listOfActivities) {
			arrayObject.put(new JSONObject()
    				.put("userID", elem.getUserId())
    				.put("date", elem.getDate())
    				.put("planActivity", elem.getPlanedActivity())
    				.put("planHours", elem.getPlanedHours())
    				.put("actualActivity", elem.getActualActivity())
    				.put("actualHours", elem.getActualHours())
    				.put("startTime", elem.getStartTime())
       				.put("endTime", elem.getEndTime())
       			    );
		}
		
		jsonString = new JSONObject()
	            .put("Result", "ok")
	            .put("Comment", "get month activities")
	            .put("listDays", arrayObject)
	            .toString();
		return Response.ok(jsonString).build();
	}
	
	

	
	
	
	
	@Override
	@GET
    @Path("/getListAllowedToDos")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
	public Response getListAllowedToDos(@CookieParam(cockieName) String cockieValue) {
		
		String jsonString;
		
		UserImpl userAdministration = new UserImpl();
		if (!userAdministration.findSession(cockieValue)) {
			LoggerFactory.getLogger(ShiftplanningControler.class).info("session exipired");
			jsonString = new JSONObject()
		            .put("Result", "faild")
		            .put("Comment", "session expired")
		            .put("Contracts", "")
		            .toString();
			return Response.status(Status.UNAUTHORIZED).entity(jsonString).build();
		}
		
		int userId = userAdministration.getUserID();
		LoggerFactory.getLogger(ShiftplanningControler.class).info("User ID: " + userId);
		AllowedToDoHandling toDosHandling = new AllowedToDoHandling();
		List<AllowedToDo> listOfToDos = toDosHandling.readListOfDoTosEntry(userId);
		JSONArray arrayObject = new JSONArray();
		for (AllowedToDo elem : listOfToDos) {
			arrayObject.put(new JSONObject()
    				.put("userID", elem.getUserID())
    				.put("todo", elem.getToDo())
       			    );
		}
		
		jsonString = new JSONObject()
	            .put("Result", "ok")
	            .put("Comment", "get todos")
	            .put("listAllowedToDos", arrayObject)
	            .toString();
		return Response.ok(jsonString).build();
	}
	

	
	
	
	
}
