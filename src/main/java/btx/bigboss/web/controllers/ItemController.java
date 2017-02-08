package btx.bigboss.web.controllers;

import btx.bigboss.beans.Item;
import btx.bigboss.beans.Translog;
import btx.bigboss.beans.User;
import btx.bigboss.exception.ResourceNotFoundException;
import btx.bigboss.repositories.ItemRepository;
import btx.bigboss.repositories.TranslogRepository;
import btx.bigboss.repositories.UserRepository;
import btx.bigboss.utils.FileUtils;
import btx.bigboss.utils.NetworkUtils;
import btx.bigboss.web.viewobjs.ItemWapper;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Created by BaiyangTX on 2017/1/31.
 */
@Controller
public class ItemController {

    @Autowired
    private UserRepository userRepository ;

    @Autowired
    private ItemRepository itemRepository ;

    @Autowired
    private TranslogRepository translogRepository ;

    @RequestMapping(value="/item/{itemid}.html" , method = RequestMethod.GET)
    public ModelAndView itemView(
            @SessionAttribute(value="userID" , required = false) Integer userID ,
            @PathVariable("itemid") Integer itemId
    ){

        User user = null ;
        if ( userID != null ){
            user = userRepository.findOne(userID );
        }
        Item item = itemRepository.findOne(itemId) ;
        if(item == null ){
            throw new ResourceNotFoundException();
        }

        Map<String , Object> models ;
        if ( user == null ){
            models = unloginItemViewModes(null,item) ;
        }else if( user.isBuyer() ){
            models = buyerItemViewModes(user,item) ;
        }else{
            models = sellerItemViewModes(user,item) ;
        }

        return new ModelAndView("item" , models);
    }

    private Map<String , Object> buyerItemViewModes(User buyer , Item item){
        Map<String , Object> models = Maps.newHashMap() ;
        models.put("role" , "buyer");
        models.put("user" , buyer) ;
        ItemWapper itemWapper = new ItemWapper(item);
        models.put("item" , itemWapper );
        return models ;
    }

    private Map<String , Object> sellerItemViewModes(User seller , Item item){
        Map<String , Object> models = Maps.newHashMap() ;
        models.put("role" , "seller");
        models.put("user" , seller) ;

        ItemWapper itemWapper = new ItemWapper(item);
        models.put("item" , itemWapper );
        return models ;
    }

    private Map<String , Object> unloginItemViewModes(User seller , Item item){
        Map<String , Object> models = Maps.newHashMap() ;
        models.put("role" , "unlogin");
        ItemWapper itemWapper = new ItemWapper(item);
        models.put("item" , itemWapper );
        return models ;
    }


    @RequestMapping(value="/item/delete/{itemid}" , method = RequestMethod.GET)
    public String itemDelete(
            @PathVariable("itemid") Integer itemid
    ){
        Translog translog = translogRepository.findByItemId(itemid);
        if( translog != null ){
            throw new IllegalStateException("Item Selled") ;
        }
        Item item = itemRepository.findOne(itemid);
        itemRepository.delete(item);
        return "redirect:/";
    }

    @RequestMapping(value="/item/edit/{itemid}.html" , method = RequestMethod.GET)
    public ModelAndView itemEdit(
            @SessionAttribute(value="userID" ) Integer userID ,
            @PathVariable("itemid") Integer itemId
    ){
        Map<String,Object> models = Maps.newHashMap() ;
        User user = userRepository.findOne(userID);
        models.put("user",user) ;
        models.put("role","seller") ;

        Item item = itemRepository.findOne(itemId);
        ItemWapper wapper = new ItemWapper(item);
        models.put("item" , wapper);
        return new ModelAndView("edit" , models);
    }

    @RequestMapping(value = "/edit" , method = RequestMethod.POST)
    public String edit(
            @RequestParam("title")String title ,
            @RequestParam("description")String description ,
            @RequestParam("imgtype")String imgtype ,
            @RequestParam(value = "imgurl", required = false ) String imgurl ,
            @RequestParam(value = "file" , required = false) MultipartFile file ,
            @RequestParam("content") String content ,
            @RequestParam("price") BigDecimal price ,
            @SessionAttribute("userID") Integer userID ,
            @RequestParam("id") Integer itemId
    ) throws IOException {
        User user = userRepository.findOne(userID);
        Item item = itemRepository.findOne(itemId);
        item.setTitle(title);
        item.setDescript(description);
        item.setContent(content);
        item.setPrice(price);
        if( !file.isEmpty()){
            item.setImgtype(Item.IMGTYPE_FILE);
            String filename = FileUtils.handleUploadImageFile(file,userID,imgSavePath)  ;
            item.setImgurl(filename);
        }else if (NetworkUtils.isUrl(imgurl)){
            item.setImgtype(Item.IMGTYPE_NETWORK);
            item.setImgurl(imgurl);
        }

        item.setPublisher(user);
        itemRepository.save(item);


        return "redirect:/item/" + itemId + ".html" ;
    }

    @Value("${bigboss.imgpath}" )
    private String imgSavePath = "D:/bigboss" ;



}
