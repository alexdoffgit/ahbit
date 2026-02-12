package com.example.ahbit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
import androidx.core.view.WindowCompat
import com.example.ahbit.ui.theme.AhbitTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
//        enableEdgeToEdge()
        setContent {
            AhbitTheme {
                TodoPage()
            }
        }
    }
}

@Composable
fun TodoPage() {
    var text by remember { mutableStateOf("") }
    val todoList = remember { mutableStateListOf("do laundry", "clean house", "do homework") }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            TodoAddField(
                text = text,
                onValueChange = { text = it },
                onAddClick = {
                    if (text.isNotBlank()) {
                        todoList.add(text)
                        text = ""
                    }
                }
            )
        }
    ) { paddingValues -> TodoList(
        itemss = todoList,
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) }
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
fun TodoList(itemss: List<String>, modifier: Modifier) {
    LazyColumn(
        modifier = modifier
    ) {
        items(itemss) {
            item -> TodoItem(item)
        }
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