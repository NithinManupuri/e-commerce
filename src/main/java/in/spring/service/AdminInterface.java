package in.spring.service;

import java.util.List;

import org.springframework.stereotype.Service;

import in.spring.entity.Admin;
import in.spring.entity.Category;
import in.spring.entity.Product;


@Service
public interface AdminInterface {
	
	

	public boolean addProduct(Product p);
	public boolean deleteProduct(Integer pid);
	public Product getProduct(Integer pid);
	public List<Product> getAllProduct();
	public Admin login(String email, String password);
	public boolean adminReg(Admin admin);
	public List<String> getCategory();
	public boolean saveCate(Category cate);

}
