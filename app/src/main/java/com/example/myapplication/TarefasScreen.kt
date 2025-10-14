package com.example.myapplication

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TarefasScreen(taskDao: TaskDao) {
    var tarefas by remember {
        mutableStateOf(
            listOf(
                Tarefa("Comprar pão", true),
                Tarefa("Estudar Android Studio", false),
                Tarefa("Enviar trabalho da faculdade", true),
                Tarefa("Ir à academia", false),
                Tarefa("Ler 20 páginas do livro", false)
            )
        )
    }

    val total = tarefas.size
    val concluidas = tarefas.count { it.concluida }
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
                onClick = { },
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
                            containerColor = if (tarefa.concluida) Color(0xFFDFF6DD) else Color(0xFFE3F2FD)
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
                                checked = tarefa.concluida,
                                onCheckedChange = { marcado ->
                                    tarefas = tarefas.toMutableList().apply {
                                        this[index] = this[index].copy(concluida = marcado)
                                    }
                                },
                                colors = CheckboxDefaults.colors(
                                    checkedColor = Color(0xFF0B3B73),
                                    uncheckedColor = Color.Gray
                                )
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = tarefa.titulo,
                                style = MaterialTheme.typography.bodyLarge,
                                color = if (tarefa.concluida) Color.Gray else Color.Black,
                                textDecoration = if (tarefa.concluida) TextDecoration.LineThrough else null
                            )
                        }
                    }
                }
            }
        }
    }
}

data class Tarefa(val titulo: String, val concluida: Boolean)
