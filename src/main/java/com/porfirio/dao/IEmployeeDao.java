package com.porfirio.dao;

import java.util.List;

import org.hibernate.HibernateException;

import com.porfirio.model.Employee;
import com.porfirio.util.Paginate;

public interface IEmployeeDao {
	public abstract List<Employee> getAllEmployees() throws HibernateException;
	public abstract Employee getEmployee(Long id) throws HibernateException;
	public abstract Paginate<Employee> getPage(int page) throws HibernateException;
	public abstract Object[] vacationalPerception(Long id, int years, int days) throws HibernateException;
	public abstract Long save(Employee employee) throws HibernateException;
	public abstract void update(Employee employee) throws HibernateException;
	public abstract void deleteEmployee(Long id) throws HibernateException;
	public abstract Employee existsEmail(String email) throws HibernateException;
}
