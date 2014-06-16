package org.family.spring.data.mongodb;

import java.util.List;
import java.util.Random;

import org.family.spring.data.mongodb.bean.DepartmentBean;
import org.family.spring.data.mongodb.bean.StaffBean;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestMongodbJoinSearch {
	public static void main(String[] args) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				new String[] { "mongodb.xml" });
		context.start();
		DepartmentRepository department=context.getBean(DepartmentRepository.class);
		StaffRepository staff=context.getBean(StaffRepository.class);
		/*//删除之前数据
		department.deleteAll();
		staff.deleteAll();
		//保存几个部门
		DepartmentBean departmentBean=new DepartmentBean();
		departmentBean.setDepartmentName("开发部门");
		department.save(departmentBean);
		//保存下边的用户
		staff.save(getStaffBean(departmentBean));
		staff.save(getStaffBean(departmentBean));
		staff.save(getStaffBean(departmentBean));
		
		departmentBean=new DepartmentBean();
		departmentBean.setDepartmentName("测试部门");
		department.save(departmentBean);
		//保存下边的用户
		staff.save(getStaffBean(departmentBean));
		staff.save(getStaffBean(departmentBean));
		staff.save(getStaffBean(departmentBean));
				
		departmentBean=new DepartmentBean();
		departmentBean.setDepartmentName("需求部门");
		department.save(departmentBean);
		//保存下边的用户
		staff.save(getStaffBean(departmentBean));
		staff.save(getStaffBean(departmentBean));
		staff.save(getStaffBean(departmentBean));*/
		
		//测试下关联查询
		List<StaffBean> rows=staff.findAll();
		for(StaffBean bean:rows){
			System.out.println(bean.getName());
			System.out.println(bean.getAge());
			System.out.println(bean.getDepartment().getDepartmentName());
		}
	}
	public static StaffBean getStaffBean(DepartmentBean departmentBean){
		StaffBean staffBean=new StaffBean();
		staffBean.setDepartment(departmentBean);
		staffBean.setAge(new Random().nextInt(60));
		staffBean.setName("员工"+new Random().nextInt(100000));
		return staffBean;
	}
}
