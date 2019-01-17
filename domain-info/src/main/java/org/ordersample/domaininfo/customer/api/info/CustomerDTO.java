package org.ordersample.domaininfo.customer.api.info;

public class CustomerDTO {
	
	private String id; 
    private String name;

	public CustomerDTO(){}
	
	public CustomerDTO(String id) {
		super();
		this.id = id;
	}

	public CustomerDTO(String id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}			
