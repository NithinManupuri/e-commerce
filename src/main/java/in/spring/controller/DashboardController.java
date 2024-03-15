package in.spring.controller;


import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.fasterxml.jackson.databind.JsonMappingException;


import in.spring.binding.ProductInfo;
import in.spring.binding.SearchFilter;
import in.spring.binding.Ticket;
import in.spring.entity.Cart;
import in.spring.entity.Category;
import in.spring.entity.Orders;
import in.spring.entity.Product;
import in.spring.service.DashInterface;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class DashboardController {
	
	@Autowired
	private DashInterface dservice;
	
	
	@GetMapping("/products")
	public String  getProducts(@RequestParam(defaultValue="0") Integer page, Model model,HttpServletRequest http) {
	
		HttpSession session=http.getSession(false);
		Object o=session.getAttribute("ID");
		if(o==null) {
			System.out.println("sunny");
			return "redirect:/";
		}
          int pageSize=3;
          

         Page<Product> p=dservice.getProducts(page,pageSize);
		
		List<String> str=dservice.getC(); 
		model.addAttribute("totalPages", p.getTotalPages());
		model.addAttribute("currentPage", page);
		Map<Integer,String> map=new HashMap<>();
		List<Product> pr=p.getContent();
		for(Product product:pr) {
			 String base64Image = Base64.getEncoder().encodeToString(product.getPimage());
             map.put(product.getPid(), base64Image);
		}
		model.addAttribute("image", map);
		
		model.addAttribute("list", p.getContent());
		model.addAttribute("cat", str);
		model.addAttribute("sc", new SearchFilter());
		
		
		return "store";
	}
	@GetMapping("/buy")
	public  String buy(@RequestParam Integer pid,Model model,HttpServletRequest http) {
		HttpSession session=http.getSession(false);
		Object o=session.getAttribute("ID");
		if(o==null) {
			return "redirect:/";
		}
	
	     Product prod=dservice.getProduct(pid);
	     ProductInfo info=new ProductInfo();
	     info.setPid(prod.getPid());
	     info.setPname(prod.getPname());
	     info.setPrice(prod.getPrice());
	    info.setQuantity(prod.getQuantity());
	    info.setCategory(prod.getCategory());
	    System.out.println(info.toString());
		model.addAttribute("product",info);
	     return "payment";

	}
	@PostMapping("/addcart")
	public String cart(@ModelAttribute("cart") Cart cart,Model model,HttpServletRequest http) {
		HttpSession session=http.getSession(false);
		Object o=session.getAttribute("ID");
		if(o==null) {
			return "redirect:/";
		}
		System.out.println(cart.toString());
		Integer uid=(Integer)o;
		System.out.println(cart.toString());
		       cart.setUid(uid);
		     Product prod=dservice.getProduct(cart.getPid());
		     ProductInfo info=new ProductInfo();
		     info.setPid(prod.getPid());
		     info.setPname(prod.getPname());
		     info.setPrice(prod.getPrice());
		     info.setPimage(prod.getPimage());
		     System.out.println(info.getPimage());
		     cart.setPimage(prod.getPimage());

		    info.setQuantity(prod.getQuantity());
		
		   
			model.addAttribute("product",info);
		    boolean cart2 = dservice.addCart(cart);
		    
		    if(cart2) {
		    	model.addAttribute("msg", "Addded to cart");
		    }else {
		    	model.addAttribute("msg", "Failed to addded");
		    }
		     return "payment";
		
	}
	@GetMapping("/getPayment")
	public String payment(@RequestParam("cartList") List<Integer> cartList, Model model, HttpServletRequest http) throws JsonMappingException, JsonProcessingException {
	    HttpSession session = http.getSession(false);
	    Integer uid = (Integer) session.getAttribute("ID");
	    if (uid == null ) {
	        return "redirect:/";
	    }
	    System.out.println(cartList);
	    if(cartList.isEmpty()) {
	    	System.out.println("95");
	    	return "redirect:/addcart";
	    }
	   

	    List<Product> plist=dservice.getAllPd(cartList);

	    List<Integer> updateProduct = dservice.updateProduct(cartList);
	    System.out.println(updateProduct.toString());
	   
	    if (updateProduct != null) {
	    	 System.out.println(updateProduct.toString());
	        List<Integer> removeCar = dservice.removeCar(cartList);
	         System.out.println(removeCar.toString());
	        dservice.getConfirmOrder(plist, updateProduct, removeCar,uid);
	        model.addAttribute("msg", "confirmed order");
	        return "Paymentstatus";
	    }
	    model.addAttribute("msg", "Failed to order");
	    return "Paymentstatus";
	}

	 
	 
	 @GetMapping("/ticket")
	 public String contact(Model model,HttpServletRequest http) {
			HttpSession session=http.getSession(false);
			Object obj=session.getAttribute("ID");
			if(obj==null) {
				return "redirect:/";
			}
		 model.addAttribute("ticket",new Ticket());
		 return "ticket";
		 
	}
	@PostMapping("/hc") 
	 public String handleContact(@ModelAttribute("ticket") Ticket ticket,Model model,HttpServletRequest http) {
	HttpSession session=http.getSession(false);
	  Object o=session.getAttribute("ID");
	  Integer id=(Integer)o;
	  if(o==null) {
		  return "redirect:/";
		  
	  }
		boolean b=dservice.getContact(ticket, id);
		System.out.println(b);
		if(b) {
		model.addAttribute("msg"," we  will get back to  you");
		}
		model.addAttribute("msg","failed");
		return "ticket";
		
	}
	
	
	@GetMapping("/choose")
	public String chooseProducts(@RequestParam(defaultValue="0")Integer page,@RequestParam String product,Model model,HttpServletRequest http) {
		HttpSession session=http.getSession(false);
		Object obj=session.getAttribute("ID");
		if(obj==null) {
			return "redirect:/";
		}
		
		int pageSize=1;
		Page<Product> p=dservice.chooseProduct(product,page,pageSize);
		Map<Integer,String> map=new HashMap<>();
		List<Product> pr=p.getContent();
		for(Product pd:pr) {
			 String base64Image = Base64.getEncoder().encodeToString(pd.getPimage());
             map.put(pd.getPid(), base64Image);
		}
		model.addAttribute("image", map);
	  model.addAttribute("cat", product);
	    model.addAttribute("product", p.getContent());
	    model.addAttribute("totalPages", p.getTotalPages());
	    
		return "choose";
	}
	
	@PostMapping("/filter")
	public String search(@ModelAttribute("sc") SearchFilter sc,Model model,HttpServletRequest http){
		HttpSession session=http.getSession(false);
		Object obj=session.getAttribute("ID");
		if(obj==null) {
			return "redirect:/";
		}
		List<Product> p= dservice.filterProduct(sc);
		Map<Integer,String> map=new HashMap<>();
		
		for(Product product:p) {
			 String base64Image = Base64.getEncoder().encodeToString(product.getPimage());
             map.put(product.getPid(), base64Image);
		}
		model.addAttribute("image", map);
              model.addAttribute("product", p);
              return "search";
	}
	
	@GetMapping("/orders")
	public String orders(Model model,HttpServletRequest http) {
		HttpSession session=http.getSession(false);
		  Object o=session.getAttribute("ID");
		  Integer uid=(Integer)o;
		  if(o==null) {
			  return "redirect:/";
		  }
		
		    List<Orders> myOrders = dservice.getMyOrders(uid);
		    Map<Integer,String> map=new HashMap<>();
			
			for(Orders product:myOrders) {
				 String base64Image = Base64.getEncoder().encodeToString(product.getImage());
	             map.put(product.getOid(), base64Image);
			}
			model.addAttribute("image", map);
		    model.addAttribute("orders", myOrders);
		    return "orders";
		
		
	}
	@GetMapping("/dashboard")
	
	public String dashing(Model model,HttpServletRequest http) {
		HttpSession session=http.getSession(false);
		Object obj=session.getAttribute("ID");
		if(obj==null) {
			return "redirect:/";
		}
		
		
		  List<Category> category = dservice.getCategory();
		
		  
		   Map<Integer,String> map=new HashMap<>();
		for(Category product: category) {
			
			 String base64Image = Base64.getEncoder().encodeToString(product.getCatalogo());
            map.put(product.getCid(), base64Image);
		}
		model.addAttribute("image", map);
		model.addAttribute("cat", category);
		return "dashboard";
	}
		
	
	@GetMapping("/usercart")
	public String userCart(Model model,HttpServletRequest http) {
		HttpSession session=http.getSession(false);
		  Object o=session.getAttribute("ID");
		  Integer uid=(Integer)o;
		  if(o==null) {
			  return "redirect:/";
		  }
		
		List<Cart> cartItems = dservice.getCartItems(uid);
		System.out.println(cartItems.toString());
		 Map<Integer,String> map=new HashMap<>();
			for(Cart product: cartItems) {
				
				String base64Image = Base64.getEncoder().encodeToString(product.getPimage());
	            System.out.println(base64Image);
				map.put(product.getCid(), base64Image);
	            System.out.println(base64Image);
			}
		model.addAttribute("cart", cartItems);
		model.addAttribute("image", map);
		return "userCart";
	}
	@GetMapping("/removecart")
	public String removeCart(Model model,@RequestParam Integer cid,HttpServletRequest http) {
		HttpSession session=http.getSession(false);
		  Object o=session.getAttribute("ID");
		  Integer uid=(Integer)o;
		  if(o==null) {
			  return "redirect:/";
		  }
		dservice.remcart(cid);
		List<Cart> cartItems = dservice.getCartItems(uid);
		 Map<Integer,String> map=new HashMap<>();
			for(Cart product: cartItems) {
				
				 String base64Image = Base64.getEncoder().encodeToString(product.getPimage());
	            map.put(product.getCid(), base64Image);
	            System.out.println(base64Image);
			}
			
		model.addAttribute("cart", cartItems);
		model.addAttribute("image", map);
		return "userCart";
		
		
		
	}
}
	
	
	

