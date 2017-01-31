package btx.bigboss.security;

import btx.bigboss.beans.User;
import btx.bigboss.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * Created by BaiyangTX on 2017/1/22.
 */
@Component
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository ;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepository.findByAccount(s);
        if ( user == null ){
            throw new UsernameNotFoundException(s);
        }
        return new UserDetailImpl(user);
    }
}
