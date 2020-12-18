package com.porfirio.service;

import java.util.List;

import org.hibernate.HibernateException;

import com.porfirio.model.Employee;
import com.porfirio.util.Paginate;

public interface IEmployeeService {
	public abstract List<Employee> getAllEmployees() throws HibernateException;
	public abstract Employee getEmployee(Long id) throws HibernateException;
	public abstract Paginate<Employee> getPage(int page) throws HibernateException;
	public abstract Object[] vacationalPerception(Long id, String hdate) throws HibernateException;
	public abstract void manageEmployee(Employee employee, int operation) throws HibernateException;
	public abstract void deleteEmployee(Long id) throws HibernateException;
	public abstract Boolean existsEmail(String email) throws HibernateException;
}
