package com.dog.cars.infrastructure.persistence.repository.person

import com.dog.cars.domain.model.Person
import com.dog.cars.domain.repository.PersonRepository
import com.dog.cars.infrastructure.persistence.mapper.toDomain
import com.dog.cars.infrastructure.persistence.mapper.toPersonModel

class PersonRepositoryImpl (private val personJpaRepository: PersonJpaRepository): PersonRepository {
    override fun getByDocument(document: String): Person? {
        return personJpaRepository.findByDocument(document)?.toDomain()
    }

    override fun save(person: Person) {
        personJpaRepository.save(person.toPersonModel())
    }
}