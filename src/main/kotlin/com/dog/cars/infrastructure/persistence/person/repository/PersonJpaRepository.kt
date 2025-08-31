package com.dog.cars.infrastructure.persistence.person.repository

import com.dog.cars.infrastructure.persistence.person.model.PersonModel
import org.springframework.data.jpa.repository.JpaRepository

interface PersonJpaRepository: JpaRepository<PersonModel, String> {
    fun findByDocument(document: String): PersonModel?
}