package com.maveric.restaurantmanagementsystem.repository;

import com.maveric.restaurantmanagementsystem.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
}
