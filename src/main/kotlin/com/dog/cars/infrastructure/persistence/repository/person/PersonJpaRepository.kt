package com.dog.cars.infrastructure.persistence.repository.person

import com.dog.cars.infrastructure.persistence.model.PersonModel
import org.springframework.data.jpa.repository.JpaRepository

interface PersonJpaRepository: JpaRepository<PersonModel, String> {
    fun findByDocument(document: String): PersonModel?

}