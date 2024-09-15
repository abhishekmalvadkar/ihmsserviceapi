package com.amalvadkar.ihms.app.models.response;

import lombok.Data;

@Data
public class HeaderResModel {
    private String field;
    private String displayName;
    private boolean sortable;
    private boolean blankable;
    private String type;
    private boolean confirmNeed;
    private boolean editable;
    private Long headerConfigId;
}
