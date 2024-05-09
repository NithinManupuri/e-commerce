package in.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import in.spring.entity.Category;

public interface CateRepo extends JpaRepository<Category,Integer> {
	
	@Query(value = "SELECT cname FROM category", nativeQuery = true)
	public List<String> getAllCategory();

}
