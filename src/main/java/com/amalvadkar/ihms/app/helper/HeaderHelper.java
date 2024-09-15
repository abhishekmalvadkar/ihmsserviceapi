package com.amalvadkar.ihms.app.helper;

import com.amalvadkar.ihms.app.mapper.HeaderMapper;
import com.amalvadkar.ihms.app.models.response.HeaderResModel;
import com.amalvadkar.ihms.common.repositories.HeaderMappingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class HeaderHelper {

    private final HeaderMappingRepository headerMappingRepo;
    private final HeaderMapper headerMapper;

    public List<HeaderResModel> fetchHeaders(Long roleId, Long menuId) {
        var headerMappingEntities = headerMappingRepo.findHeadersBasedOnRoleMenu(roleId, menuId);
        return headerMapper.toHeaderResModelList(headerMappingEntities);
    }

}
