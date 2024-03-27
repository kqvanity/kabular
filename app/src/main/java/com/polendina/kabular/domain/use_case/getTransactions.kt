package com.polendina.kabular.domain.use_case

import com.polendina.kabular.domain.repository.EarningsRepository

class getTransactions (
    private val repository: EarningsRepository
) {
    operator fun invoke() = repository.getTransactions()
}