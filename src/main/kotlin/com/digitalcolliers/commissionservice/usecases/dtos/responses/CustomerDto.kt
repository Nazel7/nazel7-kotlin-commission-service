package com.digitalcolliers.commissionservice.usecases.dtos.responses

import lombok.Builder
import java.math.BigDecimal
import java.util.*

@Builder
data class CustomerDto(

    val id: Long,
    val firstName: String?,
    val lastName: String?,
    val transactionCount: Int,
    val totalTransaction: BigDecimal,
    val calculatedCommission: BigDecimal?,
    val lastTransactionDate: Date

)
