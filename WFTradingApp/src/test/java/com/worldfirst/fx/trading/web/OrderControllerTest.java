package com.worldfirst.fx.trading.web;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.worldfirst.fx.trading.FxTradeApplication;
import com.worldfirst.fx.trading.SwaggerConfig;
import com.worldfirst.fx.trading.util.CurrencyEnum;
import com.worldfirst.fx.trading.util.OrderStatusEnum;
import com.worldfirst.fx.trading.util.OrderTypeEnum;
import com.worldfirst.fx.trading.model.TradeOrder;
import com.worldfirst.fx.trading.repository.TradeOrderRepository;
import com.worldfirst.fx.trading.service.OrderService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.*;


@RunWith(SpringRunner.class)
@WebMvcTest(value = OrderController.class, secure = false)
@ContextConfiguration(classes={FxTradeApplication.class, SwaggerConfig.class})
public class OrderControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private OrderService orderService;

	@MockBean
	private TradeOrderRepository tradeOrderRepository;



	@Test
	public void testAllOrders() throws Exception {
		TradeOrder order1 = new TradeOrder("Johnny",OrderTypeEnum.BID.value(), CurrencyEnum.GBP_USD.value(),"1.1200","3000",new Date(), OrderStatusEnum.PENDING.value());
		order1.setId(1l);
		TradeOrder order2 = new TradeOrder("Johnny",OrderTypeEnum.BID.value(), CurrencyEnum.GBP_USD.value(),"1.1200","3000",new Date(), OrderStatusEnum.PENDING.value());
		order2.setId(2l);
		List<TradeOrder> orders = new ArrayList<>();
		orders.add(order1);
		orders.add(order2);
		when(orderService.listAllOrders()).thenReturn(orders);
		mockMvc.perform(get("/orders"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(jsonPath("$", hasSize(2)));
	}

	@Test
	public void testGetOrdersForUserId() throws Exception {
		TradeOrder order1 = new TradeOrder("Johnny",OrderTypeEnum.BID.value(), CurrencyEnum.GBP_USD.value(),"1.1200","3000",new Date(), OrderStatusEnum.PENDING.value());
		order1.setId(1l);
		TradeOrder order2 = new TradeOrder("Johnny",OrderTypeEnum.BID.value(), CurrencyEnum.GBP_USD.value(),"1.1200","3000",new Date(), OrderStatusEnum.PENDING.value());
		order2.setId(2l);
		List<TradeOrder> orders = new ArrayList<>();
		orders.add(order1);
		orders.add(order2);
		when(orderService.listOrdersByUserId(Mockito.anyString())).thenReturn(orders);
		mockMvc.perform(get("/orders/{userId}","Johnny"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(jsonPath("$", hasSize(2)));
	}

	@Test
	public void testRegisterOrderSuccess() throws Exception {
		TradeOrder tradeOrder = new TradeOrder("Johnny",OrderTypeEnum.BID.value(), CurrencyEnum.GBP_USD.value(),"1.1200","3000",new Date(), OrderStatusEnum.PENDING.value());
		tradeOrder.setId(1l);

		ObjectMapper mapper = new ObjectMapper();
		String jsonString = mapper.writeValueAsString(tradeOrder);

		when(orderService.createOrder(Mockito.any(TradeOrder.class))).thenReturn(tradeOrder);
		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders.post("/orders/order").content(jsonString)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is(201)).andReturn();
		Assert.assertEquals(201, result.getResponse().getStatus());
	}

	@Test
	public void testRegisterOrderFailure() throws Exception {
		TradeOrder tradeOrder = new TradeOrder("Johnny",OrderTypeEnum.BID.value(), CurrencyEnum.EUR_USD.value(),"1.1200","3000",new Date(), OrderStatusEnum.PENDING.value());
		tradeOrder.setId(1l);

		ObjectMapper mapper = new ObjectMapper();
		String jsonString = mapper.writeValueAsString(tradeOrder);

		when(orderService.createOrder(Mockito.any(TradeOrder.class))).thenReturn(tradeOrder);
		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders.post("/orders").content(jsonString)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is(404)).andReturn();
	}

	@Test
	public void testCancelOrder() throws Exception {
		TradeOrder tradeOrder = new TradeOrder("Johnny",OrderTypeEnum.BID.value(), CurrencyEnum.GBP_USD.value(),"1.1200","3000",new Date(), OrderStatusEnum.PENDING.value());
		tradeOrder.setId(1l);
		ObjectMapper mapper = new ObjectMapper();
		String jsonString = mapper.writeValueAsString(tradeOrder);
		Mockito.doNothing().when(orderService).cancelOrder(Mockito.any(TradeOrder.class));
		mockMvc.perform(
				MockMvcRequestBuilders.put("/orders/order").content(jsonString).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is(200));
	}

	@Test
	public void testCancelOrderFailure() throws Exception {
		TradeOrder tradeOrder = new TradeOrder("Johnny",OrderTypeEnum.BID.value(), CurrencyEnum.GBP_USD.value(),"1.1200","3000",new Date(), OrderStatusEnum.EXECUTED.value());
		tradeOrder.setId(1l);
		ObjectMapper mapper = new ObjectMapper();
		String jsonString = mapper.writeValueAsString(tradeOrder);
		Mockito.doNothing().when(orderService).cancelOrder(Mockito.any(TradeOrder.class));
		mockMvc.perform(
				MockMvcRequestBuilders.put("/orders/order").content(jsonString).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is(400));
	}
	@Test
	public void testGetOrderSuccess() throws Exception {
		TradeOrder tradeOrder = new TradeOrder("Johnny",OrderTypeEnum.BID.value(), CurrencyEnum.GBP_USD.value(),"1.1200","3000",new Date(), OrderStatusEnum.PENDING.value());


		when(orderService.listOrderById(1l)).thenReturn(java.util.Optional.ofNullable(tradeOrder));

		mockMvc.perform(get("/orders/order/{id}", 1))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE));
				//.andExpect(jsonPath("$", hasSize(1)));


	}
	@Test
	public void testGetOrderNotFound() throws Exception {

		when(orderService.listOrderById(1l)).thenReturn(java.util.Optional.ofNullable(null));
		mockMvc.perform(get("/orders/order/{id}", 1))
				.andExpect(status().isNotFound());


	}
	@Test
	public void testGetMatchedTrades() throws Exception {
		TradeOrder order1 = new TradeOrder("Johnny",OrderTypeEnum.BID.value(), CurrencyEnum.GBP_USD.value(),"1.1200","3000",new Date(), OrderStatusEnum.PENDING.value());
		order1.setId(1l);
		Map<TradeOrder,List<TradeOrder>> matchedOrdersMap = new HashMap();
		matchedOrdersMap.put(order1,Arrays.asList(order1));
		when(orderService.getMatchedOrders()).thenReturn(matchedOrdersMap);
		mockMvc.perform(get("/orders/matched"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE));

	}

	@Test
	public void testGetUnMatchedTrades() throws Exception {
		TradeOrder order1 = new TradeOrder("Johnny",OrderTypeEnum.BID.value(), CurrencyEnum.GBP_USD.value(),"1.1200","3000",new Date(), OrderStatusEnum.PENDING.value());
		order1.setId(1l);
		List<TradeOrder> orders = new ArrayList<>();
		orders.add(order1);
		when(orderService.getUnMatchedOrders()).thenReturn(orders);
		mockMvc.perform(get("/orders/unMatched"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(jsonPath("$", hasSize(1)));
	}
	@Test
	public void testDeleteOrder() throws Exception {

		Mockito.doNothing().when(orderService).deleteOrderById(Mockito.anyLong());
		mockMvc.perform(
				delete("/orders/order/{id}", 1l))
				.andExpect(status().isOk());

	}

}
