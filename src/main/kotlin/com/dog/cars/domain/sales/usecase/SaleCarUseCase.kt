package com.dog.cars.domain.sales.usecase

import com.dog.cars.domain.sales.enum.SaleStatus
import com.dog.cars.domain.person.model.Buyer
import com.dog.cars.domain.car.model.Car
import com.dog.cars.domain.person.model.Person
import com.dog.cars.domain.sales.model.Sale
import com.dog.cars.domain.repository.CarRepository
import com.dog.cars.domain.repository.PersonRepository
import com.dog.cars.domain.repository.SaleRepository
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.UUID

class SaleCarUseCase(
    private val carRepository: CarRepository,
    private val personRepository: PersonRepository,
    private val saleRepository: SaleRepository,
) {
    fun sale(carId: UUID, buyerDocument: String, saleDate: LocalDateTime, discountAmount: BigDecimal) {
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

        val soldCar = car.sell(sale.id)

        carRepository.upsert(soldCar)
        saleRepository.save(sale)

    }



    fun getAll(): Collection<Sale> {
        return saleRepository.findAll()
    }

    fun getByCustomerDocument(customerDocument: String): Collection<Sale> {
        if (customerDocument.isBlank()) {
            throw IllegalArgumentException("Buyer document cannot be blank")
        }
        return saleRepository.findByCustomerDocument(customerDocument)
    }

    private fun createSale(car: Car, buyer: Person, saleDate: LocalDateTime, discount: BigDecimal): Sale {

        validateDiscount(discount, car.price)

        val salePrice = car.price - discount

        return Sale(
            id = UUID.randomUUID(),
            carId = car.id,
            customerDocument = buyer.document,
            saleDate = saleDate,
            salePrice = salePrice,
            discount = discount,
            status = SaleStatus.CREATED
        )
    }

    private fun validateDiscount(discount: BigDecimal, carPrice: BigDecimal) {
        if (discount < BigDecimal.ZERO || discount > (carPrice * BigDecimal("0.2"))) {
            throw IllegalArgumentException("Invalid discount amount: $discount for car price: $carPrice")
        }
    }
}