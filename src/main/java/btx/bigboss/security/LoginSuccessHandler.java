package btx.bigboss.security;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by BaiyangTX on 2017/1/22.
 */
@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    private static Log logger = LogFactory.getLog(LoginSuccessHandler.class);

    private String defaultSuccessUrl = "/" ;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest,
                                        HttpServletResponse httpServletResponse,
                                        Authentication authentication) throws IOException, ServletException {
        Object obj = authentication.getPrincipal() ;
        if( obj instanceof  UserDetailImpl){
            UserDetailImpl userDetail = (UserDetailImpl)obj;
            int id = userDetail.getUserID() ;
            httpServletRequest.getSession().setAttribute("userID" , id);
            httpServletResponse.sendRedirect(defaultSuccessUrl);
            logger.debug("user " + userDetail + " login success ");
        }
    }
}
