package com.dog.cars.presentation.customer.dto

import com.dog.cars.domain.model.Person

data class CustomerOutput (
    val document: String,
    val name: String
)

fun Person.toCustomerOutput(): CustomerOutput {
    return CustomerOutput(
        document = this.document,
        name = this.name
    )
}