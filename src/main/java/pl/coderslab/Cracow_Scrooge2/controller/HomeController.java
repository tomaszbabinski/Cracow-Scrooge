package pl.coderslab.Cracow_Scrooge2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.coderslab.Cracow_Scrooge2.entity.User;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/")
public class HomeController {

    @GetMapping("/")
    @ResponseBody
    public String appStarts(){
        return "Application starts";
    }

    @GetMapping("/home")
    public String homePage(HttpSession session, Model model){
        User user = (User) session.getAttribute("loggedInUser");
        if(user==null){
            return "redirect:user/login";
        }
        model.addAttribute("userName",user.getFirstName());
        return "home";
    }

}
