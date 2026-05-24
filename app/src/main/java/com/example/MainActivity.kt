package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.*
import com.example.ui.theme.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                val bgGrad = Brush.verticalGradient(
                    colors = listOf(Color(0xFF020617), Color(0xFF090E1D))
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(bgGrad)
                ) {
                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        containerColor = Color.Transparent,
                        contentWindowInsets = WindowInsets.safeDrawing
                    ) { innerPadding ->
                        QuantumLeapHub(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(innerPadding)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun QuantumLeapHub(modifier: Modifier = Modifier) {
    var sliderVal by remember { mutableFloatStateOf(1.0f) } // Default is High School / Young Minds
    val activeLevel = QuantumRepository.getLevelFromSlider(sliderVal)
    var selectedTab by remember { mutableIntStateOf(0) } // 0: Entanglement, 1: Teleportation, 2: Quiz

    // Scroll state for the lesson body
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // ==========================================
        // HERO HEADER
        // ==========================================
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "RESEARCH MODULE",
                    fontSize = 11.sp,
                    color = NeonCyan,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 2.sp,
                    fontFamily = FontFamily.Monospace,
                    modifier = Modifier.padding(bottom = 2.dp)
                )
                Text(
                    text = "Quantum Portal",
                    fontSize = 24.sp,
                    color = CosmicWhite,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.SansSerif,
                    modifier = Modifier.testTag("app_main_title")
                )
            }
            // Active Quantum Link Status & Guide Portal
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .background(NeonCyan.copy(alpha = 0.15f))
                        .border(1.dp, NeonCyan.copy(alpha = 0.4f), RoundedCornerShape(20.dp))
                        .padding(horizontal = 10.dp, vertical = 6.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .clip(CircleShape)
                                .background(SparkleGreen)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "SYNC: STABLE",
                            color = SparkleGreen,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Monospace
                        )
                    }
                }

                // Interactive Portal Icon
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(CosmicDeepNavy)
                        .border(1.dp, Color.White.copy(alpha = 0.15f), CircleShape)
                        .clickable {
                            // High-tech portal guide information
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = "Research Menu",
                        tint = NeonCyan,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // ==========================================
        // COMPLEXITY SLIDER SELECTOR
        // ==========================================
        Card(
            modifier = Modifier.fillMaxWidth().testTag("complexity_card"),
            colors = CardDefaults.cardColors(containerColor = CosmicDeepNavy),
            shape = RoundedCornerShape(16.dp),
            border = BorderStroke(1.dp, CosmicSlateCard)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Complexity Dial",
                        fontWeight = FontWeight.Bold,
                        color = CosmicWhite,
                        fontSize = 15.sp
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    // Text representation of calculated age groups
                    Text(
                        text = when {
                            sliderVal < 0.4f -> "Toddlers & Kids (Ages 6-11)"
                            sliderVal < 0.8f -> "Middle School (Ages 12-14)"
                            sliderVal < 1.3f -> "High School / AP Prep"
                            sliderVal < 1.7f -> "College Physics Undergrad"
                            else -> "Post-Doctoral Fellow (Hilbert Space)"
                        },
                        color = BrightGold,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                // The continuous Slider representing "All the people between"
                Slider(
                    value = sliderVal,
                    onValueChange = { sliderVal = it },
                    valueRange = 0.0f..2.0f,
                    modifier = Modifier.testTag("level_complexity_slider"),
                    colors = SliderDefaults.colors(
                        thumbColor = NeonCyan,
                        activeTrackColor = NeonCyan,
                        inactiveTrackColor = CosmicSlateCard
                    )
                )

                // Slider marker ticks
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(horizontalAlignment = Alignment.Start) {
                        Text("Kids 🐶", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = if (sliderVal < 0.5f) NeonCyan else CosmicGreyText)
                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Teens 🎈", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = if (sliderVal >= 0.5f && sliderVal <= 1.2f) NeonCyan else CosmicGreyText)
                    }
                    Column(horizontalAlignment = Alignment.End) {
                        Text("Post-Doc 📐", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = if (sliderVal > 1.2f) NeonCyan else CosmicGreyText)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(18.dp))

        // ==========================================
        // SECTION INTERACTIVE TABS
        // ==========================================
        TabRow(
            selectedTabIndex = selectedTab,
            containerColor = Color.Transparent,
            contentColor = NeonCyan,
            divider = {},
            indicator = { tabPositions ->
                TabRowDefaults.SecondaryIndicator(
                    modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                    color = if (selectedTab == 1) ElectricPurple else if (selectedTab == 2) BrightGold else NeonCyan
                )
            }
        ) {
            listOf(
                "Entanglement" to 0,
                "Teleportation" to 1,
                "Quiz Challenge" to 2
            ).forEach { (title, index) ->
                Tab(
                    selected = selectedTab == index,
                    onClick = { selectedTab = index },
                    modifier = Modifier.testTag("tab_$index"),
                    text = {
                        Text(
                            text = title,
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp,
                            color = if (selectedTab == index) CosmicWhite else CosmicGreyText
                        )
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // ==========================================
        // DYNAMIC LAB CONTENT SECTIONS
        // ==========================================
        when (selectedTab) {
            0 -> EntanglementSection(level = activeLevel)
            1 -> TeleportationSection(level = activeLevel)
            2 -> QuizSection(level = activeLevel, sliderVal = sliderVal)
        }

        Spacer(modifier = Modifier.height(30.dp))

        // ==========================================
        // NOBEL LAUREATES CORNER
        // ==========================================
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = CosmicSlateCard.copy(alpha = 0.4f)),
            shape = RoundedCornerShape(12.dp)
        ) {
            Row(
                modifier = Modifier.padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = "Did you know?",
                    tint = SparkleGreen,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = "Historical Quantum Link",
                        fontSize = 11.sp,
                        color = SparkleGreen,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = "The 2022 Nobel Prize in Physics was awarded to Aspect, Clauser, and Zeilinger for performing groundbreaking experiments confirming the reality of quantum entanglement!",
                        fontSize = 11.sp,
                        color = CosmicGreyText,
                        lineHeight = 15.sp
                    )
                }
            }
        }
    }
}

// ==========================================
// RENDER COMPOSABLES FOR CORE SECTIONS
// ==========================================

@Composable
fun EntanglementSection(level: QuantumLevel) {
    val lesson = QuantumRepository.entanglementLessons[level] ?: return

    Column(modifier = Modifier.fillMaxWidth()) {
        // Explainer intro Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = CosmicSlateCard),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = lesson.title,
                    fontSize = 18.sp,
                    color = NeonCyan,
                    fontWeight = FontWeight.ExtraBold
                )
                Text(
                    text = lesson.tagline,
                    fontSize = 12.sp,
                    color = CosmicGreyText,
                    fontStyle = FontStyle.Italic,
                    fontWeight = FontWeight.Medium
                )
                
                Spacer(modifier = Modifier.height(12.dp))
                
                Text(
                    text = lesson.mainText,
                    fontSize = 13.sp,
                    color = CosmicWhite,
                    lineHeight = 18.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // INTERACTIVE LAB GRAPHIC CHASSIS
        EntanglementVisualizer(level = level)

        Spacer(modifier = Modifier.height(16.dp))

        // Analogy section Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = CosmicDeepNavy),
            shape = RoundedCornerShape(12.dp),
            border = BorderStroke(1.dp, CosmicSlateCard)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Intuitive Analogy: ${lesson.analogyTitle}",
                    fontSize = 14.sp,
                    color = SparkleGreen,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = lesson.analogyText,
                    fontSize = 12.sp,
                    color = CosmicGreyText,
                    lineHeight = 17.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Bullet Facts list
        Text(
            text = "Key Takeaways",
            fontSize = 14.sp,
            color = CosmicWhite,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = 8.dp)
        )
        
        lesson.facts.forEach { fact ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                verticalAlignment = Alignment.Top
            ) {
                Text(
                    text = fact,
                    fontSize = 12.sp,
                    color = CosmicWhite,
                    lineHeight = 16.sp,
                    modifier = Modifier.padding(start = 4.dp)
                )
            }
        }
    }
}

