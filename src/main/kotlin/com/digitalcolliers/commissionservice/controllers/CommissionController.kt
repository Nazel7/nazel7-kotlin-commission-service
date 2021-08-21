package com.digitalcolliers.commissionservice.controllers

import com.digitalcolliers.commissionservice.services.TransactionService
import com.digitalcolliers.commissionservice.usecases.dtos.responses.CustomerDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("commission-request")
class CommissionController @Autowired constructor(

    val transactionService: TransactionService
) {

    @GetMapping(" ")
    fun requestForCommission(
        @RequestParam(
            "elasticCustomerId",
            required = false
        ) flexyId: String
    ): ResponseEntity<Set<CustomerDto?>> {

        val customerDto = transactionService.handleCommissionRequest(flexyId)

        return ResponseEntity<Set<CustomerDto?>>(customerDto, HttpStatus.OK)
    }

}
