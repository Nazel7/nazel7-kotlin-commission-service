package com.digitalcolliers.commissionservice.repositories

import com.digitalcolliers.commissionservice.entities.FeeTransactionPercentage
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query
import org.springframework.stereotype.Repository
import java.math.BigDecimal

@Repository
interface FeeTransactionRepo : MongoRepository<FeeTransactionPercentage, String> {

    fun findFeeTransactionPercentageByTransactionAmount(transactionValueLessThan: BigDecimal): FeeTransactionPercentage


}
