package com.advpro.gpb.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.advpro.gpb.model.PaymentModel;
import com.advpro.gpb.repository.PaymentRepository;

@RestController
public class PaymentController {
    @Autowired
    private PaymentRepository paymentRepository;

    @GetMapping("/getAllPayments")
    public ResponseEntity<List<PaymentModel>> getAllPayments() {
        try {
            List<PaymentModel> paymentList = new ArrayList<>();
            paymentRepository.findAll().forEach(paymentList::add);

            if (paymentList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(paymentList, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getPaymentById/{id}")
    public ResponseEntity<PaymentModel> getPaymentById(@PathVariable Long id) {
        Optional<PaymentModel> paymentObj = paymentRepository.findById(id);
        if (paymentObj.isPresent()) {
            return new ResponseEntity<>(paymentObj.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/addPayment")
    public ResponseEntity<PaymentModel> ddPayment(@RequestBody PaymentModel payment) {
        try {
            PaymentModel paymentObj = paymentRepository.save(payment);
            return new ResponseEntity<>(paymentObj, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/updatePayment/{id}")
    public ResponseEntity<PaymentModel> updatePayment(@PathVariable Long id, @RequestBody PaymentModel payment) {
        try {
            Optional<PaymentModel> paymentData = paymentRepository.findById(id);
            if (paymentData.isPresent()) {
                PaymentModel updatedPaymentData = paymentData.get();
                updatedPaymentData.setAmount(payment.getAmount());
                updatedPaymentData.setPaymentMethod(payment.getPaymentMethod());

                PaymentModel paymentObj = paymentRepository.save(updatedPaymentData);
                return new ResponseEntity<>(paymentObj, HttpStatus.CREATED);
            }

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/deletePayment/{id}")
    public ResponseEntity<HttpStatus> deletePayment(@PathVariable Long id) {
        try {
            paymentRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
