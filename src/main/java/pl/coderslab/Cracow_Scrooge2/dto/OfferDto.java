package pl.coderslab.Cracow_Scrooge2.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OfferDto {

    private Double price;

    private String shopName;
}
