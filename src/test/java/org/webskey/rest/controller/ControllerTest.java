package org.webskey.rest.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.webskey.rest.ActivityEntityBuilder;
import org.webskey.rest.RestApplication;
import org.webskey.rest.model.ActivityEntity;
import org.webskey.rest.service.ActivityService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RestApplication.class)
@WebAppConfiguration
public class ControllerTest {

	private MockMvc mockMvc;
	private ActivityEntityBuilder builder = new ActivityEntityBuilder();

	@Mock
	private ActivityService activityService;

	@InjectMocks
	private Controller controller;

	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
		this.mockMvc =  MockMvcBuilders.standaloneSetup(controller).build();   
	}

	@Test
	public void getActivityById_activityExists_statusOk() throws Exception {
		//given
		int id = 1;
		when(activityService.exists(id)).thenReturn(true);
		when(activityService.findById(id)).thenReturn(builder.getActivity());
		//when
		mockMvc.perform(get("/activities/" + id))
		//then		
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))		
		.andExpect(jsonPath("$.id", is(id)))
		.andExpect(jsonPath("$.name", is("name")))
		.andExpect(jsonPath("$.desc", is("desc")))
		.andExpect(jsonPath("$.num", is(11)));

		verify(activityService, times(1)).findById(id);
		verify(activityService, times(1)).exists(id);
	}

	@Test
	public void getActivityById_activityDoesntExist_statusNotFound() throws Exception {
		//given
		int id = 1;
		when(activityService.exists(id)).thenReturn(false);
		when(activityService.findById(id)).thenReturn(builder.getActivity());
		//when
		mockMvc.perform(get("/activities/" + id))
		//then			
		.andExpect(content().string("Activity with id: " + id + " not found."))
		.andExpect(status().isNotFound());

		verify(activityService, times(0)).findById(id);
		verify(activityService, times(1)).exists(id);
	}

	@Test
	public void getAllActivities_activitiesExist_statusOk() throws Exception {
		//given
		when(activityService.findAll()).thenReturn(builder.getActivityList());
		//when
		mockMvc.perform(get("/activities/list"))
		//then		
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
		.andExpect(jsonPath("$", hasSize(5)))
		.andExpect(jsonPath("$[0].id", is(1)))
		.andExpect(jsonPath("$[0].name", is("name")))
		.andExpect(jsonPath("$[0].desc", is("desc")))
		.andExpect(jsonPath("$[0].num", is(11)));

		verify(activityService, times(1)).findAll();
	}

	@Test
	public void getAllActivities_activitiesDontExist_statusNoContent() throws Exception {
		//given
		when(activityService.findAll()).thenReturn(new ArrayList<ActivityEntity>());
		//when
		mockMvc.perform(get("/activities/list"))
		//then	
		.andExpect(content().string("No activity found."))
		.andExpect(status().isNoContent());

		verify(activityService, times(1)).findAll();
	}

	//SAVE
	@Test
	public void saveActivity_activityDoesntExist_statusCreated() throws Exception {
		//given
		doNothing().when(activityService).save(any());
		when(activityService.exists(anyInt())).thenReturn(false);
		//when
		mockMvc.perform(post("/activities/save").contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(builder.getJson()))
		//then
		.andExpect(jsonPath("name", is(builder.getActivity().getName())))
		.andExpect(content().json(builder.getJson()))
		.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))		
		.andExpect(status().isCreated());

		verify(activityService, times(1)).save(any());
		verify(activityService, times(1)).exists(anyInt());
	}

	@Test
	public void saveActivity_activityAlreadyExist_statusConflict() throws Exception {
		//given
		doNothing().when(activityService).save(any());
		when(activityService.exists(anyInt())).thenReturn(true);
		//when
		mockMvc.perform(post("/activities/save").contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(builder.getJson()))
		//then
		.andExpect(content().string("Activity with id: 1 already exists."))
		.andExpect(status().isConflict());

		verify(activityService, times(0)).save(any());
		verify(activityService, times(1)).exists(anyInt());
	}

	@Test
	public void saveAllActivities_activitiesDontExist_statusCreated() throws Exception {
		//given
		doNothing().when(activityService).saveAll(any());
		when(activityService.findDuplicates(any())).thenReturn("");
		//when
		mockMvc.perform(post("/activities/saveAll").contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(builder.getJsonList()))
		//then
		.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
		.andExpect(content().json(builder.getJsonList()))
		.andExpect(jsonPath("$[1].desc", is("desc2")))
		.andExpect(status().isCreated());

		verify(activityService, times(1)).saveAll(any());
		verify(activityService, times(1)).findDuplicates(any());
	}

	@Test
	public void saveAllActivities_activitiesExist_statusConflict() throws Exception {
		//given
		doNothing().when(activityService).saveAll(any());
		when(activityService.findDuplicates(any())).thenReturn(" 15,");
		//when
		mockMvc.perform(post("/activities/saveAll").contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(builder.getJsonList()))
		//then
		.andExpect(content().string("Activities with id:" + " 15," + "already exists."))
		.andExpect(status().isConflict());

		verify(activityService, times(0)).saveAll(any());
		verify(activityService, times(1)).findDuplicates(any());
	}

	//DELETE
	@Test
	public void deleteActivity_activityDoesExist_statusNoContent() throws Exception {
		//given
		int id = 1;
		doNothing().when(activityService).deleteById(id);
		when(activityService.exists(id)).thenReturn(true);
		//when
		mockMvc.perform(delete("/activities/delete/" + id))				
		//then
		.andExpect(status().isNoContent());

		verify(activityService, times(1)).deleteById(id);
		verify(activityService, times(1)).exists(id);
	}

	@Test
	public void deleteActivity_activityDoesntExist_statusNotFound() throws Exception {
		//given
		int id = 1;
		doNothing().when(activityService).deleteById(id);
		when(activityService.exists(id)).thenReturn(false);
		//when
		mockMvc.perform(delete("/activities/delete/" + id))	
		//then
		.andExpect(content().string("Activity with id: " + id + " not found."))
		.andExpect(status().isNotFound());

		verify(activityService, times(0)).deleteById(id);
		verify(activityService, times(1)).exists(id);
	}

	@Test
	public void deleteAllActivities_activitiesDoesExist_statusNoContent() throws Exception {
		//given
		doNothing().when(activityService).deleteAll();
		when(activityService.count()).thenReturn((long)3);
		//when
		mockMvc.perform(delete("/activities/deleteAll"))				
		//then
		.andExpect(status().isNoContent());

		verify(activityService, times(1)).deleteAll();
		verify(activityService, times(1)).count();
	}

	@Test
	public void deleteAllActivities_activitiesDontExist_statusNotFound() throws Exception {
		//given
		doNothing().when(activityService).deleteAll();
		when(activityService.count()).thenReturn((long)0);
		//when
		mockMvc.perform(delete("/activities/deleteAll"))				
		//then
		.andExpect(content().string("List of activities is empty."))
		.andExpect(status().isNotFound());

		verify(activityService, times(0)).deleteAll();
		verify(activityService, times(1)).count();
	}
	//UPDATE
	@Test
	public void updateActivity_activityExists_statusOk() throws Exception {
		//given
		int id = 1;
		ActivityEntity activity = builder.getActivity();		
		when(activityService.update(id, activity)).thenReturn(activity);
		when(activityService.exists(id)).thenReturn(true);
		//when
		mockMvc.perform(put("/activities/update/" + id).contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(builder.getJson()))			
		//then
		.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))		
		.andExpect(jsonPath("$.id", is(1)))
		.andExpect(jsonPath("$.name", is("name")))
		.andExpect(jsonPath("$.desc", is("desc")))
		.andExpect(jsonPath("$.num", is(11)))
		.andExpect(status().isOk());

		verify(activityService, times(1)).update(id, activity);
		verify(activityService, times(1)).exists(id);
	}

	@Test
	public void updateActivity_activityDoesntExist_statusNotFound() throws Exception {
		//given
		int id = 1;
		ActivityEntity activity = builder.getActivity();		
		when(activityService.update(id, activity)).thenReturn(activity);
		when(activityService.exists(id)).thenReturn(false);
		//when
		mockMvc.perform(put("/activities/update/" + id).contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(builder.getJson()))			
		//then		
		.andExpect(status().isNotFound());

		verify(activityService, times(0)).update(id, activity);
		verify(activityService, times(1)).exists(id);
	}
}
