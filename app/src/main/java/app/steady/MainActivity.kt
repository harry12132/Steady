package app.steady

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private data class Lesson(val id: String, val title: String, val description: String, val category: String, val steps: List<String>)

private val lessons = listOf(
    Lesson("phone-basics", "Meet your phone", "Learn the buttons, screen and basic gestures.", "Getting started",
        listOf("Find the power button.", "Tap the screen once.", "Swipe up from the bottom.", "Use the back gesture or button to return.")),
    Lesson("calls", "Make a phone call", "Call someone from your contacts.", "Keeping in touch",
        listOf("Open the Phone app.", "Tap Contacts.", "Choose someone you know.", "Tap the call button.")),
    Lesson("messages", "Send a message", "Write and send a simple text.", "Keeping in touch",
        listOf("Open Messages.", "Choose a person.", "Tap the message box.", "Type a short message and tap Send.")),
    Lesson("photo", "Take a photo", "Take a photo and find it afterwards.", "Photos",
        listOf("Open Camera.", "Point the camera at something.", "Tap the shutter button.", "Open Photos and find your new picture.")),
    Lesson("share-photo", "Send a photo", "Share a photo with someone you know.", "Photos",
        listOf("Open Photos.", "Choose a picture.", "Tap Share.", "Choose a contact or messaging app.")),
    Lesson("maps", "Find somewhere with Maps", "Search for a place and see where it is.", "Everyday life",
        listOf("Open Maps.", "Tap the search box.", "Type the name of a place.", "Choose the result you recognise.")),
    Lesson("alarm", "Set an alarm", "Set an alarm for tomorrow morning.", "Everyday life",
        listOf("Open Clock.", "Choose Alarm.", "Tap Add.", "Choose a time and save it.")),
    Lesson("scam-text", "Spot a suspicious message", "Learn what to do when a message feels wrong.", "Staying safe",
        listOf("Pause before tapping.", "Check who sent the message.", "Look for pressure, threats or unexpected links.", "If you are unsure, close it and ask someone you trust.")),
    Lesson("fake-popup", "A popup says you have a virus", "Learn what to do when a frightening popup appears.", "Staying safe",
        listOf("Do not call the number in the popup.", "Do not install anything it recommends.", "Close the page or app if you can.", "If it keeps happening, ask a trusted person for help.")),
    Lesson("verification-code", "Protect your verification code", "Understand why a one-time code should stay private.", "Staying safe",
        listOf("A verification code is private.", "Never read a code to a stranger who calls you.", "If someone asks for it unexpectedly, stop.", "Contact the organisation using a number you already trust."))
)

private enum class Screen { Home, Learn, Safe, Help }

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { SteadyApp() }
    }
}

@Composable
private fun SteadyApp() {
    var screen by remember { mutableStateOf(Screen.Home) }
    var selectedLesson by remember { mutableStateOf<Lesson?>(null) }
    var completed by remember { mutableStateOf(setOf<String>()) }

    MaterialTheme(colorScheme = lightColorScheme(
        background = Color(248, 248, 246),
        surface = Color.White,
        primary = Color(28, 83, 67),
        onPrimary = Color.White,
        onBackground = Color(28, 31, 29)
    )) {
        Surface(Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            if (selectedLesson != null) {
                LessonScreen(selectedLesson!!, selectedLesson!!.id in completed, { selectedLesson = null }) {
                    completed = completed + selectedLesson!!.id
                    selectedLesson = null
                }
            } else {
                Scaffold(bottomBar = {
                    NavigationBar {
                        NavigationBarItem(screen == Screen.Home, { screen = Screen.Home }, icon = { Text("⌂", fontSize = 24.sp) }, label = { Text("Home") })
                        NavigationBarItem(screen == Screen.Learn, { screen = Screen.Learn }, icon = { Text("✓", fontSize = 22.sp) }, label = { Text("Learn") })
                        NavigationBarItem(screen == Screen.Safe, { screen = Screen.Safe }, icon = { Text("!", fontSize = 22.sp) }, label = { Text("Safe") })
                        NavigationBarItem(screen == Screen.Help, { screen = Screen.Help }, icon = { Text("?", fontSize = 22.sp) }, label = { Text("Help") })
                    }
                }) { padding ->
                    when (screen) {
                        Screen.Home -> HomeScreen(padding, completed.size) { selectedLesson = it }
                        Screen.Learn -> LearnScreen(padding, completed.size) { selectedLesson = it }
                        Screen.Safe -> SafeScreen(padding) { selectedLesson = it }
                        Screen.Help -> HelpScreen(padding)
                    }
                }
            }
        }
    }
}

