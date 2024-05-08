package in.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.spring.entity.AdminChat;

@Repository
public interface AdminChatRepo  extends JpaRepository<AdminChat,Integer>{

   @Query("SELECT COUNT(a) FROM AdminChat a")
    public int countRows();

}
