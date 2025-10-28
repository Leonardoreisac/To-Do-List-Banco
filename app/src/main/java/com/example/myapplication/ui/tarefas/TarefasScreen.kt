package com.example.myapplication.ui.tarefas

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.data.local.Task

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TarefasScreen(
    viewModel: TarefasViewModel = viewModel()
) {
    val tasks by viewModel.tasks.collectAsState()
    var novaTarefaText by remember { mutableStateOf("") }
    val context = LocalContext.current

    val total = tasks.size
    val concluidas = tasks.count { it.isDone }
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
                    if (novaTarefaText.isNotBlank()) {
                        viewModel.addTask(novaTarefaText)
                        novaTarefaText = ""
                        Toast.makeText(context, "Tarefa adicionada!", Toast.LENGTH_SHORT).show()
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
                .padding(horizontal = 16.dp)
        ) {
            OutlinedTextField(
                value = novaTarefaText,
                onValueChange = { novaTarefaText = it },
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
                contentPadding = PaddingValues(bottom = 80.dp)
            ) {
                items(items = tasks, key = { it.id }) { tarefa ->
                    TaskCard(
                        tarefa = tarefa,
                        onToggleDone = {
                            viewModel.toggleTaskCompletion(tarefa)
                            Toast.makeText(context, "Tarefa ${if (!tarefa.isDone) "concluída" else "marcada como pendente"}!", Toast.LENGTH_SHORT).show()
                        },
                        onDelete = {
                            viewModel.deleteTask(tarefa)
                            Toast.makeText(context, "Tarefa removida!", Toast.LENGTH_SHORT).show()
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun TaskCard(tarefa: Task, onToggleDone: () -> Unit, onDelete: () -> Unit) {
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
                onCheckedChange = { onToggleDone() },
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
                textDecoration = if (tarefa.isDone) TextDecoration.LineThrough else null,
                modifier = Modifier.weight(1f).clickable { onToggleDone() }
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "🗑",
                modifier = Modifier.clickable { onDelete() }
            )
        }
    }
}