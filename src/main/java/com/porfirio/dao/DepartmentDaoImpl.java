package com.porfirio.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.porfirio.model.Department;

@Repository
public class DepartmentDaoImpl implements IDepartmentDao {
	@Autowired
	private GenericDAO<Department> genericDAO;
	
	@Override
	@Transactional(readOnly=true)
	public List<Department> getDepartments() throws HibernateException {
		return genericDAO.selectAll(Department.class);
	}
	
	
}