@Composable
fun TeleportationSection(level: QuantumLevel) {
    val lesson = QuantumRepository.teleportationLessons[level] ?: return

    Column(modifier = Modifier.fillMaxWidth()) {
        // Explainer intro Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = CosmicSlateCard),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = lesson.title,
                    fontSize = 18.sp,
                    color = ElectricPurple,
                    fontWeight = FontWeight.ExtraBold
                )
                Text(
                    text = lesson.tagline,
                    fontSize = 12.sp,
                    color = CosmicGreyText,
                    fontStyle = FontStyle.Italic,
                    fontWeight = FontWeight.Medium
                )
                
                Spacer(modifier = Modifier.height(12.dp))
                
                Text(
                    text = lesson.mainText,
                    fontSize = 13.sp,
                    color = CosmicWhite,
                    lineHeight = 18.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // TELEPORTATION PROTOCOL VISUALIZER PANEL
        TeleportationVisualizer(level = level)

        Spacer(modifier = Modifier.height(16.dp))

        // Analogy details
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = CosmicDeepNavy),
            shape = RoundedCornerShape(12.dp),
            border = BorderStroke(1.dp, CosmicSlateCard)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Intuitive Analogy: ${lesson.analogyTitle}",
                    fontSize = 14.sp,
                    color = BrightGold,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = lesson.analogyText,
                    fontSize = 12.sp,
                    color = CosmicGreyText,
                    lineHeight = 17.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Facts bullet list
        Text(
            text = "Key Takeaways",
            fontSize = 14.sp,
            color = CosmicWhite,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = 8.dp)
        )
        
        lesson.facts.forEach { fact ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                verticalAlignment = Alignment.Top
            ) {
                Text(
                    text = fact,
                    fontSize = 12.sp,
                    color = CosmicWhite,
                    lineHeight = 16.sp,
                    modifier = Modifier.padding(start = 4.dp)
                )
            }
        }
    }
}

