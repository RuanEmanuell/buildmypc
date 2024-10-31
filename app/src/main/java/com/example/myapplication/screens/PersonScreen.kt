package com.example.myapplication.screens

import android.database.sqlite.SQLiteDatabase
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.myapplication.AppBar
import com.example.myapplication.data.DatabaseConnection
import com.example.myapplication.model.Cliente


@Composable
fun PersonScreen(navController: NavHostController, databaseConnection: DatabaseConnection, db: SQLiteDatabase) {
    val context = LocalContext.current
    var cpfValue by remember { mutableStateOf("") }
    var nameValue by remember { mutableStateOf("") }
    var emailValue by remember { mutableStateOf("") }
    var cellphoneValue by remember { mutableStateOf("") }

    fun createUser(){
        val cliente = Cliente(cpfValue, nameValue, emailValue, cellphoneValue)

        try {
            databaseConnection.insertUser(cliente, db)
            Toast.makeText(context, "Usuário adicionado!", Toast.LENGTH_SHORT).show()
        } catch (e: Exception){
            Log.e("DatabaseError", "Erro ao inserir usuário: ${e.message}")
        } finally {
            navController.navigate("home")
        }
    }

    Scaffold(
        topBar = { AppBar("Cadastrar novo usuário", true, navController) },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            OutlinedTextField(
                value = cpfValue,
                onValueChange = { if(it.length <= 11 ) cpfValue = it },
                label = { Text("Digite o CPF do cliente") },
                maxLines = 1
            )
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(
                value = nameValue,
                onValueChange = { nameValue = it },
                label = { Text("Digite o nome do cliente") },
                maxLines = 1
            )
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(
                value = emailValue,
                onValueChange = { emailValue = it },
                label = { Text("Digite o email do cliente") },
                maxLines = 1,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            )
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(
                value = cellphoneValue,
                onValueChange = { cellphoneValue = it },
                label = { Text("Digite o telefone do cliente") },
                maxLines = 1,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
            )
            Spacer(modifier = Modifier.height(10.dp))
            Button(modifier = Modifier.padding(20.dp).fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(Color(0xFF0076CE)),
                onClick = {createUser()}) {
                Text("Adicionar usuário")
            }
        }
    }

}
