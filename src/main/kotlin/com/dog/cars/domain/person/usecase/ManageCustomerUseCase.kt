package com.dog.cars.domain.person.usecase

import com.dog.cars.domain.person.model.Person
import com.dog.cars.domain.repository.PersonRepository

class ManageCustomerUseCase(
    private val customerRepository: PersonRepository
) {
    fun create (customer: Person){
        customerRepository.save(customer)
    }

    fun getByDocument(document: String): Person {
        return customerRepository.getByDocument(document)
            ?: throw IllegalArgumentException("Customer with document $document not found")
    }
}