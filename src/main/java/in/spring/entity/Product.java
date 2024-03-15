package in.spring.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Transient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Product {
	
	 @Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer pid;
	private String pname;
	private String category;
	private Integer price;
	private Integer quantity;
	
	private Integer e;
	 
	@Lob
	@Column(name="image",columnDefinition="LongBlob")
	private byte[] pimage;
	  
}
