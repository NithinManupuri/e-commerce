package in.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.spring.entity.Orders;

@Repository
public interface OrderRepo extends JpaRepository<Orders,Integer>{
	
	public List<Orders> findByUid(Integer uid);

}
