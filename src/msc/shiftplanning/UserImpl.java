package msc.shiftplanning;


import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import javax.naming.NamingException;

import javax.sql.DataSource;

import org.apache.commons.codec.binary.Base64;
import org.json.JSONObject;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jndi.JndiTemplate;

import net.spy.memcached.MemcachedClient;



//@Repository("userDao")
//@Service
public class UserImpl extends User implements UserDao {

	@Value("${memcached-duration}")
	private int MEMCACHED_DURATION = 1000;
	  
	
	
	@Autowired
	private static JdbcTemplate jdbcTemplateSimple;
	private static NamedParameterJdbcTemplate jdbcTemplate;
	  
	@Autowired
	private MyCache mccc;
	
	
	@Autowired
	protected static List<User> listUser;
	
	
	private static final String privateKey = "Sgfskjnqf ยง$12534bsanaq WKEKFVNQq	3w4hf	l-";
	//private static final String cipher = "AES/CBC/NoPadding";
	private static final String cipher = "AES/CBC/PKCS5Padding";

	private static final String ivString = "1234567812345678";
	private static final String HMAC_SHA256 = "HmacSHA256";

	private static final DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
	
	private String sessionID;
	
	
	private static byte[] hmacSha256(final String data, final String secretKey) {
	    try {
	        final Mac mac = Mac.getInstance(HMAC_SHA256);
	        mac.init(new SecretKeySpec(secretKey.getBytes(), HMAC_SHA256));
	        byte[] rawHmac = mac.doFinal(data.getBytes("UTF-8"));

			return rawHmac;
	    }
	    catch (final Exception e) {
	    	e.printStackTrace();
	    	return null;
	    }
	}
	
	

	/*
	 * encrypt the password
	 */
	private static String openssl_encrypt(String data) throws Exception {
		byte[] salt = "0".getBytes();
		
		KeySpec spec = new PBEKeySpec(privateKey.toCharArray(), salt, 65536, 256); // AES-256
		SecretKeyFactory f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
		byte[] newKey = f.generateSecret(spec).getEncoded();
		
		
		KeySpec spec1 = new PBEKeySpec(privateKey.toCharArray(), salt, 65536, 256); // AES-256
		SecretKeyFactory f1 = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
		byte[] newKey1 = f1.generateSecret(spec).getEncoded();
		
		String s0 = new String(newKey, StandardCharsets.UTF_8); 
		String s1 = new String(newKey1, StandardCharsets.UTF_8); 
		LoggerFactory.getLogger(UserImpl.class).info("Used key0=[" + s0 + "]");
		LoggerFactory.getLogger(UserImpl.class).info("Used key1=[" + s1 + "]");
		for (int i=0;i<newKey.length; i++) {
			if (newKey[i] != newKey1[i]) {
				LoggerFactory.getLogger(UserImpl.class).info("kex on pos " + i + " is difernent.");
			}
		}
		
		if (!s0.equals(s1)) {
			LoggerFactory.getLogger(UserImpl.class).info("String keys are different!");
		}
		
		//String nKey = "[B@64fasabngilSA)§UWQUBAEDKJKLSH";
		//String useKey = nKey;
		//byte[] newKey = useKey.getBytes();
		
	    Base64 base64 = new Base64();
	    Cipher ciper = Cipher.getInstance(cipher);
	    SecretKeySpec key = new SecretKeySpec(newKey, "AES");
	    IvParameterSpec iv = new IvParameterSpec(newKey, 0, ciper.getBlockSize());
	    
	    int maxKeyLen = Cipher.getMaxAllowedKeyLength("AES");
	    LoggerFactory.getLogger(UserImpl.class).info("MaxAllowedKeyLength=[" + maxKeyLen + "].");
	    LoggerFactory.getLogger(UserImpl.class).info("Used key=[" + newKey.length + "] "+ s0);

	    // Encrypt
	    ciper.init(Cipher.ENCRYPT_MODE, key, iv);
	    //ciper.init(Cipher.ENCRYPT_MODE, key);
	    byte[] encryptedCiperBytes = ciper.doFinal(data.getBytes());
	    byte[] hmac = hmacSha256(new String(encryptedCiperBytes, StandardCharsets.UTF_8), privateKey);
	    
	    byte[] base64EncryptedCiperBytes = base64.encode((new String(hmac, StandardCharsets.UTF_8) + new String(encryptedCiperBytes, StandardCharsets.UTF_8)).getBytes());
	    

	    String s = new String(base64EncryptedCiperBytes);
	    LoggerFactory.getLogger(UserImpl.class).info("Cipher : " + s);
	    return s;
	}
	
	
	/*
	 * define the query to read the attributes from db
	 */
	private String giveQuerySql() {
	  LoggerFactory.getLogger(UserImpl.class).debug("Select and Fetch the user by user ID and password");
	  return  "SELECT a.user_id, a.user_account, a.user_password, a.user_display_name \n" +
	    	  "FROM user a \n" +
	          "where a.user_account = :login_name \n" +
	    	   "  and a.user_password = :login_pwd";
	  }
	  

	
	/*
	 * define the query to read all user
	 */
	private String giveQueryForAllUserSql() {
	  LoggerFactory.getLogger(UserImpl.class).debug("Select and Fetch all user with a contract for given day");
	  return  "SELECT a.user_id, a.user_account, a.user_password, a.user_display_name \n" +
	    	  "FROM user a, contracts c \n" +
	          "where c.start_date <= :start_date \n" +
			  "  and (c.end_date >= :start_date or c.end_date is null) \n " +
			  "  and c.user_id = a.user_id ";
	  }
	  

	
	
