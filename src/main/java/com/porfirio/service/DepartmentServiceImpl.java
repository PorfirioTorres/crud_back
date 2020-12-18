package com.porfirio.service;

import java.util.List;

import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.porfirio.dao.IDepartmentDao;
import com.porfirio.model.Department;

@Service
public class DepartmentServiceImpl implements IDepartmentService{
	@Autowired
	private IDepartmentDao departmentDao;

	@Override
	public List<Department> getDepartments() throws HibernateException {
		return departmentDao.getDepartments();
	}

}
