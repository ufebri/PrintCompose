package com.raytalktech.printcompose.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.raytalktech.printcompose.model.data.ReceiverDetailOrder
import com.raytalktech.printcompose.util.TextFormattingHelper

@Composable
fun OrderItem(receiverDetailOrder: ReceiverDetailOrder, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.fillMaxWidth(), colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Row(
            modifier = modifier
                .padding(8.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = receiverDetailOrder.nameItemOrder,
                    fontWeight = FontWeight.Bold,
                    fontSize = 25.sp
                )

                Row(
                    modifier = modifier,
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Text(
                        text = String.format(
                            "%s @ %s%s",
                            TextFormattingHelper.getCurrency(
                                receiverDetailOrder.priceItemOrder.toDouble(),
                                "IDR"
                            ),
                            receiverDetailOrder.quantityItemOrder,
                            receiverDetailOrder.unitItemOrder
                        )
                    )

                    Text(
                        text = String.format(
                            "%s",
                            TextFormattingHelper.getCurrency(
                                receiverDetailOrder.amountItemOrder.toDouble(),
                                "IDR"
                            )
                        ),
                        modifier = modifier.fillMaxWidth(),
                        fontSize = 20.sp,
                        textAlign = TextAlign.End,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}