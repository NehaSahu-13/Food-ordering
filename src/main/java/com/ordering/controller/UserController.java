package com.ordering.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.ordering.Entities.Address;
import com.ordering.Entities.Cart;
import com.ordering.Entities.CartItem;
import com.ordering.Entities.Food;
import com.ordering.Entities.Member;
import com.ordering.Entities.OrderItem;
import com.ordering.Entities.Restaurant;
import com.ordering.Entities.User;
import com.ordering.dao.CartItemRepository;
import com.ordering.dao.CartRepository;
import com.ordering.dao.FoodRepository;
import com.ordering.dao.OrderItemRepository;
import com.ordering.dao.RestaurantRepository;
import com.ordering.dao.UserRepository;
import com.ordering.helper.Message;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/customer")
public class UserController {

	@Autowired
	private RestaurantRepository restaurantRepository;
	
	@Autowired
	private FoodRepository foodRepository;
	
	@Autowired
	private CartRepository cartRepository;
	
	@Autowired
	private CartItemRepository cartItemRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private OrderItemRepository orderItemRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	
	
	@RequestMapping("/search")
	public String search(Model model,HttpSession session) {
		String username=(String) session.getAttribute("login_user");
		User user=userRepository.findByEmail(username);
		model.addAttribute("loginuser", user);
		model.addAttribute("title","Search");
		String city=(String) session.getAttribute("city");
		String food=(String) session.getAttribute("food");
		List<Restaurant>restaurants=restaurantRepository.findRestaurantsByNameAndCity(food,city);
		String search=(String) session.getAttribute("search");
		if(restaurants.isEmpty()&&search!=null) {
			model.addAttribute("result","No Result Found.......");
		}else {
			model.addAttribute("restaurants",restaurants);
		}
		return "user/search";
	}
	
	@RequestMapping("/userdashboard")
	public String userdashboard(Model model,HttpSession session) {
		String username=(String) session.getAttribute("login_user");
		User user=userRepository.findByEmail(username);
		model.addAttribute("loginuser", user);
		model.addAttribute("title","Orders");
		return "redirect:/customer/orders";
	}
	
	@RequestMapping("/logout")
	public String userlogout(HttpSession session) {
		session.removeAttribute("login_user");
		return "redirect:/";
	}
	
	@RequestMapping("/home")
	public String customerHome(Model model,HttpSession session) {
		String city=(String) session.getAttribute("city");
		List<Restaurant> restaurants=restaurantRepository.findRestaurantsByCity(city);
		if(restaurants!=null) {
		model.addAttribute("restaurants",restaurants);
		}
		String username=(String) session.getAttribute("login_user");
		User user=userRepository.findByEmail(username);
		model.addAttribute("loginuser", user);
		model.addAttribute("title","Home");
		session.removeAttribute("food");
		session.removeAttribute("search");
		return "user/customerHome";
	}
	
	@PostMapping("/specifylocation")
	public String location(@RequestParam("city") String city,HttpSession session) {
	    session.setAttribute("city",city);
		return "redirect:/customer/home";
	}
	
	@RequestMapping("/foodItem/{foodname}")
	public String foodItem(@PathVariable("foodname") String foodname,HttpSession session,Model model) {
		String username=(String) session.getAttribute("login_user");
		User user=userRepository.findByEmail(username);
		model.addAttribute("loginuser", user);
		String city=(String) session.getAttribute("city");
		List<Food>foods=foodRepository.findFoodsByCityAndFoodName(city, foodname);
		model.addAttribute("foods",foods);
		model.addAttribute("title","FoodItems");
		model.addAttribute("foodname",foodname);
		return "user/topmealsfooditem";
	}
	
	@RequestMapping("/restaurantdetail/{restaurantname}")
	public String restaurantDetails(@PathVariable("restaurantname") String name,HttpSession session,Model model) {
		String username=(String) session.getAttribute("login_user");
		User user=userRepository.findByEmail(username);
		model.addAttribute("loginuser", user);
		Restaurant restaurant=restaurantRepository.findByName(name);
		model.addAttribute("Restaurant",restaurant);
		List<Food>foods=restaurant.getFoods();
		model.addAttribute("foods",foods);
		return "user/restaurantDetails";
	}
	
