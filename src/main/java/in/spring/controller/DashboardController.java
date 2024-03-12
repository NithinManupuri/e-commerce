package in.spring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import in.spring.binding.ProductInfo;
import in.spring.binding.SearchFilter;
import in.spring.binding.Ticket;
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
			return "index";
		}
          int pageSize=3;

         Page<Product> p=dservice.getProducts(page,pageSize);
		
		List<String> str=dservice.getCategory(); 
		model.addAttribute("totalPages", p.getTotalPages());
		model.addAttribute("currentPage", page);
		
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
			return "index";
		}
	
	     Product prod=dservice.getProduct(pid);
	     ProductInfo info=new ProductInfo();
	     info.setPid(prod.getPid());
	     info.setPname(prod.getPname());
	     info.setPrice(prod.getPrice());
	    info.setQuantity(prod.getQuantity());
	    System.out.println(info.toString());
		model.addAttribute("product",info);
	     return "payment";

	}
	 @GetMapping("/getPayment")
	public String payment(@RequestParam Integer total,@RequestParam Integer amount,@RequestParam Integer pid,Model model,HttpServletRequest http) {
		 HttpSession session=http.getSession(false);
			Object o=session.getAttribute("ID");
			Integer uid=(Integer)o;
			if(uid==null) {
				return "index";
			}
		
			
	       boolean b=dservice.updateProduct(pid, total);
	       System.out.println(b);
	
		if(b) {
			boolean confirmOrder = dservice.getConfirmOrder(uid, pid,total,amount);
			System.out.println(confirmOrder);
		  model.addAttribute("msg","confirmed order");
		  return "Paymentstatus";
		}
		model.addAttribute("msg","Failed to order");
		return "Paymentstatus";
		 
	 }
	 
	 
	 @GetMapping("/ticket")
	 public String contact(Model model) {
		 model.addAttribute("ticket",new Ticket());
		 return "ticket";
		 
	}
	@PostMapping("/hc") 
	 public String handleContact(@ModelAttribute("ticket") Ticket ticket,Model model,HttpServletRequest http) {
	HttpSession session=http.getSession(false);
	  Object o=session.getAttribute("ID");
	  Integer id=(Integer)o;
	  if(o==null) {
		  return "index";
		  
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
	public String chooseProducts(@RequestParam(defaultValue="0")Integer page,@RequestParam String product,Model model) {
		
		int pageSize=1;
		Page<Product> p=dservice.chooseProduct(product,page,pageSize);
	  model.addAttribute("cat", product);
	    model.addAttribute("product", p.getContent());
	    model.addAttribute("totalPages", p.getTotalPages());
	    
		return "choose";
	}
	
	@PostMapping("/filter")
	public String search(@ModelAttribute("sc") SearchFilter sc,Model model,HttpServletRequest http){
		List<Product> p= dservice.filterProduct(sc);
              model.addAttribute("product", p);
              return "search";
	}
	
	@GetMapping("/orders")
	public String orders(Model model,HttpServletRequest http) {
		HttpSession session=http.getSession(false);
		  Object o=session.getAttribute("ID");
		  Integer uid=(Integer)o;
		  if(o==null) {
			  return "index";
		  }
		
		    List<Orders> myOrders = dservice.getMyOrders(uid);
		    model.addAttribute("orders", myOrders);
		    return "orders";
		
		
	}
	@GetMapping("/dashboard")
	
	public String dashing(Model model,HttpServletRequest http) {
		
		List<String> category = dservice.getCategory();
		model.addAttribute("cat", category);
		return "dashboard";
	}
		
}
	
	
	

