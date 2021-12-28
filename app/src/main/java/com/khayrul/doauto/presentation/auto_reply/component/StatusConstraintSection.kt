package com.khayrul.doauto.presentation.auto_reply.component

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun StatusConstraintSection(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
    ) {
        Text(text = "Reply only if...")
        Spacer(modifier = Modifier.width(16.dp))
        DefaultRadioButton(
            text = "Phone is locked",
            selected = true,
            onClick = {  }
        )
        Spacer(modifier = Modifier.width(16.dp))
        DefaultRadioButton(
            text = "Phone is charging",
            selected = false,
            onClick = {  }
        )
    }
}