package com.porfirio.dao;

import java.util.List;

import org.hibernate.HibernateException;

import com.porfirio.model.Department;

public interface IDepartmentDao {
	public abstract List<Department> getDepartments() throws HibernateException;
}
