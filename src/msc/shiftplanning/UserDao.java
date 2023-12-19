package msc.shiftplanning;

import org.springframework.stereotype.Service;

@Service
public interface UserDao {
	
	public User login(User newUser);

}
