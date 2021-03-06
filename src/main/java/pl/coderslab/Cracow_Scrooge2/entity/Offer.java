package pl.coderslab.Cracow_Scrooge2.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.coderslab.Cracow_Scrooge2.dto.OfferDto;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "offers")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Offer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Product product;

    private Double price;

    private String shopName;

    private LocalDate addedAt;

    public Offer(OfferDto offerDto){
        this.setShopName(offerDto.getShopName());
        this.setPrice(offerDto.getPrice());
        this.setAddedAt(offerDto.getAddedAt());

    }
}
