package com.dog.cars.application.usecase

import com.dog.cars.domain.person.model.Person
import com.dog.cars.application.port.PersonRepository

class ManageCustomerUseCase(
    private val personRepository: PersonRepository
) {

    fun execute(person: Person): Person {
        personRepository.upsert(person)
        return person
    }

    fun getAll(): Collection<Person> {
        return personRepository.getAll()
    }

    fun getByDocument(document: String): Person? {
        return personRepository.getByDocument(document)
    }
}
