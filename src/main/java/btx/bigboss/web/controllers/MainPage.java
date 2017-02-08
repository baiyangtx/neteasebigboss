package btx.bigboss.web.controllers;

import btx.bigboss.Main;
import btx.bigboss.beans.Item;
import btx.bigboss.beans.Translog;
import btx.bigboss.beans.User;
import btx.bigboss.repositories.ItemRepository;
import btx.bigboss.repositories.TranslogRepository;
import btx.bigboss.repositories.UserRepository;

import btx.bigboss.web.viewobjs.ItemWapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.ManyToOne;
import java.util.*;

/**
 * Created by BaiyangTX on 2017/1/3.
 */
@Controller
public class MainPage {

    @Autowired
    UserRepository userRepository ;

    @Autowired
    ItemRepository itemRepository ;

    @Autowired
    TranslogRepository translogRepository ;

    @RequestMapping({"/","/index" ,"/index.html"})
    public ModelAndView index(
            @SessionAttribute(value = "userID",required = false) Integer userID){

        Map<String,Object> models;
        User user = null ;
        if( userID != null){
            user = userRepository.findOne(userID);
        }

        if( user == null ){
            models = unloginMainPageModels(null) ;
        }else if (user.isBuyer()){
            models = buyerMainPageModels(user);
        }else {
            models = sellerMainPageModels(user);
        }

        models.put("user" , user);
        return new ModelAndView("index",models);
    }

    @RequestMapping(value = {"/login.html","/login"} , method = RequestMethod.GET)
    public ModelAndView login(){
        return new ModelAndView("login");
    }


    /**
     * buyer主页展示数据
     * @param buyer
     * @return
     */
    private Map<String,Object> buyerMainPageModels(User buyer){
        Map<String,Object> models = Maps.newHashMap();
        models.put("role" , "buyer");


        List<Translog> buylogs = translogRepository.findByBuyerId(buyer.getId()) ;
        Set<Integer> buyedItemIds = Sets.newHashSet() ;

        for (Translog log : buylogs){
            buyedItemIds.add( log.getItem().getId() );
        }

        List<Item> allItems = Lists.newLinkedList() ;
        List<Item> unbuyedItems = Lists.newLinkedList() ;

        for(Item item : itemRepository.findAll()) {
            ItemWapper itemWapper = new ItemWapper(item);
            itemWapper.updateBuyed(buyedItemIds);
            allItems.add(itemWapper) ;
            if( !itemWapper.getBuyed() ){
                unbuyedItems.add(itemWapper);
                // 未购买的商品
            }
        }

        models.put("items" , allItems) ;
        models.put("unbuyedItems" , unbuyedItems) ;
        return models;
    }

    /**
     * seller主页展示数据
     * @param user
     * @return
     */
    private Map<String,Object> sellerMainPageModels(User user){
        Map<String,Object> models = Maps.newHashMap();
        models.put("role" , "seller");


        List<Translog> selledLogs = translogRepository.findByItemPublisherId(user.getId());
        Map<Integer,Integer> selledCount = Maps.newHashMap() ;
        for(Translog log : selledLogs){
            int itemid = log.getItem().getId() ;
            if( selledCount.containsKey(itemid) ){
                int count = selledCount.get(itemid) ;
                count += log.getNumber() ;
                selledCount.put(itemid, count) ;
            }else{
                selledCount.put(itemid,log.getNumber() ) ;
            }
        }

        List<Item> items = Lists.newLinkedList() ;
        for(Item item : itemRepository.findAll()) {
            ItemWapper itemWapper = new ItemWapper(item) ;
            if( selledCount.containsKey(item.getId()) ){
                itemWapper.setSelledCount(selledCount.get(item.getId()));
            }
            items.add(itemWapper) ;
        }

        models.put("items" , items) ;
        return models;
    }

    /**
     * 未登录用户主页展示
     * @param user
     * @return
     */
    private Map<String,Object> unloginMainPageModels(User user){
        Map<String,Object> models = Maps.newHashMap();
        models.put("role" , "unlogin");

        List<Item> items = Lists.newLinkedList() ;
        for(Item item : itemRepository.findAll()) {
            items.add(new ItemWapper(item)) ;
        }

        models.put("items" , items) ;
        return models;
    }
}
