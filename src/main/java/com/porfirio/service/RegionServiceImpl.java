package com.porfirio.service;

import java.util.List;

import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.porfirio.dao.IRegionDao;
import com.porfirio.model.Region;

@Service
public class RegionServiceImpl implements IRegionService{
	@Autowired
	private IRegionDao regionDao;

	@Override
	public List<Region> getRegions() throws HibernateException {
		return regionDao.getRegions();
	}

}
