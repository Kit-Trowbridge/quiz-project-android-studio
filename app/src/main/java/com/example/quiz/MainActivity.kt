package com.example.quiz

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.quiz.ui.theme.QuizTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            QuizTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Quiz(
                        question = "Which tribe did Boudica belong to?",
                        answer = "The Iceni",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Quiz(
    question: String, // should these all go inside the function?
    answer: String,
    modifier: Modifier = Modifier

) {
    var questionInput by remember { mutableStateOf("")}

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Text(
            text = question,
            modifier = modifier
                .align(Alignment.CenterHorizontally)
        )
        EditAnswerField( value = questionInput, onValueChange = { questionInput = it})
        Button(
            onClick = {}
        ) {
            Text(
                text = stringResource(R.string.button_label)
            )
        }
    }


}

@Composable
fun EditAnswerField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    TextField(
        label = { Text(stringResource(R.string.input_label)) },
        value = value,
        onValueChange = onValueChange,
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun QuizPreview() {
    QuizTheme {
        Quiz("Android?", answer = "No")
    }
}