package in.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.spring.entity.User;
import in.spring.repository.UserRepo;
import in.spring.utils.SendMail;
@Service
public class UserService implements UserInferface{
	
	@Autowired
	private UserRepo urepo;
	
	@Autowired
	private SendMail mail;

	@Override
	public User handleLogin(String email, String password) {
		  return urepo.findByEmailAndPassword(email,password);
	}

	@Override
	public boolean recoverPass(String email) {
	       User user=urepo.findByEmail(email);
		if(user==null){
			return false;
		}
	    return mail.getMail(user.getEmail(),user.getPassword());
		
	}

	@Override
	public boolean register(User user) {
		  User u=urepo.findByEmail(user.getEmail());
		  if(u==null) {
			  urepo.save(user);
			  return true;
		  }
		return false;
	}

}
