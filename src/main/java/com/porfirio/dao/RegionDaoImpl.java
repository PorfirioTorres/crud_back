package com.porfirio.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.porfirio.model.Region;

@Repository
public class RegionDaoImpl implements IRegionDao {
	@Autowired
	private GenericDAO<Region> genericDAO;

	@Override
	@Transactional(readOnly = true)
	public List<Region> getRegions() throws HibernateException {
		return genericDAO.selectAll(Region.class);
	}
	
}
