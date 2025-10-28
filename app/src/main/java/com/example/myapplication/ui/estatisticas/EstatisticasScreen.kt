package com.example.myapplication.ui.estatisticas

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel // Novo import
import androidx.compose.ui.draw.clip

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EstatisticasScreen(
    viewModel: EstatisticasViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Estatísticas Gerais") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF0B3B73),
                    titleContentColor = Color.White
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF0F0FF)),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Progresso Geral da Semana",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color(0xFF0B3B73)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "${(uiState.overallProgress * 100).toInt()}% Concluído",
                        style = MaterialTheme.typography.headlineSmall,
                        color = Color(0xFF0B3B73)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    LinearProgressIndicator(
                        progress = uiState.overallProgress,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(10.dp)
                            .clip(RoundedCornerShape(5.dp)),
                        color = Color(0xFF0B3B73),
                        trackColor = Color(0xFFB3E5FC)
                    )
                }
            }

            EstatisticasCard(
                title = "Tarefas",
                completed = uiState.completedTasks,
                total = uiState.totalTasks,
                color = Color(0xFFFF69B4)
            )

            EstatisticasCard(
                title = "Hábitos",
                completed = uiState.doneHabits,
                total = uiState.totalHabits,
                color = Color(0xFFFF5252)
            )
        }
    }
}

@Composable
fun EstatisticasCard(title: String, completed: Int, total: Int, color: Color) {
    val progress = if (total > 0) completed.toFloat() / total.toFloat() else 0f

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = color.copy(alpha = 0.1f)),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge,
                    color = color
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "$completed de $total",
                    style = MaterialTheme.typography.bodyMedium,
                    color = color.copy(alpha = 0.8f)
                )
            }
            Text(
                text = "${(progress * 100).toInt()}%",
                style = MaterialTheme.typography.headlineMedium,
                color = color
            )
        }
    }
}