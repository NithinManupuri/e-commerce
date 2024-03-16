package in.spring.service;

import java.io.IOException;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import in.spring.binding.SearchFilter;
import in.spring.entity.Admin;
import in.spring.entity.Category;
import in.spring.entity.Product;


@Service
public interface AdminInterface {
	
	

	public boolean addProduct(Product p,MultipartFile image);
	public boolean deleteProduct(Integer pid);
	public Product getProduct(Integer pid);
	public List<Product> getAllProduct();
	public Admin login(String email, String password);
	public boolean adminReg(Admin admin);
	public List<String> getCategory();
	
	public boolean saveCate(Category cate,MultipartFile image) throws IOException;
	public int getOrders();

	public int getUser();
	public List<Product> filter(SearchFilter sc);
	public int getAmount();
	
	public Page<Product> getPageAdmin(int page,int pageSize);
	public boolean getPass(String name);
	
	public boolean recieveData();
	public void acceptAndRemove();
	

}