	/*
	 * get all valid user for a given day (with a a contract for the given day)
	 */
	public List<User> readAllValidUser(Date date) {
		LoggerFactory.getLogger(UserImpl.class).debug("Search for all valid user for " +  dateFormat.format(date));
	    if (jdbcTemplate == null) {
	    	JndiTemplate jndiTemplate = new JndiTemplate();
	        DataSource dataSource = null;
			try {
				dataSource = (DataSource) jndiTemplate.lookup("java:comp/env/jdbc/DienstPlanDB");
			} catch (NamingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	    }
	    
	    listUser = jdbcTemplate.query(
	    		giveQueryForAllUserSql(),
	    		new MapSqlParameterSource()
	    	      .addValue("start_date", date),
	            new UserRowMapper());
	    LoggerFactory.getLogger(UserImpl.class).debug("Select and Fetch okay!");
		return listUser;
	}

	
	
	
	/*
	 * define the insert statement in db
	 */
	private String giveInsertSql() {
	  LoggerFactory.getLogger(UserImpl.class).debug("Add new user");
	  return  "insert into user (user_id, user_account, user_password, user_display_name) \n" +
	    	  "values \n" +
	          "(:user_id, :login_name, :login_pwd, :disply_name)";
	  }


	
	/*
	 * define the query statement to find the next id
	 */
	private String giveNexIdSql() {
	  LoggerFactory.getLogger(UserImpl.class).debug("Select SQL for next user ID");
	  return  "SELECT max(a.user_id)+1 user_id \n"
	  		+ "	    	  FROM user a";
	  }


	
	/*
	 * define the query statement to find the allowed action of an user
	 */
	private String giveAllowedToDoSql() {
	  LoggerFactory.getLogger(UserImpl.class).debug("Add new user");
	  return  "SELECT a.user_id user_id \n"
	  		+ "	    FROM allowed_todos a \n"
	  		+ "     WHERE a.user_id = :user_id \n"
	  		+ "     and a.todos_id = :todos_id";
	  }



	
	/*
	 * define the update statement to change the password of an user
	 */
	private String giveChangePwdSql() {
	  LoggerFactory.getLogger(UserImpl.class).debug("Select SQL for all action allowed of given user");
	  return  "UPDATE user a \n" +
			  " set a.user_password = :login_pwd \n" +
 		      "where a.user_account = :login_name";
	  }

	
	
	
	/* internal functio to find the next free user ID */
	protected int findNextID() {
		int retID = 1;
		int seq;
		
		if (jdbcTemplateSimple == null) {
	    	JndiTemplate jndiTemplate = new JndiTemplate();
	        DataSource dataSource = null;
			try {
				dataSource = (DataSource) jndiTemplate.lookup("java:comp/env/jdbc/DienstPlanDB");
			} catch (NamingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			jdbcTemplateSimple = new JdbcTemplate(dataSource);
	    }
		seq = jdbcTemplateSimple.queryForObject(giveNexIdSql(), Integer.class);
		if (seq > 1) {
			retID = seq;
		}
		return retID;
	}
	
	
	
	/* check if the given password is equal to the stored password for a given user */
	protected User checkPwd(String userName, String pwd) {
		LoggerFactory.getLogger(UserImpl.class).debug("New user: " +userName + "/" + pwd);
	    if (jdbcTemplate == null) {
	    	JndiTemplate jndiTemplate = new JndiTemplate();
	        DataSource dataSource = null;
			try {
				dataSource = (DataSource) jndiTemplate.lookup("java:comp/env/jdbc/DienstPlanDB");
			} catch (NamingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	    }
	    String encryptPwd = null;;
		try {
			encryptPwd = openssl_encrypt(pwd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//LoggerFactory.getLogger(UserImpl.class).debug("encrypt password:" + encryptPwd);
		LoggerFactory.getLogger(UserImpl.class).info("encrypt password:" + encryptPwd);
	    listUser = jdbcTemplate.query(
	    		giveQuerySql(),
	    		new MapSqlParameterSource()
	    	      .addValue("login_name", userName)
	    	      .addValue("login_pwd", encryptPwd),
	            new UserRowMapper());
	    LoggerFactory.getLogger(UserImpl.class).debug("Select and Fetch okay!");
		for (User elem : listUser) {
			LoggerFactory.getLogger(UserImpl.class).info("password is valid");
			return elem;
		}

		return null;
	}

	
	
	
	
	
	
	/* implement the login with checking the correct user password combination.
	 * Store the session information in a memcache  
	 */
	public UserImpl login(User newUser) {
		User elem = checkPwd(newUser.getUserName(), newUser.getPassword());
		
		if (elem != null) {
			String jsonString = new JSONObject()
	                  .put("User_ID", elem.getUserID())
	                  .put("User_Account", elem.getUserName())
	                  .put("User_Password", "")
	                  .put("User_Display_Name", elem.getDisplayName())
	                  .toString();

			byte[] values = new byte[124];
			SecureRandom sr;
			try {
				sr = SecureRandom.getInstance("SHA1PRNG");
				sr.nextBytes(values);
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			Base64 base64 = new Base64();
			String base64ValueBytes = new String(base64.encode(values), StandardCharsets.UTF_8);
			this.sessionID = base64ValueBytes;
			this.userID = elem.getUserID();
			this.userName = elem.getUserName();
			this.displayName = elem.getDisplayName();
			
			if (mccc == null) {
				mccc = new MyCache();
			}

			this.mccc.Client().set(base64ValueBytes, this.MEMCACHED_DURATION, jsonString);
			return this;
		}

		return null;
	}

	
	
	
	/* implement the logout of the user */
	public void logout(String SessionID) {
		if (mccc == null) {
			mccc = new MyCache();
		}
	
		this.mccc.Client().delete(SessionID);
	}
	
	
	
	/* add a new user in the database */
	public User add(User newUser) {
	    LoggerFactory.getLogger(UserImpl.class).debug("New user: " + newUser.getUserName() + "/" + newUser.getPassword());
	    if (jdbcTemplate == null) {
	    	JndiTemplate jndiTemplate = new JndiTemplate();
	        DataSource dataSource = null;
			try {
				dataSource = (DataSource) jndiTemplate.lookup("java:comp/env/jdbc/DienstPlanDB");
			} catch (NamingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	    }
	    String encryptPwd = null;;
		try {
			encryptPwd = openssl_encrypt(newUser.getPassword());
		} catch (Exception e) {
			e.printStackTrace();
		}
		LoggerFactory.getLogger(UserImpl.class).debug("encrypt password:" + encryptPwd);
		
		int newID = findNextID();
		
		int checkResult = jdbcTemplate.update(giveInsertSql(), new MapSqlParameterSource()
	    		  .addValue("user_id", newID)
	    	      .addValue("login_name", newUser.getUserName())
	    	      .addValue("login_pwd", encryptPwd)
	    	      .addValue("disply_name", newUser.getDisplayName()));
		
	    LoggerFactory.getLogger(UserImpl.class).debug("Insert okay!");
		if (checkResult > 0) {
			newUser.setUserID(newID);
		}

		return newUser;
	}

	
	
	
	/* provide the sql statemenet to select all stored users */
	protected String giveSelAllUserSql() {
		return  "SELECT a.user_id, a.user_account, a.user_password, a.user_display_name \n" +
				"FROM user a ";
	}
	
	
	/*
	 * get all user 
	 */
	public List<User> readAllUsers() {
		LoggerFactory.getLogger(UserImpl.class).debug("Search for all users");
	    if (jdbcTemplate == null) {
	    	JndiTemplate jndiTemplate = new JndiTemplate();
	        DataSource dataSource = null;
			try {
				dataSource = (DataSource) jndiTemplate.lookup("java:comp/env/jdbc/DienstPlanDB");
			} catch (NamingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	    }
	    
	    listUser = jdbcTemplate.query(
	    		giveSelAllUserSql(),
	    		new MapSqlParameterSource(),
	            new UserRowMapper());
	    LoggerFactory.getLogger(UserImpl.class).debug("Select and Fetch okay!");
		return listUser;
	}

	
	/* provide the sql statement select one given user by his ID */
	protected String giveSelOneUserSql() {
		return  "SELECT a.user_id, a.user_account, a.user_password, a.user_display_name \n" +
				"FROM user a \n" +
				    "where a.user_id = :user_id";
	}
	
	
	
	/* select on user by a given ID */
	public User getOneUser(int userid) {
		LoggerFactory.getLogger(UserImpl.class).debug("Get user: " + userid);
	    if (jdbcTemplate == null) {
	    	JndiTemplate jndiTemplate = new JndiTemplate();
	        DataSource dataSource = null;
			try {
				dataSource = (DataSource) jndiTemplate.lookup("java:comp/env/jdbc/DienstPlanDB");
			} catch (NamingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	    }
	    listUser = jdbcTemplate.query(
	    		giveSelOneUserSql(),
	    		new MapSqlParameterSource()
	    	      .addValue("user_id", userid),
	            new UserRowMapper());
	    LoggerFactory.getLogger(UserImpl.class).debug("Select and Fetch okay!");
		for (User elem : listUser) {
			LoggerFactory.getLogger(UserImpl.class).info("user found");
			return elem;
		}

		return null;
	}

	

	
	/* provide the sql statement check if the username is allready in use */
	protected String giveCheckUserIDSql() {
		return  "SELECT a.user_id, a.user_account, a.user_password, a.user_display_name \n" +
				"FROM user a \n" +
				    "where a.user_id <> :user_id \n"
				    + "and a.user_account = :username";
	}
	


	/* check if the username is allready in use */
	public User checkUserName(int userid, String username) {
		LoggerFactory.getLogger(UserImpl.class).debug("Get user: " + userid + "  username: " + username);
	    if (jdbcTemplate == null) {
	    	JndiTemplate jndiTemplate = new JndiTemplate();
	        DataSource dataSource = null;
			try {
				dataSource = (DataSource) jndiTemplate.lookup("java:comp/env/jdbc/DienstPlanDB");
			} catch (NamingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	    }
	    listUser = jdbcTemplate.query(
	    		giveCheckUserIDSql(),
	    		new MapSqlParameterSource()
	    	      .addValue("user_id", userid)
	    	      .addValue("username", username),
	            new UserRowMapper());
	    LoggerFactory.getLogger(UserImpl.class).debug("Select and Fetch okay!");
		for (User elem : listUser) {
			LoggerFactory.getLogger(UserImpl.class).info("user found");
			return elem;
		}

		return null;
	}

	


	
	
	
	
	/* provide the sql statement to change a given user */
	protected String giveUpdateSql() {
		return "Update user a \n"
				+ " Set a.user_account = :login_name, a.user_display_name = :disply_name, a.user_password = :login_pwd \n"
				+ "where a.user_id = :user_id";
	}
	
	
	
	
	/* provide the sql statement to change the password of a given user */
	protected String giveUpdateWithoputPasswordSql() {
		return "Update user a \n"
				+ " Set a.user_account = :login_name, a.user_display_name = :disply_name \n"
				+ "where a.user_id = :user_id";
	}
	
	
	
	/* change a given user */
	public User update(User newUser) {
	    LoggerFactory.getLogger(UserImpl.class).debug("New user: " + newUser.getUserName() + "/" + newUser.getPassword());
	    if (jdbcTemplate == null) {
	    	JndiTemplate jndiTemplate = new JndiTemplate();
	        DataSource dataSource = null;
			try {
				dataSource = (DataSource) jndiTemplate.lookup("java:comp/env/jdbc/DienstPlanDB");
			} catch (NamingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	    }
	    String pwd = newUser.getPassword();
	    int checkResult = -1;
	    if (pwd != null && !pwd.isEmpty()) {
		    String encryptPwd = null;;
			try {
				encryptPwd = openssl_encrypt(newUser.getPassword());
			} catch (Exception e) {
				e.printStackTrace();
			}
			LoggerFactory.getLogger(UserImpl.class).debug("encrypt password:" + encryptPwd);
			
			checkResult = jdbcTemplate.update(giveUpdateSql(), new MapSqlParameterSource()
		    		  .addValue("user_id", newUser.getUserID())
		    	      .addValue("login_name", newUser.getUserName())
		    	      .addValue("login_pwd", encryptPwd)
		    	      .addValue("disply_name", newUser.getDisplayName()));
			
		    LoggerFactory.getLogger(UserImpl.class).debug("Update okay!");
		} else {
			checkResult = jdbcTemplate.update(giveUpdateWithoputPasswordSql(), new MapSqlParameterSource()
		    		  .addValue("user_id", newUser.getUserID())
		    	      .addValue("login_name", newUser.getUserName())
		    	      .addValue("disply_name", newUser.getDisplayName()));
			
		    LoggerFactory.getLogger(UserImpl.class).debug("Update okay!");
		}
	    if (checkResult > 0) {
	    	return newUser;
	    }
		return new User();
	}

	

	/* get the session ID */
	public String getSessionID() {
		return sessionID;
	}



	/* set the session ID */
	public void setSessionID(String sessionID) {
		this.sessionID = sessionID;
	}

	
	
	/* find the session in the memcache */
	public boolean findSession(String SessionID) {
		
		if (mccc == null) {
			mccc = new MyCache();
		}
		
	    try {
	    	String jsonString;
	    	jsonString = this.mccc.Client().get(SessionID).toString();
	    	JSONObject obj = new JSONObject(jsonString);
	    	this.setSessionID(SessionID);
	    	this.setUserID(obj.getInt("User_ID"));
	    	this.setUserName(obj.getString("User_Account"));
	    	this.setPassword(null);
	    	this.setDisplayName(obj.getString("User_Display_Name"));
	    	LoggerFactory.getLogger(UserImpl.class).debug("Userdata are in the my cache");
	    	return true;
	    } catch (NullPointerException nullPointerException) {
	    
	    } catch (Exception e) {
	      LoggerFactory.getLogger(UserImpl.class).error(e.getLocalizedMessage());
	      LoggerFactory.getLogger(UserImpl.class).debug("Userproblem with cache");
	    } 
	    return false;

	}
	
	
	
	/* check if the user is allowed to administrate the user */
	public boolean checkToDo(int actionKey) {
		int retID = 1;
		int seq;
		
		if (jdbcTemplate == null) {
	    	JndiTemplate jndiTemplate = new JndiTemplate();
	        DataSource dataSource = null;
			try {
				dataSource = (DataSource) jndiTemplate.lookup("java:comp/env/jdbc/DienstPlanDB");
			} catch (NamingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	    }
		try {
			seq = jdbcTemplate.queryForObject(giveAllowedToDoSql(), new MapSqlParameterSource()
			    		  .addValue("user_id", this.getUserID())
			    		  .addValue("todos_id", actionKey), Integer.class);
		} catch (Exception e) {
			LoggerFactory.getLogger(UserImpl.class).info("User " + this.userID + " is not authorized!");
			return false;
		}
		if (seq > 0) {
			LoggerFactory.getLogger(UserImpl.class).info("User " + this.userID + " is authorized. Result is " + seq);
			return true;
		}
		LoggerFactory.getLogger(UserImpl.class).info("User " + this.userID + " is not authorized!");
		return false;

	}
	
	
	

	/* change the password of a given user */
	public boolean changeOwnPwd(String oldPwd, String newPwd) {
		LoggerFactory.getLogger(UserImpl.class).debug("change password from " + oldPwd + " to " + newPwd);
		if (checkPwd(this.userName, oldPwd) == null) {
			LoggerFactory.getLogger(UserImpl.class).info("wrong old password!");
			return false;
		}
	    if (jdbcTemplate == null) {
	    	JndiTemplate jndiTemplate = new JndiTemplate();
	        DataSource dataSource = null;
			try {
				dataSource = (DataSource) jndiTemplate.lookup("java:comp/env/jdbc/DienstPlanDB");
			} catch (NamingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	    }
	    String encryptPwd = null;;
		try {
			encryptPwd = openssl_encrypt(newPwd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		LoggerFactory.getLogger(UserImpl.class).debug("encrypt password:" + encryptPwd);
		
		int newID = findNextID();
		int checkResult = -1;
		try {
			checkResult = jdbcTemplate.update(giveChangePwdSql(), new MapSqlParameterSource()
		    	      .addValue("login_pwd", encryptPwd)
		    	      .addValue("login_name", this.userName));
		
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	    LoggerFactory.getLogger(UserImpl.class).debug("Update okay!");
	    if (checkResult > 0) {
	    	return true;
	    }
		return false;
	}
	
	
	
	
	
	
	/* provide the sql statement to delete a user by a given user ID */
	protected String giveDelUserSql() {
		return  "Delete \n" +
				"FROM user \n" +
				    "where user_id = :user_id";
	}
	

	
	
	/* delete a user by a given user ID */
	public boolean del(int userID) {
	    LoggerFactory.getLogger(UserImpl.class).debug("Delete user: " + userID);
	    if (jdbcTemplate == null) {
	    	JndiTemplate jndiTemplate = new JndiTemplate();
	        DataSource dataSource = null;
			try {
				dataSource = (DataSource) jndiTemplate.lookup("java:comp/env/jdbc/DienstPlanDB");
			} catch (NamingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	    }

		int checkResult = jdbcTemplate.update(giveDelUserSql(), new MapSqlParameterSource()
				.addValue("user_id",userID));
		
	    LoggerFactory.getLogger(UserImpl.class).debug("Delete user okay!");
		if (checkResult >= 0) {
			return true;
		}

		return false;
	}

	
	
	
}
