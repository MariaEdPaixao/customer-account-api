package com.customeraccountapi.repositories;

import com.customeraccountapi.domain.OperationType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OperationTypeRepository extends JpaRepository<OperationType, Long> {
}
