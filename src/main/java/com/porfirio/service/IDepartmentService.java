package com.porfirio.service;

import java.util.List;

import org.hibernate.HibernateException;

import com.porfirio.model.Department;

public interface IDepartmentService {
	public abstract List<Department> getDepartments() throws HibernateException;
}
