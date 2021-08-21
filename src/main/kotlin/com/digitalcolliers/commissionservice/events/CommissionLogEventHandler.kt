package com.digitalcolliers.commissionservice.events

import com.digitalcolliers.commissionservice.entities.CommisionRequestLog
import com.digitalcolliers.commissionservice.processor.FileProcessor
import com.digitalcolliers.commissionservice.repositories.CommissionRequestLogRepo
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component

@Component
class CommissionLogEventHandler @Autowired constructor(

    val commissionLogRepo: CommissionRequestLogRepo
) {

    val log: Logger = LoggerFactory.getLogger(CommissionLogEventHandler::class.java)

    @Async
    @EventListener
    fun activateCommissionLog(commissionLogEvent: CommissionLogEvent) {

        val ressultSet: HashSet<CommisionRequestLog>? = null

        val commissionRequestLog: CommisionRequestLog = commissionLogEvent.commissionLog

        log.info(
            "::: commissionRequestLog of id: [{}] and CustomerId: [{}] is processing :::",
            commissionRequestLog.id,
            commissionRequestLog.customerId
        )

        commissionLogRepo.save(commissionRequestLog)

        log.info(
            "::: commissionRequestLog of id: [{}] and CustomerId: [{}] is saved to DB :::",
            commissionRequestLog.id,
            commissionRequestLog.customerId
        )

    }
}
