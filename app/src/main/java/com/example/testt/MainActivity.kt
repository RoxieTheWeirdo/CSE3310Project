package com.example.testt

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color
import com.example.testt.ui.theme.TesttTheme
import androidx.compose.foundation.text.ClickableText
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.text.input.KeyboardType

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TesttTheme {
                var screen by remember { mutableStateOf("login") }
                var userType by remember { mutableStateOf('G') }
                val bottomPadding = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()
                Box(modifier = Modifier.fillMaxSize()) {
                    Column(
                        modifier = Modifier
                            .fillMaxHeight()
                            .padding(bottom = bottomPadding)
                    ) {
                        when (screen) {
                            "login" -> {
                                Login(
                                    modifier = Modifier.fillMaxSize(),
                                    onLoginSuccess = {
                                        userType = 'U'
                                        screen = "MainMenu"
                                    },
                                    onSignUpSuccess = {
                                        userType = 'U'
                                        screen = "SignUp"
                                                      },
                                    onGuestLogin = {
                                        userType = 'G'
                                        screen = "MainMenu"
                                    }
                                )
                            }
                            "SignUp" -> {
                                SignUp(
                                    modifier = Modifier.fillMaxSize(),
                                    onSignUpSuccess = { screen = "MainMenu" },
                                    onBackClick = { screen = "login"},
                                )
                            }
                            "MainMenu" -> {
                                MainMenu(
                                    modifier = Modifier.fillMaxSize(),
                                    onSwipeRight = { screen = "Exercise" },
                                    userType = userType
                                )
                            }
                            "Exercise" -> {
                                ExerciseScreen(
                                    modifier = Modifier.fillMaxSize(),
                                    onSwipeLeft = { screen = "MainMenu" },
                                    onSwipeRight = { screen = "Trainer" },
                                    onMainMenu = { screen = "MainMenu"}
                                )
                            }
                            "Trainer" -> {
                                TrainerScreen(
                                    modifier = Modifier.fillMaxSize(),
                                    onSwipeLeft = { screen = "Exercise" },
                                    onSwipeRight = { screen = "User" },
                                    onMainMenu = { screen = "MainMenu"},
                                    userType = userType
                                )
                            }
                            "User" -> {
                                SettingsScreen(
                                    modifier = Modifier.fillMaxSize(),
                                    onSwipeLeft = { screen = "Trainer" },
                                    onMainMenu = { screen = "MainMenu"},
                                    onBackClick = { screen = "login"},
                                    userType = userType
                                )
                            }
                        }
                    }
                    if (screen in listOf("MainMenu", "Exercise", "Trainer", "User")) {
                        BottomNavigationBar(
                            currentScreen = screen,
                            onScreenSelected = { newScreen ->
                                screen = newScreen
                            },
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .padding(bottom = bottomPadding)
                                .height(50.dp)
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun Login(
    modifier: Modifier = Modifier,
    onLoginSuccess: () -> Unit,
    onSignUpSuccess: () -> Unit,
    onGuestLogin: () -> Unit
) {
    var user by remember { mutableStateOf("") }
    var pass by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var secureAnswer by remember { mutableStateOf("") }
    var screen by remember { mutableIntStateOf(1) }
    BackHandler (enabled = screen == 2) {
        screen = 1
    }
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        if (screen == 1) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.duck),
                    contentDescription = ":/",      //just required :/
                    modifier = Modifier.size(300.dp),
                )
                Spacer(modifier = Modifier.height(30.dp))
                Text(text = "Please enter Username and Password!")
                Spacer(modifier = Modifier.height(10.dp))
                TextField(
                    value = user,
                    onValueChange = { user = it },
                    label = { Text("Username") },
                    modifier = Modifier.fillMaxWidth(0.8f),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Next
                    )
                )
                Spacer(modifier = Modifier.height(16.dp))
                TextField(
                    value = pass,
                    onValueChange = { pass = it },
                    label = { Text("Password") },
                    modifier = Modifier.fillMaxWidth(0.8f),
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Done
                    )
                )
                val forgot = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            color = Color.Blue,
                            textDecoration = TextDecoration.Underline,
                            fontSize = 16.sp
                        )
                    ) {
                        pushStringAnnotation(tag = "FORGOT", annotation = "")
                        append("Forgot Password?")
                        pop()
                    }
                }
                ClickableText(
                    modifier = Modifier.padding(10.dp),
                    text = forgot,
                    onClick = { offset ->
                        forgot.getStringAnnotations(tag ="FORGOT", start = offset, end = offset)
                            .firstOrNull()?.let { screen = 2 }
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = { onLoginSuccess() }) {
                    Text(text = "Log in")
                }
                Spacer(modifier = Modifier.height(16.dp))
                val annotatedText = buildAnnotatedString {
                    append("Or continue as ")
                    withStyle(
                        style = SpanStyle(
                            color = Color.Blue,
                            textDecoration = TextDecoration.Underline,
                            fontSize = 16.sp
                        )
                    ) {
                        pushStringAnnotation(tag = "G", annotation = "")
                        append("Guest")
                        pop()
                    }
                }

                ClickableText(
                    text = annotatedText,
                    modifier = Modifier.padding(10.dp),
                    onClick = { offset ->
                        annotatedText.getStringAnnotations(tag = "G", start = offset, end = offset)
                            .firstOrNull()?.let { onGuestLogin() }
                    }
                )
                val newOne = buildAnnotatedString {
                    append("Don't have an account? ")
                    withStyle(
                        style = SpanStyle(
                            color = Color.Blue,
                            textDecoration = TextDecoration.Underline,
                            fontSize = 16.sp
                        )
                    ) {
                        pushStringAnnotation(tag = "NEW", annotation = "")
                        append("Click here to make one!")
                        pop()
                    }
                }

                ClickableText(
                    text = newOne,
                    modifier = Modifier.padding(10.dp),
                    onClick = { offset ->
                        newOne.getStringAnnotations(tag = "NEW", start = offset, end = offset)
                            .firstOrNull()?.let { onSignUpSuccess() }
                    }
                )
            }
        }
        else if (screen == 2) {
            var errorMessage by remember { mutableStateOf("") }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
            Text("Please enter the following credentials")
            Spacer(modifier = Modifier.height(10.dp))
            TextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Next
                ),
                modifier = Modifier.fillMaxWidth(0.8f)
            )
            Spacer(modifier = Modifier.height(16.dp))
            TextField(
                value = secureAnswer,
                onValueChange = { secureAnswer = it },
                label = { Text("Answer to Security Question") },
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                ),
                modifier = Modifier.fillMaxWidth(0.8f),
                visualTransformation = PasswordVisualTransformation()
            )
                if (errorMessage.isNotEmpty()) {
                    Text(
                        text = errorMessage,
                        color = Color.Red,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                }
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                when {
                    email.isEmpty() -> {
                        errorMessage = "Please enter your account's email!"
                    }
                    secureAnswer.isEmpty() -> {
                        errorMessage = "Please answer the security question!"
                    }
                    else -> {
                        errorMessage = ""
                        //Add Recovery function, ignore the hidden warning
                    }
            }
            }) {
                Text(text = "Recover Account")
            }
            }
        }
    }
}

