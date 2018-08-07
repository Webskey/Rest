package org.webskey.rest;

import java.util.ArrayList;
import java.util.List;

import org.webskey.rest.entities.ActivityEntity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ActivityEntityBuilder {
	
	private ActivityEntity activityEntity;
	
	public ActivityEntityBuilder(){
		this.activityEntity = new ActivityEntity();
		activityEntity.setId(1);
		activityEntity.setName("name");
		activityEntity.setDes("desc");
		activityEntity.setNum(11);
	}
	
	public ActivityEntity getActivity() {
		return activityEntity;
	}
	
	public List<ActivityEntity> getActivityList(){
		List<ActivityEntity> list = new ArrayList<>();
		
		list.add(this.activityEntity);
		list.add(new ActivityEntity(2, "name2", "desc2", 22));
		list.add(new ActivityEntity(3, "name3", "desc3", 33));
		list.add(new ActivityEntity(4, "name4", "desc4", 44));
		list.add(new ActivityEntity(5, "name5", "desc5", 55));
		
		return list;
	}
	
	public String getJson() {
		ObjectMapper mapper = new ObjectMapper();
		String jsonString;
		try {
			jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(getActivity());
			return jsonString;
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return "";		
	}
	
	public String getJsonList() {
		ObjectMapper mapper = new ObjectMapper();
		String jsonString;
		try {
			jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(getActivityList());
			return jsonString;
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return "";		
	}
}
