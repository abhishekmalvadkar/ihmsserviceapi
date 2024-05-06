package com.amalvadkar.ihms.common.entities;

import com.amalvadkar.ihms.feedback.enums.FeedbackStatusEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name = "feedback")
@Getter
@Setter
public class FeedBackEntity extends AbstractStringIdBaseEntity {

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private FeedbackStatusEnum status;

    @Column(name = "review_comment")
    private String reviewComment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reviewed_by")
    private UserEntity reviewedBy;

    @Column(name = "reviewed_on")
    private Instant reviewedOn;

    @PrePersist
    public void prePersistForFeedBackEntity() {
        this.status = FeedbackStatusEnum.SUBMITTED;
    }

}
