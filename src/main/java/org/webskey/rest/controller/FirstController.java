package org.webskey.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import org.webskey.rest.model.Activity;
import org.webskey.rest.service.ActivityService;

@RestController
public class FirstController {

	@Autowired
	private ActivityService activityService;

	@GetMapping("/activities/{actNum}")
	public Activity getOneActivity(@PathVariable int actNum) {
		return activityService.getAct(actNum);
	}

	@GetMapping("/activities/list")
	public List<Activity> getActivityList() {
		return activityService.getActs();
	}

	@RequestMapping(value = "/user/", method = RequestMethod.GET)
	public ResponseEntity<List<Activity>> listAllUsers() {		
		return new ResponseEntity<List<Activity>>(activityService.getActs(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/user/save", method = RequestMethod.POST)
    public ResponseEntity<Activity> createUser(@RequestBody Activity activity, UriComponentsBuilder ucBuilder) {
       
		activityService.saveAct(activity);
        
        return new ResponseEntity<Activity>(activity, HttpStatus.CREATED);
    }
}