	@PostMapping("/searchfood")
	public String searchfood(@RequestParam("food") String food,HttpSession session,Model model) {
		session.setAttribute("search","search");
		session.setAttribute("food",food);
		return "redirect:/customer/search";
	}
	
	@PostMapping("/add-to-cart/{foodItem}")
	public String add_to_cart(@PathVariable("foodItem") Food foodItem,@RequestParam("quantity") String quantity,HttpSession session,HttpServletRequest request) {
		 int quant=Integer.parseInt(quantity);
		 String username=(String) session.getAttribute("login_user");
		User user=userRepository.findByEmail(username);
		Cart cart=user.getCart();
		CartItem cartItem=new CartItem();
		cartItem.setFood(foodItem);
		cartItem.setQuantity(quant);
		cartItem.setTotalprice(foodItem.getPrice()*quant);
		cartItem.setCart(cart);
		cart.getCartItems().add(cartItem);
		long total=cart.getTotal();
		total=total+cartItem.getTotalprice();
		cart.setTotal(total);
		userRepository.save(user);
		 String referrer = request.getHeader("Referer");

		    return "redirect:" + referrer;
	}
	
	@PostMapping("/update-cartItem/{cartItemId}")
	public String updateCartItem(@PathVariable("cartItemId") int cartItemId,@RequestParam("quantity") String quantity,HttpSession session) {
	 
		int quant=Integer.parseInt(quantity);
		String username=(String) session.getAttribute("login_user");
		User user=userRepository.findByEmail(username);
		CartItem cartItem=cartItemRepository.findById(cartItemId).get();
		Cart cart=user.getCart();
		long total=cart.getTotal();
		long cartItemtotal=cartItem.getTotalprice();
		long updatedCartItemtotal=quant*cartItem.getFood().getPrice();
		cartItem.setQuantity(quant);
		cartItem.setTotalprice(updatedCartItemtotal);
		System.out.println("ca id "+cartItem.getId());
		cart.setTotal(total-cartItemtotal+updatedCartItemtotal);
//		cartItemRepository.save(cartItem);
//		cartRepository.save(cart);
//		System.out.println("quantity "+c.getQuantity());
//		System.out.println("id "+c.getId());
		userRepository.save(user);
				
		
        return "redirect:/customer/cart";
	}
	
	
	@RequestMapping("/cart")
	public String usercart(HttpSession session,Model model) {
		String username=(String) session.getAttribute("login_user");
		User user=userRepository.findByEmail(username);
		Cart cart=user.getCart();
		List<CartItem>cartItems=cart.getCartItems();
		model.addAttribute("loginuser",user);
		model.addAttribute("cartItems",cartItems);
		model.addAttribute(cart);
		return "user/usercart";
	}
	
	@RequestMapping("/removecartItem/{cartItemId}")
	public String removeCartItem(@PathVariable("cartItemId") int cartItemId,HttpSession session,Model model) {
		String username=(String) session.getAttribute("login_user");
		User user=userRepository.findByEmail(username);
		CartItem cartItem=cartItemRepository.findById(cartItemId).get();
		Cart cart=user.getCart();
		long total=cart.getTotal();
		total=total-cartItem.getTotalprice();
		cart.setTotal(total);
	    cart.getCartItems().remove(cartItem);
		userRepository.save(user);
        model.addAttribute("title","Cart");
		return "redirect:/customer/cart";
	}
	
	
	
	
	
