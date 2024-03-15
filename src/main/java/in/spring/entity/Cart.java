package in.spring.entity;

import java.util.Arrays;

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
@AllArgsConstructor
@NoArgsConstructor
public class Cart {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer cid;
	private Integer uid;
	private Integer pid;
	private String pname;
	private String category;
	private Integer total;
	private Integer amount;
	@Lob
	@Column(name="pimage",columnDefinition="LongBlob")
	private byte[] pimage;
	@Override
	public String toString() {
		return "Cart [cid=" + cid + ", uid=" + uid + ", pid=" + pid + ", pname=" + pname + ", category=" + category
				+ ", total=" + total + ", amount=" + amount + ", pimage=" + Arrays.toString(pimage) + "]";
	}
	

	

}