@Composable
private fun HomeScreen(padding: PaddingValues, completedCount: Int, open: (Lesson) -> Unit) {
    LazyColumn(contentPadding = PaddingValues(24.dp, 32.dp), modifier = Modifier.padding(padding)) {
        item {
            Text("Steady", fontSize = 34.sp, fontWeight = FontWeight.SemiBold)
            Spacer(Modifier.height(8.dp))
            Text("Learn your phone. Stay safe. Feel confident.", fontSize = 20.sp, lineHeight = 28.sp)
            Spacer(Modifier.height(28.dp))
            Card(colors = CardDefaults.cardColors(containerColor = Color(232, 239, 235)), shape = RoundedCornerShape(22.dp)) {
                Column(Modifier.padding(24.dp)) {
                    Text("Keep learning", fontSize = 22.sp, fontWeight = FontWeight.SemiBold)
                    Spacer(Modifier.height(8.dp))
                    Text("${completedCount} lessons completed", fontSize = 17.sp)
                    Spacer(Modifier.height(16.dp))
                    Button(onClick = { open(lessons.first()) }, modifier = Modifier.fillMaxWidth().height(54.dp)) {
                        Text("Start a lesson", fontSize = 18.sp)
                    }
                }
            }
            Spacer(Modifier.height(26.dp))
            Text("Start with something useful", fontSize = 24.sp, fontWeight = FontWeight.SemiBold)
            Spacer(Modifier.height(12.dp))
        }
        items(lessons.take(5)) { lesson ->
            LessonRow(lesson, false, open)
            Spacer(Modifier.height(10.dp))
        }
    }
}

@Composable
private fun LearnScreen(padding: PaddingValues, completedCount: Int, open: (Lesson) -> Unit) {
    LazyColumn(contentPadding = PaddingValues(24.dp, 28.dp), modifier = Modifier.padding(padding)) {
        item {
            Text("Learn", fontSize = 34.sp, fontWeight = FontWeight.SemiBold)
            Spacer(Modifier.height(6.dp))
            Text("${completedCount} lessons completed", fontSize = 18.sp)
            Spacer(Modifier.height(22.dp))
        }
        lessons.groupBy { it.category }.forEach { (category, group) ->
            item { Text(category, fontSize = 22.sp, fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(vertical = 8.dp)) }
            items(group) { lesson ->
                LessonRow(lesson, false, open)
                Spacer(Modifier.height(10.dp))
            }
        }
    }
}

