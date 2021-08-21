package com.digitalcolliers.commissionservice.events

import com.digitalcolliers.commissionservice.entities.CommisionRequestLog
import org.springframework.context.ApplicationEvent

data class CommissionLogEvent(

    val eventSource: Any,

    val commissionLog: CommisionRequestLog

) : ApplicationEvent(eventSource)
