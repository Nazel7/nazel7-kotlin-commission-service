package com.digitalcolliers.commissionservice.entities

import lombok.Builder
import lombok.Data
import org.springframework.context.ApplicationEvent
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import java.math.BigDecimal
import java.util.*

@Builder
@Data
@Document
data class CommisionRequestLog(

    @Id
    val id: String? = null,

    @Indexed
    val customerId: Long,

    @Indexed
    val commissionAmount: BigDecimal?,

    @CreatedDate
    val commissionCreationDate: Date? = null
)
