package com.ordering.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.ordering.Entities.Address;
import com.ordering.Entities.Cart;
import com.ordering.Entities.Food;
import com.ordering.Entities.Member;
import com.ordering.Entities.Restaurant;
import com.ordering.Entities.User;
import com.ordering.dao.FoodRepository;
import com.ordering.dao.MemberRepository;
import com.ordering.dao.RestaurantRepository;
import com.ordering.dao.UserRepository;
import com.ordering.helper.Message;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;


@Controller
public class HomeController {
	
	
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private FoodRepository foodRepository;
	
	@Autowired
	private MemberRepository memberRepository;
	
	@Autowired
	private RestaurantRepository restaurantRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	

	private boolean isValidImageType(String mimeType) {
        return mimeType != null && (mimeType.equals("image/jpeg") || mimeType.equals("image/png")||mimeType.equals("image/jpg"));
    }
	
	@RequestMapping("/")
	public String  home(Model model) {
		model.addAttribute("title","Foodie-Home");
		return "home";
	}
	
	
	@RequestMapping("/signup-customer")
	public String signup(Model model) {
		model.addAttribute("title","SignUp-Customer");
		return "signup";
	}
	
	
	
	
	@RequestMapping("/signup-restaurant-owner")
	public String signup_restaurant_owner(Model model) {
		model.addAttribute("title","SignUp-RestaurantOwner");
		return "signup-restaurant-owner";
	}
	
	@RequestMapping("/login-member")
	public String login_member(Model model){
		model.addAttribute("title","Login");
		return "login-member";
	}
	
	
	
	@RequestMapping(value="/process-signup-customer",method=RequestMethod.POST)
	public String registerUser( @ModelAttribute("user") User user,Model model,HttpSession session) {
		
		try {
			
			
			if(userRepository.findByEmail(user.getEmail())!=null) {
				session.setAttribute("message", new Message("Email already exists","alert-danger"));
				return "signup";
			}
			
			user.setImage("default.png");
			user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
			Cart cart=new Cart();
			user.setCart(cart);
			cart.setUser(user);
		
			User res=this.userRepository.save(user);
             
			
			model.addAttribute("user",new User());
			session.setAttribute("message", new Message("Successfully Registered !!","alert-success"));
			return "signup";
		}
		catch(Exception e) {
			e.printStackTrace();
			model.addAttribute("user",user);
			session.setAttribute("message", new Message("Something went wrong !! ","alert-danger"));
			return "signup";
		}
		
	}
	
	
	
	@RequestMapping(value="/process-signup-restaurant-owner",method=RequestMethod.POST)
	public String registerRestaurantOwner( @ModelAttribute("owner") Member owner,Model model,HttpSession session) {
		
		try {
			
			if(memberRepository.findByEmail(owner.getEmail())!=null) {
				session.setAttribute("message", new Message("Email already exists","alert-danger"));
				return "signup-restaurant-owner";
			}
			
         	owner.setRole("ROLE_RESTAURANT-OWNER");
			owner.setImage("default.png");
	        owner.setPassword(bCryptPasswordEncoder.encode(owner.getPassword()));
			
					
			Member res=this.memberRepository.save(owner);
			session.setAttribute("currentuser", res);
			System.out.println(res);
			
            
			return "Restaurant/add_restaurant";
		}
		catch(Exception e) {
			e.printStackTrace();
			session.setAttribute("message", new Message("Something went wrong !! ","alert-danger"));
			return "signup-restaurant-owner";
		}
		
	}
	
	
	@PostMapping("/douser_login")
	public String doUserLogin(Model model,@RequestParam("username") String username,@RequestParam("password") String password,HttpSession session) {
		
		User user=userRepository.findByEmail(username);
		if(user!=null&&bCryptPasswordEncoder.matches(password, user.getPassword())) {
		
             session.setAttribute("login_user", username);
			 model.addAttribute("loginuser", user);
			
		}
		else
		{
			session.setAttribute("message", new Message("Invalid username or password !!","alert-danger"));
			return "redirect:/?error";
		}
		String city=(String) session.getAttribute("city");
		List<Restaurant> restaurants=restaurantRepository.findRestaurantsByCity(city);
		if(restaurants!=null) {
		model.addAttribute("restaurants",restaurants);
		}
		
		return "redirect:/customer/home";
	}
	
	
	@RequestMapping("/member/dashboard")
	public String dashboard(Principal principal,Model model) {
		String userName=principal.getName();
		Member member=memberRepository.findByEmail(userName);				
		model.addAttribute("member",member);
		Restaurant restaurant=restaurantRepository.findRestaurantByOwner(member.getId());
		model.addAttribute("Restaurant",restaurant);
		model.addAttribute("PageName","Home");
		model.addAttribute("title","Home");
		return "redirect:/member/restaurant/home";
	}
	
	
	
	
	
	
	@PostMapping("/process-add-Restaurant")
	public String process_add_restauarnt(@ModelAttribute("restaurant") Restaurant restaurant,@RequestParam("Restaurant-Image") MultipartFile file,@RequestParam("streetAddress") String streetAddress,@RequestParam("city") String city,@RequestParam("stateProvince") String stateProvince,@RequestParam("country") String country,@RequestParam("postalCode") String postalCode,HttpSession session) {
		try {
					
			Member owner=(Member)session.getAttribute("currentuser");
			

				String mimeType = file.getContentType();
	           
	            if (!isValidImageType(mimeType)) {
	            	session.setAttribute("message",new Message("Only jpg , jpeg or png file type are supported","alert-danger"));
					return "Restaurant/add_restaurant";
	            }

				
				restaurant.setImage(file.getOriginalFilename());
				File saveFile=new ClassPathResource("static/img").getFile();
				Path path=Paths.get(saveFile.getAbsolutePath()+File.separator+file.getOriginalFilename());
				
				Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
			
			
			Address address=new Address();
			address.setCity(city);
			address.setCountry(country);
			address.setPostalCode(postalCode);
			address.setStateProvince(stateProvince);
			address.setStreetAddress(streetAddress);
			address.setRestaurant(restaurant);
			restaurant.setAddress(address);
			
			restaurant.setRegistrationData(new Date());
			owner.setRestaurant(restaurant);
			restaurant.setOwner(owner);
			
			
			this.memberRepository.save(owner);
			session.removeAttribute("currentuser");
      		return "home";
			
			}
			catch(Exception e) {
				System.out.println("Error "+e.getMessage());
				e.printStackTrace();
				
				//error message
				session.setAttribute("message",new Message("Something went wrong !! Try again...","alert-danger"));
				
			}
		
		return "Restaurant/add_restaurant";
	}
	
}
