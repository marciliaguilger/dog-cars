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
    @PostMapping()
    fun createSale(@RequestBody body: CarSaleInput): CarSaleOutput {
        return saleCarUseCase.sale(body.carId, body.buyerDocument, body.saleDate, body.discountAmount).toSaleOutput()
    }

    @PutMapping("{saleId}/payments")
    fun createSalePayment(@PathVariable saleId: UUID, @RequestBody body: CarSalePaymentInput): CarSalePaymentOutput {
        return paymentUseCase.createPayment(saleId, body.paymentType).toCarSalePaymentOutput()
    }

    @DeleteMapping("{saleId}")
    fun cancelSale(@PathVariable saleId: UUID, @RequestBody body: CancelOrderInput) {
        saleCarUseCase.cancel(saleId, body.reason)
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