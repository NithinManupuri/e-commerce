package in.spring.entity;

import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
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
	private Date date;
	private Integer amount;
	@Lob
	@Column(name="image",columnDefinition="LongBlob")
	private byte[] image;
	private String category;

}
