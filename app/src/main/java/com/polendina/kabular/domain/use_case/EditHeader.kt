package com.polendina.kabular.domain.use_case

import com.polendina.kabular.data.database.model.TableHeader
import com.polendina.kabular.domain.repository.EarningsRepository

class EditHeader(private val earningsRepository: EarningsRepository) {
    suspend operator fun invoke(tableHeader: TableHeader) = earningsRepository.insertHeader(tableHeader = tableHeader)
}