@Composable
fun QuizSection(level: QuantumLevel, sliderVal: Float) {
    // Pick active quiz lists matching sliderVal bands
    val levelKey = when {
        sliderVal < 0.5f -> "KIDS"
        sliderVal < 1.1f -> "MIDDLE_SCHOOL"
        sliderVal < 1.7f -> "HIGH_SCHOOL"
        else -> "POST_DOC"
    }

    val questions = remember(levelKey) {
        QuantumRepository.quizDatabase.filter { it.levelKey == levelKey }
    }

    var questionIdx by remember(levelKey) { mutableIntStateOf(0) }
    var score by remember(levelKey) { mutableIntStateOf(0) }
    var selectedAnswer by remember(levelKey, questionIdx) { mutableStateOf<Int?>(null) }
    var showFeedback by remember(levelKey, questionIdx) { mutableStateOf(false) }
    var completedQuiz by remember(levelKey) { mutableStateOf(false) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = CosmicDeepNavy),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, CosmicSlateCard)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "🏆 Level Evaluation Challenge",
                fontSize = 18.sp,
                color = BrightGold,
                fontWeight = FontWeight.ExtraBold
            )
            Text(
                text = "Tests calibrated specifically for standard: ${level.title}",
                fontSize = 11.sp,
                color = CosmicGreyText
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (completedQuiz) {
                // Done View
                Column(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "Great job!",
                        tint = BrightGold,
                        modifier = Modifier.size(64.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Level Complete!",
                        style = MaterialTheme.typography.titleLarge,
                        color = CosmicWhite,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Your Score: $score / ${questions.size}",
                        color = SparkleGreen,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.ExtraBold,
                        fontFamily = FontFamily.Monospace,
                        modifier = Modifier.testTag("quiz_final_score")
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = {
                            questionIdx = 0
                            score = 0
                            selectedAnswer = null
                            showFeedback = false
                            completedQuiz = false
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = BrightGold, contentColor = CosmicBlack)
                    ) {
                        Text("Retake Challenge", fontWeight = FontWeight.SemiBold)
                    }
                }
            } else if (questions.isNotEmpty() && questionIdx < questions.size) {
                val q = questions[questionIdx]

                // Show Question Title
                Text(
                    text = "Question ${questionIdx + 1} of ${questions.size}",
                    fontSize = 12.sp,
                    color = ElectricPurple,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Monospace
                )
                
                Spacer(modifier = Modifier.height(6.dp))
                
                Text(
                    text = q.question,
                    fontSize = 14.sp,
                    color = CosmicWhite,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 19.sp,
                    modifier = Modifier.testTag("quiz_question_text")
                )

                Spacer(modifier = Modifier.height(18.dp))

                // Options
                q.options.forEachIndexed { optIdx, option ->
                    val isSelected = selectedAnswer == optIdx
                    val outlineColor = when {
                        showFeedback && optIdx == q.correctIndex -> SparkleGreen
                        showFeedback && isSelected -> RosePink
                        isSelected -> NeonCyan
                        else -> CosmicSlateCard
                    }
                    val backColor = when {
                        showFeedback && optIdx == q.correctIndex -> SparkleGreen.copy(alpha=0.1f)
                        showFeedback && isSelected -> RosePink.copy(alpha=0.1f)
                        isSelected -> NeonCyan.copy(alpha=0.08f)
                        else -> Color.Transparent
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(backColor)
                            .border(1.2.dp, outlineColor, RoundedCornerShape(10.dp))
                            .clickable(enabled = !showFeedback) {
                                selectedAnswer = optIdx
                            }
                            .padding(14.dp)
                            .testTag("quiz_option_$optIdx")
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            RadioButton(
                                selected = isSelected,
                                onClick = { if (!showFeedback) selectedAnswer = optIdx },
                                colors = RadioButtonDefaults.colors(
                                    selectedColor = if (showFeedback) SparkleGreen else NeonCyan,
                                    unselectedColor = CosmicGreyText
                                ),
                                enabled = !showFeedback
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = option,
                                fontSize = 13.sp,
                                color = CosmicWhite,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Feedback card if answered
                if (showFeedback) {
                    val isCorrect = selectedAnswer == q.correctIndex
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = if (isCorrect) SparkleGreen.copy(alpha=0.1f) else RosePink.copy(alpha=0.1f)
                        ),
                        modifier = Modifier.fillMaxWidth().testTag("quiz_feedback_card"),
                        border = BorderStroke(1.dp, if (isCorrect) SparkleGreen.copy(alpha=0.3f) else RosePink.copy(alpha=0.3f))
                    ) {
                        Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.Top) {
                            Text(
                                text = if (isCorrect) "✓ Correct! " else "✗ Incorrect! ",
                                fontWeight = FontWeight.Bold,
                                color = if (isCorrect) SparkleGreen else RosePink,
                                fontSize = 13.sp
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = q.explanation,
                                fontSize = 12.sp,
                                color = CosmicGreyText,
                                lineHeight = 16.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(18.dp))

                    Button(
                        onClick = {
                            if (questionIdx + 1 < questions.size) {
                                questionIdx++
                            } else {
                                completedQuiz = true
                            }
                        },
                        modifier = Modifier.fillMaxWidth().testTag("quiz_next_button"),
                        colors = ButtonDefaults.buttonColors(containerColor = NeonCyan, contentColor = CosmicBlack)
                    ) {
                        Text(
                            text = if (questionIdx + 1 < questions.size) "Next Question" else "Show My Score",
                            fontWeight = FontWeight.Bold
                        )
                    }
                } else {
                    Button(
                        onClick = {
                            if (selectedAnswer != null) {
                                if (selectedAnswer == q.correctIndex) {
                                    score++
                                }
                                showFeedback = true
                            }
                        },
                        enabled = selectedAnswer != null,
                        modifier = Modifier.fillMaxWidth().testTag("quiz_submit_button"),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = ElectricPurple,
                            disabledContainerColor = CosmicSlateCard,
                            contentColor = CosmicWhite,
                            disabledContentColor = CosmicGreyText
                        )
                    ) {
                        Text("Lock In Answer & Grade", fontWeight = FontWeight.Bold)
                    }
                }
            } else {
                Text(
                    text = "No questions found for this tier.",
                    color = CosmicGreyText,
                    fontSize = 12.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}
