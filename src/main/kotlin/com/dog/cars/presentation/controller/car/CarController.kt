package com.dog.cars.presentation.controller.car

import com.dog.cars.domain.usecase.SellCarUseCase
import com.dog.cars.domain.usecase.UpdateCarUseCase
import com.dog.cars.presentation.dto.*
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController("cars")
class CarController(
    private val updateCarUseCase: UpdateCarUseCase,
    private val sellCarUseCase: SellCarUseCase
) {

    @PostMapping
    fun upsertCar(@RequestBody body: CarInput): CarOutput {
        val car = updateCarUseCase.execute(body.toDomain())
        return car.toOutput()
    }

    @PostMapping("{id}/sell")
    fun sellCar(@RequestParam id: UUID, @RequestBody body: CarSellInput) {
        sellCarUseCase.execute(id, body.buyerDocument, body.saleDate, body.discountAmount)
    }
}