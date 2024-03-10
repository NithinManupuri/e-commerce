package in.spring.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import in.spring.entity.Product;


@Repository
public interface ProductRepo extends JpaRepository<Product,Integer>{
	
	@Transactional
	@Modifying
	@Query("update Product p set p.quantity = :quantity where p.id = :pid")

	public void update(Integer pid,Integer quantity);
	
	
	
	@Query("SELECT DISTINCT category FROM Product")
	public List<String> findDistinctCategories();
	
	
	
	public Page<Product> findByCategory(String name,Pageable pageable);



}
