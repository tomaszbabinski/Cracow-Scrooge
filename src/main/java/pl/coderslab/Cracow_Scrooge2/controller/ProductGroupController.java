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
import pl.coderslab.Cracow_Scrooge2.service.productGroup.ProductGroupService;

import javax.validation.Valid;

@Controller
@RequestMapping("/category")
public class ProductGroupController {

    private ProductGroupRepository productGroupRepository;
    private ProductGroupService productGroupService;

    @Autowired
    public ProductGroupController(ProductGroupRepository productGroupRepository, ProductGroupService productGroupService) {
        this.productGroupRepository = productGroupRepository;
        this.productGroupService = productGroupService;
    }

    @GetMapping("/add")
    public String addCategory(Model model) {
        model.addAttribute("productGroupDto", new ProductGroupDto());
        return "category/add";
    }

    @PostMapping("/add")
    public String addCategoryProcess(@Valid @ModelAttribute("productGroupDto") ProductGroupDto productGroupDto, BindingResult result,
                                     @SessionAttribute("loggedInUser") User user,Model model) {

        if (result.hasErrors()) {
            return "category/add";
        } else {
            if (productGroupService.checkUniqueGroup(productGroupDto.getName().toLowerCase().trim(),user.getId())) {
                ProductGroup productGroup = new ProductGroup(productGroupDto);
                productGroup.setUser(user);
                productGroupRepository.save(productGroup);
                return "redirect:/home";
            } else {
                model.addAttribute("message","The category name exists already");
                return "category/add";
            }
        }

    }

    @GetMapping("/all")
    public String allCategories(Model model, @SessionAttribute("loggedInUser") User user) {
        model.addAttribute("categories", productGroupRepository.findAllByUserId(user.getId()));
        return "category/all";
    }

    @GetMapping("/byId/{id}")
    public String categoryById(@PathVariable Long id, Model model) {
        model.addAttribute("productGroup", productGroupRepository.getOne(id));
        return "category/byId";
    }

    @GetMapping("/edit/{id}")
    public String editCategory(@PathVariable Long id, Model model) {
        model.addAttribute("productGroup", productGroupRepository.getOne(id));
        return "category/edit";
    }

    @PostMapping("/edit/{id}")
    public String processEditCategory(@ModelAttribute ProductGroup productGroup, @PathVariable Long id) {
        productGroupRepository.save(productGroup);
        return "redirect:/category/byId/"+id;
    }

}
