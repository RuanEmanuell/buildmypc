package com.example.myapplication.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.myapplication.AppBar
import com.example.myapplication.R
import com.example.myapplication.components.PcPart


@Composable
fun ComputerScreen(navController: NavHostController) {
    var cpuDropdownExpanded = remember { mutableStateOf(false)}
    var gpuDropdownExpanded = remember { mutableStateOf(false)}
    var selectedCpuIndex = remember { mutableStateOf(0)}
    var selectedGpuIndex = remember { mutableStateOf(0)}

    val cpuList = listOf("Intel Celeron", "Intel Pentium", "Intel Core i3", "Intel Core i5",
        "Intel Core i7", "Intel Core i9", "AMD Athlon", "AMD Ryzen 3", "AMD Ryzen 5", "AMD Ryzen 7",
        "AMD Ryzen 9")

    val gpuList = listOf("Geforce GTX 1650", "Geforce GTX 1660", "Geforce RTX 3050", "Geforce RTX 3060",
        "Geforce RTX 4060", "Geforce RTX 4070", "Geforce RTX 4070 SUPER", "Geforce RTX 4080", "Geforce RTX 4080 SUPER", "Geforce RTX 4090", "Radeon RX 6400",
        "Radeon RX 6500XT", "Radeon RX 6600", "Radeon RX 6650XT",  "Radeon RX 7600", "Radeon RX 7700 XT", "Radeon RX 7800 XT", "Radeon RX 7900XT", "Radeon RTX 7900XTX")

    Scaffold(
        topBar = { AppBar("Registrar novo PC", true, navController) },
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
                PcPart(cpuList, selectedCpuIndex, cpuDropdownExpanded, "Processador:",
                    "Selecione um processador", R.drawable.intel)
                PcPart(gpuList, selectedGpuIndex, gpuDropdownExpanded, "Placa de vídeo:",
                    "Selecione uma placa de vídeo", R.drawable.geforce)
            }
                }
            }

}
