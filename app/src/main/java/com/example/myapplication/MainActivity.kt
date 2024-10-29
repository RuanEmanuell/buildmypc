package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Build
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.ShoppingCart
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.myapplication.ui.theme.MyApplicationTheme
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                Scaffold(
                    topBar = { AppBar() },
                    modifier = Modifier.fillMaxSize()) {
                    innerPadding ->
                    Column (
                        modifier = Modifier.fillMaxSize().padding(innerPadding),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ){
                        MenuOption(Color(0xFF0076CE), Icons.Rounded.Build, "Montar PC", "Build PC")
                        Spacer(modifier = Modifier.size(20.dp))
                        MenuOption(Color(0XFFEDB321), Icons.Rounded.Search, "Visualizar Montagens", "Search Builds")
                        Spacer(modifier = Modifier.size(20.dp))
                        MenuOption(Color(0XFF72CC12), Icons.Rounded.Person, "Cadastrar Usuários", "Register Users")
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar() {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color(0xFF0076CE),
            titleContentColor = Color(0xFFFFFFFF)
        ),
        title = {
            Text("BuildMyPC", fontSize = 30.sp, fontWeight = FontWeight.Bold)
        }
    )
}

@Composable
fun MenuOption(color: Color, icon : ImageVector, text : String, iconDescription: String) {
    Surface(
        color = color,
        modifier = Modifier.height(75.dp).width(300.dp),
        shape = RoundedCornerShape(25),
        tonalElevation = 40.dp
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ){
            Icon(
                icon,
                tint = Color(0xFFFFFFFF),
                contentDescription = iconDescription
            )
            Spacer(modifier = Modifier.size(10.dp))
            Text(text, color = Color(0xFFFFFFFF), fontWeight = FontWeight.Bold)
        }
    }
}
