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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
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
                    App()
                }
            }
        }
    }
}


@Composable
fun App() {

    val navController = rememberNavController()
    var correctAnswers by remember { mutableIntStateOf(0) }

    NavHost(
        navController = navController, startDestination = "questionOne"
    ){
        composable(route = "questionOne") {
            QuestionScreen(
                question = "Which tribe did Boudica belong to?",
                answer = "The Iceni",
                onNextScreen = {navController.navigate("questionTwo")},
                correctAnswers = correctAnswers,
                onCorrectAnswer = { correctAnswers += 1 }
            )
        }
        composable(route = "questionTwo") {
            QuestionScreen(
                question = "Who was Henry the VIII's last wife?",
                answer = "Katherine Parr",
                onNextScreen = {navController.navigate("finalScore")}, // do you always need onNextScreen in a route? No - it's only bc your button needs it
                correctAnswers = correctAnswers,
                onCorrectAnswer = { correctAnswers += 1 }
            )
        }
        composable(route = "finalScore") {
            FinalScoreScreen(
                correctAnswers = correctAnswers
            )
        }
    }
}


@Composable
fun QuestionScreen(
    onNextScreen: () -> Unit,
    question: String, // should these all go inside the function?
    answer: String,
    correctAnswers: Int,
    onCorrectAnswer: () -> Unit,
    modifier: Modifier = Modifier

) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        var answerInput by remember { mutableStateOf("")}
        var messageProperties by remember { mutableStateOf(Pair("", Color.Black )) } // setting up pair to track & update when button clicked
        val (messageText, messageColor) = messageProperties // destructuring to use separately in Text()

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
                    val userAnswer: String = answerInput.trim()
                    // reset to 0
                    answerInput = ""

                    val answerRegex = Regex(answer.trim(), RegexOption.IGNORE_CASE)
                    val isCorrect = answerRegex.containsMatchIn(userAnswer)

                    messageProperties = if (isCorrect) Pair("Correct!", Color.Green) else Pair("Try again", Color.Red)
                    if (isCorrect) onCorrectAnswer()
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
                text = messageText,
                color = messageColor
            )
            Spacer(
                Modifier.height(25.dp)
            )
            Button(
                onClick = onNextScreen,
                modifier = modifier
                    .align(alignment = Alignment.End)
            ) {
                Text("Next")
            } // should update so that you go automatically to next screen with correct answer
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
fun QuestionScreenPreview() {
    QuizTheme {
        QuestionScreen(question = "Android?", answer = "No", onNextScreen = {}, correctAnswers = 0, onCorrectAnswer = {}) // is this right?
    }
}

@Composable
fun FinalScoreScreen(
    correctAnswers: Int,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center
    ) {
        Text(
            text="Your final score is: ${correctAnswers}/2", // change from hardcoded once refactored
            modifier = modifier
                .align(alignment = Alignment.Center)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun FinalScoreScreenPreview() {
    FinalScoreScreen(
        correctAnswers = 0 // ?
    )
}
