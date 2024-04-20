package com.amalvadkar.ihms.policy.helpers;

import com.amalvadkar.ihms.common.repositories.PolicyDocumentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Slf4j
public class PolicyDocumentViewCountHelper {

    private final PolicyDocumentRepository policyDocumentRepo;

    @Async
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void increaseViewCountInAsync(Long policyDocumentId){
        int updatedRowCount = policyDocumentRepo.increaseViewCount(policyDocumentId);
        log.debug("Updated row count for increase view count :: {}", updatedRowCount);
    }

}
