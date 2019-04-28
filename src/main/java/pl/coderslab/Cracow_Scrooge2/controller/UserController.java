package pl.coderslab.Cracow_Scrooge2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.coderslab.Cracow_Scrooge2.dto.UserDto;
import pl.coderslab.Cracow_Scrooge2.entity.User;
import pl.coderslab.Cracow_Scrooge2.repository.UserRepository;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequestMapping("/user")
public class UserController {

   private UserRepository userRepository;

   @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/register")
    public String register(Model model){
        model.addAttribute("userDto", new UserDto());
        return "registration";
    }

    @PostMapping("/register")
    public String register(@Valid UserDto userDto, BindingResult result){
        if(result.hasErrors()){
            return "user/register";
        }else{
            User user = new User(userDto);
            userRepository.save(user);
            return "/home";
        }

    }

    @GetMapping("/login")
    public String login(Model model){
        model.addAttribute("loginData", new UserDto());
        return "login";
    }

    @PostMapping("/login")
    public String loginForm(UserDto loginData, Model model, HttpSession httpSession){


        User user = this.userRepository.findByEmail(loginData.getEmail());
        if(user==null){
            model.addAttribute("user",loginData);
            model.addAttribute("message","User not found");
            return "/user/login";
        }else{
            if(!user.isPasswordCorrect(loginData.getPassword())){
                model.addAttribute("user", loginData);
                model.addAttribute("message","Wrong password");
                return "/user/login";
            }else{
                httpSession.setAttribute("loggedInUser", user);
                return "redirect:/home";
            }
        }
    }




}
