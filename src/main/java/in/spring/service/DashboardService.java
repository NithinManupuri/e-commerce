package in.spring.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import in.spring.binding.SearchFilter;
import in.spring.binding.Ticket;
import in.spring.entity.Orders;
import in.spring.entity.Product;
import in.spring.repository.OrderRepo;
import in.spring.repository.ProductRepo;
import in.spring.utils.SendMail;
@Service
public class DashboardService implements DashInterface {
	
	
	@Autowired
	private OrderRepo orepo;
	@Autowired
	private ProductRepo prepo;
	@Autowired
	private SendMail mail;
	
	

	@Override
	public List<Orders> getMyOrders(Integer uid) {
		return  orepo.findByUid(uid);
	}

	

	@Override
	public boolean getConfirmOrder(Integer uid, Integer pid,Integer total,Integer amount) {
		  
		Optional<Product> byId = prepo.findById(pid);
		
		
		if(byId.isPresent()){
		 Orders o=new Orders();
		 Product product=byId.get();
		 System.out.println(product.toString());
			 o.setUid(uid);
			 o.setPname(product.getPname());
			 o.setPrice(product.getPrice());
			 o.setQuantity(total);
			 o.setAmount(amount);
			 
			 orepo.save(o);
		   return true;
		 }
		return false;
	}

	@Override
	public boolean getContact(Ticket t,Integer uid) {
		String body="Cusmtore id "+uid+" customer mail "+t.getEmail()+" customer data= "+t.getData();
		   return mail.getMail("customer@gmail.com",body);
		    
		
	}

	@Override
	public boolean updateProduct(Integer pid,Integer total) {
		  Optional<Product> p= prepo.findById(pid);
		  if(p.isPresent()) {
			 Product product=p.get();
			 int sum=product.getQuantity()-total;
			 if(sum>=0) {
				 prepo.update(pid, sum);
				 
			
			 return true;
			 }
		  }return false;
	}
    public Product getProduct(Integer id) {
    	Optional<Product> p=prepo.findById(id);
    	  if(p.isPresent()) {
    		  return p.get();
    	  }
    	  return null;
    }

	@Override
	public List<String> getCategory() {
		    List<String> category = prepo.findDistinctCategories();
		    return category;
	}

	@Override
	public List<Product> filterProduct(SearchFilter sc) {
		Product p=new Product();
	  if(sc.getCategory()!=null &&!sc.getCategory().equals("")) {
		  p.setCategory(sc.getCategory());
		 
	  }
	  Example<Product> list=Example.of(p);
	  
		return prepo.findAll(list);
	}



	@Override
	public Page<Product> getProducts(int page, int pageSize) {
		
		PageRequest of=PageRequest.of(page, pageSize);
		  
		
		return prepo.findAll(of);
	}



	@Override
	public Page<Product> chooseProduct(String name,Integer page,Integer pageSize) {
	   
		
	
		PageRequest of=PageRequest.of(page, pageSize);
		Page<Product> byCategory = prepo.findByCategory(name,of);
		   
		return byCategory;
	}

	

}
