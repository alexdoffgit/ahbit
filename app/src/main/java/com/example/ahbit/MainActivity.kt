package com.example.ahbit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ahbit.ui.theme.AhbitTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AhbitTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    TodoPage(
                        modifier = Modifier
                            .fillMaxHeight()
                            .padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun TodoPage(modifier: Modifier) {
    Column(
        modifier = modifier
    ) {
        TodoList(
            itemss = listOf<String>("do laundry", "clean floor", "wash dish"),
            modifier = Modifier.weight(1f)
        )
        TodoAddField()
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
fun TodoAddField() {
    var text by remember { mutableStateOf("") }

    Row(
        modifier = Modifier.
            fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            value = text,
            onValueChange = { text = it }
        )
        Button(onClick = {}) {
            Text("+")
        }
    }
}