@Composable
fun MainMenu(
    modifier: Modifier = Modifier,
    onSwipeRight: () -> Unit,
    userType: Char
) {
    BackHandler(enabled = true) {/*This does nothing (No backwards press here)*/}
    Box(
        modifier = modifier
            .pointerInput(Unit) {
                detectHorizontalDragGestures { change, dragAmount ->
                    change.consume()
                    if (dragAmount < -50) {
                        onSwipeRight()
                    }
                }
            },
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            if (userType == 'G') {
                Text(text = "You are currently a guest!")
                Spacer(modifier = Modifier.height(30.dp))
            }
            Text(text = "Main Menu")
            Spacer(modifier = Modifier.height(30.dp))
            Text(text = "Things like graphs an charts will go here!")
        }
    }
}

@Composable
fun ExerciseScreen(
    modifier: Modifier = Modifier,
    onSwipeLeft: () -> Unit,
    onSwipeRight: () -> Unit,
    onMainMenu: () -> Unit
) {
    BackHandler {
        onMainMenu()
    }
    Box(
        modifier = modifier
            .pointerInput(Unit) {
                detectHorizontalDragGestures { change, dragAmount ->
                    change.consume()
                    if (dragAmount > 50) {
                        onSwipeLeft()
                    } else if (dragAmount < -50) {
                        onSwipeRight()
                    }
                }
            }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(30.dp))

            Button(onClick = {}) {
                Text(text = "Track Workout")
            }
            Spacer(modifier = Modifier.height(30.dp))

            Button(onClick = {}) {
                Text(text = "Log Workout")
            }
            Spacer(modifier = Modifier.height(30.dp))

            Button(onClick = {}) {
                Text(text = "Change Goals")
            }
            Spacer(modifier = Modifier.height(30.dp))
        }
        Button(
            onClick = {},
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 20.dp)
        ) {
            Text(text = "Special Video")
        }
    }
}


