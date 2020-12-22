package com.porfirio.service;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.porfirio.dao.IEmployeeDao;
import com.porfirio.model.Employee;
import com.porfirio.util.Paginate;

@Service
public class EmployeeServiceImpl implements IEmployeeService {
	@Autowired
	private IEmployeeDao employeeDao;

	@Override
	public List<Employee> getAllEmployees() throws HibernateException{
		return employeeDao.getAllEmployees();
	}

	@Override
	public Employee getEmployee(Long id) throws HibernateException{
		return employeeDao.getEmployee(id);
	}

	@Override
	public Paginate<Employee> getPage(int page) throws HibernateException {
		return employeeDao.getPage(page);
	}

	@Override
	public Object[] vacationalPerception(Long id, String hdate) throws HibernateException {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		GregorianCalendar calendar = (GregorianCalendar) Calendar.getInstance();
		Object[] prestations = null;
		try {
			hdate = hdate.substring(0,10);
			System.out.println(hdate);
			ParsePosition position = new ParsePosition(0);
			Date finicial = sdf.parse(hdate, position);
			
			if (position.getIndex() < hdate.length()) {
				throw new ParseException("", 0);
			}
			
			Date factual = calendar.getTime();
			
			Calendar cinicial = Calendar.getInstance(Locale.US);
			cinicial.setTime(finicial);
			
			Calendar cactual = Calendar.getInstance(Locale.US);
			cactual.setTime(factual);
			
			int dif = cactual.get(Calendar.YEAR) - cinicial.get(Calendar.YEAR);
			
			if ((cactual.get(Calendar.MONTH) < cinicial.get(Calendar.MONTH)) ||
				(cactual.get(Calendar.MONTH) == cinicial.get(Calendar.MONTH) && 
				cactual.get(Calendar.DAY_OF_MONTH) < cinicial.get(Calendar.DAY_OF_MONTH))) {
				dif--;
			}
			
			if (dif > 0) {				
				int totalDays = 0;
				ResourceBundle rb = ResourceBundle.getBundle("config");
				if (dif >= 29) {
					totalDays = Integer.parseInt(rb.getString("v" + 29));
				} else {
					totalDays = Integer.parseInt(rb.getString("v" + dif));
				}
				
				prestations = employeeDao.vacationalPerception(id, dif, totalDays);
			} else {
				prestations = null;
			}
		} catch (ParseException e) {
			throw new RuntimeException("La fecha ingresada no es correcta");
		}
		
		return prestations;
	}

	@Override
	public Long save(Employee employee) throws HibernateException {
		return employeeDao.save(employee);
	}
	
	@Override
	public void update(Employee employee) throws HibernateException {
		employeeDao.update(employee);
	}

	@Override
	public void deleteEmployee(Long id) throws HibernateException {
		employeeDao.deleteEmployee(id);
		
	}

	@Override
	public Boolean existsEmail(String email, Long id) throws HibernateException {
		Employee emp = employeeDao.existsEmail(email);
		
		if (emp != null) {
			return emp.getEmployeeId() == id;
		}
		
		return true;
	}

}
