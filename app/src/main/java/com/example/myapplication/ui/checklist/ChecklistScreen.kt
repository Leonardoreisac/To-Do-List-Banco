package com.example.myapplication.ui.checklist

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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.data.local.Habit
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChecklistScreen(
    // 1. INJETANDO O VIEWMODEL (Precisa da Factory na chamada de navegação)
    viewModel: ChecklistViewModel = viewModel()
) {
    // 2. OBSERVANDO O ESTADO DO VIEWMODEL
    // Qualquer mudança no banco de dados (inserção, update, delete)
    // será automaticamente refletida na lista 'habits'.
    val habits by viewModel.habits.collectAsState()

    var novoHabitoText by remember { mutableStateOf("") }
    val context = LocalContext.current

    // Cálculos de Progresso: Usam a lista 'habits' observada
    val progresso =
        if (habits.isNotEmpty()) habits.count { it.isDone }.toFloat() / habits.size.toFloat()
        else 0f

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
                    if (novoHabitoText.isNotBlank()) {
                        // 3. ENVIANDO EVENTO AO VIEWMODEL
                        viewModel.addHabit(novoHabitoText)
                        novoHabitoText = ""
                        Toast.makeText(context, "Hábito adicionado!", Toast.LENGTH_SHORT).show()
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
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = novoHabitoText,
                onValueChange = { novoHabitoText = it },
                label = { Text("Novo hábito") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardActions = KeyboardActions {}
            )

            // Lista de Hábitos
            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(items = habits, key = { it.id }) { habit ->
                    HabitCard(
                        habit = habit,
                        // 3. ENVIANDO EVENTOS AO VIEWMODEL
                        onToggleDone = {
                            viewModel.toggleHabitCompletion(habit)
                            Toast.makeText(context, "Hábito atualizado!", Toast.LENGTH_SHORT).show()
                        },
                        onDelete = {
                            viewModel.deleteHabit(habit)
                            Toast.makeText(context, "Hábito removido!", Toast.LENGTH_SHORT).show()
                        }
                    )
                }
            }

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

// Componente separado para o Card do Hábito
@Composable
fun HabitCard(habit: Habit, onToggleDone: () -> Unit, onDelete: () -> Unit) {
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
                    onCheckedChange = { onToggleDone() }, // Chama o evento no ViewModel
                    colors = CheckboxDefaults.colors(
                        checkedColor = Color(0xFF0B3B73),
                        uncheckedColor = Color.Gray
                    )
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = "🗑",
                    modifier = Modifier.clickable { onDelete() } // Chama o evento no ViewModel
                )
            }
        }
    }
}