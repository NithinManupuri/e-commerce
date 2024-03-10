package in.spring.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Issues {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer issueId;
	private Integer uid;
	private String uname;
	private String description;
	private String productName;

	

}
