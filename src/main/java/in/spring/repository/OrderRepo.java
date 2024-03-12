package in.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import in.spring.entity.Orders;

@Repository
public interface OrderRepo extends JpaRepository<Orders,Integer>{
	
	public List<Orders> findByUid(Integer uid);
	
	@Query("SELECT COUNT(*) FROM Orders")
	public int countOrders();
	
	@Query("Select SUM(amount) FROM Orders")
	public int totalAmount();
	
	

}
