package com.polendina.kabular.domain.use_case

import com.polendina.kabular.domain.repository.EarningsRepository

class GetMonths(
    private val earningsRepository: EarningsRepository
) {
   suspend operator fun invoke() = earningsRepository.getMonths()
}