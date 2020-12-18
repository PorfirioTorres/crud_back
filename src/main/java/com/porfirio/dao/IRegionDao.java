package com.porfirio.dao;

import java.util.List;

import org.hibernate.HibernateException;

import com.porfirio.model.Region;

public interface IRegionDao {
	public abstract List<Region> getRegions() throws HibernateException;
}
