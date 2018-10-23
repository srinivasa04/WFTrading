package com.worldfirst.fx.trading.web;

import com.worldfirst.fx.trading.util.CurrencyEnum;
import com.worldfirst.fx.trading.model.TradeOrder;
import com.worldfirst.fx.trading.service.OrderService;
import com.worldfirst.fx.trading.util.OrderStatusEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

/**
 * Rest controller for Trade order
 */
@Api(value="WorldFirst Trading REST platform", description="Operations pertaining for Foreign exchange trade.")
@RestController
@Slf4j
public class OrderController {

    Logger log = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderService orderService;

    @ApiOperation(value = "View a list of available orders", response = Iterable.class)
    @GetMapping("/orders")
    List<TradeOrder> allOrders() {
        log.info("Retrieving all orders");
        return orderService.listAllOrders();
    }

    /**
     * For registering a new order
     * @param order
     * @return the order created.
     */
    @ApiOperation(value = "Creates a new trade order")
    @PostMapping("/orders/order")
    public ResponseEntity<TradeOrder> registerOrder(@RequestBody TradeOrder order){

        //If currency is not 'GBP/USD', reject the order.
        if(!CurrencyEnum.GBP_USD.value().equals(order.getCurrency())){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        log.info("Order received for creation:"+order);
        TradeOrder responseOrder = orderService.createOrder(order);
        return new ResponseEntity(responseOrder, HttpStatus.CREATED);
    }

    /**
     * Retrieve the order with orderId
     * @param id
     * @return
     */
    @ApiOperation(value = "Retrieves a particular order", response = TradeOrder.class)
    @GetMapping("/orders/order/{id}")
    public TradeOrder getOrder(@PathVariable Long id) {
        log.info("Retrieving order for Id :"+id);
        return orderService.listOrderById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
    }

    /**
     * Retrieve all the orders for a given userId
     * @param userId
     * @return
     */
    @ApiOperation(value = "Retrieves all orders for a particular user.", response = Iterable.class)
    @GetMapping("/orders/{userId}")
    public List<TradeOrder> getOrdersForUserId(@PathVariable String userId) {
        log.info("Retrieving all matched orders for userId :"+userId);
        return orderService.listOrdersByUserId(userId);
    }

    /**
     * Method to cancel(update) the order.
     * @param order
     */
    @ApiOperation(value = "Cancel the order.")
    @PutMapping("/orders/order")
    public ResponseEntity<TradeOrder> cancelOrder(@RequestBody TradeOrder order) {

        log.info("Order received for cancel: "+order);
        // Only pending orders can be cancelled.
        if(OrderStatusEnum.PENDING.value().equals(order.getStatus())){
            orderService.cancelOrder(order);
            log.info("Order cancelled :"+order);
            return new ResponseEntity(order,HttpStatus.OK);
        }else {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

    }

    /**
     * Method to get summary of matched trades.
     * @return all matched orders
     */
    @ApiOperation(value = "Retrieves all matched orders.", response = Iterable.class)
    @GetMapping("/orders/matched")
    public Map<TradeOrder, List<TradeOrder>> getMatchedTrades() {
        log.info("Retrieving all matched orders");
        return orderService.getMatchedOrders();
    }


    @ApiOperation(value = "Retrieves all unmatched orders.", response = Iterable.class)
    @GetMapping("/orders/unMatched")
    public List<TradeOrder> getUnMatchedOrders() {
        log.info("Retrieving all unMatched orders");
        return orderService.getUnMatchedOrders();
    }

    /**
     * Method to delete(update) the order.
     * @param id
     */
    @ApiOperation(value = "Deletes an order")
    @DeleteMapping("/orders/order/{id}")
    public void deleteOrder(@PathVariable Long id) {
        log.info("Deleting  an order for id "+id);
        orderService.deleteOrderById(id);
    }

}
