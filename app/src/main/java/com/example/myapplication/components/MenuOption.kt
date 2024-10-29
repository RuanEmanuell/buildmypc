package com.example.myapplication

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController


@Composable
fun MenuOption(screen: String, navController: NavHostController, color: Color, icon : ImageVector, text : String, iconDescription: String) {
    Surface(
        onClick = {navController.navigate(screen)},
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
            Text(text, color = Color(0xFFFFFFFF), fontWeight = FontWeight.Bold, fontSize = 20.sp)
        }
    }
}
