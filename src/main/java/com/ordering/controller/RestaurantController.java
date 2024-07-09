package com.ordering.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
//import java.util.Locale.Category;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.ordering.Entities.Address;
import com.ordering.Entities.Food;
import com.ordering.Entities.FoodCategory;
import com.ordering.Entities.Member;
import com.ordering.Entities.OrderItem;
import com.ordering.Entities.Restaurant;
import com.ordering.Entities.User;
import com.ordering.dao.FoodCategoryRepository;
import com.ordering.dao.FoodRepository;
import com.ordering.dao.MemberRepository;
import com.ordering.dao.OrderItemRepository;
import com.ordering.dao.RestaurantRepository;
import com.ordering.helper.Message;

import jakarta.servlet.http.HttpSession;


@Controller
@RequestMapping("/member/restaurant")
public class RestaurantController {

	@Autowired
	private RestaurantRepository  restaurantRepository ;
	
	@Autowired
	private MemberRepository memberRepository;
	
	@Autowired
	private FoodCategoryRepository foodCategoryRepository;
	
	
	@Autowired
	private FoodRepository foodRepository;
	
	@Autowired
	private OrderItemRepository orderItemRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	
	private boolean isValidImageType(String mimeType) {
        return mimeType != null && (mimeType.equals("image/jpeg") || mimeType.equals("image/png")||mimeType.equals("image/jpg"));
    }
	
	
	@ModelAttribute
	public void addCommonData(Model model,Principal principal) {
		
		
				String userName=principal.getName();
				Member member=memberRepository.findByEmail(userName);				
				model.addAttribute("member",member);
				Restaurant restaurant=restaurantRepository.findRestaurantByOwner(member.getId());
				model.addAttribute("Restaurant",restaurant);
				System.out.println(restaurant);
				
	}
	

	@RequestMapping("/foodcategory")
	public String foodCategories(Model model,Principal principal) {
		String username=principal.getName();
		Member owner=memberRepository.findByEmail(username);
		Restaurant restaurant=owner.getRestaurant();
		List<FoodCategory>foodCategory=restaurant.getFoodCategory();
		model.addAttribute("foodcategory",foodCategory);
		model.addAttribute("title","Food Category");
		model.addAttribute("PageName","Food Category");
		for(FoodCategory fc:foodCategory) {
			System.out.println(fc.getName());
		}
		return "Restaurant/FoodCategory";
	}
	
	@RequestMapping("/home")
	public String rest_Home(Model model) {
		model.addAttribute("title","Home");
		model.addAttribute("PageName","Home");
		return "Restaurant/dashboard";
	}
	
	
	@PostMapping("/addfoodcategory")
	public String addFoodCategory(@ModelAttribute("category") FoodCategory category,Model model,Principal principal) {
		String username=principal.getName();
		Member owner=memberRepository.findByEmail(username);
		Restaurant restaurant=owner.getRestaurant();
		category.setRestaurant(restaurant);
		restaurant.getFoodCategory().add(category);
		
		restaurantRepository.save(restaurant);
		
		return "redirect:/member/restaurant/foodcategory";
	}
	
	@RequestMapping("/deleteFoodCategory/{id}")
	public String deleteFoodCategory(@PathVariable("id") Integer id) {
		Optional<FoodCategory> fcOptional=foodCategoryRepository.findById(id);
		FoodCategory fc=fcOptional.get();
		Restaurant restaurant=fc.getRestaurant();
		restaurant.getFoodCategory().remove(fc);
		restaurantRepository.save(restaurant);
		
		return "redirect:/member/restaurant/foodcategory";
	}
	
	@RequestMapping("/menu")
	public String menu(Model model,Principal principal) {
		String username=principal.getName();
		Member owner=memberRepository.findByEmail(username);
		Restaurant restaurant=owner.getRestaurant();
		List<Food>foods=restaurant.getFoods();
		model.addAttribute("foods",foods);
		model.addAttribute("title","Menu");
		model.addAttribute("PageName","Menu");
		return "Restaurant/Menu";
	}
	
