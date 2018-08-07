package org.webskey.rest.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
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
import org.webskey.rest.entities.ActivityEntity;
import org.webskey.rest.repositories.ActivityRepository;

@RunWith(MockitoJUnitRunner.class)
public class ActivityServiceTest {
	
	private ActivityEntityBuilder builder = new ActivityEntityBuilder();
	
	@Mock
	private ActivityRepository activityRepository;
	
	@InjectMocks
	private ActivityService activityService;
	
	@Test
	public void exists_shouldReturnTrue_whenExistsByIdReturnsTrue() {
		//given
		int id = 1;
		when(activityRepository.existsById(id)).thenReturn(true);
		//when
		boolean exist = activityService.exists(id);
		//then
		assertEquals(exist, true);
	}
	
	@Test
	public void findDuplicates_shouldReturnStringOfDuplicates_whenDuplicatesFoundInGivenList() {
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
	public void findDuplicates_shouldReturnEmptyString_whenNoDuplicates() {
		//given
		List<ActivityEntity> list = builder.getActivityList();
		//when
		String duplicates = activityService.findDuplicates(list);	
		//then
		assertEquals(duplicates.isEmpty(), true);
	}
	
	@Test
	public void findAll_shouldReturnListOfActivies_whenFindAllMethodReturnsIterable() {
		//given
		Iterable<ActivityEntity> x = builder.getActivityList();;
		when(activityRepository.findAll()).thenReturn(x);		
		//when
		List<ActivityEntity> list = activityService.findAll();
		//then
		assertEquals(x, list);
	}
	
	@Test
	public void findAll_shouldReturnEmptyArrayList_whenNoActiviesInTable() {
		//given		
		when(activityRepository.findAll()).thenReturn(new ArrayList<ActivityEntity>());		
		//when
		List<ActivityEntity> list = activityService.findAll();
		//then
		assertEquals(list, new ArrayList<ActivityEntity>());
	}
	
	@Test
	public void update_shouldReturnChangedActivityEntityDetails_whenMethodCalledWithNewObject() {
		//given		
		int id = 2;
		when(activityRepository.findById(id)).thenReturn(Optional.of(builder.getActivity()));	
		//when
		ActivityEntity activity = activityService.update(id, new ActivityEntity(123, "eman", "csed", 321));
		//then
		assertEquals(activity.getName(), "eman");
		assertEquals(activity.getDes(), "csed");
		
		verify(activityRepository, times(1)).findById(id);
	}
	
	@Test(expected = NullPointerException.class)
	public void update_shouldThrowNullPointerException_whenMethodCalledWithNullArgument() {
		//given		
		int id = 2;
		when(activityRepository.findById(id)).thenReturn(Optional.of(builder.getActivity()));		
		//when
		ActivityEntity activity = activityService.update(id, null);
		//then
		assertNull(activity);
	}
}
