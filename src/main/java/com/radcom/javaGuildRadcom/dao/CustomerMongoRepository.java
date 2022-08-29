package com.radcom.javaGuildRadcom.dao;

import com.radcom.javaGuildRadcom.model.mongo.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CustomerMongoRepository extends MongoRepository<Customer, String> {
}
