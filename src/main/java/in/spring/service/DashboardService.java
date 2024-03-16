package in.spring.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import in.spring.binding.SearchFilter;
import in.spring.binding.SearchOrder;
import in.spring.binding.Ticket;
import in.spring.entity.AdminChat;
import in.spring.entity.Cart;
import in.spring.entity.Category;
import in.spring.entity.Orders;
import in.spring.entity.Product;
import in.spring.repository.AdminChatRepo;
import in.spring.repository.CartRepo;
import in.spring.repository.CateRepo;
import in.spring.repository.OrderRepo;
import in.spring.repository.ProductRepo;
import in.spring.repository.UchatRepo;
import in.spring.utils.SendMail;
@Service
public class DashboardService implements DashInterface {
	
	
	@Autowired
	private OrderRepo orepo;
	@Autowired
	private ProductRepo prepo;
	@Autowired
	private SendMail mail;
	@Autowired
	private CateRepo crepo;
	@Autowired
	private CartRepo cartrepo;
	@Autowired
	private UchatRepo urepo;
	@Autowired
    private  AdminChatRepo arepo;
	
	
	

	@Override
	public Page<Orders> getMyOrders(Integer uid,Integer page,Integer pageSize) {
		PageRequest pr=PageRequest.of(page, pageSize);
		return  orepo.findByUid(uid,pr);
	}

	



	@Override
	public boolean getContact(Ticket t,Integer uid) {
		String body="Cusmtore id "+uid+" customer mail "+t.getEmail()+" customer data= "+t.getData();
		   return mail.getMail("customer@gmail.com",body);
		    
		
	}

	@Override
	
	public List<Integer> updateProduct(List<Integer> idList) {

	    List<Cart> cartList = cartrepo.findAllById(idList);
	    System.out.println("heloo");
	    
	  List<Product> productList=new ArrayList<>();
	    List<Integer> productIdList = cartList.stream().map(Cart::getPid).collect(Collectors.toList());
	    for(int i=0;i<productIdList.size();i++) {
	    	Optional<Product> byId = prepo.findById(productIdList.get(i));
	    	if(byId.isPresent()) {
	    		Product product = byId.get();
	    		productList.add(product);
	    		
	    	}
	    }
System.out.println(productIdList.size());
	   
	    boolean allSumsNonNegative = true;
	    for (int i = 0; i < productList.size(); i++) {
	        Cart cart = cartList.get(i);
	        Product product = productList.get(i);
	       
	        if (cart != null && product != null) {
	           
	            int remainingQuantity = product.getQuantity() - cart.getTotal();
	            
	           
	            if (remainingQuantity < 0) {
	               
	                allSumsNonNegative = false;
	                break;
	            }
	        } else {
	           
	           
	            allSumsNonNegative = false;
	            break;
	        }
	    }
	  
	    
	   
	    if (allSumsNonNegative) {
	        
	        for (int i = 0; i < cartList.size(); i++) {
	            Cart cart = cartList.get(i);
	            Product product = productList.get(i);
	            
	           
	            int remainingQuantity = product.getQuantity() - cart.getTotal();
	            if(remainingQuantity!=0) {
	            	  prepo.update(product.getPid(), remainingQuantity);
	            	
	            }else {
	            	prepo.deleteById(product.getPid());
	            }
	         
	          
	          
	        }
	        // Return true indicating successful updates
	        return cartList.stream().map(m->m.getTotal()).collect(Collectors.toList());
	    } else {
	       
	        return null;
	    }
	}

    public Product getProduct(Integer id) {
    	Optional<Product> p=prepo.findById(id);
    	  if(p.isPresent()) {
    		  return p.get();
    	  }
    	  return null;
    }

	@Override
	public List<Category> getCategory() {
		    List<Category> category = crepo.findAll();
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



	@Override
	public List<String> getC() {
		
		return prepo.findDistinctCategories();
	}



	@Override
	public boolean addCart(Cart cart) {
		Cart save = cartrepo.save(cart);
		System.out.println(save.getPimage());
		if(save!=null) {
			return true;
		}
		return false;
	}



	@Override
	public List<Cart> getCartItems(Integer uid) {
		// TODO Auto-generated method stub
		List<Cart> byUid = cartrepo.findByUid(uid);
		return byUid;
	}



	@Override
	public boolean remcart(Integer cid) {
		// TODO Auto-generated method stub
		cartrepo.deleteById(cid);
		return true;
	}



	



	@Override
	public List<Integer> removeCar(List<Integer> id) {
		
		List<Cart> ls=cartrepo.findAllById(id);
		List<Integer> collect = ls.stream().map(m->m.getAmount()).collect(Collectors.toList());
		cartrepo.deleteAllById(id);

		
		
		
		return collect;
	}











	@Override
	public boolean getConfirmOrder(List<Product> id, List<Integer> updateProduct, List<Integer> removeCar,Integer uid) {
		  
		
		System.out.println("YUP-"+id.size());
				for(int i=0;i<id.size(); i++) {
				 Orders o=new Orders();
				 Product product=id.get(i);
					 o.setUid(uid);
					 o.setPname(product.getPname());
					 o.setPrice(product.getPrice());
					 o.setQuantity(updateProduct.get(i));
					 o.setAmount(removeCar.get(i));
					 o.setImage(product.getPimage());
					 o.setCategory(product.getCategory());
					 
					Orders od= orepo.save(o);
				}
				return true;
		
	}










	@Override
	public List<Product> getAllPd(List<Integer> ids) {
		List<Cart> c=cartrepo.findAllById(ids);
		
		List<Integer> pi=c.stream().map(m->m.getPid()).collect(Collectors.toList());
		List<Product> productList=new ArrayList<>();
		 for(int i=0;i<pi.size();i++) {
		    	Optional<Product> byId = prepo.findById(pi.get(i));
		    	if(byId.isPresent()) {
		    		Product product = byId.get();
		    		productList.add(product);
		    		
		    	}
		    }
		 return productList;
	}




	@Override
	public List<Orders> serachOrder(SearchOrder  sc) {
		  Orders o=new Orders();
		  if(sc.getCategory()!=null && !sc.getCategory().equals("")) {
			  o.setCategory(sc.getCategory());
			  
			  
		  }
		  if(sc.getDate()!=null && !sc.getDate().equals("")) {
			  o.setDate(sc.getDate());
		  }
		 
		  Example<Orders> ex=Example.of(o);
		  List<Orders> all = orepo.findAll(ex);
		 
		return all;
	}





	@Override
	public   boolean getChat() {
		  
		  long count = urepo.count();
		  if(count==0) {
			  return false;
		  }
       return true;

	
	
}







	@Override
	public boolean requestAdmin(String data) {
		AdminChat a=new AdminChat();
		a.setMessage(data);
		System.out.println(a.getMessage());
		AdminChat save = arepo.save(a);
		if(save!=null) {
			return true;
		}
		return false;
	}





	@Override
	public void delChat() {
		// TODO Auto-generated method stub
		urepo.deleteAll();
		
	}
	





}
