package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.example.myapplication.ui.theme.MyApplicationTheme
import androidx.navigation.compose.rememberNavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                AppNavigation()
            }
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "home") {
        composable("home") { TelaPerfil(navController) }
        composable("tarefas") { TarefasScreen() }
        composable("estatisticas") { EstatisticasScreen() }
        composable("checklist") { ChecklistScreen() }
    }
}


@Composable
fun TelaPerfil(navController: NavController) {
    val headerHeight = 100.dp

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = headerHeight + 16.dp)
                .padding(horizontal = 12.dp)
        ) {
            Spacer(modifier = Modifier.height(12.dp))
            UmaNota("TAREFAS", Color(0xFFFF69B4)) {
                navController.navigate("tarefas")
            }
            Spacer(modifier = Modifier.height(12.dp))
            UmaNota("ESTATÍSTICAS", Color(0xFF76FF03)) {
                navController.navigate("estatisticas")
            }
            Spacer(modifier = Modifier.height(12.dp))
            UmaNota("CHECKLIST", Color(0xFFFF5252)) {
                navController.navigate("checklist")
            }
        }

        Header(
            modifier = Modifier
                .fillMaxWidth()
                .height(headerHeight)
                .padding(horizontal = 12.dp)
                .align(Alignment.TopCenter)
                .zIndex(1f)
        )
    }
}


@Composable
fun Header(modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(bottomStart = 18.dp, bottomEnd = 18.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF0B3B73)), // azul escuro
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Esquerda: menu + título
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = { /* menu */ }) {
                    Icon(imageVector = Icons.Default.Menu, contentDescription = "menu", tint = Color.White)
                }
                Spacer(modifier = Modifier.width(6.dp))
                Text(text = "USUARIO", style = MaterialTheme.typography.titleLarge, color = Color.White)
            }

            // Direita: notificações + avatar
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = { /* notificações */ }) {
                    Icon(imageVector = Icons.Default.Notifications, contentDescription = "notificações", tint = Color.White)
                }

                Spacer(modifier = Modifier.width(8.dp))

                Surface(
                    modifier = Modifier.size(42.dp),
                    shape = CircleShape,
                    color = Color.Transparent,
                    border = BorderStroke(2.dp, Color.White),
                    tonalElevation = 2.dp
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color(0xFF11325A), shape = CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "avatar",
                            tint = Color.White,
                            modifier = Modifier.size(22.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun UmaNota(nota: String, cor: Color, onClick: () -> Unit) {
    Card(
        onClick = onClick, // agora responde a clique
        colors = CardDefaults.cardColors(containerColor = cor),
        modifier = Modifier
            .fillMaxWidth()
            .height(110.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(
                    text = nota,
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.White
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun TelaPerfilPreview() {
    MyApplicationTheme {
    }
}