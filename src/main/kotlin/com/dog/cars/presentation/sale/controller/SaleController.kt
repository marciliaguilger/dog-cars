package com.dog.cars.presentation.sale.controller

import com.dog.cars.domain.sales.usecase.SaleCarUseCase
import com.dog.cars.domain.payments.usecase.PaymentUseCase
import com.dog.cars.presentation.sale.dto.*
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/sales")
class SaleController(
    private val saleCarUseCase: SaleCarUseCase,
    private val paymentUseCase: PaymentUseCase
) {
    @PostMapping("/cars/{id}")
    fun createOrder(@RequestParam id: UUID, @RequestBody body: CarSaleInput) {
        saleCarUseCase.sale(id, body.buyerDocument, body.saleDate, body.discountAmount)
    }

    @PutMapping("{saleId}")
    fun payOrder(@PathVariable saleId: UUID, @RequestBody body: CarSalePaymentInput): CarSalePaymentOutput {
        return paymentUseCase.pay(saleId, body.paymentType).toCarSalePaymentOutput()
    }

    @GetMapping()
    fun getAll(): Collection<CarSaleOutput>{
        return saleCarUseCase.getAll().map { it.toSaleOutput() }
    }

    @GetMapping("/{buyerDocument}")
    fun getByBuyerDocument(@RequestParam buyerDocument: String): Collection<CarSaleOutput>{
        if (buyerDocument.isBlank()) {
            throw IllegalArgumentException("Buyer document cannot be blank")
        }
        return saleCarUseCase.getByCustomerDocument(buyerDocument).map { it.toSaleOutput() }
    }
}