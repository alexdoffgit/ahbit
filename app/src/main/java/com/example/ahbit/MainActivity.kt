package com.example.ahbit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.core.view.WindowCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ahbit.ui.theme.AhbitTheme
import kotlinx.coroutines.launch
import java.util.Calendar

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
//        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            AhbitTheme {
                HabitCreationPage()
            }
        }
    }
}

@Composable
fun TodoPage(viewModel: TodoViewModel = viewModel()) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            TodoAddField(
                text = viewModel.text,
                onValueChange = viewModel::onTextChange,
                onAddClick = viewModel::addTodo
            )
        }
    ) { paddingValues -> TodoList(
        itemss = viewModel.todoList,
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) }
}

class TodoViewModel: ViewModel() {
            var text by mutableStateOf("")
        private set
    var todoList = mutableStateListOf("do laundry", "clean house", "not do homework")
        private set

    fun onTextChange(newText: String) {
        text = newText
    }

    fun addTodo() {
        if (text.isNotBlank()) {
            todoList.add(text)
            text = ""
        }
    }

    fun removeTodo(todo: String) {
        todoList.remove(todo)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TodoAddField(text: String, onValueChange: (String) -> Unit, onAddClick: () -> Unit) {
    val bringIntoViewRequester = remember { BringIntoViewRequester() }
    val scope = rememberCoroutineScope()

    Box(
        Modifier
            .imePadding()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = text,
                onValueChange = onValueChange,
                modifier = Modifier
                    .bringIntoViewRequester(bringIntoViewRequester)
                    .onFocusChanged {
                        if (it.isFocused) {
                            scope.launch { bringIntoViewRequester.bringIntoView() }
                        }
                    }
            )
            Button(onClick = onAddClick) {
                Text("+")
            }
        }
    }
}

@Composable
fun TodoList(itemss: List<String>, modifier: Modifier) {
    LazyColumn(
        modifier = modifier
    ) {
        items(itemss) {
                item -> TodoItem(item)
        }
    }
}

@Composable
fun TodoItem(content: String) {
    var checked by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier.padding(1.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = checked,
            onCheckedChange = { checked = it }
        )
        Text(content)
    }
}


@Composable
fun HabitCreationPage() {
    var habitName by remember { mutableStateOf("") }

    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Column {
            Text(text = "Habit")
            OutlinedTextField(
                value = habitName,
                onValueChange = { habitName = it }
            )
        }
        Select(items = listOf("Every Day", "Every Month", "Custom Week"))
        Column {
            Text("Time")
            TapTime()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Select(items: List<String>) {
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf<String?>(null) }

    Column {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            TextField(
                value = selectedText ?: "",
                onValueChange = {},
                readOnly = true,
                label = { Text("Choose fruit") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier.menuAnchor(type = ExposedDropdownMenuAnchorType.PrimaryNotEditable, enabled = true)
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                items.forEach { item ->
                    DropdownMenuItem(
                        text = { Text(item) },
                        onClick = {
                            selectedText = item
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun TapTime() {
    var showTimeDial by remember { mutableStateOf(false) }
    var selectedTime by remember { mutableStateOf<Pair<Int, Int>?>(null) }

    // ill stop right here, i don't know how the time UI should look like
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimeDialModal(
    onDismiss: () -> Unit,
    onConfirm: (Int, Int) -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            tonalElevation = 8.dp,
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            val currentTime = Calendar.getInstance()

            val timePickerState = rememberTimePickerState(
                initialHour = currentTime.get(Calendar.HOUR_OF_DAY),
                initialMinute = currentTime.get(Calendar.MINUTE),
                is24Hour = true,
            )

            Column(modifier = Modifier.padding(16.dp)) {
                TimePicker(state = timePickerState)
                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TextButton(onClick = onDismiss) { Text("Dismiss") }
                    Spacer(modifier = Modifier.width(8.dp))
                    TextButton(onClick = {
                        onConfirm(timePickerState.hour, timePickerState.minute)
                    }) { Text("Confirm") }
                }
            }
        }
    }
}