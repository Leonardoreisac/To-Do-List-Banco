package com.example.myapplication

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EstatisticasScreen() {

    val tarefas = listOf(
        Tarefa("Comprar pão", true),
        Tarefa("Estudar Android Studio", false),
        Tarefa("Enviar trabalho da faculdade", true),
        Tarefa("Ir à academia", false),
        Tarefa("Ler 20 páginas do livro", false)
    )

    val total = tarefas.size
    val concluidas = tarefas.count { it.concluida }
    val pendentes = total - concluidas
    val percentual = if (total > 0) (concluidas * 100) / total else 0

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Estatísticas") },
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
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "📊 Estatísticas de Tarefas",
                style = MaterialTheme.typography.headlineSmall,
                color = Color(0xFF0B3B73)
            )

            Spacer(modifier = Modifier.height(24.dp))

            EstatisticaCard("Total de Tarefas", total.toString(), Color(0xFF0B3B73))
            Spacer(modifier = Modifier.height(12.dp))
            EstatisticaCard("Concluídas", concluidas.toString(), Color(0xFF4CAF50))
            Spacer(modifier = Modifier.height(12.dp))
            EstatisticaCard("Pendentes", pendentes.toString(), Color(0xFFFFC107))
            Spacer(modifier = Modifier.height(12.dp))
            EstatisticaCard("Percentual Concluído", "$percentual%", Color(0xFF2196F3))
        }
    }
}

@Composable
fun EstatisticaCard(titulo: String, valor: String, cor: Color) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp),
        colors = CardDefaults.cardColors(containerColor = cor.copy(alpha = 0.08f)),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = titulo,
                style = MaterialTheme.typography.titleMedium,
                color = Color.Black
            )
            Text(
                text = valor,
                style = MaterialTheme.typography.headlineSmall,
                color = cor
            )
        }
    }
}
