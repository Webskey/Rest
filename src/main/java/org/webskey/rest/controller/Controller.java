package org.webskey.rest.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.webskey.rest.model.ActivityEntity;
import org.webskey.rest.repositories.ActivityRepository;

@RestController
public class Controller {

	@Autowired
	private ActivityRepository activityRepository;

	@GetMapping("/activities/{id}")
	public ResponseEntity<?> getActivityById(@PathVariable int id) {		
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

	@PostMapping("/user/save")
	public ResponseEntity<ActivityEntity> createUser(@RequestBody ActivityEntity activity) {

		activityRepository.save(activity);
		return new ResponseEntity<ActivityEntity>(activity, HttpStatus.CREATED);
	}
	
	@DeleteMapping("user/delete/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable int id){
		activityRepository.deleteById(id);
		return new ResponseEntity<ActivityEntity>(HttpStatus.NO_CONTENT);
	}
}
