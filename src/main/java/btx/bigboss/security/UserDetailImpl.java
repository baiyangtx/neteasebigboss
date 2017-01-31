package btx.bigboss.security;

import btx.bigboss.beans.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * Created by BaiyangTX on 2017/1/22.
 */
public class UserDetailImpl implements UserDetails {

    private User user ;

    public UserDetailImpl(User user){
        this.user = user ;
    }


    public int getUserID(){
        return this.user.getId() ;
    }

    @Override
    public String toString() {
        return super.toString() + String.format("[UserID:=%d , account=%s]",
                getUserID(), getUsername());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return user.getPassword() ;
    }

    @Override
    public String getUsername() {
        return user.getAccount() ;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
