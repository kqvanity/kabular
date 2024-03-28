package com.polendina.kabular.domain.use_case

import com.polendina.kabular.domain.model.Month
import com.polendina.kabular.domain.repository.EarningsRepository

class GetTransactions (
    private val repository: EarningsRepository
) {
    suspend operator fun invoke(month: Month) = repository.getTransactions(month = month)
}