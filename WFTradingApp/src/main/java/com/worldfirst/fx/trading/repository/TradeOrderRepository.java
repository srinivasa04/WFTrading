package com.worldfirst.fx.trading.repository;

import com.worldfirst.fx.trading.model.TradeOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * TradeOrder JPA Repository to perform CRUD operations for order entity.
 */
public interface TradeOrderRepository extends JpaRepository<TradeOrder, Long>
{
  List<TradeOrder> findByOrderTypeAndStatus(String emailAddress, String lastname);

}
