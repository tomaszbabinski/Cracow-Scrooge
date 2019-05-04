package pl.coderslab.Cracow_Scrooge2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.Cracow_Scrooge2.dto.ProductGroupDto;
import pl.coderslab.Cracow_Scrooge2.entity.ProductGroup;
import pl.coderslab.Cracow_Scrooge2.entity.User;
import pl.coderslab.Cracow_Scrooge2.repository.ProductGroupRepository;

import javax.validation.Valid;

@Controller
@RequestMapping("/category")
public class ProductGroupController {

    private ProductGroupRepository productGroupRepository;

    @Autowired
    public ProductGroupController(ProductGroupRepository productGroupRepository) {
        this.productGroupRepository = productGroupRepository;
    }

    @GetMapping("/add")
    public String addCategory(Model model) {
        model.addAttribute("productGroupDto", new ProductGroupDto());
        return "category/add";
    }

    @PostMapping("/add")
    public String addCategoryProcess(@Valid @ModelAttribute("productGroupDto") ProductGroupDto productGroupDto, BindingResult result, Model model,
    @SessionAttribute("loggedInUser") User user) {
        if (result.hasErrors()) {
            return "category/add";
        } else {

            ProductGroup productGroup = new ProductGroup(productGroupDto);
            productGroup.setUser(user);
            productGroupRepository.save(productGroup);
            return "redirect:/home";
        }

    }

}