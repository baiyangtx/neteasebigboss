package btx.bigboss.beans;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Created by BaiyangTX on 2017/1/22.
 */
@Entity
public class Item {

    public static final int IMGTYPE_FILE = 1 ;
    public static final int IMGTYPE_NETWORK = 2 ;


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id ;

    String title ;

    String imgurl ;

    int imgtype ;

    String descript ;

    String content ;

    BigDecimal price ;

    @ManyToOne
    User publisher ;

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", imgurl='" + imgurl + '\'' +
                ", imgtype=" + imgtype +
                ", descript='" + descript + '\'' +
                ", content='" + content + '\'' +
                ", price=" + price +
                ", publisher=" + publisher +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public int getImgtype() {
        return imgtype;
    }

    public void setImgtype(int imgtype) {
        this.imgtype = imgtype;
    }

    public String getDescript() {
        return descript;
    }

    public void setDescript(String descript) {
        this.descript = descript;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public User getPublisher() {
        return publisher;
    }

    public void setPublisher(User publisher) {
        this.publisher = publisher;
    }
}
