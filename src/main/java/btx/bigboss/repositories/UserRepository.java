package btx.bigboss.repositories;

import btx.bigboss.beans.User;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by BaiyangTX on 2017/1/25.
 */
public interface UserRepository extends CrudRepository<User,Integer> {

    User findByAccount(String account) ;

}
