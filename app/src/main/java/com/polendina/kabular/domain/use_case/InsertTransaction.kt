package com.polendina.kabular.domain.use_case

import com.polendina.kabular.domain.model.Transaction
import com.polendina.kabular.domain.repository.EarningsRepository

class InsertTransaction(
    private val earningsRepository: EarningsRepository
) {
    suspend operator fun invoke(transaction: Transaction) = earningsRepository.insertTransaction(transaction = transaction)
}