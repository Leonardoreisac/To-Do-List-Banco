package com.example.myapplication

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChecklistScreen(habitDao: HabitDao) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    // Lista de hábitos do banco
    var habitos by remember { mutableStateOf(listOf<Habit>()) }
    var novoHabito by remember { mutableStateOf("") }

    // Carregar hábitos do banco
    LaunchedEffect(Unit) {
        habitos = habitDao.getAll()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Checklist de Hábitos") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF0B3B73),
                    titleContentColor = Color.White
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    if (novoHabito.isNotBlank()) {
                        val habit = Habit(title = novoHabito)
                        scope.launch {
                            habitDao.insert(habit)
                            habitos = habitDao.getAll()
                            Toast.makeText(context, "Hábito adicionado!", Toast.LENGTH_SHORT).show()
                        }
                        novoHabito = ""
                    }
                },
                containerColor = Color(0xFF0B3B73)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Adicionar hábito", tint = Color.White)
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = novoHabito,
                onValueChange = { novoHabito = it },
                label = { Text("Novo hábito") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardActions = KeyboardActions {}
            )

            habitos.forEach { habit ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5)),
                    elevation = CardDefaults.cardElevation(6.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = habit.title,
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color(0xFF0B3B73)
                        )

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Checkbox(
                                checked = habit.isDone,
                                onCheckedChange = { checked ->
                                    scope.launch {
                                        habitDao.update(habit.copy(isDone = checked))
                                        habitos = habitDao.getAll()
                                        Toast.makeText(context, "Hábito atualizado!", Toast.LENGTH_SHORT).show()
                                    }
                                },
                                colors = CheckboxDefaults.colors(
                                    checkedColor = Color(0xFF0B3B73),
                                    uncheckedColor = Color.Gray
                                )
                            )

                            Spacer(modifier = Modifier.width(8.dp))

                            Text(
                                text = "🗑",
                                modifier = Modifier.clickable {
                                    scope.launch {
                                        habitDao.delete(habit)
                                        habitos = habitDao.getAll()
                                        Toast.makeText(context, "Hábito removido!", Toast.LENGTH_SHORT).show()
                                    }
                                }
                            )
                        }
                    }
                }
            }

            val progresso =
                if (habitos.isNotEmpty()) habitos.count { it.isDone }.toFloat() / habitos.size.toFloat()
                else 0f

            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "Progresso da semana",
                style = MaterialTheme.typography.titleMedium,
                color = Color(0xFF0B3B73)
            )
            LinearProgressIndicator(
                progress = progresso,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(12.dp)
                    .clip(RoundedCornerShape(6.dp)),
                color = Color(0xFF0B3B73),
                trackColor = Color(0xFFB3E5FC)
            )
        }
    }
}
