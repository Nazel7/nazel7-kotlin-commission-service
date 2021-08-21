package com.digitalcolliers.commissionservice.usecases.dtos.domains

import lombok.Builder
import org.springframework.data.mongodb.core.index.Indexed
import java.math.BigDecimal

@Builder
data class Transaction(

    val id: String,

    val transactionId: Number,

    val transactionAmount: BigDecimal,

    val customerFirstName: String?,

    val customerLast: String?,

    val customerId: Number,

    val customerLastName: String?,

    val transactionDate: Long
)