	@RequestMapping("/addmenuform")
	public String menuForm(Principal principal,Model model) {
		String username=principal.getName();
		Member owner=memberRepository.findByEmail(username);
		Restaurant restaurant=owner.getRestaurant();
		List<FoodCategory> fc=restaurant.getFoodCategory();
		model.addAttribute("fc",fc);
		
		return "Restaurant/add-menu-form";
	}
	
	@PostMapping("/process-add-menu")
	public String process_add_menu(@ModelAttribute("food") Food food,Principal principal,@RequestParam("category") Integer fcId,@RequestParam("veg") String veg,@RequestParam("food-image") MultipartFile file,HttpSession session) {
		
		  String username=principal.getName();  
		  Member owner=memberRepository.findByEmail(username);
		try {

			String mimeType = file.getContentType();
           
            if (!isValidImageType(mimeType)) {
            	session.setAttribute("message",new Message("Only jpg , jpeg or png file type are supported","alert-danger"));
				return "Restaurant/add-menu-form";
            }

			
			food.setImages(file.getOriginalFilename());
			File saveFile=new ClassPathResource("static/img").getFile();
			Path path=Paths.get(saveFile.getAbsolutePath()+File.separator+file.getOriginalFilename());
			
			Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
		}
		catch(Exception e) {
			e.printStackTrace();
			
			//error message
			session.setAttribute("message",new Message("Something went wrong !! Try again...","alert-danger"));
		}
		
		 Restaurant restaurant=owner.getRestaurant();
		FoodCategory foodcategory=foodCategoryRepository.findById(fcId).get();
		 food.setCreationDate(new Date());	
		 food.setFoodCategory(foodcategory);
		 food.setRestaurant(restaurant);
		 food.setIsVegetarian(veg);
		 restaurant.getFoods().add(food);
		 restaurantRepository.save(restaurant);
		 
		return "redirect:/member/restaurant/menu";
	}
	
	@RequestMapping("/deleteFoodItem/{id}")
	public String deleteFoodItem(@PathVariable("id") Integer id,Principal principal) {
	      Optional<Food>foodOptional= foodRepository.findById(id);
	      Food food=foodOptional.get();
	      Restaurant restaurant=food.getRestaurant();
	      restaurant.getFoods().remove(food);
	      restaurantRepository.save(restaurant);
	      
	      return "redirect:/member/restaurant/menu";
	}
	
	
	@RequestMapping("/updateRestaurant")
	public String updateRestaurant(Principal principal,Model model) {
		 String username=principal.getName();  
		  Member owner=memberRepository.findByEmail(username);
		  Restaurant restaurant=owner.getRestaurant();
		  Address address=restaurant.getAddress();
		  model.addAttribute("address",address);
		  model.addAttribute("title","Update Form");
		  model.addAttribute("PageName","Edit Restaurant Details");
		model.addAttribute("restaurant",restaurant);
		return "Restaurant/UpdateRestaurantform";
	}
	
	
	@PostMapping("/process-update-Restaurant")
	public String process_update_restaurant(@ModelAttribute("restaurant") Restaurant restaurant,@ModelAttribute("address") Address address,Principal principal,@RequestParam("RestaurantImage") MultipartFile file,HttpSession session) {
		Restaurant oldRestDetail=restaurantRepository.findById(restaurant.getId()).get();
		address.setId(oldRestDetail.getAddress().getId());
		try {
		   if(file.isEmpty()) {
			restaurant.setImage(oldRestDetail.getImage());
		   }
		  else {
			File deleteFile=new ClassPathResource("static/img").getFile();
			File file1=new File(deleteFile,oldRestDetail.getImage());
			file1.delete();				
			
			File saveFile=new ClassPathResource("static/img").getFile();
			Path path=Paths.get(saveFile.getAbsolutePath()+File.separator+file.getOriginalFilename());
			
			Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
			restaurant.setImage(file.getOriginalFilename());
		   }
		}
		catch(Exception e) {
			session.setAttribute("message",new Message("Something went wrong !! Try again...","alert-danger"));
		}
		
		Member owner=oldRestDetail.getOwner();
        restaurant.setOwner(owner);
     	restaurant.setAddress(address);
     	restaurant.setRegistrationData(oldRestDetail.getRegistrationDate());
     	if(oldRestDetail.getOrders()!=null) {
     		restaurant.setOrders(oldRestDetail.getOrders());
     	}
     	if(oldRestDetail.getFoods()!=null) {
     		restaurant.setFoods(oldRestDetail.getFoods());
     	}
     	if(oldRestDetail.getFoodCategory()!=null) {
     		restaurant.setFoodCategory(oldRestDetail.getFoodCategory());
     	}
		address.setRestaurant(restaurant);
		
		restaurantRepository.save(restaurant);
		session.setAttribute("message", new Message("Your changes have saved successfully  !!","alert-success"));
		return "redirect:/member/restaurant/updateRestaurant";
	}
	
