package pl.coderslab.Cracow_Scrooge2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.coderslab.Cracow_Scrooge2.dto.UserDto;
import pl.coderslab.Cracow_Scrooge2.entity.User;
import pl.coderslab.Cracow_Scrooge2.repository.ProductGroupRepository;
import pl.coderslab.Cracow_Scrooge2.repository.UserRepository;
import pl.coderslab.Cracow_Scrooge2.service.user.UserService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequestMapping("/user")
public class UserController {

    private UserRepository userRepository;
    private ProductGroupRepository productGroupRepository;
    private UserService userService;

    @Autowired
    public UserController(UserRepository userRepository, ProductGroupRepository productGroupRepository, UserService userService) {
        this.userRepository = userRepository;
        this.productGroupRepository = productGroupRepository;
        this.userService = userService;
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("userDto", new UserDto());
        return "registration";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("userDto") UserDto userDto, BindingResult result) {
        if (result.hasErrors()) {
            return "registration";
        } else {
            User user = new User(userDto);
            userRepository.save(user);
            User userWithCategories = userRepository.findByEmail(user.getEmail());
            userService.createStartingCategories(userWithCategories);

            return "redirect:/user/login";
        }

    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("loginData", new UserDto());
        return "login";
    }

    @PostMapping("/login")
    public String loginForm(UserDto loginData, Model model, HttpSession httpSession) {


        User user = this.userRepository.findByEmail(loginData.getEmail());
        if (user == null) {
            model.addAttribute("loginData", new UserDto());
            model.addAttribute("message", "Wrong password or email");
            return "login";
        } else {
            if (!user.isPasswordCorrect(loginData.getPassword())) {
                model.addAttribute("loginData", new UserDto());
                model.addAttribute("message", "Wrong password or email");
                return "login";
            } else {
                httpSession.setAttribute("loggedInUser", user);
                model.addAttribute("userName", user.getFirstName());
                return "home";
            }
        }


    }

    @GetMapping("/logout")
    public String logout(HttpSession s) {
        s.removeAttribute("loggedInUser");

        return "redirect:/home";
    }


}
