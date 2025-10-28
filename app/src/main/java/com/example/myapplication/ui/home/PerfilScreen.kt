@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.myapplication.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun PerfilScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Perfil") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF0B3B73),
                    titleContentColor = Color.White
                ),
                navigationIcon = {
                    IconButton(onClick = { navController.navigate("home") }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Voltar", tint = Color.White)
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            // Cabeçalho do perfil
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .background(Color(0xFF0B3B73)),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Avatar",
                        tint = Color.White,
                        modifier = Modifier.size(64.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        "Leonardo Reis",
                        color = Color.White,
                        style = MaterialTheme.typography.headlineSmall
                    )
                    Text(
                        "Usuário: admin",
                        color = Color.White,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))


            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5)),
                elevation = CardDefaults.cardElevation(6.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        "Informações Pessoais",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color(0xFF0B3B73)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Nome: Tonico Tinoco")
                    Text("Email: tinocotoninho@email.com")
                    Text("CPF: 123.456.789-00")
                }
            }

            Spacer(modifier = Modifier.height(12.dp))


            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5)),
                elevation = CardDefaults.cardElevation(6.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        "Experiência Profissional",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color(0xFF0B3B73)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Empresa: Universidade Positivo")
                    Text("Cargo: Desenvolvedor Android")
                    Text("Período: Jan 2024 - Presente")
                }
            }
        }
    }
}
