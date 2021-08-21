package com.digitalcolliers.commissionservice.repositories

import com.digitalcolliers.commissionservice.entities.TransactionModel
import com.mongodb.client.DistinctIterable
import org.springframework.data.mongodb.repository.Aggregation
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface TransactionRepo: MongoRepository<TransactionModel,String>{

    fun findTransactionModelByCustomerIdOrderByLastTransactionDateDesc(customerId: Long): List<TransactionModel>

    fun findDistinctFirstByCustomerId()

}
