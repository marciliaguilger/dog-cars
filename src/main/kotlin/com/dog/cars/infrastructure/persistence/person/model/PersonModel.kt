package com.dog.cars.infrastructure.persistence.person.model

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "person")
class PersonModel(
    @Id
    val document: String,
    val name: String
)