package in.spring.service;



import org.springframework.stereotype.Service;

import in.spring.entity.User;

@Service
public interface UserInferface {

      public User handleLogin(String email,String password);
      
      public boolean recoverPass(String email);
      
      public boolean register(User user);
      
      
    
       
      
      
}
