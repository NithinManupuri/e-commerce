package in.spring.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Category {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer cid;
	private String cname;
	@Lob
	@Column(name="catalogo",columnDefinition="LongBlob")
	private byte[] catalogo;

}
