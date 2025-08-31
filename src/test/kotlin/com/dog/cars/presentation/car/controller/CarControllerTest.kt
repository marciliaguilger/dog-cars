package com.dog.cars.presentation.car.controller

import com.dog.cars.application.usecase.ManageCarUseCase
import com.dog.cars.domain.car.Car
import com.dog.cars.presentation.car.dto.CarInput
import com.fasterxml.jackson.databind.ObjectMapper
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import java.math.BigDecimal
import java.util.*

class CarControllerTest {

    private lateinit var manageCarUseCase: ManageCarUseCase
    private lateinit var carController: CarController
    private lateinit var mockMvc: MockMvc
    private lateinit var objectMapper: ObjectMapper

    @BeforeEach
    fun setUp() {
        manageCarUseCase = mockk()
        carController = CarController(manageCarUseCase)
        mockMvc = MockMvcBuilders.standaloneSetup(carController).build()
        objectMapper = ObjectMapper()
    }

    @Test
    fun `should create car successfully`() {
        // Given
        val carInput = CarInput("Toyota", "Corolla", 2022, "White", BigDecimal("50000.00"), true)
        val createdCar = Car(UUID.randomUUID(), "Toyota", "Corolla", 2022, "White", BigDecimal("50000.00"), true, null)
        
        every { manageCarUseCase.execute(any()) } returns createdCar

        // When & Then
        mockMvc.perform(
            post("/cars")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(carInput))
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.brand").value("Toyota"))
            .andExpect(jsonPath("$.model").value("Corolla"))
            .andExpect(jsonPath("$.year").value(2022))
            .andExpect(jsonPath("$.color").value("White"))
            .andExpect(jsonPath("$.price").value(50000.00))
            .andExpect(jsonPath("$.available").value(true))

        verify { manageCarUseCase.execute(any()) }
    }

    @Test
    fun `should get available cars`() {
        // Given
        val car1 = Car(UUID.randomUUID(), "Toyota", "Corolla", 2022, "White", BigDecimal("50000.00"), true, null)
        val car2 = Car(UUID.randomUUID(), "Honda", "Civic", 2021, "Black", BigDecimal("45000.00"), true, null)
        
        every { manageCarUseCase.getCars(true) } returns listOf(car1, car2)

        // When & Then
        mockMvc.perform(
            get("/cars")
                .param("available", "true")
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.length()").value(2))
            .andExpect(jsonPath("$[0].brand").value("Toyota"))
            .andExpect(jsonPath("$[1].brand").value("Honda"))

        verify { manageCarUseCase.getCars(true) }
    }

    @Test
    fun `should get unavailable cars`() {
        // Given
        val soldCar = Car(UUID.randomUUID(), "Toyota", "Corolla", 2022, "White", BigDecimal("50000.00"), false, UUID.randomUUID())
        
        every { manageCarUseCase.getCars(false) } returns listOf(soldCar)

        // When & Then
        mockMvc.perform(
            get("/cars")
                .param("available", "false")
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.length()").value(1))
            .andExpect(jsonPath("$[0].available").value(false))

        verify { manageCarUseCase.getCars(false) }
    }
}
