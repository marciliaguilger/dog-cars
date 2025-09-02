package com.dog.cars.application.port

import com.dog.cars.domain.person.Person
import java.util.*

interface PersonRepository {
    fun upsert(person: Person)
    fun getById(id: UUID): Person?
    fun getByDocument(document: String): Person?
    fun getAll(): Collection<Person>
}
