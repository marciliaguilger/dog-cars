package com.dog.cars.presentation.customer.controller

import com.dog.cars.domain.person.usecase.ManageCustomerUseCase
import com.dog.cars.presentation.customer.dto.CustomerInput
import com.dog.cars.presentation.customer.dto.CustomerOutput
import com.dog.cars.presentation.customer.dto.toCustomerOutput
import com.dog.cars.presentation.customer.dto.toPerson
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/customers")
class CustomerController(
    private val manageCustomerUseCase: ManageCustomerUseCase
) {

    @PostMapping
    fun createCustomer(@RequestBody customer: CustomerInput){
        manageCustomerUseCase.create(customer.toPerson())
    }

    @GetMapping("/{document}")
    fun getCustomer(@PathVariable document: String): CustomerOutput {
        return manageCustomerUseCase.getByDocument(document).toCustomerOutput()
    }
}