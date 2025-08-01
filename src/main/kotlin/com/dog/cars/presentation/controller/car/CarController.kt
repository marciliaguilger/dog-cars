package com.dog.cars.presentation.controller.car

import com.dog.cars.domain.usecase.UpdateCarUseCase
import com.dog.cars.presentation.dto.CarInput
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController("cars")
class CarController(
    updateCarUseCase: UpdateCarUseCase
) {

    @PostMapping
    fun upsertCar(@RequestBody body: CarInput) {

    }
}