package btx.bigboss.web.viewobjs;

import btx.bigboss.beans.Item;
import btx.bigboss.beans.User;

import java.math.BigDecimal;

/**
 * Created by BaiyangTX on 2017/1/31.
 */
public class ItemWapper extends Item {

    private Item item ;

    public ItemWapper(Item item){
        this.item = item ;
    }

    @Override
    public int getId() {
        return item.getId();
    }

    @Override
    public String getContent() {
        return item.getContent() ;
    }

    @Override
    public BigDecimal getPrice() {
        return item.getPrice() ;
    }

    @Override
    public String getDescript() {
        return item.getDescript();
    }

    @Override
    public String getTitle() {
        return item.getTitle() ;
    }

    @Override
    public User getPublisher() {
        return item.getPublisher() ;
    }

    public String getSrc(){
        if( item.getImgtype() == IMGTYPE_NETWORK ){
            return item.getImgurl() ;
        }else{
            return "/itemimage/" + item.getImgurl() ;
        }
    }
}
