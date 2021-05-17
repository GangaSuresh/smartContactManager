package com.smart.smartcontactmanager.controller;

import com.smart.smartcontactmanager.entities.Contact;
import com.smart.smartcontactmanager.entities.User;
import com.smart.smartcontactmanager.helper.Message;
import com.smart.smartcontactmanager.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
public class HomeController {
    @Autowired
    private UserRepository userRepository;

//    @GetMapping("/test")
//    @ResponseBody
//    public String test(){
//        User user=new User();
//        user.setName("Ganga");
//        user.setEmail("gangasuresh@gmail.com");
//        Contact contact=new Contact();
//        user.getContacts().add(contact);
//        userRepository.save(user);
//        return "Working";
//    }
    @RequestMapping("/")
    public  String home(Model model){
        model.addAttribute("title","Home - Smart Contact Manager");
        return "home";
    }
    @RequestMapping("/about")
    public  String about(Model model){
        model.addAttribute("title","About - Smart Contact Manager");
        return "about";
    }
    @RequestMapping("/signup")
    public  String signup(Model model){
        model.addAttribute("title","Register - Smart Contact Manager");
        model.addAttribute("user",new User());
        return "signup";
    }

    @PostMapping("/do_register")
    public String registerUser(@ModelAttribute("user") User user,
                               @RequestParam(value = "agreement",defaultValue = "false")boolean agreement,
                               Model model, HttpSession session)
    {
       try{
           System.out.println("Agreement: "+agreement);
           System.out.println("User: "+user);
           if(!agreement){
               System.out.println("You have not agrred to terms and conditions");
                throw new Exception("You have not agrred to terms and conditions");
           }
           user.setRole("ROLE_USER");
           user.setEnabled(true);
           user.setImageUrl("default.png");
           User result=this.userRepository.save(user);
           model.addAttribute("user",new User());
           session.setAttribute("message",new Message("Successfully registered!","alert-success"));
           return "signup";


       }catch (Exception e){
           e.printStackTrace();
           model.addAttribute("user",user);
           session.setAttribute("message",new Message("Something went wrong! "+e.getMessage(),"alert-danger"));
           return "signup";

       }

    }
}
