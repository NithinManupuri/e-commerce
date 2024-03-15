package in.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import in.spring.binding.Login;
import in.spring.binding.RecoverPass;
import in.spring.entity.User;
import in.spring.service.UserInferface;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class UserController {
	
	@Autowired
	private UserInferface uservice;
	@GetMapping("/")
	public String LoginForm(Model model) {
		
		model.addAttribute("login",new Login());
	
		return "index";
		
	}
	
	@PostMapping("/save")
	public String dash(HttpServletRequest request ,@ModelAttribute("login") Login login,Model model) {
		
		User user=uservice.handleLogin(login.getEmail(), login.getPassword());
		
		 if(user==null) {
			 
			 return "redirect:/";
		 }
	     
	     HttpSession session=request.getSession(true);
	     session.setAttribute("ID", user.getUid());
	       return "redirect:/dashboard";
	}
	
	@GetMapping("/recoverpass")
	public String recoverPass(Model model) {
		model.addAttribute("recover", new RecoverPass());
		return "recover";
	
	
	}
	@PostMapping("/recover")
	public String handleRecover(@ModelAttribute("recover") RecoverPass recover,Model model) {
	          boolean b= uservice.recoverPass(recover.getEmail());
	          if(b) {
	        	  model.addAttribute("succMsg", "Send to email ");
	        	  return "recover";
	          }
	          model.addAttribute("errMsg", "Invalid Email");
	          return "recover";
		
	}
	@GetMapping("/register")
	public String register(Model model) {
		model.addAttribute("user", new User());
		return "register";
	}
	
	@PostMapping("/reg")
	public String handleRegister(@ModelAttribute("user") User user,RedirectAttributes redirectAttributes) {
		System.out.println(user.toString());
		  boolean b= uservice.register(user);
		  System.out.println(b);
		  if(b) {
			  redirectAttributes.addFlashAttribute("msg","succesfully registered");
		  }else {
			  redirectAttributes.addFlashAttribute("msg","failed to register");
		  }
		
		  return "redirect:/register";
		
	}
	
	@GetMapping("/ulogout")
	public String logout(HttpServletRequest request) {
	    HttpSession session = request.getSession(false);

	    if (session != null) {
	        session.invalidate();
	    }

	    return "redirect:/"; 
	}

}
