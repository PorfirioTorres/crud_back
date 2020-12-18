package com.porfirio.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Paginate<T> implements Serializable {
	
	private Boolean first;
	private Boolean last;
	private List<T> elements = new ArrayList<>();
	private Integer totalPages;
	
	public Boolean getFirst() {
		return first;
	}
	
	public void setFirst(Boolean first) {
		this.first = first;
	}
	
	public Boolean getLast() {
		return last;
	}
	
	public void setLast(Boolean last) {
		this.last = last;
	}
	
	public List<T> getElements() {
		return elements;
	}
	
	public void setElements(List<T> elements) {
		this.elements = elements;
	}
	public Integer getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(Integer totalPages) {
		this.totalPages = totalPages;
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
}
