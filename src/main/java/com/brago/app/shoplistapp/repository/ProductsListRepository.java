package com.brago.app.shoplistapp.repository;

import com.brago.app.shoplistapp.model.ProductsList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductsListRepository extends JpaRepository<ProductsList, Long> {
}
