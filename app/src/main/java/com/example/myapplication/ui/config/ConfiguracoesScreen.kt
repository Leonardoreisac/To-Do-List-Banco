@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.myapplication.ui.config

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun ConfiguracoesScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Configurações") },
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
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Configurações do Aplicativo",
                style = MaterialTheme.typography.headlineSmall,
                color = Color(0xFF0B3B73)
            )

            Spacer(modifier = Modifier.height(24.dp))


            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5)),
                elevation = CardDefaults.cardElevation(6.dp)
            ) {
                Text(
                    text = "Tema do App: Claro/Escuro",
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5)),
                elevation = CardDefaults.cardElevation(6.dp)
            ) {
                Text(
                    text = "Notificações: Ativar/Desativar",
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5)),
                elevation = CardDefaults.cardElevation(6.dp)
            ) {
                Text(
                    text = "Conta: Alterar senha",
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { navController.navigate("home") },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF0B3B73),
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Voltar")
            }
        }
    }
}
