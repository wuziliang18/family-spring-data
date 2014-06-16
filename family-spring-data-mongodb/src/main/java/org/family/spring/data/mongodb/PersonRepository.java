package org.family.spring.data.mongodb;

import java.util.List;

import org.family.spring.data.mongodb.bean.Person;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;
//如果没有其他方法 则不用实现
public interface PersonRepository extends MongoRepository<Person, Long> {
	
}
