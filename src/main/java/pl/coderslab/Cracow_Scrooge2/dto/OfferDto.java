package pl.coderslab.Cracow_Scrooge2.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OfferDto {

    private Double price;

    private String shopName;

    private LocalDate addedAt;
}
