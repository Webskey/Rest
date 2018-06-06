package org.webskey.rest.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.webskey.rest.model.ActivityEntity;
import org.webskey.rest.repository.ActivityRepository;

@RestController
public class Controller {
	
	static Logger log = LogManager.getLogger(Controller.class);
	
	@Autowired
	private ActivityRepository activityRepository;
	
	// GET
	@GetMapping("/activities/{id}")
	public ResponseEntity<?> getActivityById(@PathVariable int id) {	
		log.info("getActivityById");
		if(!activityRepository.findById(id).isPresent())
			return new ResponseEntity<String>("Activity with id: " + id + " not found.",
                    HttpStatus.NOT_FOUND);		
		return new ResponseEntity<ActivityEntity>(activityRepository.findById(id).get(), HttpStatus.OK);
	}

	@GetMapping("/activities/list")
	public List<ActivityEntity> getAllActivities() {
		List<ActivityEntity> list = new ArrayList<>();
		for(ActivityEntity row : activityRepository.findAll())
			list.add(row);
		return list;
	}	
	// SAVE
	@PostMapping("/activities/save")
	public ResponseEntity<ActivityEntity> saveActivity(@RequestBody ActivityEntity activity) {
		activityRepository.save(activity);
		return new ResponseEntity<ActivityEntity>(activity, HttpStatus.CREATED);
	}
	
	@PostMapping("/activities/saveAll")
	public ResponseEntity<?> saveAllActivities(@RequestBody List<ActivityEntity> list){
		activityRepository.saveAll(list);
		return new ResponseEntity<List<ActivityEntity>>(list, HttpStatus.CREATED);
	}	
	// DELETE
	@DeleteMapping("/activities/delete/{id}")
	public ResponseEntity<?> deleteActivityById(@PathVariable int id){
		activityRepository.deleteById(id);
		return new ResponseEntity<ActivityEntity>(HttpStatus.NO_CONTENT);
	}
	
	@DeleteMapping("/activities/deleteAll")
	public ResponseEntity<?> deleteAllActivities(){
		activityRepository.deleteAll();
		return new ResponseEntity<ActivityEntity>(HttpStatus.NO_CONTENT);
	}
	// UPDATE
	@PutMapping("/activities/update/{id}")
	public ResponseEntity<?> updateActivityById(@PathVariable int id, @RequestBody ActivityEntity activity){
		ActivityEntity currActivity = activityRepository.findById(id).get();
		currActivity.setName(activity.getName());
		currActivity.setDesc(activity.getDesc());
		currActivity.setNum(activity.getNum());
		activityRepository.save(currActivity);
		return new ResponseEntity<ActivityEntity>(HttpStatus.OK);
	}
}
