package pl.coderslab.Cracow_Scrooge2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.Cracow_Scrooge2.dto.OfferDto;
import pl.coderslab.Cracow_Scrooge2.entity.Offer;
import pl.coderslab.Cracow_Scrooge2.entity.User;
import pl.coderslab.Cracow_Scrooge2.repository.OfferRepository;
import pl.coderslab.Cracow_Scrooge2.repository.ProductRepository;
import pl.coderslab.Cracow_Scrooge2.service.offer.OfferService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/offer")
public class OfferController {

    private ProductRepository productRepository;
    private OfferRepository offerRepository;
    private OfferService offerService;

    @Autowired
    public OfferController(ProductRepository productRepository, OfferRepository offerRepository,OfferService offerService) {
        this.productRepository = productRepository;
        this.offerRepository = offerRepository;
        this.offerService = offerService;
    }

    @GetMapping("/add/{id}")
    public String addOffer(@PathVariable Long id,Model model){
        model.addAttribute("offerDto",new OfferDto());
//        model.addAttribute("product",productRepository.getOne(id));
        return "offer/add";
    }

    @PostMapping("/add/{id}")
    public String processOfferAdd(@ModelAttribute OfferDto offerDto,@PathVariable Long id){
        Offer offer = new Offer(offerDto);
        offer.setProduct(productRepository.getOne(id));
        offer.setAddedAt(LocalDate.now());
        offerRepository.save(offer);
        return "redirect:/product/getById/"+id;
    }

    @GetMapping("/getById/{id}")
    public String getById(@PathVariable Long id, Model model){
        Offer offer = this.offerRepository.getOne(id);
        model.addAttribute("offer",offer);
        return "offer/offerById";
    }

    @GetMapping("/all")
    public String showAllOffers(Model model,@SessionAttribute("loggedInUser") User user){
        model.addAttribute("offers",offerRepository.findAllByProductUserId(user.getId()));
        return "offer/all";
    }

    @GetMapping("/bestOffers")
    @ResponseBody
    public List<Offer> renderChart(@SessionAttribute("loggedInUser") User user){
        return offerRepository.findBestOffersForAgivenProductAndUserId(user.getId());
    }

    @GetMapping("/allShops")
    public String allShops(Model model,@SessionAttribute("loggedInUser") User user){
        List<Offer> offers = offerRepository.findAllByProductUserId(user.getId());
        model.addAttribute("shops",offerService.getAllShops(offers));
        return "offer/shops";
    }

    @GetMapping("/getByShopName/{shopName}")
    public String offerByShopName(Model model,@SessionAttribute("loggedInUser") User user,@PathVariable String shopName){
        String searchedShop = shopName.toLowerCase();
        List<Offer> offers = offerRepository.findAllByProductUserId(user.getId());
        model.addAttribute("byShopName",offerService.getByShopName(offers,searchedShop));
        return "offer/byShopName";
    }

}
