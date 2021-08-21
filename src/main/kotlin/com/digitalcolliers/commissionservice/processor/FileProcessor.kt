package com.digitalcolliers.commissionservice.processor

import com.digitalcolliers.commissionservice.entities.FeeTransactionPercentage
import com.digitalcolliers.commissionservice.entities.TransactionModel
import com.digitalcolliers.commissionservice.repositories.FeeTransactionRepo
import com.digitalcolliers.commissionservice.repositories.TransactionRepo
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.context.event.ApplicationContextEvent
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async
import org.springframework.util.ResourceUtils
import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


@Configuration
class FileProcessor @Autowired constructor(

    val transactionRepo: TransactionRepo,

    val feeTransactionRepo: FeeTransactionRepo
) {

    @EventListener
    fun run(application: ApplicationContextEvent) {

        loadTransactions()

        loadFeeWages()


    }

    @Async
    fun loadTransactions() {
        val log: Logger = LoggerFactory.getLogger(FileProcessor::class.java)

        log.info("::: [{}] starting transaction data synchronisation process...")

        try {

            if (transactionRepo.count() > 0L) {
                log.info("::: Transaction file already log to DB :::")
                return
            }

            val file = ResourceUtils.getFile("classpath:transactions.csv")

            val inputStream = FileInputStream(file)
            val reader = BufferedReader(InputStreamReader(inputStream))
            val data: ArrayList<TransactionModel> = ArrayList()

            for (line in reader.lines().skip(1)) {
                val dataInput: List<String> = line.split(",(?=(?:[^\\\"]*\\\"[^\\\"]*\\\")*(?![^\\\"]*\\\"))".toRegex())
                val tranxAmount = dataInput.get(1).replace(",", ".").replace("\"", "")
                val dateString = dataInput.get(5).replace(".", "/")
                val date: Date = SimpleDateFormat("dd/MM/yyyy hh:mm:ss").parse(dateString)

                val transactionProcessed = TransactionModel(
                    transactionId = dataInput.get(0).toLong(),
                    transactionAmount = tranxAmount.toBigDecimal(),
                    customerFirstName = dataInput.get(2),
                    customerId = dataInput.get(3).toLong(),
                    customerLastName = dataInput.get(4),
                    lastTransactionDate = date
                )

                data.add(transactionProcessed)

            }

            transactionRepo.saveAll(data)

            log.info("::: [{}] transactions processed to DB :::" + data.size);


        } catch (e: Exception) {

            e.stackTrace
        }
    }

    @Async
    fun loadFeeWages() {

        val log: Logger = LoggerFactory.getLogger(FileProcessor::class.java)

        log.info("::: [{}] starting feeWages data synchronisation process...")

        try {

            if (feeTransactionRepo.count() > 0L) {
                log.info("::: feeWages file already log to DB :::")
                return
            }

            val file = ResourceUtils.getFile("classpath:fee_wages.csv")

            val inputStream = FileInputStream(file)
            val reader = BufferedReader(InputStreamReader(inputStream))
            val data: ArrayList<FeeTransactionPercentage> = ArrayList()

            for (line in reader.lines().skip(1)) {
                val dataInput: List<String> = line.split(",(?=(?:[^\\\"]*\\\"[^\\\"]*\\\")*(?![^\\\"]*\\\"))".toRegex())
                val amount = dataInput.get(1).replace(",", ".").replace("\"", "")

                val feeTransactionProcessed = FeeTransactionPercentage(
                    transactionAmount = dataInput.get(0).toBigDecimal(),
                    feeTransactionPercentage = amount.toDouble()
                )

                data.add(feeTransactionProcessed)

            }

            feeTransactionRepo.saveAll(data)

            log.info("::: [{}] transactions processed to DB :::" + data.size);


        } catch (e: Exception) {

            e.stackTrace
        }
    }
}
