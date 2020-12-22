package com.porfirio.dao;

import java.io.Serializable;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.porfirio.util.HibernateUtil;

@Component
public class GenericDAO<T> {
	private Session session;
	
	@SuppressWarnings("unchecked")
	public List<T> selectAll(Class<T> entityClass) throws HibernateException{
		session = HibernateUtil.getSessionFactory().openSession();
		List<T> objs=null;
		try{
			objs = session.createQuery("FROM " + entityClass.getSimpleName()).list();
		}catch(HibernateException he){
			handleException(he);
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return objs;
	}
	
	public T selectById(Serializable id, Class<T> entity) throws HibernateException {
		session = HibernateUtil.getSessionFactory().openSession();
		T obj = null;
		try {
			obj = session.get(entity, id);
		}catch(HibernateException e) {
			handleException(e);
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return obj;
	}
	
	@Transactional
	public Serializable save(T entity) {
		session = HibernateUtil.getSessionFactory().openSession();
		
		try {
			session.beginTransaction();
			
			Serializable x = session.save(entity);
			
			session.getTransaction().commit();
			return x;
		} catch(HibernateException e) {
			session.getTransaction().rollback();
			handleException(e);
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return null;
	}
	
	@Transactional
	public void update(T entity) {
		session = HibernateUtil.getSessionFactory().openSession();
		
		try {
			session.beginTransaction();
			
			session.merge(entity);
			
			session.getTransaction().commit();
		} catch(HibernateException e) {
			session.getTransaction().rollback();
			handleException(e);
		} finally {
			if (session != null) {
				session.close();
			}
		}
		
	}
	
	@Transactional
	public void delete(Serializable id, Class<T> entity){
		session = HibernateUtil.getSessionFactory().openSession();
		try{
			session.beginTransaction();
			T obj = session.get(entity, id);
			
			if (obj != null) {
				session.delete(obj);
				session.flush();
			} 
			session.getTransaction().commit();
		}catch(HibernateException he){
			session.getTransaction().rollback();
			handleException(he);
		} finally {
			if (session != null) session.close();
		}
	}
	
	
	////////////////////////
	
	protected void handleException(HibernateException he) throws HibernateException{
		System.err.println(he.getMessage());
		throw he;
	}

	
	
}

