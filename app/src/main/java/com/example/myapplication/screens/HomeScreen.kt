package com.example.myapplication

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Build
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun HomeScreen(navController: NavHostController) {
    Scaffold(
        topBar = { AppBar("BuildMyPc", false, navController) },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            MenuOption(
                "build",
                navController,
                Color(0xFF0076CE),
                Icons.Rounded.Build,
                "Montar PC",
                "Build PC")
            Spacer(modifier = Modifier.size(30.dp))
            MenuOption(
                "build",
                navController,
                Color(0XFFEDB321),
                Icons.Rounded.Search,
                "Visualizar Montagens",
                "Search Builds"
            )
            Spacer(modifier = Modifier.size(30.dp))
            MenuOption(
                "pc",
                navController,
                Color(0XFFF51800),
                Icons.Rounded.Add,
                "Cadastrar Modelos de PC",
                "Register PC models"
            )
            Spacer(modifier = Modifier.size(30.dp))
            MenuOption(
                "person",
                navController,
                Color(0XFF72CC12),
                Icons.Rounded.Person,
                "Cadastrar Usuários",
                "Register Users"
            )
        }
    }
}

