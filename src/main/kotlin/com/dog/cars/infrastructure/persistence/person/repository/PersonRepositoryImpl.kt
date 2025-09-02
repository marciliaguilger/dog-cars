package com.dog.cars.infrastructure.persistence.person.repository

import com.dog.cars.domain.person.Person
import com.dog.cars.application.port.PersonRepository
import com.dog.cars.infrastructure.persistence.person.mapper.toDomain
import com.dog.cars.infrastructure.persistence.person.mapper.toPersonModel
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
class PersonRepositoryImpl (private val personJpaRepository: PersonJpaRepository): PersonRepository {
    override fun getByDocument(document: String): Person? {
        return personJpaRepository.findByDocument(document)?.toDomain()
    }

    override fun upsert(person: Person) {
        personJpaRepository.save(person.toPersonModel())
    }

    override fun getById(id: UUID): Person? {
        return personJpaRepository.findById(id.toString()).orElse(null)?.toDomain()
    }

    override fun getAll(): Collection<Person> {
        return personJpaRepository.findAll().map { it.toDomain() }
    }
}