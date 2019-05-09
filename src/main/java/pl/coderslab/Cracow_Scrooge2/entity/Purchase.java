package pl.coderslab.Cracow_Scrooge2.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.coderslab.Cracow_Scrooge2.dto.PurchaseDto;

import javax.persistence.*;

@Entity
@Table(name = "purchases")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonIgnore
    private User user;

    @ManyToOne
    private Product product;

    @OneToOne
    private Offer offer;

    private Double offerPrice;

    private Double averagePrice;

    private Double savedMoney;

    private Integer quantity;

    public Purchase(PurchaseDto purchaseDto) {
        this.user = purchaseDto.getUser();
        this.product = purchaseDto.getProduct();
        this.offer = purchaseDto.getOffer();
        this.offerPrice = purchaseDto.getOffer().getPrice();
        this.quantity = purchaseDto.getQuantity();
        this.averagePrice = purchaseDto.getAveragePrice();
    }


}
