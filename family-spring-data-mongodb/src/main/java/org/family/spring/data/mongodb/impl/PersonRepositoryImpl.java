package org.family.spring.data.mongodb.impl;

import java.util.List;

import org.family.spring.data.mongodb.PersonRepository;
import org.family.spring.data.mongodb.bean.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;
@Repository  
public class PersonRepositoryImpl {
	@Autowired
	private  MongoTemplate mongoTemplate;
	

	
	
}
