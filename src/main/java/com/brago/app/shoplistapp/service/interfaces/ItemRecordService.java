package com.brago.app.shoplistapp.service.interfaces;

import com.brago.app.shoplistapp.dto.ItemRecordDto;

import java.util.List;

public interface ItemRecordService {

    List<ItemRecordDto> getAllItemRecords();
    ItemRecordDto getItemRecord(Long itemRecordId);
    ItemRecordDto createItemRecord(ItemRecordDto itemRecordDto);
    ItemRecordDto updateItemRecord(Long itemRecordId, ItemRecordDto itemRecordDto);
    void deleteItemRecord(Long itemRecordId);
}
