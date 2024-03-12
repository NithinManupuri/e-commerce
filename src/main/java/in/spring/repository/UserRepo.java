package in.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import in.spring.entity.User;


@Repository
public interface UserRepo  extends JpaRepository<User,Integer>{
	
	public User findByEmailAndPassword(String email,String password);
	public User findByEmail(String email);
	
	@Query("select count(*) from User")
	public int totalUser();

}
