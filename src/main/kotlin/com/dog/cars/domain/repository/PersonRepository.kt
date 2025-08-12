package com.dog.cars.domain.repository

import com.dog.cars.domain.model.Person

interface PersonRepository {
    fun getByDocument(document: String): Person?
    fun save(person: Person)
}