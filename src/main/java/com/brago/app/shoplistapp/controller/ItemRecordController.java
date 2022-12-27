package com.brago.app.shoplistapp.controller;

import com.brago.app.shoplistapp.dto.ItemRecordDto;
import com.brago.app.shoplistapp.service.interfaces.ItemRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/api/registerItem")
public class ItemRecordController {

    private final ItemRecordService itemRecordService;

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<ItemRecordDto> getAllItemRecords() {
        return itemRecordService.getAllItemRecords();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ItemRecordDto getItemRecord(@PathVariable("id") Long itemRecordId) {
        return itemRecordService.getItemRecord(itemRecordId);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ItemRecordDto createItemRecord(@RequestBody @Validated ItemRecordDto itemRecordDto) {
        return itemRecordService.createItemRecord(itemRecordDto);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ItemRecordDto updateItemRecord(@PathVariable("id") Long itemRecordId, @RequestBody @Validated ItemRecordDto itemRecordDto) {
        return itemRecordService.updateItemRecord(itemRecordId, itemRecordDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteItemRecord(@PathVariable("id") Long itemRecordId) {
        itemRecordService.deleteItemRecord(itemRecordId);
    }

}
