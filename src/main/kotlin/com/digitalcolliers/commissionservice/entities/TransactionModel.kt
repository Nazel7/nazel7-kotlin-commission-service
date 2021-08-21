package com.digitalcolliers.commissionservice.entities

import lombok.Builder
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import java.math.BigDecimal
import java.util.*
import javax.print.DocFlavor
@Builder
@Document
data class TransactionModel(

    val id: String? = null,

    val transactionId: Long,

    val transactionAmount: BigDecimal,

    val customerFirstName: String?,

    @Indexed
    val customerId: Long,

    val customerLastName: String?,

    val lastTransactionDate: Date

)

