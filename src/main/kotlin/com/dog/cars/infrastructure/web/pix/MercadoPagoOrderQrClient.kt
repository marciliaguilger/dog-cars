package com.dog.cars.infrastructure.web.pix

import com.dog.cars.infrastructure.web.pix.dto.CreateDynamicQrOrderRequest
import com.dog.cars.infrastructure.web.pix.dto.CreateDynamicQrOrderResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

@FeignClient(name = "mercadoPagoOrderQrClient", url = "\${mercadopago.baseUrl}")
interface MercadoPagoOrderQrClient {
    @PostMapping(
        value = ["/instore/orders/qr/seller/collectors/{user_id}/pos/{external_pos_id}/qrs"],
        consumes = ["application/json"],
        produces = ["application/json"]
    )
    fun createDynamicQrOrder(
        @PathVariable("user_id") userId: Long,
        @PathVariable("external_pos_id") externalPosId: String,
        @RequestBody request: CreateDynamicQrOrderRequest
    ): CreateDynamicQrOrderResponse
}