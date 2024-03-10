package in.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.spring.entity.Admin;
import in.spring.entity.Product;


@Repository
public interface AdminRepo extends JpaRepository<Admin,Integer> {

	

	public Admin findByEmailAndPassword(String email, String password);

}
