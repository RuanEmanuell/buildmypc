package com.example.myapplication.screens

import Montagem
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.myapplication.AppBar
import com.example.myapplication.R
import com.example.myapplication.data.DatabaseConnection
import com.example.myapplication.model.Cliente
import com.example.myapplication.model.Computador
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment

@Composable
fun BuildScreen(
    navController: NavHostController,
    databaseConnection: DatabaseConnection,
    db: SQLiteDatabase
) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    var expanded by remember { mutableStateOf(false) }
    var model by remember { mutableIntStateOf(0) }
    var userIndex by remember { mutableIntStateOf(0) }
    var computerList by remember { mutableStateOf(listOf<Computador>()) }
    var userList by remember { mutableStateOf(listOf<Cliente>()) }
    var page by remember { mutableIntStateOf(0) }

    fun getAllPcs(): List<Computador> {
        val pcList = databaseConnection.findAllPcs(db)

        if (pcList.isEmpty()) {
            navController.navigate("home")
            Toast.makeText(
                context,
                "Adicione pelo menos um modelo de computador para fazer montagens!",
                Toast.LENGTH_SHORT
            ).show()
        }
        return pcList
    }

    fun getAllUsers(): List<Cliente> {
        val usList = databaseConnection.findAllUsers(db)

        if (usList.isEmpty()) {
            navController.navigate("home")
            Toast.makeText(
                context,
                "Cadastre pelo menos um usuário para fazer montagens!",
                Toast.LENGTH_SHORT
            ).show()
        }
        return usList
    }

    fun createBuild() {
        val montagem = Montagem(0, model + 1, userList[userIndex].cpf)

        try {
            databaseConnection.insertBuild(montagem, db)
            Toast.makeText(context, "Montagem de pc adicionada!", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Log.e("DatabaseError", "Erro ao inserir montagem: ${e.message}")
        } finally {
            navController.navigate("home")
        }
    }

    LaunchedEffect(Unit) {
        computerList = getAllPcs()
        userList = getAllUsers()
    }

    Scaffold(
        topBar = { AppBar("Montar PC", true, navController) },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(state = scrollState),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Column(
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxSize()
            ) {
                if (computerList.isNotEmpty()) {
                    if (page == 0) {
                        Row {
                            Text(
                                "Modelo do PC: ",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                            Text(
                                "Modelo ${model + 1}",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                        Spacer(modifier = Modifier.size(10.dp))
                        OutlinedButton(
                            onClick = { expanded = true },
                        ) {
                            Text(
                                text = "Selecione um modelo",
                                fontSize = 16.sp
                            )
                        }
                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            computerList.forEach { computer ->
                                DropdownMenuItem(
                                    text = { Text(computer.toString()) },
                                    onClick = { model = computer.id - 1; expanded = false }
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(20.dp))
                        Column(
                            modifier = Modifier.border(
                                2.dp,
                                Color.LightGray,
                                RoundedCornerShape(50.dp)
                            )
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(120.dp)
                                    .padding(20.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Column(modifier = Modifier.width(200.dp)) {
                                    Text(
                                        "Processador: ",
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                    Text(
                                        computerList[model].cpu,
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                }
                                Image(
                                    painter = painterResource(
                                        id = if (computerList[model].cpu.substring(
                                                0,
                                                5
                                            ) == "Intel"
                                        ) R.drawable.intel else R.drawable.amd
                                    ),
                                    contentDescription = "CPU",
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(20.dp))
                        Column(
                            modifier = Modifier.border(
                                2.dp,
                                Color.LightGray,
                                RoundedCornerShape(50.dp)
                            )
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(120.dp)
                                    .padding(20.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Column(modifier = Modifier.width(200.dp)) {
                                    Text(
                                        "Placa de vídeo: ",
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                    Text(
                                        computerList[model].gpu,
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                }
                                Image(
                                    painter = painterResource(
                                        id = if (computerList[model].gpu.substring(
                                                0,
                                                7
                                            ) == "Geforce"
                                        ) R.drawable.geforce else R.drawable.radeon
                                    ),
                                    contentDescription = "GPU",
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(20.dp))
                        Column(
                            modifier = Modifier.border(
                                2.dp,
                                Color.LightGray,
                                RoundedCornerShape(50.dp)
                            )
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(120.dp)
                                    .padding(20.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Column(modifier = Modifier.width(200.dp)) {
                                    Text(
                                        "Memória RAM: ",
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                    Text(
                                        "${computerList[model].ram}GB",
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                }
                                Image(
                                    painter = painterResource(
                                        id = R.drawable.ram
                                    ),
                                    contentDescription = "RAM",
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(20.dp))
                        Column(
                            modifier = Modifier.border(
                                2.dp,
                                Color.LightGray,
                                RoundedCornerShape(50.dp)
                            )
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(120.dp)
                                    .padding(20.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Column(modifier = Modifier.width(200.dp)) {
                                    Text(
                                        "Armazenamento do SSD: ",
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                    Text(
                                        "${computerList[model].ssd}GB",
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                }
                                Image(
                                    painter = painterResource(
                                        id = R.drawable.ssd
                                    ),
                                    contentDescription = "SSD",
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }
                        }
                        Spacer(Modifier.height(20.dp))
                        Text(
                            "Valor: R$${computerList[model].valor}",
                            fontSize = 25.sp,
                            fontWeight = FontWeight.SemiBold,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                    } else {
                        Row {
                            Text(
                                "Usuário da montagem: ",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                            Text(
                                userList[userIndex].nome,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                        Spacer(modifier = Modifier.size(10.dp))
                        OutlinedButton(
                            onClick = { expanded = true },
                        ) {
                            Text(
                                text = "Selecione um usuário",
                                fontSize = 16.sp
                            )
                        }
                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            userList.forEach { user ->
                                DropdownMenuItem(
                                    text = { Text(user.nome) },
                                    onClick = {
                                        userIndex =
                                            userList.indexOfFirst { u -> u.cpf === user.cpf }; expanded =
                                        false
                                    }
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(20.dp))
                        Column(
                            modifier = Modifier
                                .border(
                                    2.dp,
                                    Color.LightGray,
                                    RoundedCornerShape(50.dp)
                                )
                                .fillMaxWidth()
                                .padding(20.dp)
                        ) {
                            Text(
                                "Nome completo: ${userList[userIndex].nome}",
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 15.sp,
                                modifier = Modifier.padding(5.dp)
                            )
                            Text(
                                "CPF: ${userList[userIndex].cpf}",
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 15.sp,
                                modifier = Modifier.padding(5.dp)
                            )
                            Text(
                                "Email: ${userList[userIndex].email}",
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 15.sp,
                                modifier = Modifier.padding(5.dp)
                            )
                            Text(
                                "Telefone: ${userList[userIndex].telefone}",
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 15.sp,
                                modifier = Modifier.padding(5.dp)
                            )
                        }
                    }

                    FilledIconButton(
                        modifier = Modifier.fillMaxWidth().padding(20.dp),
                        colors = IconButtonDefaults.filledIconButtonColors(Color(0xFF0076CE)),
                        onClick = { if (page == 0) page += 1 else createBuild() }) {
                        Icon(
                            if(page == 0) Icons.AutoMirrored.Filled.ArrowForward else Icons.Filled.Add,
                            contentDescription = "Continuar",
                            tint = Color(0xFFFFFFFF),
                        )
                    }

                }
            }

        }
    }
}
