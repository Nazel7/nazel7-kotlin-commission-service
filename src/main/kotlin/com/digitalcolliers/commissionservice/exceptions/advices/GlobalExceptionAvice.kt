package com.digitalcolliers.commissionservice.exceptions.advices

import com.digitalcolliers.commissionservice.usecases.dtos.responses.ErrorResponse
import com.fasterxml.jackson.core.JsonProcessingException
import lombok.extern.slf4j.Slf4j
import org.apache.tomcat.util.http.fileupload.FileUploadException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.HttpClientErrorException.Forbidden
import java.net.UnknownHostException

@RestControllerAdvice
class GlobalExceptionAvice {

    @ExceptionHandler(UnknownHostException::class)
    fun resolveUnknownHostException(ex: UnknownHostException): ResponseEntity<ErrorResponse>? {
        ex.printStackTrace()
        val errorResponse: ErrorResponse = ErrorResponse(
            status = ex.cause.toString(),
            message = ex.message,
            timeStamp = System.currentTimeMillis()
        )

        return ResponseEntity(errorResponse, HttpStatus.BAD_GATEWAY)
    }

    @ExceptionHandler(HttpClientErrorException::class)
    @Throws(
        JsonProcessingException::class
    )
    fun resolveHttpClientErrorException(
        ex: HttpClientErrorException
    ): Any? {
        ex.printStackTrace()
        val errorResponse: ErrorResponse = ErrorResponse(
            status = ex.cause.toString(),
            message = ex.message,
            timeStamp = System.currentTimeMillis()
        )

        return ResponseEntity(errorResponse, HttpStatus.ALREADY_REPORTED)
    }

    @ExceptionHandler(FileUploadException::class)
    fun resolveFileUploadUnsuccessfulException(
        ex: FileUploadException
    ): ResponseEntity<ErrorResponse> {
        ex.printStackTrace()
        val errorResponse: ErrorResponse = ErrorResponse(
            status = ex.cause.toString(),
            message = ex.message,
            timeStamp = System.currentTimeMillis()
        )
        return ResponseEntity(errorResponse, HttpStatus.NON_AUTHORITATIVE_INFORMATION)
    }
}

@ExceptionHandler(Forbidden::class)
fun resolveAccessForbiddenException(ex: Forbidden): ResponseEntity<ErrorResponse>? {
    val errorResponse: ErrorResponse = ErrorResponse(
        status = ex.statusText,
        message = ex.message,
        timeStamp = System.currentTimeMillis()
    )

    return ResponseEntity(errorResponse, HttpStatus.FORBIDDEN)
}
