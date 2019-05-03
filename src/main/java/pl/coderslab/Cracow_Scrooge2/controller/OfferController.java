package pl.coderslab.Cracow_Scrooge2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.Cracow_Scrooge2.entity.Offer;
import pl.coderslab.Cracow_Scrooge2.repository.OfferRepository;
import pl.coderslab.Cracow_Scrooge2.repository.ProductRepository;

@Controller
@RequestMapping("/offer")
public class OfferController {

    private ProductRepository productRepository;
    private OfferRepository offerRepository;

    @Autowired
    public OfferController(ProductRepository productRepository, OfferRepository offerRepository) {
        this.productRepository = productRepository;
        this.offerRepository = offerRepository;
    }

    @GetMapping("/add/{id}")
    public String addOffer(@PathVariable Long id,Model model){
        model.addAttribute("offer",new Offer());
        model.addAttribute("product",productRepository.getOne(id));
        return "offer/add";
    }

    @PostMapping("/add/{id}")
    public String processOfferAdd(@ModelAttribute Offer offer,@PathVariable Long id){
        offer.setProduct(productRepository.getOne(id));
        offerRepository.save(offer);
        return "redirect:/product/getById/"+id;
    }

}
