package org.ordersample.orderviewservice.repository;

import org.ordersample.orderviewservice.model.*;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends MongoRepository<Customer,String>{

}			
