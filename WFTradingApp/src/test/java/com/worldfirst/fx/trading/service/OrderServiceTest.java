package com.worldfirst.fx.trading.service;

import com.worldfirst.fx.trading.util.CurrencyEnum;
import com.worldfirst.fx.trading.util.OrderStatusEnum;
import com.worldfirst.fx.trading.util.OrderTypeEnum;
import com.worldfirst.fx.trading.model.TradeOrder;
import com.worldfirst.fx.trading.repository.TradeOrderRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class OrderServiceTest {

    @Mock
    private TradeOrderRepository tradeOrderRepository;


    @InjectMocks
    OrderService orderService;

    @Before
    public void setUp() {
        ReflectionTestUtils.setField(orderService, "currentPrice", 1.2100);
    }


    @Test
    public void testCreateOrder() throws Exception{
        TradeOrder order1 = new TradeOrder("Johnny", OrderTypeEnum.BID.value(), CurrencyEnum.GBP_USD.value(),"1.1200","3000",new Date(), OrderStatusEnum.PENDING.value());
        order1.setId(1l);
        when(tradeOrderRepository.save(Mockito.any(TradeOrder.class))).thenReturn(order1);
        TradeOrder responseOrder = orderService.createOrder(order1);
        assertEquals(Long.valueOf(1l), responseOrder.getId());
    }

    @Test
    public void testListAllOrders() throws Exception{
        TradeOrder order1 = new TradeOrder("Johnny", OrderTypeEnum.BID.value(), CurrencyEnum.GBP_USD.value(),"1.1200","3000",new Date(), OrderStatusEnum.PENDING.value());
        order1.setId(1l);
        List<TradeOrder> orders = new ArrayList<>();
        orders.add(order1);
        when(tradeOrderRepository.findAll()).thenReturn(orders);
        assertEquals(1, orderService.listAllOrders().size());
    }


    @Test
    public void testListOrderById() throws Exception{
        TradeOrder order1 = new TradeOrder("John", OrderTypeEnum.BID.value(), CurrencyEnum.GBP_USD.value(),"1.1200","3000",new Date(), OrderStatusEnum.PENDING.value());
        order1.setId(1l);
        Optional<TradeOrder> orders = Optional.of(order1);
        when(tradeOrderRepository.findById(Mockito.any())).thenReturn(orders);
        Optional<TradeOrder> responseOrder = orderService.listOrderById(1l);
        assertEquals(Long.valueOf(1l), responseOrder.get().getId());
    }
    @Test
    public void testDeleteOrderById() throws Exception{
        TradeOrder order1 = new TradeOrder("Sumanth", OrderTypeEnum.BID.value(), CurrencyEnum.GBP_USD.value(),"1.1200","3000",new Date(), OrderStatusEnum.PENDING.value());
        order1.setId(1l);
        Mockito.doNothing().when(tradeOrderRepository).deleteById(Mockito.any());
        orderService.deleteOrderById(1l);
    }

    @Test
    public void testCancelOrder() throws Exception{
        TradeOrder order1 = new TradeOrder("Roma", OrderTypeEnum.BID.value(), CurrencyEnum.GBP_USD.value(),"1.1200","3000",new Date(), OrderStatusEnum.PENDING.value());
        order1.setId(1l);
        when(tradeOrderRepository.save(Mockito.any(TradeOrder.class))).thenReturn(order1);
        orderService.cancelOrder(order1);
    }

    @Test
    public void testGetMatchedOrders() throws Exception{
        TradeOrder order1 = new TradeOrder("Johnny", OrderTypeEnum.BID.value(), CurrencyEnum.GBP_USD.value(),"1.1200","3000",new Date(), OrderStatusEnum.PENDING.value());
        order1.setId(1l);
        TradeOrder order2 = new TradeOrder("Sam", OrderTypeEnum.ASK.value(), CurrencyEnum.GBP_USD.value(),"1.1200","300",new Date(), OrderStatusEnum.PENDING.value());
        order2.setId(2l);
        TradeOrder order3 = new TradeOrder("Sam", OrderTypeEnum.ASK.value(), CurrencyEnum.GBP_USD.value(),"1.1200","300",new Date(), OrderStatusEnum.PENDING.value());
        order3.setId(3l);
        when(tradeOrderRepository.findByOrderTypeAndStatus(OrderTypeEnum.BID.value(),OrderStatusEnum.PENDING.value())).thenReturn(Arrays.asList(order1));
        when(tradeOrderRepository.findByOrderTypeAndStatus(OrderTypeEnum.ASK.value(),OrderStatusEnum.PENDING.value())).thenReturn(Arrays.asList(order2,order3));
        assertEquals(2, orderService.getMatchedOrders().get(order1).size());
    }

    @Test
    public void testGetUnMatchedOrders() throws Exception{
        TradeOrder order1 = new TradeOrder("Fred", OrderTypeEnum.ASK.value(), CurrencyEnum.GBP_USD.value(),"1.200","3000",new Date(), OrderStatusEnum.PENDING.value());
        order1.setId(1l);
        TradeOrder order2 = new TradeOrder("Fred", OrderTypeEnum.ASK.value(), CurrencyEnum.GBP_USD.value(),"1.2111","3000",new Date(), OrderStatusEnum.PENDING.value());
        order1.setId(2l);
        when(tradeOrderRepository.findByOrderTypeAndStatus(OrderTypeEnum.ASK.value(),OrderStatusEnum.PENDING.value())).thenReturn(Arrays.asList(order1,order2));
        assertEquals(1, orderService.getUnMatchedOrders().size());
    }

    @Test
    public void testListOrdersByUserId() throws Exception{
        TradeOrder order1 = new TradeOrder("Fred", OrderTypeEnum.ASK.value(), CurrencyEnum.GBP_USD.value(),"1.1200","3000",new Date(), OrderStatusEnum.PENDING.value());
        order1.setId(1l);
        List<TradeOrder> orders = new ArrayList<>();
        orders.add(order1);
        when(tradeOrderRepository.findAll()).thenReturn(orders);
        assertEquals(1, orderService.listOrdersByUserId("Fred").size());
    }
}