@Composable
private fun LessonRow(lesson: Lesson, done: Boolean, open: (Lesson) -> Unit) {
    Card(modifier = Modifier.fillMaxWidth().clickable { open(lesson) }, shape = RoundedCornerShape(18.dp), colors = CardDefaults.cardColors(containerColor = Color.White)) {
        Row(Modifier.padding(20.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(Modifier.size(48.dp).background(Color(232, 239, 235), RoundedCornerShape(14.dp)), contentAlignment = Alignment.Center) {
                Text(if (done) "✓" else "→", fontSize = 22.sp, fontWeight = FontWeight.Bold)
            }
            Spacer(Modifier.size(16.dp))
            Column(Modifier.weight(1f)) {
                Text(lesson.title, fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
                Spacer(Modifier.height(4.dp))
                Text(lesson.description, fontSize = 16.sp, lineHeight = 22.sp)
            }
        }
    }
}

@Composable
private fun SafeScreen(padding: PaddingValues, open: (Lesson) -> Unit) {
    LazyColumn(contentPadding = PaddingValues(24.dp, 28.dp), modifier = Modifier.padding(padding)) {
        item {
            Text("Stay safe", fontSize = 34.sp, fontWeight = FontWeight.SemiBold)
            Spacer(Modifier.height(8.dp))
            Text("If something feels strange, don't rush. Steady can help you work out what to do.", fontSize = 19.sp, lineHeight = 27.sp)
            Spacer(Modifier.height(22.dp))
            Card(colors = CardDefaults.cardColors(containerColor = Color(246, 235, 220)), shape = RoundedCornerShape(20.dp)) {
                Column(Modifier.padding(22.dp)) {
                    Text("The golden rule", fontSize = 22.sp, fontWeight = FontWeight.SemiBold)
                    Spacer(Modifier.height(8.dp))
                    Text("Stop before you click, pay, install or share information. If you are unsure, ask someone you trust.", fontSize = 18.sp, lineHeight = 26.sp)
                }
            }
            Spacer(Modifier.height(24.dp))
        }
        items(lessons.filter { it.category == "Staying safe" }) { lesson ->
            LessonRow(lesson, false, open)
            Spacer(Modifier.height(10.dp))
        }
    }
}

@Composable
private fun HelpScreen(padding: PaddingValues) {
    var selected by remember { mutableStateOf<String?>(null) }

    Column(Modifier.fillMaxSize().padding(padding).padding(24.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
        Text("Something isn't right?", fontSize = 32.sp, fontWeight = FontWeight.SemiBold)
        Text("You don't need to know the technical answer. Start with what happened.", fontSize = 19.sp, lineHeight = 27.sp)
        HelpButton("My phone is showing strange ads") { selected = "Strange ads" }
        HelpButton("I received a strange message") { selected = "Strange message" }
        HelpButton("Someone called me") { selected = "Unexpected call" }
        HelpButton("I installed something and now my phone is strange") { selected = "Strange app" }
        HelpButton("I clicked something by mistake") { selected = "I clicked something" }
        HelpButton("I don't know what to do") { selected = "I don't know" }
    }

    if (selected != null) {
        AlertDialog(
            onDismissRequest = { selected = null },
            title = { Text(selected!!) },
            text = {
                Text(
                    "Stop before you click, pay, install anything or share information. " +
                        "Close the message or popup if you can. If you are still unsure, ask someone you trust."
                )
            },
            confirmButton = {
                TextButton(onClick = { selected = null }) { Text("Got it") }
            }
        )
    }
}

@Composable
private fun HelpButton(label: String, onClick: () -> Unit) {
    OutlinedButton(onClick = onClick, modifier = Modifier.fillMaxWidth().height(58.dp)) {
        Text(label, fontSize = 18.sp)
    }
}

@Composable
private fun LessonScreen(lesson: Lesson, completed: Boolean, onBack: () -> Unit, onComplete: () -> Unit) {
    Column(Modifier.fillMaxSize().padding(24.dp)) {
        TextButton(onClick = onBack) { Text("Back", fontSize = 17.sp) }
        Spacer(Modifier.height(12.dp))
        Text(lesson.title, fontSize = 34.sp, fontWeight = FontWeight.SemiBold)
        Spacer(Modifier.height(10.dp))
        Text(lesson.description, fontSize = 20.sp, lineHeight = 28.sp)
        Spacer(Modifier.height(28.dp))
        Text("Let's do it together", fontSize = 22.sp, fontWeight = FontWeight.SemiBold)
        Spacer(Modifier.height(12.dp))
        lesson.steps.forEachIndexed { index, step ->
            Row(Modifier.padding(vertical = 9.dp), verticalAlignment = Alignment.Top) {
                Text("${index + 1}", fontSize = 18.sp, fontWeight = FontWeight.Bold, modifier = Modifier.size(30.dp))
                Text(step, fontSize = 19.sp, lineHeight = 27.sp, modifier = Modifier.weight(1f))
            }
        }
        Spacer(Modifier.weight(1f))
        Button(onClick = onComplete, modifier = Modifier.fillMaxWidth().height(58.dp)) {
            Text(if (completed) "Done" else "I finished this", fontSize = 19.sp)
        }
    }
}
