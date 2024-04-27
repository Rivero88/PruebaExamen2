package com.example.pruebaexamen2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pruebaexamen2.Data.Asignatura
import com.example.pruebaexamen2.Data.DataSource
import com.example.pruebaexamen2.Data.UiStateAcademia
import com.example.pruebaexamen2.Data.ViewModelAcademia
import com.example.pruebaexamen2.ui.theme.PruebaExamen2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PruebaExamen2Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    PantallaPrincipal(asignaturas=DataSource.asignaturas, viewModel= viewModel())
                }
            }
        }
    }
}
@Composable
fun PantallaPrincipal(modifier: Modifier = Modifier ,asignaturas: ArrayList<Asignatura>,
                      viewModel: ViewModelAcademia){
    val uiState by viewModel.uiState.collectAsState()
    Column (modifier){
        Text(text = "Bienvenid@ Academia", modifier
            .fillMaxWidth()
            .background(Color.LightGray)
            .padding(start = 30.dp, top = 25.dp))
        PantallaAsignaturas(modifier, DataSource.asignaturas,
            viewModel)
        CuadroTextField(viewModel)
        CuadroTextoInferior(modifier, uiState)
    }
}

@Composable
fun PantallaAsignaturas(modifier: Modifier = Modifier, asignaturas: ArrayList<Asignatura>,
                        viewModel: ViewModelAcademia){
    Column(modifier.height(300.dp)) {
        LazyVerticalGrid(columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.Center,
            horizontalArrangement = Arrangement.Center){
            items(asignaturas){ asignatura ->
                Card(modifier.padding(8.dp)) {
                    Text(text = "Asig.: ${asignatura.nombre}",
                        modifier
                            .fillMaxWidth()
                            .background(Color.Yellow)
                            .padding(10.dp),
                        textAlign = TextAlign.Center)
                    Text(text = "€/hora: ${asignatura.precioHora}",
                        modifier
                            .fillMaxWidth()
                            .background(Color.Cyan)
                            .padding(10.dp),
                        textAlign = TextAlign.Center)
                    Row (modifier.align(Alignment.CenterHorizontally)){
                        Button(onClick = { viewModel.sumarHoras(asignatura,viewModel.introducirHoras, asignaturas) }) {
                            Text(text = "+")
                        }
                        Button(onClick = { viewModel.restarHoras(asignatura,viewModel.introducirHoras,asignaturas) }) {
                            Text(text = "-")
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CuadroTextField(viewModel: ViewModelAcademia){
    TextField(value = viewModel.introducirHoras ,
        onValueChange = { viewModel.nuevoValorHoras(it) },
        label = { Text(text = "Horas a contratar o eliminar.") },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Next
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
    )
}

@Composable
fun CuadroTextoInferior(modifier: Modifier = Modifier, uiState: UiStateAcademia){
    Column (modifier
        .fillMaxWidth()
        .background(Color.LightGray)
        .padding(10.dp)) {
        Text(text = "Última acción: \n${uiState.textoUltAccion}",
            modifier
                .fillMaxWidth()
                .background(Color.Magenta))
        Text(text = "Resumen: \n${uiState.textoResumen}",
            modifier
                .fillMaxWidth()
                .background(Color.White))
    }
}


