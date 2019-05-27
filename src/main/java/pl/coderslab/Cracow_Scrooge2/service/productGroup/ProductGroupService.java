package pl.coderslab.Cracow_Scrooge2.service.productGroup;

import org.springframework.stereotype.Service;
import pl.coderslab.Cracow_Scrooge2.entity.ProductGroup;
import pl.coderslab.Cracow_Scrooge2.repository.ProductGroupRepository;

@Service
public class ProductGroupService {

    private ProductGroupRepository productGroupRepository;

    public ProductGroupService(ProductGroupRepository productGroupRepository) {
        this.productGroupRepository = productGroupRepository;
    }

    public boolean checkUniqueGroup(String productGroupName, Long id) {
        ProductGroup productGroup = productGroupRepository.findByNameAndUserId(productGroupName,id);
        if (productGroup == null) {
            return true;
        } else {
            return false;
        }
    }
}
