package com.brago.app.shoplistapp.service;

import com.brago.app.shoplistapp.dto.ItemRecordDto;
import com.brago.app.shoplistapp.exception.ErrorType;
import com.brago.app.shoplistapp.exception.ResourceNotFoundException;
import com.brago.app.shoplistapp.mapper.ItemRecordMapper;
import com.brago.app.shoplistapp.model.ItemRecord;
import com.brago.app.shoplistapp.repository.ItemRecordRepository;
import com.brago.app.shoplistapp.service.interfaces.ItemRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemRecordServiceImpl implements ItemRecordService {

    public static final String ITEM_RECORD = "ITEM RECORD";
    private final ItemRecordRepository itemRecordRepository;
    private final ItemRecordMapper itemRecordMapper;

    @Override
    public List<ItemRecordDto> getAllItemRecords() {
        return itemRecordRepository.findAll()
                .stream()
                .map(itemRecordMapper::entityToDto)
                .toList();
    }

    @Override
    public ItemRecordDto getItemRecord(Long itemRecordId) {
        var itemRecord = itemRecordRepository
                .findById(itemRecordId)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorType.RESOURCE_NOT_FOUND_ERROR, ITEM_RECORD, itemRecordId.toString()));
        return itemRecordMapper.entityToDto(itemRecord);
    }

    @Override
    public ItemRecordDto createItemRecord(ItemRecordDto itemRecordDto) {
        ItemRecord itemRecord = itemRecordMapper.dtoToEntity(itemRecordDto);
        return itemRecordMapper.entityToDto(
                itemRecordRepository.save(itemRecord)
        );
    }

    @Override
    public ItemRecordDto updateItemRecord(Long itemRecordId, ItemRecordDto itemRecordDto) {
        var itemRecordToUpdate = itemRecordRepository
                .findById(itemRecordId)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorType.RESOURCE_NOT_FOUND_ERROR, ITEM_RECORD, itemRecordId.toString()));

        itemRecordToUpdate.setPrice(itemRecordDto.getPrice());
        itemRecordToUpdate.setInitialPrice(itemRecordDto.getInitialPrice());
        itemRecordToUpdate.setQuantity(itemRecordDto.getQuantity());
        itemRecordToUpdate.setDiscountPercentage(itemRecordDto.getDiscountPercentage());
        itemRecordToUpdate.setNotes(itemRecordDto.getNotes());
        itemRecordToUpdate.setInCart(itemRecordToUpdate.isInCart());

        return itemRecordMapper.entityToDto(
                itemRecordRepository.save(itemRecordToUpdate)
        );
    }

    @Override
    public void deleteItemRecord(Long itemRecordId) {
        if (!itemRecordRepository.existsById(itemRecordId)) {
            throw new ResourceNotFoundException(ErrorType.RESOURCE_NOT_FOUND_ERROR, ITEM_RECORD, itemRecordId.toString());
        }
        itemRecordRepository.deleteById(itemRecordId);
    }
}
