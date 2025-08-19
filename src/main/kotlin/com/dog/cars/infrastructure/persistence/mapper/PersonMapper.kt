package com.dog.cars.infrastructure.persistence.mapper

import com.dog.cars.domain.person.model.Person
import com.dog.cars.infrastructure.persistence.model.PersonModel

fun PersonModel.toDomain(): Person {
    return Person(
        document = document,
        name = name
    )
}

fun Person.toPersonModel(): PersonModel {
    return PersonModel(
        document = document,
        name = name
    )
}