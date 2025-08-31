package com.dog.cars.infrastructure.persistence.repository.person

import com.dog.cars.domain.person.model.Person
import com.dog.cars.application.port.PersonRepository
import com.dog.cars.infrastructure.persistence.mapper.toDomain
import com.dog.cars.infrastructure.persistence.mapper.toPersonModel
import org.springframework.stereotype.Repository

@Repository
class PersonRepositoryImpl (private val personJpaRepository: PersonJpaRepository): PersonRepository {
    override fun getByDocument(document: String): Person? {
        return personJpaRepository.findByDocument(document)?.toDomain()
    }

    override fun upsert(person: Person) {
        personJpaRepository.save(person.toPersonModel())
    }

    override fun getById(id: java.util.UUID): Person? {
        return personJpaRepository.findById(id.toString()).orElse(null)?.toDomain()
    }

    override fun getAll(): Collection<Person> {
        return personJpaRepository.findAll().map { it.toDomain() }
    }
}