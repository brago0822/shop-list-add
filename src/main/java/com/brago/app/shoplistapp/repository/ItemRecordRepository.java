package com.brago.app.shoplistapp.repository;

import com.brago.app.shoplistapp.model.ItemRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRecordRepository extends JpaRepository<ItemRecord, Long> {
}
