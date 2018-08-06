package org.webskey.rest.repositories;

import org.springframework.data.repository.CrudRepository;
import org.webskey.rest.entities.ActivityEntity;

public interface ActivityRepository extends CrudRepository<ActivityEntity, Integer> { }