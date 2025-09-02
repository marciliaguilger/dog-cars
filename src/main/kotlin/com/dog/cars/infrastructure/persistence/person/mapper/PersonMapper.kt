package com.dog.cars.infrastructure.persistence.person.mapper

import com.dog.cars.domain.person.Person
import com.dog.cars.infrastructure.persistence.person.model.PersonModel

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