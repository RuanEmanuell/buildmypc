package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.screens.BuildScreen


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
                val navController = rememberNavController()
                SetupNavHost(navController = navController)
            }
        }
    }

@Composable
fun SetupNavHost(navController:NavHostController){
    NavHost(navController = navController, startDestination = "home"){
        composable("home") {HomeScreen(navController)}
        composable("build") { BuildScreen(navController) }
    }
}



