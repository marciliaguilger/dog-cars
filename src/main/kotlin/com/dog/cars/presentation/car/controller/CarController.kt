package com.dog.cars.presentation.car.controller

import com.dog.cars.application.usecase.ManageCarUseCase
import com.dog.cars.presentation.car.dto.CarInput
import com.dog.cars.presentation.car.dto.CarOutput
import com.dog.cars.presentation.car.dto.toDomain
import com.dog.cars.presentation.car.dto.toOutput
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/cars")
class CarController(
    private val manageCarUseCase: ManageCarUseCase,
) {

    @PostMapping
    fun create(@RequestBody body: CarInput): CarOutput {
        val car = manageCarUseCase.execute(body.toDomain())
        return car.toOutput()
    }

    @GetMapping
    fun getCars(@RequestParam available: Boolean): Collection<CarOutput> {
        return manageCarUseCase.getCars(available).map { it.toOutput() }
    }
}