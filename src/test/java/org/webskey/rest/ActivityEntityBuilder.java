package org.webskey.rest;

import java.util.ArrayList;
import java.util.List;

import org.webskey.rest.model.ActivityEntity;

public class ActivityEntityBuilder {
	
	private ActivityEntity activityEntity;
	
	public ActivityEntityBuilder(){
		this.activityEntity = new ActivityEntity();
		activityEntity.setId(1);
		activityEntity.setName("name");
		activityEntity.setDesc("desc");
		activityEntity.setNum(11);
	}
	
	public ActivityEntity getActivity() {
		return activityEntity;
	}
	
	public List<ActivityEntity> getActivityList(){
		List<ActivityEntity> list = new ArrayList<>();
		list.add(this.activityEntity);
		list.add(this.activityEntity);
		list.add(this.activityEntity);
		list.add(this.activityEntity);
		list.add(this.activityEntity);
		return list;
	}
}
