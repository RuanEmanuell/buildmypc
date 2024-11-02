package com.example.myapplication

import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.data.DatabaseConnection
import com.example.myapplication.screens.BuildScreen
import com.example.myapplication.screens.ComputerScreen
import com.example.myapplication.screens.PersonScreen
import com.example.myapplication.screens.SearchScreen


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val databaseConnection = DatabaseConnection(this)
        val db = databaseConnection.writableDatabase

        enableEdgeToEdge()
        setContent {
                val navController = rememberNavController()
                SetupNavHost(navController, databaseConnection, db)
            }
        }
    }

@Composable
fun SetupNavHost(navController:NavHostController, databaseConnection: DatabaseConnection, db: SQLiteDatabase){
    NavHost(navController = navController, startDestination = "home"){
        composable("home") {HomeScreen(navController)}
        composable("build") { BuildScreen(navController, databaseConnection, db) }
        composable("search") { SearchScreen(navController, databaseConnection, db) }
        composable("pc") { ComputerScreen(navController, databaseConnection, db) }
        composable("person") { PersonScreen(navController, databaseConnection, db) }
    }
}



