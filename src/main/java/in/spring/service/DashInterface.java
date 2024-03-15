package in.spring.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import in.spring.binding.SearchFilter;
import in.spring.binding.Ticket;
import in.spring.entity.Cart;
import in.spring.entity.Category;
import in.spring.entity.Orders;
import in.spring.entity.Product;


@Service
public interface DashInterface {

	public List<Orders> getMyOrders(Integer uid);
    public Page<Product> getProducts(int page,int pageSize);
  
    public List<Integer> updateProduct(List<Integer> id);
    public boolean getContact(Ticket t,Integer uid);
    public Product getProduct(Integer id);
    public List<Category> getCategory();
    public List<String> getC();
    public List<Product> filterProduct(SearchFilter sc);
    public Page<Product> chooseProduct(String name,Integer page,Integer pageSize);
    public boolean addCart(Cart cart);
    public List<Cart> getCartItems(Integer uid);
    public boolean remcart(Integer cid);
	public boolean getConfirmOrder(List<Product> id, List<Integer> updateProduct, List<Integer> removeCar,Integer uid);
	public List<Integer> removeCar(List<Integer> id);
	public List<Product> getAllPd(List<Integer> ids);
    
}
 