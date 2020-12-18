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

import com.porfirio.model.Department;
import com.porfirio.service.IDepartmentService;

@CrossOrigin(origins= {"*"})
@RestController
@RequestMapping(value="/departments")
public class DepartmentRestController {
	@Autowired
	private IDepartmentService departmentService;
	
	@GetMapping(value="/all")
	public ResponseEntity<?> getAllDepartments() {
		ResponseEntity<?> rEntity = null;
		Map<String, Object> response = new HashMap<>();
		HttpStatus httpResponse;
		
		List<Department> departments = null;
		
		try {
			
			departments = departmentService.getDepartments();
			
			if (departments == null || departments.size() == 0) {
				response.put("message", "No hay departamentos que mostrar");
				httpResponse = HttpStatus.NOT_FOUND;
			} else {
				response.put("departments", departments);
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
