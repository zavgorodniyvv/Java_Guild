package com.radcom.javaGuildRadcom.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface CustomerMongoRepository extends MongoRepository<Customer, String> {
}
