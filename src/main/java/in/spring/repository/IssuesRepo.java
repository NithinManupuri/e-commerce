package in.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.spring.entity.Issues;


@Repository
public interface IssuesRepo  extends JpaRepository<Issues,Integer>{
	
}
