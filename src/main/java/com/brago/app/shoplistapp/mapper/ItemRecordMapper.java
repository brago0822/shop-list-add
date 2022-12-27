package com.brago.app.shoplistapp.mapper;

import com.brago.app.shoplistapp.dto.ItemRecordDto;
import com.brago.app.shoplistapp.model.ItemRecord;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ItemRecordMapper {

    ItemRecordDto entityToDto(ItemRecord itemRecord);
    ItemRecord dtoToEntity(ItemRecordDto itemRecordDto);

}
