package pl.coderslab.Cracow_Scrooge2.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "efficiencies")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Efficiency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonIgnore
    private Product product;

    private LocalDate startUsage;
    private LocalDate endUsage;

    private Long efficiencyInDays;
}
