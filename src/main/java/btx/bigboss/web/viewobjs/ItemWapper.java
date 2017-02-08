package btx.bigboss.web.viewobjs;

import btx.bigboss.beans.Item;
import btx.bigboss.beans.User;

import java.math.BigDecimal;
import java.util.Set;

/**
 * Created by BaiyangTX on 2017/1/31.
 */
public class ItemWapper extends Item {

    private Item item ;

    private boolean buyed ;

    private int selledCount = 0 ;

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

    public String getImgfiletype(){
        return item.getImgtype() == IMGTYPE_NETWORK ? "network" :"upload";
    }

    public boolean getBuyed(){
        return this.buyed ;
    }

    public void setBuyed(boolean buyed){
        this.buyed = buyed ;
    }

    public void updateBuyed(Set<Integer> buyedItemIDs){
        int itemid = getId() ;
        this.buyed = buyedItemIDs.contains(itemid) ;
    }

    public int getSelledCount() {
        return selledCount;
    }

    public void setSelledCount(int selledCount) {
        this.selledCount = selledCount;
    }
}
