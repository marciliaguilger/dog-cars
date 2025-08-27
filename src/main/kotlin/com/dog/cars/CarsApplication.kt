package com.dog.cars

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.boot.context.properties.ConfigurationPropertiesScan

@SpringBootApplication
@EnableFeignClients
@ConfigurationPropertiesScan
class CarsApplication

fun main(args: Array<String>) {
	runApplication<CarsApplication>(*args)
}
