package com.example.myapplication.screens

import android.database.sqlite.SQLiteDatabase
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.myapplication.AppBar
import com.example.myapplication.R
import com.example.myapplication.components.PcPart
import com.example.myapplication.data.DatabaseConnection
import com.example.myapplication.model.Computador


@Composable
fun ComputerScreen(navController: NavHostController, databaseConnection: DatabaseConnection, db: SQLiteDatabase) {
    val context = LocalContext.current

    var cpuDropdownExpanded = remember { mutableStateOf(false)}
    var gpuDropdownExpanded = remember { mutableStateOf(false)}
    var ramDropdownExpanded = remember { mutableStateOf(false)}
    var ssdDropdownExpanded = remember { mutableStateOf(false)}

    var selectedCpuIndex = remember { mutableStateOf(0)}
    var selectedGpuIndex = remember { mutableStateOf(0)}
    var selectedRamIndex = remember { mutableStateOf(0)}
    var selectedSsdIndex = remember { mutableStateOf(0)}

    val cpuList = listOf("Intel Celeron", "Intel Pentium", "Intel Core i3", "Intel Core i5",
        "Intel Core i7", "Intel Core i9", "AMD Athlon", "AMD Ryzen 3", "AMD Ryzen 5", "AMD Ryzen 7",
        "AMD Ryzen 9")

    val gpuList = listOf("Geforce GTX 1650", "Geforce GTX 1660", "Geforce RTX 3050", "Geforce RTX 3060",
        "Geforce RTX 4060", "Geforce RTX 4070", "Geforce RTX 4070 SUPER", "Geforce RTX 4080", "Geforce RTX 4080 SUPER", "Geforce RTX 4090", "Radeon RX 6400",
        "Radeon RX 6500XT", "Radeon RX 6600", "Radeon RX 6650XT",  "Radeon RX 7600", "Radeon RX 7700 XT", "Radeon RX 7800 XT", "Radeon RX 7900XT", "Radeon RX 7900XTX")

    val ramList = listOf("4GB", "8GB", "16GB", "24GB", "32GB", "48GB", "64GB")
    val ssdList = listOf("120GB", "240GB", "512GB", "1TB", "2TB")

    fun createPC(){
        val cpu = cpuList[selectedCpuIndex.value]
        val gpu = gpuList[selectedGpuIndex.value]
        val ram = ramList[selectedRamIndex.value].substring(0, ramList[selectedRamIndex.value].length - 2).toFloat()
        val ssd = ssdList[selectedRamIndex.value].substring(0, ramList[selectedSsdIndex.value].length - 2).toFloat()


        val computer = Computador(cpu, gpu, ram, ssd, 3000.0F, "12345678910")

        databaseConnection.insertPC(computer, db)

        Toast.makeText(context, "PC adicionado!", Toast.LENGTH_SHORT).show()
    }

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
                    "Selecione um processador",
                    if(cpuList[selectedCpuIndex.value].substring(0, 5) == "Intel") R.drawable.intel else R.drawable.amd)

                PcPart(gpuList, selectedGpuIndex, gpuDropdownExpanded, "Placa de vídeo:",
                    "Selecione uma placa de vídeo",
                    if(gpuList[selectedGpuIndex.value].substring(0, 7) == "Geforce") R.drawable.geforce else R.drawable.radeon)

                PcPart(ramList, selectedRamIndex, ramDropdownExpanded, "Memória RAM:",
                    "Selecione uma quantidade de RAM",
                    R.drawable.ram)

                PcPart(ssdList, selectedSsdIndex, ssdDropdownExpanded, "SSD:",
                    "Selecione um espaço de SSD",
                    R.drawable.ssd)

                Button(modifier = Modifier.padding(20.dp).fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(Color(0xFF0076CE)),
                        onClick = {createPC()}) {
                    Text("Adicionar PC")
                }
            }
                }
            }

}
