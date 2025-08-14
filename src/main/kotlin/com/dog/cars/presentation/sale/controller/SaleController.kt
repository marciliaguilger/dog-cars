package com.dog.cars.presentation.sale.controller

import com.dog.cars.domain.usecase.SellCarUseCase
import com.dog.cars.presentation.sale.dto.CarSellInput
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/sales")
class SaleController(
    private val sellCarUseCase: SellCarUseCase
) {
    @PostMapping("/cars/{id}")
    fun sellCar(@RequestParam id: UUID, @RequestBody body: CarSellInput) {
        sellCarUseCase.execute(id, body.buyerDocument, body.saleDate, body.discountAmount)
    }
}