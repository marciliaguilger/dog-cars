package com.dog.cars.domain.usecase

import com.dog.cars.domain.model.Buyer
import com.dog.cars.domain.model.Car
import com.dog.cars.domain.model.Person
import com.dog.cars.domain.model.Sale
import com.dog.cars.domain.repository.CarRepository
import com.dog.cars.domain.repository.PersonRepository
import com.dog.cars.domain.repository.SaleRepository
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.UUID

class SellCarUseCase(
    private val carRepository: CarRepository,
    private val personRepository: PersonRepository,
    private val saleRepository: SaleRepository
) {
    fun execute(carId: UUID, buyerDocument: String, saleDate: LocalDateTime, discountAmount: BigDecimal) {
        val car = carRepository.getById(carId)
            ?: throw IllegalArgumentException("Car with id $carId not found")

        if (!car.available) {
            throw IllegalStateException("Car with id $carId is already sold")
        }

        val person = personRepository.getByDocument(buyerDocument)
            ?: throw IllegalArgumentException("Buyer with document $buyerDocument not found")

        val buyer = Buyer(
            document = person.document,
            name = person.name,
        )

        val sale = createSale(car, buyer, saleDate, discountAmount)

        car.sell(sale.id)

        carRepository.upsert(car)
        saleRepository.save(sale)

    }

    private fun createSale(car: Car, buyer: Person, saleDate: LocalDateTime, discount: BigDecimal): Sale {

        validateDiscount(discount, car.price)

        val salePrice = car.price - discount

        return Sale(
            id = UUID.randomUUID(),
            carId = car.id,
            customerDocument = buyer.document,
            saleDate = saleDate,
            salePrice = salePrice
        )
    }

    private fun validateDiscount(discount: BigDecimal, carPrice: BigDecimal) {
        if (discount < BigDecimal.ZERO || discount > (carPrice * BigDecimal("0.2"))) {
            throw IllegalArgumentException("Invalid discount amount: $discount for car price: $carPrice")
        }
    }
}