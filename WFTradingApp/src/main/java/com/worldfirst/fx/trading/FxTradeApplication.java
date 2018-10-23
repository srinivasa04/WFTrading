package com.worldfirst.fx.trading;

import com.worldfirst.fx.trading.util.CurrencyEnum;
import com.worldfirst.fx.trading.util.OrderStatusEnum;
import com.worldfirst.fx.trading.util.OrderTypeEnum;
import com.worldfirst.fx.trading.model.TradeOrder;
import com.worldfirst.fx.trading.repository.TradeOrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;

@SpringBootApplication
@Slf4j
public class FxTradeApplication {

	Logger log = LoggerFactory.getLogger(FxTradeApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(FxTradeApplication.class, args);
	}

	/**
	 * Insert some dummy orders for testing.
	 * @param repository
	 * @return
	 */
	@Bean
	public CommandLineRunner insertDummyOrders(TradeOrderRepository repository) {
		return (args) -> {
			// save a couple of customers
			repository.save(new TradeOrder("Sam123", OrderTypeEnum.BID.value(), CurrencyEnum.GBP_USD.value(),"1.1200","8500",new Date(), OrderStatusEnum.PENDING.value()));
			repository.save(new TradeOrder("John123", OrderTypeEnum.ASK.value(), CurrencyEnum.GBP_USD.value(),"1.1200","4000",new Date(), OrderStatusEnum.PENDING.value()));
			repository.save(new TradeOrder("Jack456", OrderTypeEnum.ASK.value(), CurrencyEnum.GBP_USD.value(),"1.100","4000",new Date(), OrderStatusEnum.PENDING.value()));
			repository.save(new TradeOrder("Jill123", OrderTypeEnum.ASK.value(), CurrencyEnum.GBP_USD.value(),"1.1200","300",new Date(), OrderStatusEnum.PENDING.value()));
			repository.save(new TradeOrder("Mary001", OrderTypeEnum.BID.value(), CurrencyEnum.GBP_USD.value(),"1.1100","300",new Date(), OrderStatusEnum.EXECUTED.value()));
			repository.save(new TradeOrder("Fred789", OrderTypeEnum.BID.value(), CurrencyEnum.GBP_USD.value(),"1.1000","300",new Date(), OrderStatusEnum.CANCELLED.value()));


			// fetch all orders
			log.info("TradeOrder found with findAll():");
			log.info("-------------------------------");
			for (TradeOrder order : repository.findAll()) {
				log.info(order.toString());
			}

		};
	}
}
