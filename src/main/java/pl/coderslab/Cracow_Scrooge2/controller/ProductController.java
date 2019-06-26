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
import pl.coderslab.Cracow_Scrooge2.service.product.ProductService;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductController {

    private ProductRepository productRepository;
    private ProductGroupRepository productGroupRepository;
    private EfficiencyService efficiencyService;
    private ProductService productService;

    @Autowired
    public ProductController(ProductRepository productRepository, ProductGroupRepository productGroupRepository, EfficiencyService efficiencyService, ProductService productService) {
        this.productRepository = productRepository;
        this.productGroupRepository = productGroupRepository;
        this.efficiencyService = efficiencyService;
        this.productService = productService;
    }


    @GetMapping("/add")
    public String add(Model model){
        model.addAttribute("product",new Product());
        return "product/add";
    }

    @PostMapping("/add")
    public String addProcess(@ModelAttribute Product product, HttpSession httpSession,Model model){
        product.setUser((User) httpSession.getAttribute("loggedInUser"));
        product.setIsActive("No");
        if(productService.checkIfExists(product.getName(), product.getBrand(),product.getUser().getId())){
            model.addAttribute("message","This product already exists");
            model.addAttribute("product", new Product());
            return "product/add";
        }
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

    @GetMapping("/like/{id}")
    public String likeProduct(@PathVariable Long id){
        Product product = productRepository.getOne(id);
        product.setIsLiked("Yes");
        productRepository.save(product);
        return "redirect:/product/all";
    }

    @GetMapping("/unlike/{id}")
    public String unlikeProduct(@PathVariable Long id){
        Product product = productRepository.getOne(id);
        product.setIsLiked("No");
        productRepository.save(product);
        return "redirect:/product/all";
    }


    @ModelAttribute("categories")
    List<ProductGroup> getCategories(HttpSession session){
        User user = (User) session.getAttribute("loggedInUser");
        return productGroupRepository.findAllByUserId(user.getId());
    }
}
