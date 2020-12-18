package com.porfirio.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.porfirio.model.Region;
import com.porfirio.service.IRegionService;

@CrossOrigin(origins= {"*"})
@RestController
@RequestMapping(value="/regions")
public class RegionRestController {
	@Autowired
	private IRegionService regionService;
	
	@GetMapping(value="/all")
	public ResponseEntity<?> getAllRegions() {
		ResponseEntity<?> rEntity = null;
		Map<String, Object> response = new HashMap<>();
		HttpStatus httpResponse;
		
		List<Region> regions = null;
		
		try {
			
			regions = regionService.getRegions();
			
			if (regions == null || regions.size() == 0) {
				response.put("message", "No hay regiones que mostrar");
				httpResponse = HttpStatus.NOT_FOUND;
			} else {
				response.put("regions", regions);
				httpResponse = HttpStatus.OK;
			}
			
			rEntity = new ResponseEntity<Map<String, Object>>(response, httpResponse);
			
		}catch (HibernateException ex) {
			
			response.put("error", ex.getMessage());
			rEntity = new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			System.out.println(ex.getCause().getMessage());
		}
		
		return rEntity;
	}
}
