package com.digitalcolliers.commissionservice.services

import com.digitalcolliers.commissionservice.entities.CommisionRequestLog
import com.digitalcolliers.commissionservice.entities.FeeTransactionPercentage
import com.digitalcolliers.commissionservice.entities.TransactionModel
import com.digitalcolliers.commissionservice.events.CommissionLogEvent
import com.digitalcolliers.commissionservice.repositories.FeeTransactionRepo
import com.digitalcolliers.commissionservice.repositories.TransactionRepo
import com.digitalcolliers.commissionservice.usecases.dtos.responses.CustomerDto
import com.mongodb.client.DistinctIterable
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationEventPublisher
import org.springframework.context.event.EventListener
import org.springframework.data.crossstore.ChangeSetPersister
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.Update
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import org.springframework.web.client.HttpClientErrorException
import java.math.BigDecimal
import java.math.MathContext
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashSet


@Service
class TransactionService @Autowired constructor(

    val repository: TransactionRepo,
    val feeRepository: FeeTransactionRepo,
    val applicationEvent: ApplicationEventPublisher,
    var customerInfoResolver: HashSet<CustomerDto>

) {
    @Async
    @Throws(Exception::class, HttpClientErrorException.NotFound::class)
    fun getTotalCommissionByCustomerId(customerId: Long): CustomerDto? {

        val log: Logger = LoggerFactory.getLogger(TransactionService::class.java)

        val transactionDetails = repository.findTransactionModelByCustomerIdOrderByLastTransactionDateDesc(customerId)
        var totalTransaction: BigDecimal? = BigDecimal(0)
        var commissionValue: BigDecimal? = BigDecimal(0)
        var customerDataProvider: CustomerDto? = null
        val commissionDataAggregator: HashSet<CustomerDto>? = null
        var transactionDetail: TransactionModel? = null
        val mathContext: MathContext = MathContext(6)

        for (transaction: TransactionModel in transactionDetails) {

            totalTransaction = totalTransaction?.add(transaction.transactionAmount, mathContext)
            transactionDetail = transaction

        }
        if (totalTransaction == null) {

            throw Exception("No transaction found for this customer with Id= $customerId")
        }
        val feeDetails: List<FeeTransactionPercentage> = feeRepository.findAll();

        for (feeAmount: FeeTransactionPercentage in feeDetails) {

            if (totalTransaction < feeAmount.transactionAmount) {

                commissionValue =
                    totalTransaction.multiply(BigDecimal((feeAmount.feeTransactionPercentage) / 100), mathContext)

                log.info(
                    "::: commissionValue is= [{}] for customer with id= [{}]",
                    commissionValue,
                    transactionDetail?.customerId
                )

            }
        }
        if (transactionDetail != null) {
            customerDataProvider = CustomerDto(
                id = transactionDetail.customerId,
                firstName = transactionDetail.customerFirstName,
                lastName = transactionDetail.customerLastName,
                transactionCount = transactionDetails.size,
                totalTransaction = totalTransaction,
                calculatedCommission = commissionValue,
                lastTransactionDate = transactionDetails.get(0).lastTransactionDate
            )
        }
        val date: Date = Date(System.currentTimeMillis())
        val commissionLogEvent = (customerDataProvider?.id)?.let {
            CommisionRequestLog(
                customerId = it,
                commissionAmount = customerDataProvider.calculatedCommission, commissionCreationDate = date
            )
        }
        val commissionlogEvent = commissionLogEvent?.let { CommissionLogEvent(this, it) }

        log.info("::: commissionRequestEvent with cumstomerId: [{}] log to DB :::", commissionLogEvent?.customerId)

        if (commissionlogEvent != null) {
            applicationEvent.publishEvent(commissionlogEvent)
        }

        if (customerDataProvider != null) {
            commissionDataAggregator?.add(customerDataProvider)
        }
        log.info("::: CommissionRequest: [{}] send to client :::", commissionDataAggregator)

        return customerDataProvider
    }

    @Async
    @Throws(Exception::class, ChangeSetPersister.NotFoundException::class)
    fun handleCommissionRequest(customerIds: String): Set<CustomerDto?>? {
        var customerDto: CustomerDto? = null
        var transactionDetail: TransactionModel? = null
        var customerData: HashSet<Long?>? = null


        try {
            val stringDivider: String
            var charToLong: Long = 0
            val queryString = "All".lowercase()

            if (customerIds.length == 1) {

                val customerid = customerIds.toLong()

                customerDto = getTotalCommissionByCustomerId(customerid)
                return setOf(customerDto)

            }

            // Retrieve all customer for commission processing :::
            if (customerIds.contains(queryString) || customerIds.equals(null)) {

                val transactionInfo: Page<TransactionModel> = repository.findAll(Pageable.ofSize(100))

                for (customerDetail: TransactionModel in transactionInfo) {

                    customerData?.add(customerDto?.id)

                    if (customerData != null) {
                        for (id: Long? in customerData.iterator()) {

                            customerDto = id?.let { getTotalCommissionByCustomerId(it) }
                            if (customerDto != null) {
                                customerInfoResolver.add(customerDto)
                            }

                        }

                    }
                }

                return customerInfoResolver?.toSet()
            }

            // Send to all userId found in the COMMA seperated string

            stringDivider = customerIds
            val processedQueryData = customerIds.replace(",", "")
            for (i: Char in processedQueryData) {

                charToLong = i.toString().toLong()

                customerDto = getTotalCommissionByCustomerId(charToLong)

                if (customerDto != null) {
                    customerInfoResolver.add(customerDto)
                }

            }

            return customerInfoResolver


        } catch (e: Exception) {
            e.stackTrace
        }
        throw Exception("Unable to process the customer commission or customer not found :::")
    }

}
