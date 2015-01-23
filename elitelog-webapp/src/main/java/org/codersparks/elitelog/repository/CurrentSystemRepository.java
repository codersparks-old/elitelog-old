package org.codersparks.elitelog.repository;

import org.codersparks.elitelog.model.CurrentSystem;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

public interface CurrentSystemRepository extends MongoRepository<CurrentSystem, String> {

	
}
