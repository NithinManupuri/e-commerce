package in.spring.service;


import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import in.spring.binding.SearchFilter;
import in.spring.entity.Admin;
import in.spring.entity.Category;
import in.spring.entity.Product;
import in.spring.entity.UserChat;
import in.spring.repository.AdminChatRepo;
import in.spring.repository.AdminRepo;
import in.spring.repository.CateRepo;
import in.spring.repository.OrderRepo;
import in.spring.repository.ProductRepo;
import in.spring.repository.UchatRepo;
import in.spring.repository.UserRepo;
import in.spring.utils.SendMail;



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
	
	@Autowired
	private SendMail mail;
	
	@Autowired
	private AdminChatRepo adminChat;
	@Autowired
	private UchatRepo userChat;
	
		
	

	@Override
	public boolean addProduct(Product p,MultipartFile image) {
		if(image!=null && !image.isEmpty()) {
			try {
				p.setPimage(image.getBytes());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
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
	public boolean saveCate(Category cate,MultipartFile image) throws IOException {
		if(!image.isEmpty()&&image!=null) {
			cate.setCatalogo(image.getBytes());
			
		}
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




	@Override
	public List<Product> filter(SearchFilter sc) {
		Product p=new Product();
		  if(sc.getCategory()!=null &&!sc.getCategory().equals("")) {
			  p.setCategory(sc.getCategory());
			 
		  }
		  Example<Product> list=Example.of(p);
		  
			return prepo.findAll(list);
	}




	@Override
	public Page<Product> getPageAdmin(int page, int pageSize) {
		
		   PageRequest  of=PageRequest.of(page, pageSize);
		   return   prepo.findAll(of);
		 
	}

	@Override
	public boolean getPass(String name) {
		      Admin byEmail = arepo.findByEmail(name);
		      if(byEmail!=null) {
		    	  mail.getMail(byEmail.getEmail(), byEmail.getName());
		    	  return true;
		      }
		return false;
	}




	@Override
	public boolean recieveData() {
		// TODO Auto-generated method stub
		   long count = adminChat.count();
		   if(count!=0) {
			   return true;
		   }
		return false;
	}




	@Override
	public void acceptAndRemove() {
		 UserChat u=new UserChat();
		  u.setMessage("connected");
		  userChat.save(u);
		  adminChat.deleteAll();
		
	}

}
