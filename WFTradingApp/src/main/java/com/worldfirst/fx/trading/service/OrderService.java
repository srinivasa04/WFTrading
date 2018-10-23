package com.worldfirst.fx.trading.service;

import com.worldfirst.fx.trading.util.OrderStatusEnum;
import com.worldfirst.fx.trading.util.OrderTypeEnum;
import com.worldfirst.fx.trading.model.TradeOrder;
import com.worldfirst.fx.trading.repository.TradeOrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Service implementation class for orders.
 */
@Slf4j
@Service
public class OrderService {

    @Autowired
    TradeOrderRepository orderRepository;

    @Value( "${wf.fx.gbp.to.usd.current.price}" )
    private Double currentPrice;

    /**
     * Creates a new order.
     * @param order
     * @return order
     */
    public TradeOrder createOrder(TradeOrder order){
        // For new orders set the status as pending.
        order.setStatus(OrderStatusEnum.PENDING.name());
        order.setCreatedDate(new Date());
        return orderRepository.save(order);
    }

    /**
     * Retrieves all the orders.
     * @return list of orders
     */
    public List<TradeOrder> listAllOrders(){
        return (List<TradeOrder>) orderRepository.findAll();
    }

    /**
     * Retrieves all orders with a specific order Id
     * @param orderId
     * @return order list
     */
    public Optional<TradeOrder> listOrderById(Long orderId){
        return orderRepository.findById(orderId);
    }

/**
     * Deletes an order from the system.
     * @param orderId
   */
    public void deleteOrderById(Long orderId){
        orderRepository.deleteById(orderId);
    }

    /**
     * Method to updated order status to cancelled.
     * @param order
     */
    public void cancelOrder(TradeOrder order){
        // For cancelled orders set the status as cancelled.
        order.setStatus(OrderStatusEnum.CANCELLED.value());
        orderRepository.save(order);
    }

    /**
     * Method to retrieve all matched orders with the current price.
     *
     * @return all matched trade orders.
     */
    public Map<TradeOrder,List<TradeOrder>> getMatchedOrders(){
        Map<TradeOrder,List<TradeOrder>> matchedOrdersMap = new HashMap();
        List<TradeOrder> matchedOrders = null;

        List<TradeOrder> pendingBidOrders = orderRepository.findByOrderTypeAndStatus(OrderTypeEnum.BID.value(),
                OrderStatusEnum.PENDING.value());
        List<TradeOrder> pendingAskOrders = orderRepository.findByOrderTypeAndStatus(OrderTypeEnum.ASK.value(),
                OrderStatusEnum.PENDING.value());
        /**
         * For all ORDER BID's, identify the matching orders(ASK) and associate the orders with the BID.
         */
        for (TradeOrder bidOrder : pendingBidOrders) {
            matchedOrders = pendingAskOrders.stream()
                    .filter(askOrder -> (Double.valueOf(askOrder.getPrice()) <= currentPrice
                             && Double.valueOf(askOrder.getPrice()) <=Double.valueOf(bidOrder.getPrice())
                             && Double.valueOf(askOrder.getAmount()) <=Double.valueOf(bidOrder.getAmount())
                    )).collect(Collectors.toList());
            matchedOrdersMap.put(bidOrder,matchedOrders);
        }
        return matchedOrdersMap;
    }

    /**
     * Method to retrieve all unmatched orders for the current price.
     *
     * @return list of trade orders .
     */
    public List<TradeOrder> getUnMatchedOrders() {
        List<TradeOrder> pendingAskOrders = orderRepository.findByOrderTypeAndStatus(OrderTypeEnum.ASK.value(),
                OrderStatusEnum.PENDING.value());
        return pendingAskOrders.stream()
                .filter(order -> Double.valueOf(order.getPrice()) > currentPrice
                ).collect(Collectors.toList());
    }

    /**
     * Retrieve all the orders for a particular user.
     * @param userId
     * @return all orders for the user.
     */
    public List<TradeOrder> listOrdersByUserId(String userId) {

        List<TradeOrder> allOrders = orderRepository.findAll();
        return allOrders.stream()
                .filter(order -> order.getUserId().equals(userId))
                       .collect(Collectors.toList());
    }
}
