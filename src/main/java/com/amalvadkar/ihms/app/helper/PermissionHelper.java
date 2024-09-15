package com.amalvadkar.ihms.app.helper;

import com.amalvadkar.ihms.app.enums.MenuEnum;
import com.amalvadkar.ihms.app.exceptions.handlers.UnauthorizedAccessException;
import com.amalvadkar.ihms.common.entities.MenuEntity;
import com.amalvadkar.ihms.common.entities.RoleMenuEntity;
import com.amalvadkar.ihms.common.exceptions.ResourceNotFoundException;
import com.amalvadkar.ihms.common.repositories.MenuRepository;
import com.amalvadkar.ihms.common.repositories.RoleMenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PermissionHelper {

    private final RoleMenuRepository roleMenuRepo;
    private final MenuRepository menuRepo;

    public RoleMenuEntity checkRolePermissionForMenu(Long roleId, MenuEnum menuEnum) {
        MenuEntity menuEntity = menuRepo.findActiveMenu(menuEnum.getCode())
                .orElseThrow(() -> new ResourceNotFoundException("menu not found"));
        return roleMenuRepo.findByRoleIdAndMenuId(menuEntity.getId(), roleId)
                .orElseThrow(UnauthorizedAccessException::new);
    }

}
