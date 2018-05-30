package org.webskey.rest.repositories;

import org.springframework.data.repository.CrudRepository;
import org.webskey.rest.model.ActivityEntity;

public interface ActivityRepository extends CrudRepository<ActivityEntity, Integer> { }