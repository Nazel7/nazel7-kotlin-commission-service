package com.digitalcolliers.commissionservice.entities

import lombok.Builder
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import java.math.BigDecimal
@Builder
@Document
data class FeeTransactionPercentage(
    @Id
    val id: String?= null,

    @Indexed
    val transactionAmount: BigDecimal,

    val feeTransactionPercentage: Double

)
