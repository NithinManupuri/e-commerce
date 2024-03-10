package in.spring.entity;

import java.time.LocalDate;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Orders {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer oid;
	private Integer uid;
	private String pname;
	private Integer quantity;
	private Integer price;
	@CreationTimestamp
	private LocalDate date;
	private Integer amount;

}
