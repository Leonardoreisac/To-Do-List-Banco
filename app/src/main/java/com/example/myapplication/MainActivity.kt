package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material3.Surface
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.data.local.AppDatabase
import com.example.myapplication.data.repository.HabitRepository
import com.example.myapplication.data.repository.TaskRepository
import com.example.myapplication.ui.checklist.ChecklistScreen
import com.example.myapplication.ui.checklist.ChecklistViewModelFactory
import com.example.myapplication.ui.config.ConfiguracoesScreen
import com.example.myapplication.ui.estatisticas.EstatisticasScreen
import com.example.myapplication.ui.estatisticas.EstatisticasViewModelFactory
import com.example.myapplication.ui.home.PerfilScreen
import com.example.myapplication.ui.login.LoginScreen
import com.example.myapplication.ui.tarefas.TarefasScreen
import com.example.myapplication.ui.tarefas.TaskViewModelFactory
import com.example.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {

    private lateinit var taskFactory: TaskViewModelFactory
    private lateinit var checklistFactory: ChecklistViewModelFactory
    private lateinit var estatisticasFactory: EstatisticasViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val database = AppDatabase.getDatabase(applicationContext)
        val taskRepository = TaskRepository(database.taskDao())
        val habitRepository = HabitRepository(database.habitDao())

        taskFactory = TaskViewModelFactory(taskRepository)
        checklistFactory = ChecklistViewModelFactory(habitRepository)
        estatisticasFactory = EstatisticasViewModelFactory(taskRepository, habitRepository)

        setContent {
            MyApplicationTheme {
                Surface(color = MaterialTheme.colorScheme.background) {

                    AppNavigation(
                        taskFactory = taskFactory,
                        checklistFactory = checklistFactory,
                        estatisticasFactory = estatisticasFactory
                    )
                }
            }
        }
    }
}

@Composable
fun AppNavigation(
    taskFactory: TaskViewModelFactory,
    checklistFactory: ChecklistViewModelFactory,
    estatisticasFactory: EstatisticasViewModelFactory
) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login") {

        // LOGIN SCREEN
        composable("login") {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate("home") {
                        popUpTo("login") { inclusive = true }
                    }
                }
            )
        }

        // HOME/PERFIL SCREEN
        composable("home") { TelaPerfil(navController) }

        // TAREFAS SCREEN (MVVM)
        composable("tarefas") {
            TarefasScreen(viewModel = viewModel(factory = taskFactory))
        }

        // CHECKLIST SCREEN (MVVM)
        composable("checklist") {
            ChecklistScreen(viewModel = viewModel(factory = checklistFactory))
        }

        // ESTATÍSTICAS SCREEN (MVVM)
        composable("estatisticas") {
            EstatisticasScreen(viewModel = viewModel(factory = estatisticasFactory))
        }

        // OUTRAS TELAS
        composable("perfil") { PerfilScreen(navController) }
        composable("configuracoes") { ConfiguracoesScreen(navController) }
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
            UmaNota("TAREFAS", Color(0xFFFF69B4)) { navController.navigate("tarefas") }
            Spacer(modifier = Modifier.height(12.dp))
            UmaNota("ESTATÍSTICAS", Color(0xFF76FF03)) { navController.navigate("estatisticas") }
            Spacer(modifier = Modifier.height(12.dp))
            UmaNota("CHECKLIST", Color(0xFFFF5252)) { navController.navigate("checklist") }
        }

        Header(
            navController = navController,
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
fun Header(navController: NavController, modifier: Modifier = Modifier) {
    var menuExpanded by remember { mutableStateOf(false) }

    Card(
        modifier = modifier,
        shape = RoundedCornerShape(bottomStart = 18.dp, bottomEnd = 18.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF0B3B73)),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(42.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF11325A))
                        .clickable { menuExpanded = !menuExpanded },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.Menu, contentDescription = "Menu", tint = Color.White)
                }

                DropdownMenu(
                    expanded = menuExpanded,
                    onDismissRequest = { menuExpanded = false }
                ) {
                    DropdownMenuItem(text = { Text("Perfil") }, onClick = {
                        menuExpanded = false
                        navController.navigate("perfil")
                    })
                    DropdownMenuItem(text = { Text("Configurações") }, onClick = {
                        menuExpanded = false
                        navController.navigate("configuracoes")
                    })
                    DropdownMenuItem(text = { Text("Logout") }, onClick = {
                        menuExpanded = false
                        navController.navigate("login") {
                            popUpTo("login") { inclusive = true }
                        }
                    })
                }

                Spacer(modifier = Modifier.width(8.dp))
                Text("USUARIO", style = MaterialTheme.typography.titleLarge, color = Color.White)
            }

            Box(
                modifier = Modifier
                    .size(42.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF11325A))
                    .clickable { navController.navigate("perfil") },
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.Person, contentDescription = "Perfil", tint = Color.White)
            }
        }
    }
}

@Composable
fun UmaNota(nota: String, cor: Color, onClick: () -> Unit) {
    Card(
        onClick = onClick,
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
            Icon(Icons.Default.Lock, contentDescription = null, tint = Color.White, modifier = Modifier.size(32.dp))
            Spacer(modifier = Modifier.width(12.dp))
            Text(text = nota, style = MaterialTheme.typography.titleMedium, color = Color.White)
        }
    }
}