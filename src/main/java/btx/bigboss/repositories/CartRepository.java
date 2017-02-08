package btx.bigboss.repositories;

import btx.bigboss.beans.Cart;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by BaiyangTX on 2017/2/3.
 */
public interface CartRepository extends CrudRepository<Cart,Integer> {

    List<Cart> findByBuyerId(int userId) ;
}
