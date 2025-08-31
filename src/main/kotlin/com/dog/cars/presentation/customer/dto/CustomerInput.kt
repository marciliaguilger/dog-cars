package com.dog.cars.presentation.customer.dto

import com.dog.cars.domain.person.Person

data class CustomerInput (
    val name: String,
    val document: String,
)

fun CustomerInput.toPerson(): Person {
    return Person(
        name = this.name,
        document = this.document
    )
}