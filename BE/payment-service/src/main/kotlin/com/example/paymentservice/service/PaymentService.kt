package com.example.paymentservice.service

import com.example.installmentcontractservice.model.InstallmentContract
import com.example.paymentservice.model.Payment
import com.example.paymentservice.repository.PaymentRepository
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono
import java.time.LocalDate
import java.time.ZoneId
import java.util.Date
import java.time.temporal.ChronoUnit

enum class PaymentStatus {
    PENDING,
    PAID,
    OVERDUE
}

@Service
class PaymentService(
    private val paymentRepository: PaymentRepository,
    private val webClientBuilder: WebClient.Builder
) {
    fun getPaymentsByContract(contractId: Int) = paymentRepository.findByContractId(contractId)

    fun createPayment(contractId: Int): Payment? {
        val payment = Payment()
        payment.contractId = contractId
        payment.status = PaymentStatus.PENDING.toString()
        val contract = getContractById(contractId) ?: return null

        payment.paymentDue = getNextPaymentDue(contract)
        payment.amountDue = calculateNextAmountDue(contract)

        return paymentRepository.save(payment)
    }

    fun updatePaymentPenalty(payment: Payment): Payment? {
        val currentDate = LocalDate.now()
        val paymentDueLocalDate = payment.paymentDue?.toInstant()?.atZone(ZoneId.systemDefault())?.toLocalDate()
        val daysBetween = ChronoUnit.DAYS.between(paymentDueLocalDate, currentDate)
        val contract = getContractById(payment.contractId) ?: return null

        if (daysBetween > contract.maximumDayOutstanding) {
            payment.amountPenalty = payment.amountDue * contract.penaltyRate * daysBetween
            payment.status = PaymentStatus.OVERDUE.toString()
        }

        return paymentRepository.save(payment)
    }

    fun updatePaymentPaid(payment: Payment): Payment? {
        payment.status = PaymentStatus.PAID.toString()
        payment.paymentDate = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant())
        val contract = getContractById(payment.contractId) ?: return null
        contract.amountRemaining -= payment.amountDue

        updateContract(contract) ?: return null

        return paymentRepository.save(payment)
    }

    private fun getNextPaymentDue(contract: InstallmentContract): Date? {
        val payments = getPaymentsByContract(contract.id)
        val lastPayment = payments.lastOrNull()
        return if (lastPayment == null) {
            val firstDatePaidLocalDate = contract.firstDatePaid.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
            val nextPaymentDate = firstDatePaidLocalDate.plusMonths(1)
            val paymentDueDate = LocalDate.of(nextPaymentDate.year, nextPaymentDate.month, contract.dayPaidEachCycle.toInt())

            Date.from(paymentDueDate.atStartOfDay(ZoneId.systemDefault()).toInstant())
        } else {
            val lastPaymentDate = lastPayment.paymentDue?.toInstant()?.atZone(ZoneId.systemDefault())?.toLocalDate()
            val nextPaymentDate = lastPaymentDate?.plusMonths(1)

            Date.from(nextPaymentDate?.atStartOfDay(ZoneId.systemDefault())?.toInstant())
        }
    }

    private fun calculateNextAmountDue(contract: InstallmentContract): Double {
        return calculateMonthlyPrincipal(contract) + (contract.amountRemaining * (calculateCurrentInterestRate(contract) / 100 / 12)) / contract.installmentPeriod
    }

    private fun calculateCurrentInterestRate(contract: InstallmentContract): Double {
        return contract.annualInterestRate + calculateInterestRateIncrease(contract)
    }

    private fun calculateMonthlyPrincipal(contract: InstallmentContract): Double {
        return contract.products.sumOf { it.amount } / contract.installmentPeriod
    }

    private fun calculateInterestRateIncrease(contract: InstallmentContract): Double {
        val currentDate = LocalDate.now()
        val firstDatePaidLocalDate = contract.firstDatePaid.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()

        val yearsBetween = ChronoUnit.YEARS.between(firstDatePaidLocalDate, currentDate)

        return yearsBetween * contract.interestRateIncrease
    }

    private fun getContractById(contractId: Int): InstallmentContract? = webClientBuilder.build().get()
        .uri("http://installment-contract-service/api/installment-contract/$contractId")
        .retrieve()
        .bodyToMono(InstallmentContract::class.java)
        .block()

    private fun updateContract(contract: InstallmentContract): InstallmentContract? = webClientBuilder.build().post()
        .uri("http://installment-contract-service/api/installment-contract/update")
        .bodyValue(contract)
        .retrieve()
        .bodyToMono(InstallmentContract::class.java)
        .block()
}