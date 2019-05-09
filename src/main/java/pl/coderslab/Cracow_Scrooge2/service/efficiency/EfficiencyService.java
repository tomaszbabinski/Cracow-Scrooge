package pl.coderslab.Cracow_Scrooge2.service.efficiency;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.coderslab.Cracow_Scrooge2.entity.Efficiency;
import pl.coderslab.Cracow_Scrooge2.entity.Product;
import pl.coderslab.Cracow_Scrooge2.repository.EfficiencyRepository;

import java.time.LocalDate;

import static java.time.temporal.ChronoUnit.DAYS;

@Service
public class EfficiencyService {

    private EfficiencyRepository efficiencyRepository;

    @Autowired
    public EfficiencyService(EfficiencyRepository efficiencyRepository) {
        this.efficiencyRepository = efficiencyRepository;
    }

    public void saveEfficiency(Product product){
        Efficiency efficiency = new Efficiency();
        efficiency.setStartUsage(product.getBegOfUsage());
        efficiency.setEndUsage(LocalDate.now());
        efficiency.setProduct(product);
        efficiency.setEfficiencyInDays(DAYS.between(efficiency.getStartUsage(),efficiency.getEndUsage()));
        efficiencyRepository.save(efficiency);
    }
}
