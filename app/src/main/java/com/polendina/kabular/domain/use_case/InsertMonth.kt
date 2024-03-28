package com.polendina.kabular.domain.use_case

import com.polendina.kabular.domain.model.Month
import com.polendina.kabular.domain.repository.EarningsRepository

class InsertMonth(private val earningsRepository: EarningsRepository) {
    suspend operator fun invoke(month: Month) = earningsRepository.insertMonth(month = month)
}