package com.digitalcolliers.commissionservice

import com.digitalcolliers.commissionservice.processor.FileProcessor
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class CommissionServiceApplication

fun main(args: Array<String>) {
	runApplication<CommissionServiceApplication>(*args)
}

