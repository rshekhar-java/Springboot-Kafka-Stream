package com.rs.kafka.repositories;

import com.rs.kafka.entity.*;
import org.springframework.data.repository.*;
import org.springframework.stereotype.*;
import org.springframework.stereotype.Repository;

/**
 * created by rs 5/11/2022.
 */
@Repository
public interface OrderItemRepository extends CrudRepository<OrderItem, Integer> {

}
