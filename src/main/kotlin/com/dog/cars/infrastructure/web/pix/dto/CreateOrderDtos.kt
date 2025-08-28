package com.dog.cars.infrastructure.web.pix.dto

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import java.math.BigDecimal

@JsonInclude(JsonInclude.Include.NON_NULL)
data class CreateDynamicQrOrderRequest(
    @JsonProperty("external_reference") val externalReference: String,
    @JsonProperty("title") val title: String?,
    @JsonProperty("description") val description: String?,
    @JsonProperty("notification_url") val notificationUrl: String?,
    @JsonProperty("total_amount") val totalAmount: BigDecimal,
    @JsonProperty("items") val items: List<DynamicQrItem>?,
    @JsonProperty("sponsor") val sponsor: DynamicQrSponsor?,
    @JsonProperty("cash_out") val cashOut: DynamicQrCashOut?
)

data class DynamicQrItem(
    @JsonProperty("sku_number") val skuNumber: String?,
    @JsonProperty("category") val category: String?,
    @JsonProperty("title") val title: String,
    @JsonProperty("description") val description: String?,
    @JsonProperty("unit_price") val unitPrice: BigDecimal,
    @JsonProperty("quantity") val quantity: Int,
    @JsonProperty("unit_measure") val unitMeasure: String,
    @JsonProperty("total_amount") val totalAmount: BigDecimal
)

data class DynamicQrSponsor(
    @JsonProperty("id") val id: Long
)

data class DynamicQrCashOut(
    @JsonProperty("amount") val amount: BigDecimal
)

data class CreateDynamicQrOrderResponse(
    @JsonProperty("qr_data") val qrData: String,
    @JsonProperty("in_store_order_id") val inStoreOrderId: String
)

