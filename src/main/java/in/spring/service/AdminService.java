package in.spring.service;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.spring.entity.Admin;
import in.spring.entity.Category;
import in.spring.entity.Product;
import in.spring.repository.AdminRepo;
import in.spring.repository.CateRepo;
import in.spring.repository.OrderRepo;
import in.spring.repository.ProductRepo;
import in.spring.repository.UserRepo;



@Service
public  class AdminService implements AdminInterface{

	@Autowired
	private AdminRepo arepo;
	
	@Autowired
	private ProductRepo prepo;
	
	@Autowired
	private CateRepo crepo;
	
	@Autowired
	private OrderRepo orepo;
	@Autowired
	private UserRepo urepo;
	
		
	

	@Override
	public boolean addProduct(Product p) {
		Product product=prepo.save(p);
		 if(product.getPid()!=null) {
            return true;
		 }
		 return false;
	}

	
	

	@Override
	public boolean deleteProduct(Integer pid) {
		  Optional<Product> product=prepo.findById(pid);
		  if(product.isPresent()) {
			      prepo.delete(product.get());
			      return true;
		  }
		
          return false;
	}

	@Override
	public Product getProduct(Integer pid) {
		Optional<Product> p=prepo.findById(pid);
		if(p.isPresent()) {
			return p.get();
		}
		return null;
	}

	@Override
	public List<Product> getAllProduct() {
		  return prepo.findAll();
		
	}

	@Override
	public Admin login(String email, String password) {
		return arepo.findByEmailAndPassword(email,password);
	}




	@Override
	public boolean adminReg(Admin admin) {
		Admin save = arepo.save(admin);
		if(save!=null) {
			return true;
		}
		return false;
	}




	@Override
	public List<String> getCategory() {
		List<String> allCategory = crepo.getAllCategory();
		return allCategory;
	}




	@Override
	public boolean saveCate(Category cate) {
		Category save = crepo.save(cate);
		if(save!=null) {
			return true;
		}
		
		return false;
	}




	@Override
	public int getOrders() {
		return orepo.countOrders();
		}




	@Override
	public int getUser() {
		
		return urepo.totalUser();
	}




	@Override
	public int getAmount() {
		// TODO Auto-generated method stub
		return orepo.totalAmount();
	}









	

}
