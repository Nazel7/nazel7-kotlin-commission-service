package com.digitalcolliers.commissionservice.repositories

import com.digitalcolliers.commissionservice.entities.CommisionRequestLog
import org.springframework.data.mongodb.repository.MongoRepository
import java.util.HashSet

interface CommissionRequestLogRepo : MongoRepository<CommisionRequestLog, String> {

}
