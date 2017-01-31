package btx.bigboss.web.controllers;

import btx.bigboss.beans.Item;
import btx.bigboss.beans.User;
import btx.bigboss.repositories.ItemRepository;
import btx.bigboss.repositories.UserRepository;
import btx.bigboss.utils.MD5Utils;
import com.google.common.collect.Maps;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;

/**
 * Created by BaiyangTX on 2017/1/31.
 */
@Controller
public class PublishController {

    private static final Log logger = LogFactory.getLog(PublishController.class ) ;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ItemRepository itemRepository;


    @RequestMapping(value = "/publish.html", method = RequestMethod.GET)
    public ModelAndView publish(
            @SessionAttribute("userID") Integer userID
    ){
        User user = userRepository.findOne(userID);
        Map<String ,Object> models = Maps.newHashMap() ;
        models.put("user" , user);
        models.put("role" , "seller") ;

        return new ModelAndView("publish", models);
    }

    @RequestMapping(value = "/publish" , method = RequestMethod.POST)
    public String publish(
            @RequestParam("title")String title ,
            @RequestParam("description")String description ,
            @RequestParam("imgtype")String imgtype ,
            @RequestParam(value = "imgurl", required = false ) String imgurl ,
            @RequestParam(value = "file" , required = false) MultipartFile file ,
            @RequestParam("content") String content ,
            @RequestParam("price") BigDecimal price ,
            @SessionAttribute("userID") Integer userID
    ) throws IOException {
        User user = userRepository.findOne(userID);
        Item item = new Item();
        item.setTitle(title);
        item.setDescript(description);
        item.setContent(content);
        item.setPrice(price);
        switch (imgtype) {
            case "upload":
                item.setImgtype(Item.IMGTYPE_FILE);
                String filename = handleUploadImageFile(file , userID) ;
                item.setImgurl(filename);
                break;
            case "network":
                item.setImgtype(Item.IMGTYPE_NETWORK);
                item.setImgurl(imgurl);
                break;
            default:
                throw new IllegalStateException("illegal img type ");
        }
        item.setPublisher(user);
        itemRepository.save(item);
        logger.debug(item);
        return "redirect:/";
    }


    @Value("${bigboss.imgpath}" )
    private String imgSavePath = "D:/bigboss" ;


    private String handleUploadImageFile(MultipartFile file,int userid ) throws IOException {
        String originalFilename = file.getOriginalFilename() ;
        String filename = userid + "" + System.currentTimeMillis() + originalFilename ;
        filename = MD5Utils.string2MD5(filename) ;

        File savePath = new File(imgSavePath);
        if (!savePath.exists()){
            if(!savePath.mkdirs() ){
                throw new IllegalStateException("mkdir for " + imgSavePath + " failed") ;
            }
        }

        File imgfile = new File(imgSavePath + File.separator + filename );
        file.transferTo(imgfile);

        return filename ;
    }
}
