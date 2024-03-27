package com.polendina.kabular.presentation.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable

@Composable
fun AppBottomNavigationBar() {
    BottomAppBar {
        NavigationBarItem(selected = true, onClick = { /*TODO*/ }, icon = {
            Icon(imageVector = Icons.Default.Home, contentDescription = null)
        }
        )
    }
}