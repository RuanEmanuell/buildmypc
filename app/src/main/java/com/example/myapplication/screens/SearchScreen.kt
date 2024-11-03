package com.example.myapplication.screens

import android.database.sqlite.SQLiteDatabase
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.myapplication.AppBar
import com.example.myapplication.data.DatabaseConnection
import com.example.myapplication.model.Computador
import com.example.myapplication.model.MontagemView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(navController: NavHostController, databaseConnection: DatabaseConnection, db: SQLiteDatabase) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    var buildList by remember { mutableStateOf(listOf<MontagemView>()) }
    var originalBuildList by remember { mutableStateOf(listOf<MontagemView>()) }
    var deleteBuildIndex by remember { mutableIntStateOf(0) }
    var showDialog by remember { mutableStateOf(false) }

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

    fun deleteBuild(buildId: Int){
        try {
            databaseConnection.deleteBuild(buildId, db)
            Toast.makeText(
                context,
                "Montagem $buildId apagada com sucesso!",
                Toast.LENGTH_SHORT
            ).show()

            buildList = buildList.filter { it.id != buildId }

        } catch (e: Exception){
            Toast.makeText(
                context,
                "Ocorreu um erro ao tentar apagar: ${e.message}",
                Toast.LENGTH_SHORT
            ).show()
        } finally {
            showDialog = false
        }
    }

    LaunchedEffect(Unit) {
        originalBuildList = getAllBuilds()
        buildList = originalBuildList
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
            Column (modifier = Modifier.fillMaxSize().padding(20.dp).verticalScroll(state = scrollState),) {
                buildList.forEach {
                    build ->
                    Column(modifier = Modifier.fillMaxSize()){
                        Column(
                            modifier = Modifier.border(
                                2.dp,
                                Color.LightGray,
                                RoundedCornerShape(10.dp)
                            ).fillMaxSize().padding(10.dp)
                        ){
                            Row(modifier = Modifier.fillMaxWidth().padding(10.dp, 10.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically){
                                Text("ID da montagem: ${build.id}",
                                    fontWeight = FontWeight.Bold, fontSize = 25.sp)
                                IconButton(
                                    onClick = { showDialog = true; deleteBuildIndex = build.id } ) {
                                    Icon(
                                        Icons.Filled.Delete,
                                        contentDescription = "Deletar",
                                        tint = Color(0XFFF51800),
                                    )
                                }
                            }
                            Text("Nome do Cliente: ${build.clienteNome}",
                                fontSize = 20.sp,
                                modifier = Modifier.padding(10.dp, 5.dp))
                            Text("CPF do Cliente: ${build.clienteCpf}",
                                fontSize = 20.sp,
                                modifier = Modifier.padding(10.dp, 5.dp))
                            Text("Telefone do Cliente: ${build.clienteTelefone}",
                                fontSize = 20.sp,
                                modifier = Modifier.padding(10.dp, 5.dp))
                            Text("Email do Cliente: ${build.clienteEmail}",
                                fontSize = 20.sp,
                                modifier = Modifier.padding(10.dp, 5.dp))
                            Text("ID do modelo do PC: ${build.modeloId + 1}",
                                fontSize = 20.sp,
                                modifier = Modifier.padding(10.dp, 5.dp))
                            Text("Configuração do PC: ${build.toStringModelo()}",
                                fontSize = 20.sp,
                                modifier = Modifier.padding(10.dp, 5.dp))
                            Text("Valor do PC: R$ ${build.modeloValor}",
                                fontSize = 20.sp,
                                modifier = Modifier.padding(10.dp, 10.dp))
                        }
                        Spacer(modifier = Modifier.height(15.dp))

                        if(showDialog){
                            BasicAlertDialog(onDismissRequest = {showDialog = false},
                                content = {
                                    Column (
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        modifier = Modifier.width(300.dp).height(200.dp).background(color = Color(0XFFFFFFFF))) {
                                        Text("Deseja apagar essa montagem?", fontSize = 30.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth().padding(20.dp))
                                        Row(horizontalArrangement = Arrangement.SpaceAround,
                                            modifier = Modifier.fillMaxWidth().padding(20.dp)){
                                            Button(onClick = {deleteBuild(deleteBuildIndex)}, colors = ButtonDefaults.buttonColors(Color(0XFF72CC12))) {
                                                Text("Sim", color = Color(0XFFFFFFFF), fontSize = 20.sp)
                                            }
                                            Button(onClick = {showDialog = false}, colors = ButtonDefaults.buttonColors(Color(0XFFF51800))) {
                                                Text("Não", color = Color(0XFFFFFFFF), fontSize = 20.sp)
                                            }
                                        }
                                    }
                                }
                            )
                        }
                    }
                    }
            }
        }
    }
}

