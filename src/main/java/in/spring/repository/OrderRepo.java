package in.spring.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import in.spring.entity.Orders;

@Repository
public interface OrderRepo extends JpaRepository<Orders,Integer>{
	
	public Page<Orders> findByUid(Integer uid, PageRequest pageRequest);
	
	@Query("SELECT COUNT(*) FROM Orders")
	public Long countOrders();
	
	@Query("Select SUM(amount) FROM Orders")
	public Long totalAmount();
	
	

}
