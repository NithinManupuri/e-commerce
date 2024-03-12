package in.spring.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import in.spring.entity.Admin;
import in.spring.entity.Category;
import in.spring.entity.Product;
import in.spring.service.AdminInterface;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class AdminController {
	
	@Autowired
	private AdminInterface aservice;
	
	@GetMapping("/admin")
	public String admin(Model model) {
		model.addAttribute("admin", new Admin());
		return "adminPage";
	
	}
	
	@PostMapping("/saveAdmin")
	public String handleAdmin(@ModelAttribute("admin") Admin admin, Model model,HttpServletRequest http) {
	    System.out.println(admin.toString());

	    Admin a = aservice.login(admin.getEmail(), admin.getPassword());
       System.out.println(a);
	    if (a == null) {
	    	 model.addAttribute("msg", "InvalidCredentials");
	        return "redirect:/admin";
	    } 
	        
	   
	       HttpSession session=http.getSession(true);
	       session.setAttribute("AID", a.getAid());
	       
	        return "redirect:/dash";
	    
	}
	@GetMapping("/dash")
	public String dash(Model model) {
		  int orders = aservice.getOrders();
		   int amount= aservice.getAmount();
	      int users= aservice.getUser();
		  model.addAttribute("number",orders);
		  model.addAttribute("user",users);
		  model.addAttribute("amount",amount);
		  return "dash";
		
	}


	@GetMapping("/add")
	public String addProduct(Model model,HttpServletRequest http) {
		HttpSession session=http.getSession(false);
		Object obj=session.getAttribute("AID");
		if(obj==null) {
			return "redirect:/admin";
		}
		 List<String> category = aservice.getCategory();
		model.addAttribute("cat", category);
		model.addAttribute("product",new Product());
		return "addProduct";
	}

@PostMapping("/saveProduct")
public String saveProduct(@ModelAttribute("product") Product product, Model model) {
    
        boolean flag = aservice.addProduct(product);

        if (flag) {
            model.addAttribute("msg", "Product added successfully");
        } else {
            model.addAttribute("msg", "Product addition failed");
        }
        List<String> category = aservice.getCategory();
		model.addAttribute("cat", category);
		model.addAttribute("product",new Product());


    return "addProduct";
}
	
	
	@GetMapping("/update")
	public String update(@RequestParam Integer pid,Model model) {
		
		 model.addAttribute("product",aservice.getProduct(pid));
		 return "addProduct";
		
	}
	@GetMapping("/allProduct")
	public String productStore(Model model,HttpServletRequest http) {
		HttpSession session=http.getSession(false);
		Object obj=session.getAttribute("AID");
		if(obj==null) {
			return "redirect:/admin";
		}
		 List<Product> productList = aservice.getAllProduct();

		    model.addAttribute("list", productList);
		    return "productStore";
	}
	
	@GetMapping("/remove")
	public String delete(@RequestParam Integer pid,Model model,HttpServletRequest http) {
		HttpSession session=http.getSession(false);
		Object obj=session.getAttribute("AID");
		if(obj==null) {
			return "redirect:/admin";
		}
		       aservice.deleteProduct(pid);
		
			model.addAttribute("list",aservice.getAllProduct());
			return "productStore";
		
	}
	
	
	@GetMapping("/logout")
	public String logout(HttpServletRequest request) {
	    HttpSession session = request.getSession(false);

	    if (session != null) {
	        session.invalidate();
	    }

	    return "redirect:/admin"; 
	}
	@GetMapping("/adminreg")
	public String registerAdmin(Model model) {
		model.addAttribute("admin", new Admin());
		return "adminReg";
		
	}
	@PostMapping("/regAdmin")
	public String saveAdminReg(@ModelAttribute("admin") Admin admin,Model model){
		
		boolean adminReg = aservice.adminReg(admin);
		if(adminReg) {
			model.addAttribute("msg", "Registered");
		}else {
			model.addAttribute("msg", "Failed ");
		}
		return "adminReg";
	}

	@GetMapping("/addCategory")
	public String addCategory(Model model) {
		model.addAttribute("category", new Category());
		return "addCategory";
		
	}
	
	@PostMapping("/saveCate")
	public String saveCategory(@ModelAttribute("cate") Category cate,Model model ) {
		
		boolean saveCate = aservice.saveCate(cate);
		if(saveCate) {
			model.addAttribute("msg","Added");
		}else {
			model.addAttribute("msg", "failed");
			
		}
		model.addAttribute("category", new Category());
		return "addCategory";
		
	}
	  

	
	
	
	
	
}
