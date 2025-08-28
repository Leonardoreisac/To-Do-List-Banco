package com.example.myapplication

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChecklistScreen() {
    var habitos = remember {
        mutableStateListOf("Beber 2L de água 💧", "Ler 20 páginas 📖", "Caminhar 30min 🚶")
    }
    var checkedStates = remember { mutableStateListOf(false, false, false) }
    var novoHabito by remember { mutableStateOf("") }

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
                        habitos.add(novoHabito)
                        checkedStates.add(false)
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
                singleLine = true
            )


            habitos.forEachIndexed { index, habito ->
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
                            text = habito,
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color(0xFF0B3B73)
                        )

                        Checkbox(
                            checked = checkedStates[index],
                            onCheckedChange = { checkedStates[index] = it },
                            colors = CheckboxDefaults.colors(
                                checkedColor = Color(0xFF0B3B73),
                                uncheckedColor = Color.Gray
                            )
                        )
                    }
                }
            }


            val progresso =
                if (habitos.isNotEmpty()) checkedStates.count { it }.toFloat() / habitos.size.toFloat()
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