	@RequestMapping("/profile")
	public String profile(Principal principal,Model model) {
		String username=principal.getName();
		Member owner=memberRepository.findByEmail(username);
		model.addAttribute("owner",owner);
		model.addAttribute("title","Profile");
		model.addAttribute("PageName","Your Profile");
		return "Restaurant/profile";
	}
	
	@PostMapping("/process-update-profile")
	public String updateProfile(@ModelAttribute("member") Member member,@RequestParam("ProfileImage") MultipartFile file,Principal principal,HttpSession session,Model model) {
		String username=principal.getName();
		Member oldMemberDetail=memberRepository.findByEmail(username);
		try {
			   if(file.isEmpty()) {
				member.setImage(oldMemberDetail.getImage());
			   }
			  else {
				File deleteFile=new ClassPathResource("static/img").getFile();
				File file1=new File(deleteFile,oldMemberDetail.getImage());
				file1.delete();				
				
				File saveFile=new ClassPathResource("static/img").getFile();
				Path path=Paths.get(saveFile.getAbsolutePath()+File.separator+file.getOriginalFilename());
				
				Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
				member.setImage(file.getOriginalFilename());
			   }
			}
			catch(Exception e) {
				session.setAttribute("message",new Message("Something went wrong !! Try again...","alert-danger"));
			}
		member.setEmail(oldMemberDetail.getEmail());
		member.setPassword(oldMemberDetail.getPassword());
		member.setRole(oldMemberDetail.getRole());
		member.setRestaurant(oldMemberDetail.getRestaurant());
		Member res=memberRepository.save(member);
		model.addAttribute("owner",res);
		session.setAttribute("message", new Message("Your profile updated successfully  !!","alert-success"));
		return "redirect:/member/restaurant/profile";
	}
	
	
	
	@PostMapping("/change-password")
	public String change_password(Principal principal,@RequestParam("oldPassword") String oldPassword,@RequestParam("newPassword") String newPassword,HttpSession session) {
		String username=principal.getName();
		Member member=memberRepository.findByEmail(username);
		if(this.bCryptPasswordEncoder.matches(oldPassword, member.getPassword())){
	
		member.setPassword(this.bCryptPasswordEncoder.encode(newPassword));
		this.memberRepository.save(member);
		session.setAttribute("message",new Message("Your password has successfully changed...","alert-success"));
		
	}
	else {
		session.setAttribute("message",new Message("Please Enter correct old password !!","alert-danger"));
		
	}
	
		return "redirect:/member/restaurant/profile";	
		
	}
	
	
	@RequestMapping("/orders")
	public String orders(Principal principal,Model model) {
		model.addAttribute("PageName","My Orders");
		model.addAttribute("title","Orders");
		String username=principal.getName();
		Member member=memberRepository.findByEmail(username);
		Restaurant res=member.getRestaurant();
		List<OrderItem>orders=res.getOrders();
		Collections.reverse(orders);
		model.addAttribute("orders",orders);
		return "Restaurant/orders";
	}
	
	@PostMapping("/change-orderstatus/{orderId}")
	public String changeOrderStatus(@PathVariable("orderId") Integer orderId,@RequestParam("status") String status) {
		System.out.println("id "+orderId);
		OrderItem order=orderItemRepository.findById(orderId).get();
		order.setOrderStatus(status);
		orderItemRepository.save(order);
		return "redirect:/member/restaurant/orders";
	}
}
