package com.kes.app050_kt_jetpackcompose.ui.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType

@Preview
@Composable
fun MultiplicationTable() {
    val evenColor = Color.White
    val oddColor = Color(0xFFB9F6CA)
    val borderColor = Color.LightGray

    Column {
        for (i in 1 until 10) {
            Row(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
            ) {
                for (j in 1 until 10) {
                    val isEven = (i + j) % 2 == 0
                    val backgroundColor =
                        if (isEven) evenColor
                        else oddColor
                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .weight(1f)
                            .border(width = Dp(1f), color = borderColor)
                            .background(backgroundColor),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "${i * j}",
                            fontSize = TextUnit(20f, TextUnitType.Sp)
                        )
                    }
                }
            }
        }
    }
}