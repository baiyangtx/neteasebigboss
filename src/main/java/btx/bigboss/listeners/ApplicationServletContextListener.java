package btx.bigboss.listeners;

import btx.bigboss.beans.User;
import btx.bigboss.repositories.UserRepository;
import btx.bigboss.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Created by BaiyangTX on 2017/1/25.
 */
@WebListener
public class ApplicationServletContextListener implements ServletContextListener {

    @Autowired
    private UserRepository userRepository ;



    /**
     * * Notification that the web application initialization process is starting.
     * All ServletContextListeners are notified of context initialization before
     * any filter or servlet in the web application is initialized.
     *
     * @param sce Information about the ServletContext that was initialized
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        initUser();
    }

    /**
     * * Notification that the servlet context is about to be shut down. All
     * servlets and filters have been destroy()ed before any
     * ServletContextListeners are notified of context destruction.
     *
     * @param sce Information about the ServletContext that was destroyed
     */
    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }




    private void initUser(){
        User buyer = userRepository.findByAccount("buyer");
        String md5Buyer = MD5Utils.string2MD5("reyub") ;
        if( buyer == null ){
            buyer = new User() ;
            buyer.setUsername("买家");
            buyer.setAccount("buyer");
            buyer.setPassword(md5Buyer);
            buyer.setRole(User.BUYER);
            userRepository.save(buyer);
        }
        System.out.println("MD5 of reyub:" + md5Buyer);
        User seller = userRepository.findByAccount("seller");
        String md5Seller =MD5Utils.string2MD5("relles") ;
        if ( seller == null ){
            seller = new User() ;
            seller.setUsername("卖家");
            seller.setAccount("seller");
            seller.setPassword(md5Seller);
            seller.setRole(User.SELLER);
            userRepository.save(seller);
        }
        System.out.println("MD5 of relles:" + md5Seller);
    }
}
