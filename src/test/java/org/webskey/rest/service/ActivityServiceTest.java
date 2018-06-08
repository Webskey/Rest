package org.webskey.rest.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.webskey.rest.ActivityEntityBuilder;
import org.webskey.rest.model.ActivityEntity;
import org.webskey.rest.repository.ActivityRepository;

@RunWith(MockitoJUnitRunner.class)
public class ActivityServiceTest {
	
	private ActivityEntityBuilder builder = new ActivityEntityBuilder();
	
	@Mock
	private ActivityRepository activityRepository;
	
	@InjectMocks
	private ActivityService activityService;
	
	@Test
	public void test() {
		//given
		int id = 1;
		when(activityRepository.existsById(id)).thenReturn(true);
		//when
		boolean exist = activityService.exists(id);
		//then
		assertEquals(exist, true);
	}
	
	@Test
	public void findDuplicates() {
		//given
		List<ActivityEntity> list = builder.getActivityList();				
		when(activityRepository.existsById(1)).thenReturn(true);		
		when(activityRepository.existsById(3)).thenReturn(true);
		//when
		String duplicates = activityService.findDuplicates(list);	
		//then
		assertEquals(duplicates, " 1, 3,");
	}
	
	@Test
	public void findDuplicates2() {
		//given
		List<ActivityEntity> list = builder.getActivityList();
		//when
		String duplicates = activityService.findDuplicates(list);	
		//then
		assertEquals(duplicates.isEmpty(), true);
	}
	
	@Test
	public void findAll() {
		//given
		Iterable<ActivityEntity> x = builder.getActivityList();;
		when(activityRepository.findAll()).thenReturn(x);		
		//when
		List<ActivityEntity> list = activityService.findAll();
		//then
		assertEquals(x, list);
	}
	
	@Test
	public void findAll1() {
		//given		
		when(activityRepository.findAll()).thenReturn(new ArrayList<ActivityEntity>());		
		//when
		List<ActivityEntity> list = activityService.findAll();
		//then
		assertEquals(list, new ArrayList<ActivityEntity>());
	}
	
	@Test
	public void update() {
		//given		
		int id = 2;
		when(activityRepository.findById(id)).thenReturn(Optional.of(builder.getActivity()));		
		//when
		ActivityEntity activity = activityService.update(id, new ActivityEntity(123, "eman", "csed", 321));
		//then
		assertEquals(activity, builder.getActivity());
	}
	
	@Test
	public void update1() {
		//given		
		int id = 2;
		when(activityRepository.findById(id)).thenReturn(Optional.of(builder.getActivity()));		
		//when
		ActivityEntity activity = activityService.update(id, new ActivityEntity(123, "eman", "csed", 321));
		//then
		assertEquals(activity, builder.getActivity());
	}
}
