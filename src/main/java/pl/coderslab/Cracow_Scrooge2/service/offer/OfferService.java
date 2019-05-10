package pl.coderslab.Cracow_Scrooge2.service.offer;

import org.springframework.stereotype.Service;
import pl.coderslab.Cracow_Scrooge2.entity.Offer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class OfferService {

    public Set<String> getAllShops(List<Offer> offers){
        Set<String> shopList = new HashSet<>();
        for (Offer o: offers){
            shopList.add(o.getShopName().toUpperCase().trim());
        }
        return shopList;

    }

    public List<Offer> getByShopName(List<Offer> offers,String shopName){
        List<Offer> listByShopName = new ArrayList<>();

        for (Offer o:offers) {
            if(o.getShopName().toLowerCase().trim().equals(shopName)){
                listByShopName.add(o);
            }
        }
        return listByShopName;
    }
}
