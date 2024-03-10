package in.spring.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import in.spring.binding.SearchFilter;
import in.spring.binding.Ticket;
import in.spring.entity.Orders;
import in.spring.entity.Product;


@Service
public interface DashInterface {

	public List<Orders> getMyOrders(Integer uid);
    public Page<Product> getProducts(int page,int pageSize);
    public boolean getConfirmOrder(Integer uid,Integer total,Integer amount);
    public boolean updateProduct(Integer pid,Integer total);
    public boolean getContact(Ticket t,Integer uid);
    public Product getProduct(Integer id);
    public List<String> getCategory();
    public List<Product> filterProduct(SearchFilter sc);
    public Page<Product> chooseProduct(String name,Integer page,Integer pageSize);
   
}
 