package org.ordersample.customerservice.model;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="Customers")
@Setter
@Getter
@NoArgsConstructor
public class Customer{

	@Id
	private String id; 
    private String name;
	
	public Customer(String id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

}
