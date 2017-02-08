package btx.bigboss.beans;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Created by BaiyangTX on 2017/2/2.
 */
@Entity
public class Translog {

    @Id
    @GeneratedValue()
    private int id;

    @ManyToOne
    Item item ;

    @ManyToOne
    User buyer ;

    Timestamp timestamp ;

    BigDecimal price ;

    int number ;


    @Override
    public String toString() {
        return "Translog{" +
                "id=" + id +
                ", item=" + item +
                ", buyer=" + buyer +
                ", timestamp=" + timestamp +
                ", price=" + price +
                ", number=" + number +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public User getBuyer() {
        return buyer;
    }

    public void setBuyer(User buyer) {
        this.buyer = buyer;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
