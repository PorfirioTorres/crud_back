package com.porfirio.service;

import java.util.List;

import org.hibernate.HibernateException;

import com.porfirio.model.Region;

public interface IRegionService {
	public abstract List<Region> getRegions() throws HibernateException;
}
