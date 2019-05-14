package pl.coderslab.Cracow_Scrooge2.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.coderslab.Cracow_Scrooge2.entity.ProductGroup;
import pl.coderslab.Cracow_Scrooge2.entity.User;
import pl.coderslab.Cracow_Scrooge2.repository.ProductGroupRepository;

@Service
public class UserService {

    private ProductGroupRepository productGroupRepository;

    @Autowired
    public UserService(ProductGroupRepository productGroupRepository) {
        this.productGroupRepository = productGroupRepository;
    }

    public void createStartingCategories(User user){
        ProductGroup chemistry = new ProductGroup("Chemistry");
        chemistry.setUser(user);
        productGroupRepository.save(chemistry);

        ProductGroup clothes = new ProductGroup("Clothes");
        clothes.setUser(user);
        productGroupRepository.save(clothes);

        ProductGroup electronics = new ProductGroup("Electronics");
        electronics.setUser(user);
        productGroupRepository.save(electronics);

        ProductGroup groceries = new ProductGroup("Groceries");
        groceries.setUser(user);
        productGroupRepository.save(groceries);


    }
}
