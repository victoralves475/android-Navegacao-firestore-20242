package com.example.navegacao1.ui.telas

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.navegacao1.model.dados.Usuario
import com.example.navegacao1.model.dados.UsuarioDAO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun TelaCadastro(
    modifier: Modifier = Modifier,
    onCadastroSuccess: () -> Unit
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    var nome by remember { mutableStateOf("") }
    var senha by remember { mutableStateOf("") }
    var mensagemErro by remember { mutableStateOf<String?>(null) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxWidth()
    ) {
        OutlinedTextField(
            value = nome,
            onValueChange = { nome = it },
            label = { Text("Nome") }
        )
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextField(
            value = senha,
            onValueChange = { senha = it },
            visualTransformation = PasswordVisualTransformation(),
            label = { Text("Senha") }
        )
        Spacer(modifier = Modifier.height(10.dp))
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                if (nome.isNotBlank() && senha.isNotBlank()) {
                    scope.launch(Dispatchers.IO) {
                        UsuarioDAO().adicionar(Usuario(nome = nome, senha = senha)) {
                            // Após cadastro com sucesso, navega de volta para a tela de login
                            onCadastroSuccess()
                        }
                    }
                } else {
                    mensagemErro = "Preencha todos os campos!"
                }
            }
        ) {
            Text("Cadastrar")
        }
        mensagemErro?.let { msg ->
            LaunchedEffect(msg) {
                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                mensagemErro = null
            }
        }
    }
}
