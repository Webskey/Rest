package org.webskey.rest.controller;

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
import org.webskey.rest.service.ActivityService;

@RestController
public class Controller {

	private static Logger log = LogManager.getLogger(Controller.class);

	@Autowired
	private ActivityService activityService;

	// GET
	@GetMapping("/activities/{id}")
	public ResponseEntity<?> getActivityById(@PathVariable int id) {			
		if(!activityService.exists(id)) {
			log.error("Rerieving activity details with id: {} - negative(no such id).", id);
			return new ResponseEntity<String>("Activity with id: " + id + " not found.",
					HttpStatus.NOT_FOUND);	
		}
		log.info("Rerieving activity details with id: {} - positive.", id);
		return new ResponseEntity<ActivityEntity>(activityService.findById(id), HttpStatus.OK);
	}

	@GetMapping("/activities")
	public ResponseEntity<?> getAllActivities() {
		List<ActivityEntity> list = activityService.findAll();		
		if(list.isEmpty()) {
			log.error("Retrieving all activities failed. List is empty");
			return new ResponseEntity<String>("No activity found.", HttpStatus.NO_CONTENT);
		}		
		log.info("Rerieving all activities details - positive.");
		return new ResponseEntity<List<ActivityEntity>>(list, HttpStatus.OK);
	}	
	// SAVE
	@PostMapping("/activities")
	public ResponseEntity<?> saveActivity(@RequestBody ActivityEntity activity) {
		if(activityService.exists(activity.getId())) {
			log.error("Saving new activity with id: {} - negative(already exists).", activity.getId());
			return new ResponseEntity<String>("Activity with id: " + activity.getId() + " already exists.",
					HttpStatus.CONFLICT);
		}
		activityService.save(activity);
		log.info("Saving new activity with id: {} - positive.", activity.getId());
		return new ResponseEntity<ActivityEntity>(activity, HttpStatus.CREATED);
	}

	@PostMapping("/activities/save-all")
	public ResponseEntity<?> saveAllActivities(@RequestBody List<ActivityEntity> list){
		String duplicates = activityService.findDuplicates(list);
		if(!duplicates.isEmpty()) {			
			log.error("Saving new activities failed. Activities with id:{}already exists.", duplicates);
			return new ResponseEntity<String>("Activities with id:" + duplicates + "already exists.", HttpStatus.CONFLICT);
		}
		activityService.saveAll(list);
		log.info("Saving new {} activities - positive.", list.size());
		return new ResponseEntity<List<ActivityEntity>>(list, HttpStatus.CREATED);
	}	
	// DELETE
	@DeleteMapping("/activities/{id}")
	public ResponseEntity<?> deleteActivityById(@PathVariable int id){
		if(!activityService.exists(id)) {
			log.info("Deleting activity with id: {} - negative(no such id).", id);
			return new ResponseEntity<String>("Activity with id: " + id + " not found.", HttpStatus.NOT_FOUND);
		}
		activityService.deleteById(id);
		log.info("Deleting activity with id: {} - positive.", id);
		return new ResponseEntity<ActivityEntity>(HttpStatus.NO_CONTENT);
	}

	@DeleteMapping("/activities")
	public ResponseEntity<?> deleteAllActivities(){
		if(activityService.count() == 0) {
			log.error("Deleting all activities - negative(list of activities is empty).");
			return new ResponseEntity<String>("List of activities is empty.", HttpStatus.NOT_FOUND);
		}
		activityService.deleteAll();
		log.info("Deleting all activities - positive.");
		return new ResponseEntity<ActivityEntity>(HttpStatus.NO_CONTENT);
	}
	// UPDATE
	@PutMapping("/activities/{id}")
	public ResponseEntity<?> updateActivityById(@PathVariable int id, @RequestBody ActivityEntity activity){
		if(!activityService.exists(id)) {
			log.error("Updating activity with id: {} - negative(no such activity).", id);
			return new ResponseEntity<String>("Activity with id: " + id + " not found.", HttpStatus.NOT_FOUND);
		}				
		log.info("Updating activity with id: {} - positive.", id);
		return new ResponseEntity<ActivityEntity>(activityService.update(id, activity), HttpStatus.OK);
	}
}
