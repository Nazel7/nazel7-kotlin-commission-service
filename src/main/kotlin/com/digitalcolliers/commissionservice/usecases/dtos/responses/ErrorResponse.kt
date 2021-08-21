package com.digitalcolliers.commissionservice.usecases.dtos.responses

data class ErrorResponse(

    var status: String? = null,
    var message: String? = null,
    var timeStamp: Long? = null
)
