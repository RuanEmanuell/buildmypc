package com.example.myapplication.screens

import android.database.sqlite.SQLiteDatabase
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.example.myapplication.AppBar
import com.example.myapplication.data.DatabaseConnection
import com.example.myapplication.model.Computador
import com.example.myapplication.model.MontagemView

@Composable
fun SearchScreen(navController: NavHostController, databaseConnection: DatabaseConnection, db: SQLiteDatabase) {
    val context = LocalContext.current

    var buildList by remember { mutableStateOf(listOf<MontagemView>()) }

    fun getAllBuilds(): List<MontagemView> {
        val bdList = databaseConnection.findAllBuilds(db)

        if (bdList.isEmpty()) {
            navController.navigate("home")
            Toast.makeText(
                context,
                "Adicione pelo menos uma montagem pra poder visualizar a lista!",
                Toast.LENGTH_SHORT
            ).show()
        }
        return bdList
    }

    LaunchedEffect(Unit) {
        buildList = getAllBuilds()
    }

    Scaffold(
        topBar = { AppBar("Visualizar montagens", true, navController) },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Column (modifier = Modifier.fillMaxSize()) {
                buildList.forEach {
                    build -> Column(){
                        Text(build.clienteCpf)
                        Text(build.modeloId.toString())
                        Text(build.modeloCpu)
                    }
                }
            }
        }
    }
}

