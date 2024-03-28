package com.polendina.kabular.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.polendina.kabular.data.repository.EarningsRepositoryImpl
import com.polendina.kabular.domain.use_case.InsertTransaction
import com.polendina.kabular.domain.use_case.DelTransaction
import com.polendina.kabular.domain.use_case.EditHeader
import com.polendina.kabular.domain.use_case.GetHeaders
import com.polendina.kabular.domain.use_case.GetMonths
import com.polendina.kabular.domain.use_case.GetTransactions
import com.polendina.kabular.domain.use_case.InsertMonth
import com.polendina.kabular.domain.use_case.UseCases
import com.polendina.kabular.presentation.earnings.EarningsScreen
import com.polendina.kabular.presentation.earnings.EarningsViewModelImpl
import com.polendina.kabular.ui.theme.KabularTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val earningsRepository = EarningsRepositoryImpl(application)
        setContent {
            KabularTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // TODO: Hilt those dependencies!
                    EarningsScreen(earningsViewModel = EarningsViewModelImpl(useCases = UseCases(
                        getTransactions = GetTransactions(earningsRepository),
                        insertTransaction = InsertTransaction(earningsRepository = earningsRepository),
                        delTransaction = DelTransaction(),
                        getHeaders = GetHeaders(earningsRepository),
                        editHeader = EditHeader(earningsRepository),
                        insertMonth = InsertMonth(earningsRepository),
                        getMonths = GetMonths(earningsRepository)
                    )))
                }
            }
        }
    }
}
