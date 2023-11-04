package com.raytalktech.printcompose.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.raytalktech.printcompose.model.data.ReceiverOrder
import com.raytalktech.printcompose.model.data.dummyPrintingData
import com.raytalktech.printcompose.ui.theme.PrintComposeTheme

@Composable
fun PrintingItems(receiverOrder: ReceiverOrder, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.fillMaxWidth(), colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Row(
            modifier = modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = String.format(
                    "%s \n%s",
                    receiverOrder.receiverName,
                    receiverOrder.receiverLastUpdate
                )
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun PrintingItemPreview() {
    PrintComposeTheme {
        PrintingItems(receiverOrder = dummyPrintingData[1])
    }
}