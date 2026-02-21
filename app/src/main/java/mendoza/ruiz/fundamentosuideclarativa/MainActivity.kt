package mendoza.ruiz.fundamentosuideclarativa

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import mendoza.ruiz.fundamentosuideclarativa.ui.theme.FundamentosUIdeclarativaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       enableEdgeToEdge()
        setContent {
            MainScreen()
        }
    }
}
@Composable      //MI PRIMERA FUNCION
fun MainScreen(){
    var counter by remember {mutableStateOf(0)}

    val tasks = remember {
        mutableStateListOf(
            "Aprender compose",
            "Sacar la basura",
            "Ir de compras",
            "Subir proyecto a GitHub"
        )
    }
   Scaffold(
       topBar = {
               //tittle = {Text("TaskCounterApp")}
       }
   ) { paddingValues ->
       Column(
           modifier = Modifier
               .padding(paddingValues)
               .fillMaxWidth()
       ) {
           CounterHeader(
               counter = counter,
               onIncrement = {counter++}
           )
           TaskList(tasks = tasks)
       }

   }
}

//Practice-1
@Composable  //MI SEGUNDA FUNCION
fun CounterHeader(
    counter: Int,
    onIncrement:() -> Unit){
    Card(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(6.dp)
    ){
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ){
            Text(
                text = "Tareas por cumplir",
                style = MaterialTheme.typography.titleMedium

            )

            Button(
                onClick = onIncrement,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 8.dp)

            ){
                Text("Agregar tarea")
            }
        }
    }
}
//Fin
@Composable   //MI TERCERA FUNCION
fun TaskItem(task: String){
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth()
            .clickable{expanded = !expanded}
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = task,
                style = MaterialTheme.typography.titleMedium
            )

            AnimatedVisibility(visible = expanded) {
                Text(
                    text = "Toca para marcar como completado",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
    }
}

@Composable  //MI CUARTA FUNCION
fun TaskList(tasks: List<String>){
    LazyColumn(modifier = Modifier.fillMaxSize()
    ) {
        items(tasks) { task ->
            TaskItem(task = task)
        }
    }
}

@Preview
@Composable
fun MainScreenPreview(){
    MainScreen()
}
