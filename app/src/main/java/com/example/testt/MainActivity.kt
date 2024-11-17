package com.example.testt

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.net.Uri
import androidx.compose.ui.platform.LocalContext
import androidx.compose.runtime.Composable
import androidx.compose.ui.layout.ContentScale

data class UserInfo(
    val user: String,
    val pass: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val age: Int,
    val weight: Float,
    val heightFt: Int,
    val heightIn: Float,
    val answer: String,
    val usertype: String
)
data class ExAd(
    val Id: Int,
    val url: String,
)
val pileofAds = listOf(
    ExAd(
        Id = R.drawable.la,
        url = "https://www.google.com/maps/search/LA+Fitness/@32.6817147,-97.2619051,12z/data=!3m1!4b1?entry=ttu&g_ep=EgoyMDI0MTExMy4xIKXMDSoASAFQAw%3D%3D",
    ),
    ExAd(
        Id = R.drawable.shakeitup,
        url = "https://www.amazon.com/whey-protein-shake/s?k=whey+protein+shake"
    ),
    ExAd(
        Id = R.drawable.egg,
        url = "https://www.3gcardio.com/elite-runner-treadmill-is-commercial-quality/"
    ),
    ExAd(
        Id = R.drawable.joinus,
        url = "https://fitnessconnection.com/gyms/arlington"
    ),
    ExAd(
        Id = R.drawable.amazon,
        url = "https://www.amazon.com/Signature-Fitness-Neoprene-Anti-Slip-Anti-roll/dp/B0CCK5MNRN?crid=18I4UE785TRF9&dib=eyJ2IjoiMSJ9.9AEalanbV_Sjz-nKmlGYbjyz2Dbt2Ar9lFXnBTjcaUfv-7me3wKqiFStiToZxBG_3VocPUWXfGmNPhUdQMQLAyhSK2Ex9STxeA80vdrpzH_PhoJxOyeaBFQtkgWJXyTrsi6I-cSg8uvSMv0Wsw2c4i1H98iMz0jSRmwjQKYosnc9Mwgx1k0FAbBaedtvmvBlOLqQ75hTR96QQ99me_3iYko99LIozcicoSUuWkNKKsf9x5RKkqwEgmLMaBSoBzyf3ufDCb0iJdEwLx_1ESsvgX1dFiY5qtdqxDo7eMmnKT0.HoaxChMg1eDHwfP9XOh00X2ydqqV8Da49939nyLUpQM&dib_tag=se&keywords=Dumb%2Bbells&sprefix=dumb%2Bbel%2Caps%2C517&sr=8-15&th=1"
    )
)
data class InAd(
    val title: String,
    val imageResId: Int,
    val onAdClick: () -> Unit
)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TesttTheme {
                var screen by remember { mutableStateOf("login") }
                var userType by remember { mutableStateOf("G") }
                val bottomPadding =
                    WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()

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
                                        userType = "U"
                                        screen = "MainMenu"
                                    },
                                    onSignUpSuccess = {
                                        userType = "U"
                                        screen = "SignUp"
                                    },
                                    onGuestLogin = {
                                        userType = "G"
                                        screen = "MainMenu"
                                    }
                                )
                            }

                            "SignUp" -> {
                                SignUp(
                                    modifier = Modifier.fillMaxSize(),
                                    onSignUpSuccess = { screen = "MainMenu" },
                                    onBackClick = { screen = "login" },
                                )
                            }

                            "MainMenu" -> {
                                MainMenu(
                                    modifier = Modifier.fillMaxSize(),
                                    onSwipeRight = { screen = "Exercise" },
                                )
                            }

                            "Exercise" -> {
                                ExerciseScreen(
                                    modifier = Modifier.fillMaxSize(),
                                    onSwipeLeft = { screen = "MainMenu" },
                                    onSwipeRight = { screen = "Trainer" },
                                    onMainMenu = { screen = "MainMenu" },
                                    onToSettings = { screen = "User" }
                                )
                            }

                            "Trainer" -> {
                                TrainerScreen(
                                    modifier = Modifier.fillMaxSize(),
                                    onSwipeLeft = { screen = "Exercise" },
                                    onSwipeRight = { screen = "User" },
                                    onMainMenu = { screen = "MainMenu" },
                                )
                            }

                            "User" -> {
                                SettingsScreen(
                                    modifier = Modifier.fillMaxSize(),
                                    onSwipeLeft = { screen = "Trainer" },
                                    onMainMenu = { screen = "MainMenu" },
                                    onBackClick = { screen = "login" },
                                )
                            }
                        }
                    }

                    // Bottom navigation bar for the main screens
                    if (screen in listOf("MainMenu", "Exercise", "Trainer", "User")) {
                        BottomNavigationBar(
                            currentScreen = screen,
                            onScreenSelected = { newScreen -> screen = newScreen },
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
object GlobalUserInfo {
    var userInfo: UserInfo? = null
}
class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, "USERINFO.db", null, 1) {
    override fun onCreate(db: SQLiteDatabase) {
        val createTable = """
            CREATE TABLE Users (
                User TEXT PRIMARY KEY,
                Pass TEXT NOT NULL,
                First TEXT,
                Last TEXT,
                Email TEXT UNIQUE,
                Age INTEGER,
                HeightFt INTEGER,
                HeightIn REAL,
                Weight REAL,
                Answer TEXT,
                UserType TEXT
            )
        """.trimIndent()
        db.execSQL(createTable)
    }
    fun getUsersByType(userType: String): List<UserInfo> {
        val db = readableDatabase
        val query = "SELECT * FROM Users WHERE UserType = ?"
        val cursor = db.rawQuery(query, arrayOf(userType))

        val users = mutableListOf<UserInfo>()
        while (cursor.moveToNext()) {
            val user = cursor.getString(cursor.getColumnIndexOrThrow("User"))
            val pass = cursor.getString(cursor.getColumnIndexOrThrow("Pass"))
            val firstName = cursor.getString(cursor.getColumnIndexOrThrow("First"))
            val lastName = cursor.getString(cursor.getColumnIndexOrThrow("Last"))
            val email = cursor.getString(cursor.getColumnIndexOrThrow("Email"))
            val age = cursor.getInt(cursor.getColumnIndexOrThrow("Age"))
            val weight = cursor.getFloat(cursor.getColumnIndexOrThrow("Weight"))
            val heightFt = cursor.getInt(cursor.getColumnIndexOrThrow("HeightFt"))
            val heightIn = cursor.getFloat(cursor.getColumnIndexOrThrow("HeightIn"))
            val answer = cursor.getString(cursor.getColumnIndexOrThrow("Answer"))
            val usertype = cursor.getString(cursor.getColumnIndexOrThrow("UserType"))

            users.add(UserInfo(user, pass, firstName, lastName, email, age, weight, heightFt, heightIn, answer, usertype))
        }
        cursor.close()
        db.close()

        return users
    }
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS Users")
        onCreate(db)
    }
    fun addUser(
        user: String, pass: String, first: String, last: String, email: String,
        age: Int, heightFt: Int, heightIn: Float, weight: Float, answer: String,
        userType: String
    ): Boolean {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("User", user)
            put("Pass", pass)
            put("First", first)
            put("Last", last)
            put("Email", email)
            put("Age", age)
            put("HeightFt", heightFt)
            put("HeightIn", heightIn)
            put("Weight", weight)
            put("Answer", answer)
            put("UserType", userType)
        }
        val result = db.insert("Users", null, values)
        db.close()
        return result != -1L
    }
    fun validateLogin(username: String, password: String): Boolean {
        val db = readableDatabase
        val query = "SELECT * FROM Users WHERE User = ? AND Pass = ?"
        val cursor = db.rawQuery(query, arrayOf(username, password))
        val isValid = cursor.count > 0
        cursor.close()
        db.close()
        return isValid
    }
    fun getUserInfo(username: String, password: String): UserInfo? {
        val db = readableDatabase
        val query = """
        SELECT User, Pass, First, Last, Email, Age, Weight, HeightFt, HeightIn, Answer, UserType
        FROM Users WHERE User = ? AND Pass = ?
    """
        val cursor = db.rawQuery(query, arrayOf(username, password))

        return if (cursor.moveToFirst()) {
            val user = cursor.getString(cursor.getColumnIndexOrThrow("User"))
            val pass = cursor.getString(cursor.getColumnIndexOrThrow("Pass"))
            val firstName = cursor.getString(cursor.getColumnIndexOrThrow("First"))
            val lastName = cursor.getString(cursor.getColumnIndexOrThrow("Last"))
            val email = cursor.getString(cursor.getColumnIndexOrThrow("Last"))
            val age = cursor.getInt(cursor.getColumnIndexOrThrow("Age"))
            val weight = cursor.getFloat(cursor.getColumnIndexOrThrow("Weight"))
            val heightFt = cursor.getInt(cursor.getColumnIndexOrThrow("HeightFt"))
            val heightIn = cursor.getFloat(cursor.getColumnIndexOrThrow("HeightIn"))
            val answer = cursor.getString(cursor.getColumnIndexOrThrow("Answer"))
            val usertype = cursor.getString(cursor.getColumnIndexOrThrow("UserType"))
            cursor.close()
            db.close()
            UserInfo(user, pass,firstName, lastName, email, age, weight, heightFt, heightIn, answer, usertype)
        } else {
            cursor.close()
            db.close()
            null
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
    val context = LocalContext.current
    val dbHelper = DatabaseHelper(context)
    var errorMessage by remember { mutableStateOf("") }
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
                Button(onClick = {
                    if (user.isNotEmpty() && pass.isNotEmpty()) {
                        val isValid = dbHelper.validateLogin(user, pass)
                        if (isValid) {
                            val userInfo = dbHelper.getUserInfo(user, pass)
                            if (userInfo != null) {
                                GlobalUserInfo.userInfo = userInfo
                                onLoginSuccess()
                            }
                            else {
                                errorMessage = "Error retrieving user data!"
                            }
                        }
                        else {
                            errorMessage = "Invalid username or password!"
                        }
                    }
                    else {
                        errorMessage = "Please enter both username and password!"
                    }
                }) {
                    Text(text = "Log in")
                }

                if (errorMessage.isNotEmpty()) {
                    Text(
                        text = errorMessage,
                        color = Color.Red,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
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
                        //Add Recovery functions, ignore the hidden warning
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
fun AdView(ad: ExAd, modifier: Modifier = Modifier) {
    val context = LocalContext.current

    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.LightGray)
            .clickable {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(ad.url))
                context.startActivity(intent)
            }
    ) {
        Image(
            painter = painterResource(id = ad.Id),
            contentDescription = ":/",
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp),
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
fun MainMenu(
    modifier: Modifier = Modifier,
    onSwipeRight: () -> Unit,
) {
    val userInfo = GlobalUserInfo.userInfo
    BackHandler(enabled = true) { /* This does nothing (No back press here) */ }
    val randomAd = remember { pileofAds.random() }
    Column(
        modifier = modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectHorizontalDragGestures { change, dragAmount ->
                    change.consume()
                    if (dragAmount < -35) {
                        onSwipeRight()
                    }
                }
            }
    ) {
        AdView(ad = randomAd, modifier = Modifier.padding(8.dp))
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                if (userInfo == null) {
                    Text(text = "You are currently a guest!")
                    Spacer(modifier = Modifier.height(30.dp))
                }
                Text(text = "Main Menu")
                Spacer(modifier = Modifier.height(30.dp))
                Text(text = "Things like graphs and charts will go here!")
            }
        }
    }
}
fun getRandomClassAd(users: List<UserInfo>, onNavigateToSettings: () -> Unit): InAd? {
    val teachers = users.filter { it.usertype == "U" } // Change this back to "T" when trainers exist!
    if (teachers.isEmpty()) {
        return null
    }
    val randomTeacher = teachers.random()
    return InAd(
        title = "Join ${randomTeacher.firstName}'s class!",
        imageResId = R.drawable.oh,
        onAdClick = onNavigateToSettings
    )
}
@Composable
fun ExerciseAdView(ad: InAd, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.LightGray)
            .clickable { ad.onAdClick() },
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = ad.imageResId),
                contentDescription = ":/",
                modifier = Modifier
                    .size(60.dp),
                contentScale = ContentScale.Crop
            )
            Text(
                text = ad.title,
                modifier = Modifier.weight(1f)
            )
        }
    }
}
@Composable
fun ExerciseScreen(
    modifier: Modifier = Modifier,
    onSwipeLeft: () -> Unit,
    onSwipeRight: () -> Unit,
    onMainMenu: () -> Unit,
    onToSettings: () -> Unit
) {
    val context = LocalContext.current
    val dbHelper = DatabaseHelper(context)
    val users = dbHelper.getUsersByType("U")
    BackHandler { onMainMenu() }
    val ad = getRandomClassAd(users, onToSettings)
    Box(
        modifier = modifier
            .pointerInput(Unit) {
                detectHorizontalDragGestures { change, dragAmount ->
                    change.consume()
                    if (dragAmount > 35) onSwipeLeft() else if (dragAmount < -35) onSwipeRight()
                }
            }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            ad?.let { ExerciseAdView(ad = it, modifier = Modifier.padding(8.dp)) }
            Spacer(modifier = Modifier.height(30.dp))
            Button(onClick = {}) { Text(text = "Track Workout") }
            Spacer(modifier = Modifier.height(30.dp))
            Button(onClick = {}) { Text(text = "Log Workout") }
            Spacer(modifier = Modifier.height(30.dp))
            Button(onClick = {}) { Text(text = "Change Goals") }
            Spacer(modifier = Modifier.height(30.dp))
        }
        Button(
            onClick = {},
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 45.dp)
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
) {
    var screen by remember {mutableIntStateOf(1)}
    var review by remember {mutableStateOf((""))}
    var starRating by remember { mutableIntStateOf(5) } // Default to 5 stars
    val userInfo = GlobalUserInfo.userInfo
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
        if (userInfo == null) {
            Text(text = "This is for premium users only!")
        }
        else if (screen == 1 && userInfo.usertype != "G") {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Button(onClick = {}) {
                    Text(text = "View Workout Goals")
                }
            }
            Button(
                onClick = {screen = 2},
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(45.dp)
            ) {
                Text(text = "Review Trainer")
            }
        }
        else if (screen == 2 && userInfo.usertype != "G") {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    for (i in 1..5) {
                        Icon(
                            imageVector = if (i <= starRating) Icons.Filled.Star else Icons.Outlined.Star,
                            contentDescription = "Star $i",
                            tint = if (i <= starRating) Color.Yellow else Color.Gray,
                            modifier = Modifier
                                .size(55.dp)
                                .clickable { starRating = i }
                                .padding(horizontal = 4.dp)
                        )

                    }
                }
                //Text(text = "$starRating") test to see star rating
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
                    .padding(45.dp)
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
) {
    val userInfo = GlobalUserInfo.userInfo
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

                if (userInfo != null && userInfo.usertype != "G") {
                    Text(
                        text = "Age: ${userInfo.age}\tWeight: ${userInfo.weight}lbs",
                        modifier = Modifier.padding(16.dp)
                    )
                    Text(
                        text = "Height: ${userInfo.heightFt}'${userInfo.heightIn}''",
                        modifier = Modifier.padding(16.dp)
                    )
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
            Button(onClick = {
                GlobalUserInfo.userInfo = null
                onBackClick() })
            {
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
    val context = LocalContext.current
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
                            val dbHelper = DatabaseHelper(context)
                            val userType = "U"
                            val isInserted = dbHelper.addUser(
                                user = user,
                                pass = pass,
                                first = first,
                                last = last,
                                email = email,
                                age = ageValue,
                                heightFt = heightFeetValue,
                                heightIn = heightInchesValue,
                                weight = weightValue,
                                answer = secureAnswer,
                                userType = userType
                            )

                            if (isInserted) {
                                val userInfo = dbHelper.getUserInfo(user, pass)
                                if (userInfo != null) {
                                    GlobalUserInfo.userInfo = userInfo
                                    onSignUpSuccess()
                                }
                            } else {
                                errorMessage = "Could not make new user!"
                            }
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
        Log.d("TAG", "Starting Log")
        Login(onLoginSuccess = {}, onSignUpSuccess = {}, onGuestLogin = {})
    }
}
