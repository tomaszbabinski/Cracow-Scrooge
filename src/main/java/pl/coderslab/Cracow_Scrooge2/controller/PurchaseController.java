package pl.coderslab.Cracow_Scrooge2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.Cracow_Scrooge2.dto.PurchaseDto;
import pl.coderslab.Cracow_Scrooge2.entity.Purchase;
import pl.coderslab.Cracow_Scrooge2.entity.User;
import pl.coderslab.Cracow_Scrooge2.repository.OfferRepository;
import pl.coderslab.Cracow_Scrooge2.repository.ProductRepository;
import pl.coderslab.Cracow_Scrooge2.repository.PurchaseRepository;

import java.util.List;

@Controller
@RequestMapping("/purchase")
public class PurchaseController {

    private PurchaseRepository purchaseRepository;
    private ProductRepository productRepository;
    private OfferRepository offerRepository;

    @Autowired
    public PurchaseController(PurchaseRepository purchaseRepository, ProductRepository productRepository, OfferRepository offerRepository) {
        this.purchaseRepository = purchaseRepository;
        this.productRepository = productRepository;
        this.offerRepository = offerRepository;
    }

    @GetMapping("/add/{productId}/{offerId}")
    public String addPurchase(@PathVariable Long productId, @PathVariable Long offerId, Model model) {
        PurchaseDto purchaseDto = new PurchaseDto();
        purchaseDto.setOffer(offerRepository.getOne(offerId));
        purchaseDto.setProduct(productRepository.getOne(productId));
        model.addAttribute("purchaseDto", purchaseDto);
        return "purchase/add";
    }

    @PostMapping("/add")
    public String addPurchaseProcess(@ModelAttribute PurchaseDto purchaseDto, @SessionAttribute("loggedInUser") User user) {
        Purchase purchase = new Purchase(purchaseDto);
        Double averagePrice = (Math.round(offerRepository.findAveragePriceByProductId(purchase.getProduct().getId())) * 100 / 100.00);
        purchase.setUser(user);
        purchase.setOfferPrice(purchaseDto.getOffer().getPrice());
        Double savings = ((averagePrice-purchase.getOfferPrice())*(purchaseDto.getQuantity()));
        purchase.setSavedMoney(savings);
        purchaseRepository.save(purchase);
        return "redirect:/home";
    }

    @GetMapping("/chart")
    @ResponseBody
    public List<Purchase> renderChart(@SessionAttribute("loggedInUser") User user){
        return purchaseRepository.findAllByUser(user);
    }

}
