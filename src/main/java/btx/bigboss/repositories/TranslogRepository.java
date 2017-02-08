package btx.bigboss.repositories;

import btx.bigboss.beans.Translog;
import btx.bigboss.beans.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by BaiyangTX on 2017/2/2.
 */
public interface TranslogRepository
        extends CrudRepository<Translog,Integer> {

    List<Translog> findByBuyerId(int userID) ;

    List<Translog> findByItemPublisherId(int sellerId);

    Translog findByItemId(int itemid ) ;
}
