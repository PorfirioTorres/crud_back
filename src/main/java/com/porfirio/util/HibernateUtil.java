package com.porfirio.util;

import javax.persistence.EntityManagerFactory;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HibernateUtil {
	
	private static SessionFactory sessionFactory;
	
	private HibernateUtil() {

	}
	
	@Autowired
	private void loadSessionFactory(EntityManagerFactory factory) {
	    if(factory.unwrap(SessionFactory.class) == null){
	      throw new NullPointerException("factory is not a hibernate factory");
	    }
	    System.out.println("SessionFactory construido");
	    sessionFactory = factory.unwrap(SessionFactory.class);
	}
	
	
	public static SessionFactory getSessionFactory() {
		if (sessionFactory != null) {
			return sessionFactory;
		} else {
			throw new RuntimeException("SessionFactory is null");
		}
		
	}

//	public void setSessionFactory(SessionFactory sessionFactory) {
//		this.sessionFactory = sessionFactory;
//	}
	
}
