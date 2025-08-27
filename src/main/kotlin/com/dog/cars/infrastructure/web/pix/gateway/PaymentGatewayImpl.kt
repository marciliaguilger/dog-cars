package com.dog.cars.infrastructure.web.pix.gateway

import com.dog.cars.domain.payments.gateway.PaymentGateway
import com.dog.cars.domain.payments.model.Payment
import com.dog.cars.domain.payments.model.PixQrCodePayment
import com.dog.cars.infrastructure.web.pix.MercadoPagoOrderQrClient
import com.dog.cars.infrastructure.config.MercadoPagoProperties
import com.dog.cars.infrastructure.web.pix.dto.*
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.*

@Service
class PaymentGatewayImpl(
    private val mercadoPagoOrderQrClient: MercadoPagoOrderQrClient,
    private val mercadoPagoProperties: MercadoPagoProperties
): PaymentGateway {
    
    override fun pixPaymentGenerateQrCode(payment: Payment): PixQrCodePayment {
        val orderRequest = createRequest(payment)

        val response = mercadoPagoOrderQrClient.createDynamicQrOrder(
            userId = mercadoPagoProperties.collectorId ?: 0L,
            externalPosId = mercadoPagoProperties.posExternalId ?: "DEFAULT_POS",
            request = orderRequest
        )

        return createResponse(payment, response)
    }

    private fun createResponse(payment: Payment, response: CreateDynamicQrOrderResponse):PixQrCodePayment {
        return PixQrCodePayment(
            pixKey = response.qrData,
            createdDate = LocalDateTime.now(),
            status = "PENDING",
            type = "qr",
            totalAmount = payment.totalAmount,
            externalSaleId = payment.saleId
        )
    }

    private fun createRequest(payment: Payment): CreateDynamicQrOrderRequest{
        return CreateDynamicQrOrderRequest(
            externalReference = payment.saleId.toString(),
            title = "Venda de Carro",
            description = payment.description,
            notificationUrl = null, // TODO: Configurar URL de notificação
            totalAmount = payment.totalAmount.toInt(),
            items = payment.items.map { item ->
                DynamicQrItem(
                    skuNumber = item.id.toString(),
                    category = "automotive",
                    title = item.title,
                    description = "Venda de veículo",
                    unitPrice = item.unitPrice.toInt(),
                    quantity = item.quantity,
                    unitMeasure = "unidade",
                    totalAmount = (item.unitPrice * item.quantity.toBigDecimal()).toInt()
                )
            },
            sponsor = DynamicQrSponsor(
                id = mercadoPagoProperties.clientId.toLongOrNull() ?: 0L
            ),
            cashOut = DynamicQrCashOut(amount = 0)
        )
    }
}