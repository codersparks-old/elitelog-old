package org.codersparks.elitelog.repository;

import org.codersparks.elitelog.model.SystemVisit;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SystemVisitRepository extends MongoRepository<SystemVisit, String> {

}
