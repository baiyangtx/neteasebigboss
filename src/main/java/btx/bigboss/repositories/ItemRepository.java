package btx.bigboss.repositories;

import btx.bigboss.beans.Item;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by BaiyangTX on 2017/1/25.
 */
public interface ItemRepository extends CrudRepository<Item,Integer> {
}
