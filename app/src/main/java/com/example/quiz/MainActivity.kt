package com.example.quiz

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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
import androidx.compose.ui.unit.dp
import com.example.quiz.ui.theme.QuizTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            QuizTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(), // is this necessary? without it, text not vertically aligned
                    color = MaterialTheme.colorScheme.background
                ) {
                    Quiz(
                        question = "Which tribe did Boudica belong to?",
                        answer = "The Iceni",
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
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        var answerInput by remember { mutableStateOf("")}
        var message: String // need state here too?

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
        ) {
            Text(
                text = question,
                modifier = modifier
                    .align(Alignment.CenterHorizontally)
            )
            Spacer(
                Modifier.height(25.dp)
            )
            EditAnswerField( value = answerInput, onValueChange = { answerInput = it})
            Spacer(
                Modifier.height(25.dp)
            )
            Button(
                onClick = {
                    // take / save? current answerInput value
                    val userAnswer: String = answerInput
                    // reset to 0
                    answerInput = ""
                    // check if it's equal to answer (with regex?)
                    // Display a different message if it matches or not
                    message = if (userAnswer === answer) "Correct!" else "Try again"
                },
                modifier = modifier
            ) {
                Text(
                    text = stringResource(R.string.button_label)
                )
            }
            Spacer(
                Modifier.height(25.dp)
            )
            Text(
                text = message
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
        singleLine = true,
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