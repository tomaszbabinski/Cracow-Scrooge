package pl.coderslab.Cracow_Scrooge2.service.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.coderslab.Cracow_Scrooge2.entity.Product;
import pl.coderslab.Cracow_Scrooge2.repository.ProductRepository;

import java.util.List;

@Service
public class ProductService {

    private ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public boolean checkIfExists(String name, String brand, Long id){

        List<Product> products = productRepository.findAllByUserId(id);
        for(Product product: products){
            if(product.getName().equals(name) && product.getBrand().equals(brand)){
                return true;
            }
        }
        return false;
    }

}
