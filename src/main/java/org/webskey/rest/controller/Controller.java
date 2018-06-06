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

	private static Logger log = LogManager.getLogger(Controller.class);

	@Autowired
	private ActivityRepository activityRepository;

	// GET
	@GetMapping("/activities/{id}")
	public ResponseEntity<?> getActivityById(@PathVariable int id) {			
		if(!activityRepository.findById(id).isPresent()) {
			log.error("Rerieving activity details with id: {} - negative(no such id).", id);
			return new ResponseEntity<String>("Activity with id: " + id + " not found.",
					HttpStatus.NOT_FOUND);	
		}
		log.info("Rerieving activity details with id: {} - positive.", id);
		return new ResponseEntity<ActivityEntity>(activityRepository.findById(id).get(), HttpStatus.OK);
	}

	@GetMapping("/activities/list")
	public ResponseEntity<?> getAllActivities() {
		List<ActivityEntity> list = new ArrayList<>();
		for(ActivityEntity row : activityRepository.findAll())
			list.add(row);
		if(list.isEmpty()) {
			log.error("Retrieving all activities failed. List is empty");
			return new ResponseEntity<String>("No activity found.", HttpStatus.NO_CONTENT);
		}		
		log.info("Rerieving all activities details - positive.");
		return new ResponseEntity<List<ActivityEntity>>(list, HttpStatus.OK);
	}	
	// SAVE
	@PostMapping("/activities/save")
	public ResponseEntity<?> saveActivity(@RequestBody ActivityEntity activity) {
		if(activityRepository.existsById(activity.getId())) {
			log.error("Saving new activity with id: {} - negative(already exists).", activity.getId());
			return new ResponseEntity<String>("Activity with id: " + activity.getId() + " already exists.",
					HttpStatus.CONFLICT);
		}
		activityRepository.save(activity);
		log.info("Saving new activity with id: {} - positive.", activity.getId());
		return new ResponseEntity<ActivityEntity>(activity, HttpStatus.CREATED);
	}

	@PostMapping("/activities/saveAll")
	public ResponseEntity<?> saveAllActivities(@RequestBody List<ActivityEntity> list){
		StringBuilder sb = new StringBuilder();
		boolean flag = false;
		for(ActivityEntity activity : list) {
			if(activityRepository.existsById(activity.getId())) {
				sb.append(" " + activity.getId() + ",");
				flag = true;
			}
		}
		if(flag) {
			log.error("Saving new activities failed. Activities with id:{}already exists.", sb);
			return new ResponseEntity<String>("Activities with id:" + sb + "already exists.", HttpStatus.CONFLICT);
		}
		activityRepository.saveAll(list);
		log.info("Saving new {} activities - positive.", list.size());
		return new ResponseEntity<List<ActivityEntity>>(list, HttpStatus.CREATED);
	}	
	// DELETE
	@DeleteMapping("/activities/delete/{id}")
	public ResponseEntity<?> deleteActivityById(@PathVariable int id){
		if(!activityRepository.existsById(id)) {
			log.info("Deleting activity with id: {} - negative(no such id).", id);
			return new ResponseEntity<String>("Activity with id: " + id + " not found.", HttpStatus.NOT_FOUND);
		}
		activityRepository.deleteById(id);
		log.info("Deleting activity with id: {} - positive.", id);
		return new ResponseEntity<ActivityEntity>(HttpStatus.NO_CONTENT);
	}

	@DeleteMapping("/activities/deleteAll")
	public ResponseEntity<?> deleteAllActivities(){
		if(activityRepository.count() == 0) {
			log.error("Deleting all activities - negative(list of activities is empty).");
			return new ResponseEntity<String>("List of activities is empty.", HttpStatus.NOT_FOUND);
		}
		activityRepository.deleteAll();
		log.info("Deleting all activities - positive.");
		return new ResponseEntity<ActivityEntity>(HttpStatus.NO_CONTENT);
	}
	// UPDATE
	@PutMapping("/activities/update/{id}")
	public ResponseEntity<?> updateActivityById(@PathVariable int id, @RequestBody ActivityEntity activity){
		if(!activityRepository.findById(id).isPresent()) {
			log.error("Updating activity with id: {} - negative(no such activity).", id);
			return new ResponseEntity<String>("Activity with id: " + id + " not found.", HttpStatus.NOT_FOUND);
		}
		ActivityEntity currActivity = activityRepository.findById(id).get();
		currActivity.setName(activity.getName());
		currActivity.setDesc(activity.getDesc());
		currActivity.setNum(activity.getNum());
		activityRepository.save(currActivity);
		log.info("Updating activity with id: {} - positive.", id);
		return new ResponseEntity<ActivityEntity>(HttpStatus.OK);
	}
}
