package in.spring.binding;

import jakarta.persistence.Column;
import jakarta.persistence.Lob;
import lombok.Data;

@Data
public class ProductInfo {
	private Integer pid;
	private String pname;
	private String category;
	private Integer total;
	private Integer price;
	private Integer quantity;
	@Lob
	@Column(name="image",columnDefinition="LongBlob")
    private byte[] pimage;
	

}
