package mendoza.ruiz.fundamentosuideclarativa


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       enableEdgeToEdge()
        setContent {
            MaterialTheme{
                MainScreen()
            }
        }
    }
}
    @OptIn(ExperimentalMaterial3Api::class)
@Composable      //MI PRIMERA FUNCION
fun MainScreen(){
    var counter by remember {mutableStateOf(0)}

    val tasks = remember {
        mutableStateListOf(
            "Aprender compose",
            "Preparar la cena",
            "Ir de compras",
            "Subir proyecto a GitHub"
        )
    }
   Scaffold(
       topBar = {
           TopAppBar(
               title = {Text("Lista de tareas")}
           )
       }
    ) { paddingValues ->
       Column(
           modifier = Modifier
               .padding(paddingValues)
               .fillMaxSize()

       ) {
           CounterHeader(
               counter = tasks.size,
               onAddTask = {
                   nombreDeLaTarea ->
                   if(!tasks.contains(nombreDeLaTarea)){
                       tasks.add(nombreDeLaTarea)
                   }
               }
           )
           TaskList(
               tasks = tasks,
               onDeleteTask = {tareaBorrar ->
                   tasks.remove(tareaBorrar)// <- Eliminamos la tarea de la lista
               }
           )
       }
   }
}

//Practice-1
@Composable  //MI SEGUNDA FUNCION
fun CounterHeader(
    counter: Int,
    onAddTask: (String) -> Unit
){
    var textInput by remember {mutableStateOf("")}

    Card(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(6.dp)
    ){
        Column(
            modifier = Modifier
                .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Tareas por cumplir: $counter",
                style = MaterialTheme.typography.titleMedium
            )

            //Campo de texto para que el usuario escriba
            OutlinedTextField(
                value = textInput,
                onValueChange = {nuevoTexto -> textInput = nuevoTexto},
                label = {Text("Nombre de la nueva tarea")},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )
            Button(
                onClick = {
                    if (textInput.isNotBlank()){
                        onAddTask(textInput)
                        textInput = ""
                    }
                },
                //La linea 146 es una forma para cambiar el color de fondo de un boton
                colors = ButtonDefaults.buttonColors( Color.DarkGray),
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 8.dp)

            ) {
                Text("Agregar tarea")
            }
        }
    }

}

//Fin
@OptIn(ExperimentalMaterial3Api::class)
@Composable   //MI TERCERA FUNCION
fun TaskItem(
    task: String,
    onDelete: () -> Unit
){
    var expanded by remember {mutableStateOf(false)}

    //Control del estado (deslizamiento)
    val dismissState = rememberSwipeToDismissBoxState(
        confirmValueChange = {dismissValue ->
          if (dismissValue == SwipeToDismissBoxValue.EndToStart){
              onDelete()
              true
          }else {
              false
          }
        }
    )
    SwipeToDismissBox(
        state = dismissState,
        enableDismissFromStartToEnd = false,
        backgroundContent = {
            //Fondo rojo que aparece al deslizar
            val color by animateColorAsState(
                if (dismissState.targetValue == SwipeToDismissBoxValue.EndToStart)
                Color.Red else Color.Transparent,
                label = "ColorAnimacion"
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .padding(end = 16.dp),
                contentAlignment = Alignment.CenterEnd){
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Eliminar tarea",
                    tint = Color.White
                )
            }
        }
    ){
        Card(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .fillMaxWidth()
                .clickable{expanded = !expanded}
        ){
            Column(modifier = Modifier.padding(16.dp)){
                Text(
                    text = task,
                    style = MaterialTheme.typography.titleMedium
                )
                AnimatedVisibility(visible = expanded) {
                    Text(
                        text = "Marcar como compeltado",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }
        }
    }
}

@Composable  //MI CUARTA FUNCION
fun TaskList(tasks: List<String>, onDeleteTask: (String) -> Unit){
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(
            items = tasks,
            key = { it }
        ){
            task ->
            TaskItem(
                task = task,
                onDelete = {onDeleteTask(task)}
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview(){
    MainScreen()
}
