package com.porfirio.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="EMPLOYEES")
public class Employee implements Serializable{
	@Id
	@Column(name="EMPLOYEE_ID")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="employ_seq_gen")
	@SequenceGenerator(name="employ_seq_gen", allocationSize=1, sequenceName="EMPLOY_PORF_SEQ")
	private Long employeeId;
	
	@Column(name="FIRST_NAME")
	private String firstName;
	
	@Column(name="LAST_NAME")
	private String lastName;
	
	@Column(name="HIRE_DATE")
	private Date hireDate;
	
	@Column(name="email", unique = true)
	private String email;
	
	@ManyToOne
	@JoinColumn(name="REGION_ID")
	private Region region;
	
	@Column(name="SALARY")
	private Double salary;
	
	@Column(name="COMMISSION_PCT")
	private Double commissionPCT;
	
	@ManyToOne
	@JoinColumn(name="DEPARTMENT_ID")
	private Department department;

	public Long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getHireDate() {
		return hireDate;
	}

	public void setHireDate(Date hireDate) {
		this.hireDate = hireDate;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Region getRegion() {
		return region;
	}

	public void setRegion(Region region) {
		this.region = region;
	}

	public Double getSalary() {
		return salary;
	}

	public void setSalary(Double salary) {
		this.salary = salary;
	}

	public Double getCommissionPCT() {
		return commissionPCT;
	}

	public void setCommissionPCT(Double commissionPCT) {
		this.commissionPCT = commissionPCT;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}
	
	@Override
	public String toString() {
		return "Employee [employeeId=" + employeeId + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", hireDate=" + hireDate + ", email=" + email + ", salary=" + salary + ", commissionPCT="
				+ commissionPCT + "]";
	}



	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
