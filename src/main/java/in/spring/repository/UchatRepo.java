package in.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.spring.entity.UserChat;


@Repository
public interface UchatRepo  extends JpaRepository<UserChat,Integer>{

}
