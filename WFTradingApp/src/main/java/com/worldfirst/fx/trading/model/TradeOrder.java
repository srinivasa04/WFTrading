package com.worldfirst.fx.trading.model;



import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.text.DecimalFormat;
import java.util.Date;

/**
 * TradeOrder entity.
 */
@Data
@Entity
public class TradeOrder {

    /**
     * Assuming the decimal values to 4
     */
    static DecimalFormat decimalFormat;

    static {
        decimalFormat = new DecimalFormat("0.0000");
    }


    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    @ApiModelProperty(required = true, value = "USER Id")
    private String userId;

    /**
     * Order Type like BID/ASK
     */
    @ApiModelProperty(required = true, value = "Order Type like BID/ASK")
    private String orderType;
    /**
     * Currency in pair, eg: GBP/USD
     */
    @ApiModelProperty(required = true, value = "Trade currency for eg: GBP/USD")
    private String currency;
    /**
     * BID/ASK price
     */
    @ApiModelProperty(required = true, value = "price")
    private String price;
    /**
     * Total amount for BID/ASK
     */
    @ApiModelProperty(required = true, value = "Total amount")
    private String amount;
    /**
     * Order created date & time
     */
    private Date createdDate;

    /**
     *
     * Holding different status of Order like Pending, Cancel, Executed.
     */
    private String status;



    public TradeOrder() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public TradeOrder(String userId, String orderType, String currency, String price, String amount, Date createdDate, String status) {
        this.userId = userId;
        this.orderType = orderType;
        this.currency = currency;
        this.price = price;
        this.amount = amount;
        this.status =status;
        this.createdDate=createdDate;
        }

    @Override
    public String toString() {
        return "TradeOrder{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", orderType='" + orderType + '\'' +
                ", currency='" + currency + '\'' +
                ", price=" + price +
                ", amount=" + amount +
                ", createdDate=" + createdDate +
                ", status='" + status + '\'' +
                '}';
    }
}
