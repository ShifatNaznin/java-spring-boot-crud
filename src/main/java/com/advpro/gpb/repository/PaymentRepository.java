package com.advpro.gpb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.advpro.gpb.model.PaymentModel;
@Repository
public interface PaymentRepository extends JpaRepository<PaymentModel, Long> {

}
