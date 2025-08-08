package com.user.donuthole

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.user.donuthole.ui.theme.DonutHoleTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DonutHoleTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MyComponentWithButton(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

/*
MyComponent composable will run everytime counter changes. Why??
Because Column is a inline function.
So smaller lambda scope will become MyComponent lambda
so it basically means this
@Composable
fun MyComponent(modifier: Modifier) {
    var counter by remember { mutableIntStateOf(0) }
    LogCompositions("Anas", "MyComposable function")
    CustomText(
        text = "Counter: $counter",
        modifier = modifier.clickable {
            counter++
        }
    )
}
*/

@Composable
fun MyComponent(modifier: Modifier) {
    var counter by remember { mutableIntStateOf(0) }
    LogCompositions("Anas", "MyComposable function")

    Column {
        LogCompositions("Anas", "MyComposable Column function")
        CustomText(
            text = "Counter: $counter",
            modifier = modifier.clickable {
                counter++
            }
        )
    }
}

/*
MyComponent composable will run for first time only
Because in this case it will be able to find smaller scope which is Button compose
*/

@Composable
fun MyComponentWithButton(modifier: Modifier) {
    var counter by remember { mutableIntStateOf(0) }
    LogCompositions("Anas", "MyComposable function")

    Button(onClick = { counter++ }) {
        LogCompositions("Anas", "MyComposable Button function")
        CustomText(
            text = "Counter: $counter",
            modifier = modifier.clickable {
                counter++
            }
        )
    }
}

@Composable
fun CustomText(
    text: String,
    modifier: Modifier,
) {
    LogCompositions("Anas", "CustomText function")
    Text(
        text = text,
        modifier = modifier.padding(32.dp),
        style = TextStyle(
            fontSize = 20.sp,
            textDecoration = TextDecoration.Underline,
            fontFamily = FontFamily.Monospace
        )
    )
}

class Ref(var value: Int)

// Note the inline function below which ensures that this function is essentially
// copied at the call site to ensure that its logging only recompositions from the
// original call site.
@Composable
inline fun LogCompositions(tag: String, msg: String) {
    val ref = remember { Ref(0) }
    SideEffect { ref.value++ }
    Log.d(tag, "Compositions: $msg ${ref.value}")
}