	@RequestMapping("/deliveryform")
	public String deliveryform(HttpSession session,Model model) {
		String username=(String) session.getAttribute("login_user");
		User user=userRepository.findByEmail(username);
		model.addAttribute("loginuser",user);
		Address address=new Address();
		if(user.getAddresses().size()>0) {
		address=user.getAddresses().get(0);
		}
		model.addAttribute("address",address);
		return "user/deliveryform";
	}
	
	
	@RequestMapping("/orders")
	public String orders(HttpSession session,Model model) {
		String username=(String) session.getAttribute("login_user");
		User user=userRepository.findByEmail(username);
		model.addAttribute("loginuser",user);
		model.addAttribute("title","Orders");
		List<OrderItem>orders=user.getOrders();
		model.addAttribute("orders",orders);
		return "user/orders";
	}
	
	
	@PostMapping("/process-order")
	public String process_order(@ModelAttribute("address") Address address,HttpSession session) {
		
		String username=(String) session.getAttribute("login_user");
		User user=userRepository.findByEmail(username);
		if(user.getAddresses().size()==0) {
			user.getAddresses().add(address);
		}
		List<CartItem>cartItems=user.getCart().getCartItems();
		for(CartItem c:cartItems) {
			System.out.println("for");
			OrderItem order=new OrderItem();
			System.out.println(1);
			order.setUser(user);
			System.out.println(2);
			order.setRestaurant(c.getFood().getRestaurant());
			System.out.println(3);
			order.setFood(c.getFood());
			order.setQuantity(c.getQuantity());
			order.setTotalPrice(c.getTotalprice());
			order.setOrderStatus("Pending");
			order.setCreatedAt(new Date());
			order.setDeliveryAddress(address);
			System.out.println(5);
			OrderItem o=orderItemRepository.save(order);
			System.out.println("o "+o.getId());
			user.getOrders().add(o);
	  }
		cartItems.clear();
		user.getCart().setTotal(0);
		userRepository.save(user);
		
		return "redirect:/customer/orders";
		
	}
	

	
	
	@RequestMapping("/profile")
	public String profile(HttpSession session,Model model) {
		String username=(String) session.getAttribute("login_user");
		User user=userRepository.findByEmail(username);
		model.addAttribute("loginuser",user);
		model.addAttribute("title","Profile");
		return "user/profile";
	}
	
	@PostMapping("/process-update-profile")
	public String update_profile(@ModelAttribute("user") User user,@RequestParam("ProfileImage") MultipartFile file,HttpSession session,Model model) {
		String username=(String) session.getAttribute("login_user");
		User olduserDetails=userRepository.findByEmail(username);
		try {
			   if(file.isEmpty()) {
				user.setImage(olduserDetails.getImage());
			   }
			  else {
				File deleteFile=new ClassPathResource("static/img").getFile();
				File file1=new File(deleteFile,olduserDetails.getImage());
				file1.delete();				
				
				File saveFile=new ClassPathResource("static/img").getFile();
				Path path=Paths.get(saveFile.getAbsolutePath()+File.separator+file.getOriginalFilename());
				
				Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
				user.setImage(file.getOriginalFilename());
			   }
			}
			catch(Exception e) {
				session.setAttribute("message",new Message("Something went wrong !! Try again...","alert-danger"));
			}
		user.setEmail(olduserDetails.getEmail());
		user.setPassword(olduserDetails.getPassword());
		user.setCart(olduserDetails.getCart());
		user.setAddresses(olduserDetails.getAddresses());
		user.setOrders(olduserDetails.getOrders());
		userRepository.save(user);
		session.setAttribute("message", new Message("Your profile updated successfully  !!","alert-success"));
		return "redirect:/customer/profile";
	}
	
	@PostMapping("/change-password")
	public String change_password(@RequestParam("oldPassword") String oldPassword,@RequestParam("newPassword") String newPassword,HttpSession session) {
		String username=(String) session.getAttribute("login_user");
		User user=userRepository.findByEmail(username);
		if(this.bCryptPasswordEncoder.matches(oldPassword, user.getPassword())){
	
		user.setPassword(this.bCryptPasswordEncoder.encode(newPassword));
		this.userRepository.save(user);
		session.setAttribute("message",new Message("Your password has successfully changed...","alert-success"));
		
	}
	else {
		session.setAttribute("message",new Message("Please Enter correct old password !!","alert-danger"));
		
	}
	
		return "redirect:/customer/profile";
	}
}