@Composable
fun TrainerScreen(
    modifier: Modifier = Modifier,
    onSwipeLeft: () -> Unit,
    onMainMenu: () -> Unit,
    onSwipeRight: () -> Unit,
    userType: Char,
) {
    var screen by remember {mutableIntStateOf(1)}
    var review by remember {mutableStateOf((""))}
    BackHandler(enabled = screen == 2) {
        screen = 1
    }
    BackHandler(enabled = screen == 1) {
        onMainMenu()
    }
    Box(
        modifier = modifier
            .pointerInput(Unit) {
                detectHorizontalDragGestures { change, dragAmount ->
                    change.consume()
                    if (dragAmount > 50) {
                        onSwipeLeft()
                    } else if (dragAmount < -50) {
                        onSwipeRight()
                    }
                }
            },
        contentAlignment = Alignment.Center
    ) {         ///Need to update the below once we get paid users
        if (userType == 'G') {
            Text(text = "This is for users only!")
        }
        if (screen == 1 && userType != 'G') {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Button(onClick = {}) {
                    Text(text = "View Workout Goals")
                }
            }
            Button(
                onClick = {screen = 2},
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(20.dp)
            ) {
                Text(text = "Review Trainer")
            }
        }
        else if (screen == 2 && userType != 'G') {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "Write what you think about your trainer\nYour trainer will not know it's from you\n")
                TextField(
                    value = review,
                    onValueChange = { review = it },
                    label = { Text("What are your thoughts?") },
                    modifier = Modifier.fillMaxWidth(0.8f)
                )
            }
            Button(onClick = {},
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(20.dp)
            ) {
                Text(text = "Submit Review")
            }
        }
    }
}

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    onSwipeLeft: () -> Unit,
    onMainMenu: () -> Unit,
    onBackClick: () -> Unit,
    userType: Char
) {
    var screen by remember { mutableIntStateOf(1)}
    BackHandler (enabled = screen == 1){
        onMainMenu()
    }
    BackHandler (enabled = screen == 2){
        screen = 1
    }
    Box(
        modifier = modifier
            .pointerInput(Unit) {
                detectHorizontalDragGestures { change, dragAmount ->
                    change.consume()
                    if (dragAmount > 50) {
                        onSwipeLeft()
                    }
                }
            },
        contentAlignment = Alignment.Center
    ) {
        if (screen == 1) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                if (userType != 'G') {
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(onClick = {}) {
                        Text(text = "Upgrade to premium")
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(onClick = {}) {
                        Text(text = "External Devices")
                    }
                }
                else {
                    Text(text = "You are currently a guest\nand do not have access to all features!")
                }
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = {screen = 2}) {
                    Text(text = "Account Settings")
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
        if (screen == 2) {
            Button(onClick = {onBackClick() }) {
                Text(text = "Log out")
            }
        }
    }
}
@Composable
fun BottomNavigationBar(
    currentScreen: String,
    onScreenSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val screens = listOf("MainMenu", "Exercise", "Trainer", "User")
    val icons = listOf(
        R.drawable.mainbottom,
        R.drawable.exercisebottom,
        R.drawable.trainerbottom,
        R.drawable.settingsbottom
    )
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(Color.LightGray),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        screens.forEachIndexed { index, screen ->
            val isSelected = currentScreen == screen
            val backgroundColor = if (isSelected) Color.Gray else Color.Transparent

            Column(
                modifier = Modifier
                    .clickable { onScreenSelected(screen) }
                    .background(backgroundColor)
                    .padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = icons[index]),
                    contentDescription = screen,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = screen.replace("MainMenu", "Main Menu").replace("User", "Settings"),
                    color = if (isSelected) Color.White else Color.Black
                )
            }
        }
    }
}
@Composable
fun SignUp(
    modifier: Modifier = Modifier,
    onSignUpSuccess: () -> Unit,
    onBackClick: () -> Unit
) {
    var user by remember { mutableStateOf("") }
    var first by remember { mutableStateOf("") }
    var last by remember { mutableStateOf("") }
    var pass by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var heightFeet by remember { mutableStateOf("") }
    var heightInches by remember { mutableStateOf("") }
    var weight by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var secureAnswer by remember { mutableStateOf("") }
    var screen by remember { mutableIntStateOf(1) }

    var errorMessage by remember { mutableStateOf("") }
    BackHandler (enabled = screen == 2) {
        screen = 1
    }
    BackHandler (enabled = screen == 1) {
        onBackClick()
    }
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (screen == 1) {
                Text("Please enter the following credentials")
                Spacer(modifier = Modifier.height(10.dp))
                TextField(
                    value = user,
                    onValueChange = { user = it },
                    label = { Text("Username") },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Next
                    ),
                    modifier = Modifier.fillMaxWidth(0.8f)
                )
                Spacer(modifier = Modifier.height(16.dp))
                TextField(
                    value = pass,
                    onValueChange = { pass = it },
                    label = { Text("Password") },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Next
                    ),
                    modifier = Modifier.fillMaxWidth(0.8f),
                    visualTransformation = PasswordVisualTransformation()
                )
                Spacer(modifier = Modifier.height(16.dp))
                TextField(
                    value = first,
                    onValueChange = { first = it },
                    label = { Text("First Name") },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Next
                    ),
                    modifier = Modifier.fillMaxWidth(0.8f)
                )
                Spacer(modifier = Modifier.height(16.dp))
                TextField(
                    value = last,
                    onValueChange = { last = it },
                    label = { Text("Last Name") },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Next
                    ),
                    modifier = Modifier.fillMaxWidth(0.8f)
                )
                Spacer(modifier = Modifier.height(16.dp))
                TextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Done
                    ),
                    modifier = Modifier.fillMaxWidth(0.8f),
                    visualTransformation = PasswordVisualTransformation()
                )
                Spacer(modifier = Modifier.height(30.dp))
                if (errorMessage.isNotEmpty()) {
                    Text(
                        text = errorMessage,
                        color = Color.Red,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                }
                Button(onClick = {
                    if (user.isEmpty()) {
                        errorMessage = "Please enter a username!"
                    }
                    else if (user.length < 6) {
                        errorMessage = "Username must be at least 6 characters long!"
                    }
                    else if (pass.isEmpty()) {
                        errorMessage = "Please enter a password!"
                    }
                    else if (pass.length < 8 || !pass.any { it.isLetterOrDigit().not() }) {
                        errorMessage = "Password must be 8 characters and contain at least 1 special character!"
                    }
                    else if (first.isEmpty()) {
                        errorMessage = "Please enter a first name!"
                    }
                    else if (last.isEmpty()) {
                        errorMessage = "Please enter a last name!"
                    }
                    else if (email.isEmpty()) {
                        errorMessage = "Please enter an email!"
                    }
                    else {
                        errorMessage = ""
                        screen = 2
                    }
                }) {
                    Text(text = "Next")
                }
            }
            else {
                Text("Please enter your Age, Height, and Weight!")
                Spacer(modifier = Modifier.height(10.dp))
                TextField(
                    value = age,
                    onValueChange = { age = it.filter { char -> char.isDigit() } },
                    label = { Text("Age") },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    ),
                    modifier = Modifier.fillMaxWidth(0.8f)
                )
                Spacer(modifier = Modifier.height(16.dp))
                TextField(
                    value = heightFeet,
                    onValueChange = { heightFeet = it.filter { char -> char.isDigit() } },
                    label = { Text("Height (Feet)") },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    ),
                    modifier = Modifier.fillMaxWidth(0.8f)
                )
                Spacer(modifier = Modifier.height(16.dp))
                TextField(
                    value = heightInches,
                    onValueChange = { newValue ->
                        if (newValue.all { it.isDigit() || it == '.' }) {
                            heightInches = newValue
                        }
                    },
                    label = { Text("Height (Inches)") },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    ),
                    modifier = Modifier.fillMaxWidth(0.8f)
                )
                Spacer(modifier = Modifier.height(16.dp))
                TextField(
                    value = weight,
                    onValueChange = { newValue ->
                        if (newValue.all { it.isDigit() || it == '.' }) {
                            weight = newValue
                        }
                    },
                    label = { Text("Weight") },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    ),
                    modifier = Modifier.fillMaxWidth(0.8f)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "What city were you born in?")
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = secureAnswer,
                    onValueChange = { secureAnswer = it },
                    label = { Text("Answer to Security Question") },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Done
                    ),
                    modifier = Modifier.fillMaxWidth(0.8f),
                    visualTransformation = PasswordVisualTransformation()
                )
                Spacer(modifier = Modifier.height(30.dp))
                if (errorMessage.isNotEmpty()) {
                    Text(
                        text = errorMessage,
                        color = Color.Red,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                }
                Button(onClick = {
                    val ageValue = age.toIntOrNull()
                    val heightFeetValue = heightFeet.toIntOrNull()
                    val heightInchesValue = heightInches.toFloatOrNull()
                    val weightValue = weight.toFloatOrNull()
                    errorMessage = ""
                    when {
                        ageValue == null || ageValue < 1 || ageValue > 120 -> {
                            errorMessage = "This age is invalid!"
                        }
                        heightFeetValue == null || heightFeetValue < 1 || heightFeetValue > 12 -> {
                            errorMessage = "The amount of feet is invalid!"
                        }
                        heightInchesValue == null || heightInchesValue < 0 || heightInchesValue >= 12 -> {
                            errorMessage = "The amount of inches is invalid!"
                        }
                        weightValue == null || weightValue < 20 || weightValue > 700 -> {
                            errorMessage = "This weight is invalid!"
                        }
                        secureAnswer.isEmpty() -> {
                            errorMessage = "Please answer the security question!"
                        }
                        else -> {
                            errorMessage = ""
                            onSignUpSuccess()
                        }
                    }
                }) {
                    Text(text = "Sign Up")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun App() {
    TesttTheme {
        Login(onLoginSuccess = {}, onSignUpSuccess = {}, onGuestLogin = {})
    }
}
