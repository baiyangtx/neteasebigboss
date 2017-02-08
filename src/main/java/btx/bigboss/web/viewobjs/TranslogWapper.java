package btx.bigboss.web.viewobjs;

import btx.bigboss.beans.Item;
import btx.bigboss.beans.Translog;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Created by BaiyangTX on 2017/2/6.
 */
public class TranslogWapper  {

    private Translog translog ;

    private ItemWapper item;

    public TranslogWapper(Translog translog){
        this.translog = translog ;
        this.item = new ItemWapper(translog.getItem());
    }

    public Item getItem(){
        return this.item;
    }

    public Timestamp getTimestamp(){
        return translog.getTimestamp() ;
    }

    public BigDecimal getPrice(){
        return translog.getPrice() ;
    }

    public int getNumber(){
        return translog.getNumber() ;
    }
}
