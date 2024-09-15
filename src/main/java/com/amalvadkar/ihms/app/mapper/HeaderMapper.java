package com.amalvadkar.ihms.app.mapper;

import com.amalvadkar.ihms.app.models.response.HeaderResModel;
import com.amalvadkar.ihms.common.entities.HeaderConfigEntity;
import com.amalvadkar.ihms.common.entities.HeaderMappingEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class HeaderMapper {

    public List<HeaderResModel> toHeaderResModelList(List<HeaderMappingEntity> headerMappingEntities){
        return headerMappingEntities.stream()
                .map(HeaderMapper::toHeaderResModel)
                .toList();
    }

    private static HeaderResModel toHeaderResModel(HeaderMappingEntity headerMappingEntity) {
        HeaderResModel headerResModel = new HeaderResModel();

        HeaderConfigEntity headerConfigEntity = headerMappingEntity.getHeaderConfigEntity();
        headerResModel.setHeaderConfigId(headerConfigEntity.getId());
        headerResModel.setBlankable(headerMappingEntity.getHeaderConfigEntity().getBlankable());
        headerResModel.setField(headerConfigEntity.getMappingName());
        headerResModel.setType(headerConfigEntity.getHeaderType());
        headerResModel.setEditable(headerMappingEntity.getEditable());
        headerResModel.setSortable(headerConfigEntity.getSortable());
        headerResModel.setConfirmNeed(headerConfigEntity.getConfirmNeed());
        headerResModel.setDisplayName(headerConfigEntity.getHeaderName());

        return headerResModel;
    }

}
