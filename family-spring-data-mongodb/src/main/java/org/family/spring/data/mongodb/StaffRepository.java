package org.family.spring.data.mongodb;

import org.family.spring.data.mongodb.bean.StaffBean;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface StaffRepository extends MongoRepository<StaffBean,Long>{

}
