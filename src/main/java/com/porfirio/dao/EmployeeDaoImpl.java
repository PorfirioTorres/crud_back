package com.porfirio.dao;

import java.util.List;
import java.util.ResourceBundle;

import javax.persistence.ParameterMode;

import javax.persistence.StoredProcedureQuery;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.porfirio.model.Employee;
import com.porfirio.util.HibernateUtil;
import com.porfirio.util.Paginate;

@Repository
public class EmployeeDaoImpl implements IEmployeeDao {
	@Autowired
	private GenericDAO<Employee> genericDAO;
	
	
	@Override
	@Transactional(readOnly=true)
	public List<Employee> getAllEmployees() throws HibernateException{
		return genericDAO.selectAll(Employee.class);
	}

	@Override
	@Transactional(readOnly=true)
	public Employee getEmployee(Long id) throws HibernateException {
		return genericDAO.selectById(id, Employee.class);
	}

	@Override
	@Transactional(readOnly=true)
	public Paginate<Employee> getPage(int page) throws HibernateException {
		List<Employee> employees = null;
		Paginate<Employee> paginate = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		CriteriaBuilder cb = session.getCriteriaBuilder();
		CriteriaQuery<Employee> cq = cb.createQuery(Employee.class);
		Root<Employee> root = cq.from(Employee.class);
		cq.select(root);
		
        TypedQuery<Employee> q = session.createQuery(cq);
        
        try {
        	ResourceBundle rb = ResourceBundle.getBundle("config");
        	int pageSize = Integer.parseInt(rb.getString("pageSize"));
        	int totalPages = getTotalPages(pageSize, session, cb);
        	
        	if (page <= totalPages) {
        		q.setFirstResult((page-1) * pageSize);
        		q.setMaxResults(pageSize);
        		employees = q.getResultList();
        		
        		paginate = new Paginate<>();
        		paginate.setTotalPages(totalPages);
        		paginate.setElements(employees);
        		
        		if (page == 1) {
        			paginate.setFirst(true);
        			paginate.setLast(false);
        		} else {
        			if (page == totalPages) {
        				paginate.setFirst(false);
        				paginate.setLast(true);
        			} else {
        				paginate.setFirst(false);
        				paginate.setLast(false);
        			}
        		}
        	}
        	
    		
        } catch (NumberFormatException | HibernateException e) {
        	throw new RuntimeException("Error interno de la aplicación");
        } finally {
        	if (session != null) session.close();
        }
        
		return paginate;
	}
	
	
	private int getTotalPages (int pageSize, Session session, CriteriaBuilder cb) {
		CriteriaQuery<Long> criteq = cb.createQuery(Long.class);
		Root<Employee> root = criteq.from(Employee.class);
		criteq.multiselect(cb.count(root));
		long totalEntities = session.createQuery(criteq).getSingleResult();
		//System.out.println("No. entities " + totalEntities);
        int totalPages = (int)Math.ceil((double)totalEntities/pageSize);
        //System.out.println("No. pages " + totalPages);
        return totalPages;
	}

	@Override
	@Transactional(readOnly=true)
	public Object[] vacationalPerception(Long id, int years, int days) throws HibernateException {
		Object[] perceptions = new Object[3];
		perceptions[0] = (Integer)days;
		Session session = HibernateUtil.getSessionFactory().openSession();
		StoredProcedureQuery percepVacations = session.createStoredProcedureQuery("EMPLOYEESPACK.VACATIONAL_PERCEPTION");
		percepVacations.registerStoredProcedureParameter("IDEMPLOYEE", Long.class, ParameterMode.IN);
		percepVacations.registerStoredProcedureParameter("SERVICE_YEARS", Integer.class, ParameterMode.IN);
		percepVacations.registerStoredProcedureParameter("TOTAL_DAYS", Integer.class, ParameterMode.IN);
		percepVacations.registerStoredProcedureParameter("VACATIONAL_BONUS", Double.class, ParameterMode.OUT);
		percepVacations.registerStoredProcedureParameter("FULL_PERCEPTION", Double.class, ParameterMode.OUT);
		
		percepVacations.setParameter("IDEMPLOYEE", id);
		percepVacations.setParameter("SERVICE_YEARS", years);
		percepVacations.setParameter("TOTAL_DAYS", days);
		
		perceptions[1] = (Double) percepVacations.getOutputParameterValue("VACATIONAL_BONUS");
		perceptions[2] = (Double) percepVacations.getOutputParameterValue("FULL_PERCEPTION");
		
		System.out.println(perceptions);
		session.close();
		return perceptions;
	}

	@Override
	public void manageEmployee(Employee employee, int operation) throws HibernateException {
		genericDAO.managerEntity(employee, operation);
	}

	@Override
	public void deleteEmployee(Long id) throws HibernateException {
		genericDAO.delete(id, Employee.class);
	}

	@Override
	@SuppressWarnings({"rawtypes", "unchecked"})
	@Transactional
	public Boolean existsEmail(String email) throws HibernateException {
		Session session = HibernateUtil.getSessionFactory().openSession();
		String hql = "FROM Employee e WHERE e.email= :email ";
		
		Query query = session.createQuery(hql);
		query.setParameter("email", email);
		List<Employee> emp = query.list();
		
		return emp.size() == 0;
	}

}
