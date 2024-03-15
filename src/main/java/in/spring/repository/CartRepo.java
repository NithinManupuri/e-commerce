package in.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import in.spring.entity.Cart;

public interface CartRepo extends JpaRepository<Cart,Integer> {
	
	
	public List<Cart> findByUid(Integer uid);

}
