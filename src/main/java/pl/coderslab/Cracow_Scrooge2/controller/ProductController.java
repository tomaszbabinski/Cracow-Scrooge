package pl.coderslab.Cracow_Scrooge2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.Cracow_Scrooge2.entity.Product;
import pl.coderslab.Cracow_Scrooge2.entity.ProductGroup;
import pl.coderslab.Cracow_Scrooge2.entity.User;
import pl.coderslab.Cracow_Scrooge2.repository.ProductGroupRepository;
import pl.coderslab.Cracow_Scrooge2.repository.ProductRepository;
import pl.coderslab.Cracow_Scrooge2.service.efficiency.EfficiencyService;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductController {

    private ProductRepository productRepository;
    private ProductGroupRepository productGroupRepository;
    private EfficiencyService efficiencyService;

    @Autowired
    public ProductController(ProductRepository productRepository, ProductGroupRepository productGroupRepository, EfficiencyService efficiencyService) {
        this.productRepository = productRepository;
        this.productGroupRepository = productGroupRepository;
        this.efficiencyService = efficiencyService;
    }

    @GetMapping("/add")
    public String add(Model model){
        model.addAttribute("product",new Product());
        return "product/add";
    }

    @PostMapping("/add")
    public String addProcess(@ModelAttribute Product product, HttpSession httpSession){
        product.setUser((User) httpSession.getAttribute("loggedInUser"));
        product.setIsActive("No");
        productRepository.save(product);
        return "redirect:/product/all";
    }



    @GetMapping("/all")
    public String getAllProducts(Model model,HttpSession session){
        User user = (User) session.getAttribute("loggedInUser");
        model.addAttribute("products",productRepository.findAllByUserId(user.getId()));
        return "product/allProducts";
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

    @GetMapping("/remove/{id}")
    public String remove(@PathVariable Long id,Model model){
        model.addAttribute("product",productRepository.getOne(id));
        return "product/remove";
    }

    @PostMapping("/remove/{id}")
    public String processRemoval(@PathVariable Long id){
        productRepository.delete(productRepository.getOne(id));
        return "redirect:/product/all";
    }

    @GetMapping("/findByName")
    public String findByName(@RequestParam String product, Model model,HttpSession session){
        User user = (User) session.getAttribute("loggedInUser");
        model.addAttribute("products",productRepository.findAllByNameAndUserId(product,user.getId()));
        return "product/allProducts";
    }

    @GetMapping("/start/{id}")
    public void start(@PathVariable Long id){
        Product product = productRepository.getOne(id);
        product.setIsActive("Yes");
        product.setBegOfUsage(LocalDate.now());
        productRepository.save(product);
    }

    @GetMapping("/stop/{id}")
    public void stop(@PathVariable Long id){
        Product product = productRepository.getOne(id);
        product.setIsActive("No");
        product.setEndOfUsage(LocalDate.now());
        efficiencyService.saveEfficiency(product);
        product = productRepository.getOne(id);
        product.setBegOfUsage(null);
        product.setEndOfUsage(null);
        productRepository.save(product);

    }


    @ModelAttribute("categories")
    List<ProductGroup> getCategories(HttpSession session){
        User user = (User) session.getAttribute("loggedInUser");
        return productGroupRepository.findAllByUserId(user.getId());
    }
}
