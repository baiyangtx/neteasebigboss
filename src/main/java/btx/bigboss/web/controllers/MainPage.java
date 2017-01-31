package btx.bigboss.web.controllers;

import btx.bigboss.beans.Item;
import btx.bigboss.beans.User;
import btx.bigboss.repositories.ItemRepository;
import btx.bigboss.repositories.UserRepository;

import btx.bigboss.web.viewobjs.ItemWapper;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by BaiyangTX on 2017/1/3.
 */
@Controller
public class MainPage {

    @Autowired
    UserRepository userRepository ;

    @Autowired
    ItemRepository itemRepository ;


    @RequestMapping({"/","/index" ,"/index.html"})
    public ModelAndView index(
            @SessionAttribute(value = "userID",required = false) Integer userID){
        String role = "unlogin" ;
        Map<String,Object> models = new HashMap<>();
        if( userID != null ){
            User user = userRepository.findOne(userID);
            if( user != null ){
                models.put("user" , user) ;
                role = user.isBuyer() ? "buyer" : "seller" ;
            }
        }
        models.put("role" , role);

        List<Item> items = Lists.newLinkedList() ;
        for(Item item : itemRepository.findAll()) {
            items.add(new ItemWapper(item)) ;
        }

        models.put("items" , items) ;
        return new ModelAndView("index",models);
    }

    @RequestMapping(value = "/login.html" , method = RequestMethod.GET)
    public ModelAndView login(){
        return new ModelAndView("login");
    }

}
