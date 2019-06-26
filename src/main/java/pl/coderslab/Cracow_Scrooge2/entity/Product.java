package pl.coderslab.Cracow_Scrooge2.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "products")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String brand;

    @ManyToOne
    private ProductGroup group;

    @OneToMany(mappedBy = "product")
    @JsonIgnore
    private List<Purchase> purchases;

    @ManyToOne
    @JsonIgnore
    private User user;

    @OneToMany(mappedBy = "product")
    @JsonIgnore
    private List<Offer> offers;

    @OneToMany(mappedBy = "product")
    private List<Efficiency> efficiencies;

    private String isActive;
    private LocalDate begOfUsage;
    private LocalDate endOfUsage;

    private String isLiked;

    public Double getAverageEfficiency() {
        Long sumOfAllEfficiencies = efficiencies.stream().map(efficiency -> (efficiency.getEfficiencyInDays())).reduce((a, b) -> Long.sum(a, b)).orElse(0L);
        Double averageEfficiency = (double) Math.round(sumOfAllEfficiencies * 100.0) / 100.0;
        return averageEfficiency;
    }
}
