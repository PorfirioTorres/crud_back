package com.porfirio.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.porfirio.model.Employee;
import com.porfirio.service.IEmployeeService;
import com.porfirio.util.Paginate;

import com.porfirio.util.Constants;

@CrossOrigin(origins = { "*" })
@RestController
@RequestMapping(value = "/employees")
public class EmployeeRestController {
	@Autowired
	private IEmployeeService employeeService;

	@GetMapping(value = "/lista")
	public List<String> getLista() {
		List<String> lista = new ArrayList<>();
		lista.add("Hola");
		lista.add("Estoy");
		lista.add("Probando");

		System.out.println("/*************lista**********/");
		return lista;
	}

	@GetMapping(value = "/all")
	public ResponseEntity<?> getAllEmployees() {
		ResponseEntity<?> rEntity = null;
		List<Employee> employees = null;
		Map<String, Object> response = new HashMap<>();
		HttpStatus httpResponse;

		try {
			employees = employeeService.getAllEmployees();

			if (employees == null || employees.size() == 0) {
				response.put("message", "No hay empleados que mostrar");
				httpResponse = HttpStatus.NOT_FOUND;
			} else {
				response.put("employees", employees);
				httpResponse = HttpStatus.OK;
			}

			rEntity = new ResponseEntity<Map<String, Object>>(response, httpResponse);
		} catch (HibernateException ex) {
			response.put("message", ex.getMessage());
			rEntity = new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return rEntity;
	}

	@GetMapping(value = "/employee/{id}")
	public ResponseEntity<?> getEmployee(@PathVariable Long id) {
		ResponseEntity<?> rEntity = null;
		Employee employee = null;
		Map<String, Object> response = new HashMap<>();
		HttpStatus httpResponse;

		try {
			employee = employeeService.getEmployee(id);

			if (employee == null) {
				response.put("message", "La búsqueda no obtuvo resultados");
				httpResponse = HttpStatus.NOT_FOUND;
			} else {
				response.put("employee", employee);
				httpResponse = HttpStatus.OK;
			}
			rEntity = new ResponseEntity<Map<String, Object>>(response, httpResponse);
		} catch (HibernateException ex) {
			response.put("message", ex.getMessage());
			rEntity = new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return rEntity;
	}

	@GetMapping(value = "/page/{page}")
	public ResponseEntity<?> getPage(@PathVariable Integer page) {
		ResponseEntity<?> rEntity = null;
		Paginate<Employee> paginate = null;
		Map<String, Object> response = new HashMap<>();
		HttpStatus httpResponse;

		try {
			paginate = employeeService.getPage(page);
			if (paginate == null) {
				response.put("message", "No hay empleados que mostrar");
				httpResponse = HttpStatus.NOT_FOUND;
			} else {
				response.put("paginate", paginate);
				httpResponse = HttpStatus.OK;
			}

			rEntity = new ResponseEntity<Map<String, Object>>(response, httpResponse);
		} catch (HibernateException ex) {
			response.put("message", ex.getMessage());
			rEntity = new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return rEntity;
	}

	@GetMapping(value = "/vacations")
	public ResponseEntity<?> getVacationalPerceptions(@RequestParam Long id, @RequestParam String hdate) {
		ResponseEntity<?> rEntity = null;
		Map<String, Object> response = new HashMap<>();
		HttpStatus httpResponse;
		Object[] perceptions = new Double[2];
		// System.out.println(hdate.getClass().getSimpleName());
		System.out.println(hdate);

		try {
			perceptions = employeeService.vacationalPerception(id, hdate);

			if ((Double) perceptions[1] == 0 && (Double) perceptions[2] == 0) {
				response.put("message", "No existe un usuario con ese id");
				httpResponse = HttpStatus.NOT_FOUND;
			} else {
				response.put("days", perceptions[0]);
				response.put("bonus", perceptions[1]);
				response.put("total", perceptions[2]);
				httpResponse = HttpStatus.OK;
			}
			rEntity = new ResponseEntity<Map<String, Object>>(response, httpResponse);
		} catch (HibernateException ex) {
			response.put("message", ex.getMessage());
			rEntity = new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch(RuntimeException e) {
			response.put("message", e.getMessage());
			rEntity = new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_GATEWAY);
		}

		return rEntity;
	}
	
	@PostMapping(value = "/manage")
	public ResponseEntity<?> insert(@RequestBody Employee employee) {
		ResponseEntity<?> rEntity = null;
		Map<String, Object> response = new HashMap<>();

		
		try {
			employeeService.manageEmployee(employee, Constants.INSERT);
			
			response.put("success", "El empleado ha sido agregado con éxito");
			
			rEntity = new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
		} catch (HibernateException ex) {
			response.put("message", ex.getMessage());
			rEntity = new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch(RuntimeException e) {
			response.put("message", e.getMessage());
			rEntity = new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_GATEWAY);
		}

		return rEntity;
	}
	
	@PutMapping(value = "/manage")
	public ResponseEntity<?> update(@RequestBody Employee employee) {
		ResponseEntity<?> rEntity = null;
		Map<String, Object> response = new HashMap<>();
		HttpStatus httpResponse;
		
		try {
			Employee tmp = employeeService.getEmployee(employee.getEmployeeId());
			
			if (tmp == null) {
				response.put("message", "El empleado no existe en la base de datos");
				httpResponse = HttpStatus.NOT_FOUND;
			} else {
				employeeService.manageEmployee(employee, Constants.UPDATE);
				response.put("success", "El empleado ha sido actualizado con éxito");
				httpResponse = HttpStatus.CREATED;
			}
			
			
			rEntity = new ResponseEntity<Map<String, Object>>(response, httpResponse);
		} catch (HibernateException ex) {
			response.put("message", ex.getMessage());
			rEntity = new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch(RuntimeException e) {
			response.put("message", e.getMessage());
			rEntity = new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_GATEWAY);
		}

		return rEntity;
	}
	
	@DeleteMapping(value = "/manage/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		ResponseEntity<?> rEntity = null;
		Map<String, Object> response = new HashMap<>();
		HttpStatus httpResponse;
		Employee tmp = null;
		try {
			tmp = employeeService.getEmployee(id);
			
			if (tmp != null) {
				employeeService.deleteEmployee(id);
				response.put("success", "El empleado ha sido eliminado con éxito");
				httpResponse = HttpStatus.OK;
			} else {
				response.put("message", "El empleado no existe en la base de datos");
				httpResponse = HttpStatus.NOT_FOUND;
			}
			
			
			rEntity = new ResponseEntity<Map<String, Object>>(response, httpResponse);
		} catch (HibernateException ex) {
			response.put("message", ex.getMessage());
			rEntity = new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		} 

		return rEntity;
	}
	
	@GetMapping("/exists")
	public ResponseEntity<?> existsEmail(@RequestParam String email) {
		ResponseEntity<?> rEntity = null;
		Map<String, Object> response = new HashMap<>();
		
		try {
			if (employeeService.existsEmail(email)) {
				// el email no existe en la BD
				response.put("message", "email válido");
			} else {
				response.put("error", "El email ya existe en la BD");
			}
			rEntity = new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
		} catch (HibernateException ex) {
			response.put("message", ex.getMessage());
			rEntity = new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		} 
		
		return rEntity;
	}
}
