package com.amalvadkar.ihms.common.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "policy_document")
@Getter
@Setter
public class PolicyDocumentEntity extends AbstractBaseEntity {

    @Column(name = "title", nullable = false)
    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "policy_category_id")
    private PolicyCategoryEntity policyCategoryEntity;

    @Column(name = "path", nullable = false)
    private String path;

    @Column(name = "view_count", nullable = false)
    private Long viewCount;

    @Column(name = "display_order", nullable = false)
    private BigDecimal displayOrder;

}
