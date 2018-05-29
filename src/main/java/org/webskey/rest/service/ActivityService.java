package org.webskey.rest.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.webskey.rest.model.Activity;

@Component
public class ActivityService {
	
	public Activity getAct(int num) {
		Activity act = new Activity();
		act.setName("name");
		act.setDesc("desc");
		act.setNum(num);
		
		return act;
	}
	
	public List<Activity> getActs(){
		List<Activity> list = new ArrayList<>();
		list.add(new Activity("name1", "desc1", 1));
		list.add(new Activity("name2", "desc2", 2));
		list.add(new Activity("name3", "desc3", 3));
		list.add(new Activity("name4", "desc4", 4));
		
		return list;
	}
	
	public void saveAct(Activity activity) {
		System.out.println("Activity: " + activity.getName() + " saved.");
	}
}
