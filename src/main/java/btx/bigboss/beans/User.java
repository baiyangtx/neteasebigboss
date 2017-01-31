package btx.bigboss.beans;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by BaiyangTX on 2017/1/4.
 */
@Entity
public class User {

    public static final int BUYER = 1 ;
    public static final int SELLER = 1 << 1 ;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id ;


    String account ;

    String password ;

    String username ;

    int role ;

    public boolean isBuyer(){
        return role == BUYER ;
    }

    public boolean isSeller(){
        return role == SELLER ;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
