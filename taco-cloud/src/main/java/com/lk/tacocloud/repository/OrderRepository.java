package com.lk.tacocloud.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.lk.tacocloud.domain.TacoOrder;


/**
    Seems simple enough, right? Not so quick. When you save a TacoOrder, you also must
    save the Taco objects that go with it. And when you save the Taco objects, you’ll also
    need to save an object that represents the link between the Taco and each Ingredient
    that makes up the taco.  Enter IngredientRef.
 */
@Repository
public interface OrderRepository extends CrudRepository<TacoOrder, Long> {
    
    // should be findOrderBy...
    List<TacoOrder> findDeliveryByDeliveryZip(String delivery);

    /**
     * Let’s consider another, more complex example. Suppose that you need to query
        for all orders delivered to a given ZIP code within a given date range. In that case, the
        following method, when added to OrderRepository, might prove useful:

        Spring Data also understands find, read, and get as synonymous for fetching
        one or more entities. P 91 / 118
     */
    List<TacoOrder> readOrdersByDeliveryZipAndPlacedAtBetween(String deliveryZip, Date startDate, Date endDate);
}
