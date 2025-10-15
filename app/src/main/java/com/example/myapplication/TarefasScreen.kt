package com.example.myapplication

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TarefasScreen(taskDao: TaskDao) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    var tarefas by remember { mutableStateOf(listOf<Task>()) }
    var novaTarefa by remember { mutableStateOf("") }


    LaunchedEffect(Unit) {
        tarefas = taskDao.getAll()
    }

    val total = tarefas.size
    val concluidas = tarefas.count { it.isDone }
    val pendentes = total - concluidas

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Tarefas") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF0B3B73),
                    titleContentColor = Color.White
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    if (novaTarefa.isNotBlank()) {
                        val tarefa = Task(title = novaTarefa,)
                        scope.launch {
                            taskDao.insert(tarefa)
                            tarefas = taskDao.getAll()
                            Toast.makeText(context, "Tarefa adicionada!", Toast.LENGTH_SHORT).show()
                        }
                        novaTarefa = ""
                    }
                },
                containerColor = Color(0xFF0B3B73)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Adicionar Tarefa",
                    tint = Color.White
                )
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = novaTarefa,
                onValueChange = { novaTarefa = it },
                label = { Text("Nova tarefa") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "📌 Minhas Tarefas",
                style = MaterialTheme.typography.headlineSmall,
                color = Color(0xFF0B3B73)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Total: $total | Concluídas: $concluidas | Pendentes: $pendentes",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                contentPadding = PaddingValues(bottom = padding.calculateBottomPadding() + 80.dp)
            ) {
                items(tarefas.size) { index ->
                    val tarefa = tarefas[index]

                    Card(
                        shape = RoundedCornerShape(16.dp),
                        elevation = CardDefaults.cardElevation(6.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = if (tarefa.isDone) Color(0xFFDFF6DD) else Color(0xFFE3F2FD)
                        ),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(14.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Checkbox(
                                checked = tarefa.isDone,
                                onCheckedChange = { marcado ->
                                    scope.launch {
                                        taskDao.update(tarefa.copy(isDone = marcado))
                                        tarefas = taskDao.getAll()
                                        Toast.makeText(
                                            context,
                                            "Tarefa ${if (marcado) "concluída" else "marcada como pendente"}!",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                },
                                colors = CheckboxDefaults.colors(
                                    checkedColor = Color(0xFF0B3B73),
                                    uncheckedColor = Color.Gray
                                )
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = tarefa.title,
                                style = MaterialTheme.typography.bodyLarge,
                                color = if (tarefa.isDone) Color.Gray else Color.Black,
                                textDecoration = if (tarefa.isDone) TextDecoration.LineThrough else null
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            // Botão de excluir com Toast
                            Text(
                                text = "🗑",
                                modifier = Modifier.clickable {
                                    scope.launch {
                                        taskDao.delete(tarefa)
                                        tarefas = taskDao.getAll()
                                        Toast.makeText(context, "Tarefa removida!", Toast.LENGTH_SHORT).show()
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
