package btx.bigboss.web.controllers;

import btx.bigboss.beans.Cart;
import btx.bigboss.beans.Item;
import btx.bigboss.beans.Translog;
import btx.bigboss.beans.User;
import btx.bigboss.repositories.CartRepository;
import btx.bigboss.repositories.ItemRepository;
import btx.bigboss.repositories.TranslogRepository;
import btx.bigboss.repositories.UserRepository;
import btx.bigboss.web.viewobjs.TranslogWapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

/**
 * Created by BaiyangTX on 2017/2/3.
 * 交易相关接口
 */
@Controller
public class TransController {

    private static final Log logger = LogFactory.getLog(TransController.class);

    @Autowired
    private UserRepository userRepository ;

    @Autowired
    private ItemRepository itemRepository ;

    @Autowired
    private CartRepository cartRepository ;

    @Autowired
    private TranslogRepository translogRepository;

    @RequestMapping(value = "/addcart" , method = RequestMethod.POST)
    public String addcart(
            @SessionAttribute("userID") Integer userID ,
            @RequestParam("itemid") Integer itemid ,
            @RequestParam(value = "number" , defaultValue = "1") Integer number
    ){
        User buyer = userRepository.findOne(userID);
        Item item = itemRepository.findOne(itemid);

        Cart cart = new Cart();
        cart.setBuyer(buyer);
        cart.setItem(item);
        cart.setNum(number);
        cartRepository.save(cart);

        return "redirect:/item/" + itemid + ".html" ;
    }


    @RequestMapping( value = "/settlement.html",method = RequestMethod.GET)
    public ModelAndView settlement(
            @SessionAttribute("userID") Integer userID ,
            @RequestHeader(value = "Referer",defaultValue = "/") String refer
    ){
        User user = userRepository.findOne(userID) ;
        List<Cart> carts = cartRepository.findByBuyerId(userID) ;
        Map<String,Object> models = Maps.newHashMap() ;
        models.put("role" , "buyer") ;
        models.put("user" , user ) ;
        models.put("carts" , carts) ;
        models.put("referer" , refer ) ;
        return new ModelAndView("settlement" , models) ;
    }

    @RequestMapping(value = "/buy" , method = RequestMethod.POST)
    public String buy(
            @SessionAttribute("userID") int userID
    ){
        User user = userRepository.findOne(userID) ;
        List<Cart> carts = cartRepository.findByBuyerId(userID) ;

        Map<Integer,Integer> itemNuber = Maps.newHashMap() ;
        Map<Integer,Item> itemMap = Maps.newHashMap() ;
        for ( Cart cart : carts ){
            int itemid = cart.getItem().getId() ;
            if( itemNuber.containsKey(itemid)){
                int num = itemNuber.get(itemid) ;
                itemNuber.put(itemid, num + cart.getNum() ) ;
            }else{
                itemNuber.put(itemid, cart.getNum() ) ;
            }
            itemMap.put(itemid,cart.getItem()) ;
        }

        List<Translog> translogs = Lists.newArrayList() ;
        for(Integer itemid : itemMap.keySet() ){
            Item item = itemMap.get(itemid);
            Translog translog = new Translog();
            translog.setBuyer(user);
            translog.setItem(itemMap.get(itemid));
            translog.setNumber(itemNuber.get(itemid));
            translog.setPrice(item.getPrice());
            translog.setTimestamp(new Timestamp(System.currentTimeMillis()));
            translogs.add(translog);
        }

        translogRepository.save(translogs);
        cartRepository.delete(carts);

        return "redirect:/financial.html" ;
    }


    @RequestMapping(value = "/financial.html" , method = RequestMethod.GET)
    public ModelAndView financial(
            @SessionAttribute("userID") int userID
    ){
        User user = userRepository.findOne(userID) ;
        List<Translog> translogs = translogRepository.findByBuyerId(userID) ;
        Map<String,Object> models = Maps.newHashMap() ;
        models.put("role" , "buyer") ;
        models.put("user" , user ) ;
//
//        logger.debug("user:" + user);
//        logger.debug("translogs:" + translogs.size());
//

        List<TranslogWapper> translogsWappers = Lists.newLinkedList() ;
        BigDecimal totalPriceAll = new BigDecimal(0) ;
        for(Translog translog : translogs){
            TranslogWapper translogWapper = new TranslogWapper(translog);
            BigDecimal totalPrice = translogWapper.getPrice().multiply(
                    new BigDecimal(translog.getNumber())
            );
            totalPriceAll = totalPriceAll.add(totalPrice) ;

            translogsWappers.add(translogWapper) ;
        }
        models.put("translogs" , translogsWappers) ;
        models.put("totalPrice" , totalPriceAll) ;


        return new ModelAndView("financial", models);
    }
}
