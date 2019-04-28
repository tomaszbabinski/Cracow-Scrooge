package pl.coderslab.Cracow_Scrooge2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.Cracow_Scrooge2.entity.Product;
import pl.coderslab.Cracow_Scrooge2.entity.User;
import pl.coderslab.Cracow_Scrooge2.repository.ProductRepository;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/product")
public class ProductController {

    private ProductRepository productRepository;

    @Autowired
    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping("/add")
    public String add(Model model){
        model.addAttribute("product",new Product());
        return "add";
    }

    @PostMapping("/add")
    public String addProcess(@ModelAttribute Product product, HttpSession httpSession){
        product.setUser((User) httpSession.getAttribute("loggedInUser"));
        productRepository.save(product);
        return "redirect:/product/all";
    }



    @GetMapping("/all")
    public String getAllProducts(Model model,HttpSession session){
        User user = (User) session.getAttribute("loggedInUser");
        model.addAttribute("products",productRepository.findAllByUserId(user.getId()));
        return "allProducts";
    }

    @GetMapping("/getById/{id}")
    public String getById(@PathVariable Long id, Model model){
        Product product = this.productRepository.getOne(id);
        model.addAttribute("product",product);
        return "product/productById";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id,Model model){
        model.addAttribute("product",productRepository.findById(id));
        return "product/edit";
    }

    @PostMapping("/edit/{id}")
    public String processEdit(@ModelAttribute Product product){
        productRepository.save(product);
        return "redirect:/product/all";
    }
}
