package com.example.myapplication.screens

import android.database.sqlite.SQLiteDatabase
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.myapplication.AppBar
import com.example.myapplication.data.DatabaseConnection

@Composable
fun BuildScreen(navController: NavHostController, databaseConnection: DatabaseConnection, db: SQLiteDatabase) {
    var expanded by remember { mutableStateOf(false)}
    var model by remember { mutableStateOf(0)}

    Scaffold(
        topBar = { AppBar("Montar PC", true, navController) },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Column (
                modifier = Modifier.padding(20.dp).fillMaxSize()
            ) {
                Text(
                    "Modelo:",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.size(10.dp))
                OutlinedButton(
                    onClick = {expanded = true},
                ) {
                    Text(
                        text = "Selecione um modelo",
                        fontSize = 16.sp
                    )
                }
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = {expanded = false}
                ) {
                    DropdownMenuItem(
                        text = { Text("Modelo 1") },
                        onClick = {model = 0; expanded = false}
                    )
                    DropdownMenuItem(
                        text = { Text("Modelo 2") },
                        onClick = {model = 1; expanded = false}
                    )
                }
            }

        }
    }
}