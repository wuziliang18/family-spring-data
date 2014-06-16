package org.family.spring.data.mongodb;

import org.family.spring.data.mongodb.bean.DepartmentBean;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DepartmentRepository extends MongoRepository<DepartmentBean,Long>{
	
}
