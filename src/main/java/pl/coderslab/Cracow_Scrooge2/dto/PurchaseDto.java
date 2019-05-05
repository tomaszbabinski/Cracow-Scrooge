package pl.coderslab.Cracow_Scrooge2.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.coderslab.Cracow_Scrooge2.entity.Offer;
import pl.coderslab.Cracow_Scrooge2.entity.Product;
import pl.coderslab.Cracow_Scrooge2.entity.User;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseDto {

    private Long id;

    private User user;

    private Product product;

    private Offer offer;

    private Double offerPrice;

    private Double savedMoney;

    private Integer quantity;
}
