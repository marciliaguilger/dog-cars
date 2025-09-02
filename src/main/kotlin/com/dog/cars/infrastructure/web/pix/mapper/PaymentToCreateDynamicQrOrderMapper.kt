package com.dog.cars.infrastructure.web.pix.mapper

import com.dog.cars.domain.payments.Payment
import com.dog.cars.infrastructure.config.MercadoPagoProperties
import com.dog.cars.infrastructure.web.pix.dto.CreateDynamicQrOrderRequest
import com.dog.cars.infrastructure.web.pix.dto.DynamicQrCashOut
import com.dog.cars.infrastructure.web.pix.dto.DynamicQrItem
import com.dog.cars.infrastructure.web.pix.dto.DynamicQrSponsor
import org.springframework.stereotype.Component

@Component
class PaymentToCreateDynamicQrOrderMapper {

    fun map(payment: Payment, properties: MercadoPagoProperties): CreateDynamicQrOrderRequest {
        return CreateDynamicQrOrderRequest(
            externalReference = payment.saleId.toString(),
            title = "Venda de Carro",
            description = payment.description,
            notificationUrl = null,
            totalAmount = payment.totalAmount,
            items = payment.items.map { item ->
                DynamicQrItem(
                    skuNumber = item.id.toString(),
                    category = "automotive",
                    title = item.title,
                    description = "Venda de veículo",
                    unitPrice = item.unitPrice,
                    quantity = item.quantity,
                    unitMeasure = "unidade",
                    totalAmount = (item.unitPrice * item.quantity.toBigDecimal())
                )
            },
            sponsor = DynamicQrSponsor(
                id = (properties.collectorId ?: 0L)
            ),
            cashOut = DynamicQrCashOut(amount = java.math.BigDecimal.ZERO)
        )
    }
}


