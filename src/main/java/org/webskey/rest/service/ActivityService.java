package org.webskey.rest.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webskey.rest.model.ActivityEntity;
import org.webskey.rest.repository.ActivityRepository;

@Service
public class ActivityService {

	@Autowired
	private ActivityRepository activityRepository;

	public boolean exists(int id) {
		return activityRepository.existsById(id);
	}

	public String findDuplicates(List<ActivityEntity> list) {
		StringBuilder sb = new StringBuilder();		
		for(ActivityEntity activity : list) {
			if(exists(activity.getId())) {
				sb.append(" " + activity.getId() + ",");				
			}
		}
		return sb.toString();
	}

	public ActivityEntity findById(int id) {
		return activityRepository.findById(id).get();
	}

	public List<ActivityEntity> findAll(){
		List<ActivityEntity> list = new ArrayList<>();
		for(ActivityEntity row : activityRepository.findAll())
			list.add(row);
		return list;
	}

	public void save(ActivityEntity activity) {
		activityRepository.save(activity);
	}

	public void saveAll(List<ActivityEntity> list) {
		activityRepository.saveAll(list);
	}

	public long count() {
		return activityRepository.count();
	}

	public void deleteAll() {
		activityRepository.deleteAll();
	}

	public void deleteById(int id) {
		activityRepository.deleteById(id);
	}

	public ActivityEntity update(int id, ActivityEntity activity) {
		ActivityEntity currActivity = activityRepository.findById(id).get();	
		currActivity.setName(activity.getName());
		currActivity.setDesc(activity.getDesc());
		currActivity.setNum(activity.getNum());
		activityRepository.save(currActivity);
		return currActivity;
	}
}
