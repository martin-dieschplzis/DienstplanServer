package msc.shiftplanning;


import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Context;

@ApplicationPath("/shiftplan")
public class ShiftPlanningServer extends Application {

	
	private Set<Object> singletons = new HashSet<Object>();
	
	
	
	
	public ShiftPlanningServer() {
		System.out.println("ShiftPlanning-server starts ......");
		singletons.add(new ShiftplanningControler());
	}
	
	
	
	@Override
	public Set<Object> getSingletons() {
		return singletons;
	}
	
	
/*
	@Override
    public Map<String, Object> getProperties() {
        System.out.println(">>>>>>>>>>>>>>>> get properties");
        Map<String, Object> props = new HashMap<>();
        props.put("message", "Hello Configuration Properties!");
        return props;
    }
*/
	
}

	
	
