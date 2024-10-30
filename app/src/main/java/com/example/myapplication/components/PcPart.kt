package com.example.myapplication.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PcPart(partList: List<String>, selectedPart: MutableState<Int>, dropdownExpanded: MutableState<Boolean>, headerText: String, dropdownText: String, image: Int) {
    Row(modifier = Modifier.height(100.dp)) {
        Column(modifier = Modifier.width(250.dp)) {
            Text(
                "$headerText ${partList[selectedPart.value]}",
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.size(10.dp))
            OutlinedButton(
                onClick = { dropdownExpanded.value = true }
            ) {
                Text(
                    text = dropdownText,
                    fontSize = 15.sp
                )
            }
            DropdownMenu(
                expanded = dropdownExpanded.value,
                onDismissRequest = { dropdownExpanded.value = false }
            ) {
                partList.forEach { cpu ->
                    DropdownMenuItem(
                        text = { Text(cpu) },
                        onClick = {
                            selectedPart.value = partList.indexOf(cpu); dropdownExpanded.value = false
                        }
                    )
                }
            }
        }
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.End,
        ) {
            Image(
                painter = painterResource(
                    id = image
                ),
                contentDescription = headerText,
                modifier = Modifier.size(100.dp)
            )
        }
    }
    Spacer(modifier = Modifier.height(15.dp))
}