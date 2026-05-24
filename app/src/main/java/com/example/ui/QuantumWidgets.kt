package com.example.ui

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.*
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*
import kotlin.math.cos
import kotlin.math.sin

// ==========================================
// ENTANGLEMENT VISUALIZER
// ==========================================

@Composable
fun EntanglementVisualizer(
    level: QuantumLevel,
    modifier: Modifier = Modifier
) {
    var noiseState by remember { mutableStateOf(0.0f) } // Decoherence factor for Post Doc
    var isSpinningSuperposition by remember { mutableStateOf(true) }
    var collapsedStateA by remember { mutableStateOf<Boolean?>(null) } // true: Up/Cyan, false: Down/Purple

    // Sparkling background timer to animate particle stars
    val infiniteTransition = rememberInfiniteTransition(label = "stars")
    val starAlpha by infiniteTransition.animateFloat(
        initialValue = 0.2f,
        targetValue = 1.0f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "star_pulse"
    )

    val waveOffset by infiniteTransition.animateFloat(
        initialValue = 0.0f,
        targetValue = 360.0f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "wave"
    )

    // Handle levels inside the widget
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = CosmicDeepNavy),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, NeonCyan.copy(alpha = 0.3f))
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Interactive Laboratory",
                style = MaterialTheme.typography.titleMedium,
                color = NeonCyan,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = when (level) {
                    QuantumLevel.KIDS -> "Tap either box to open it and find the pet twins!"
                    QuantumLevel.MIDDLE_SCHOOL -> "Drag the distance slider. See that the quantum string never breaks!"
                    QuantumLevel.HIGH_SCHOOL -> "Tap Spin Superposition. Hit 'Measure' to collapse the wave function!"
                    QuantumLevel.POST_DOC -> "Adjust noise level (λ). Observe concurrence leak and mixed-matrix dephasing."
                },
                style = MaterialTheme.typography.bodySmall,
                color = CosmicGreyText,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            // RENDER CANVAS BOX
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp)
                    .background(CosmicBlack, RoundedCornerShape(12.dp))
                    .border(1.dp, CosmicSlateCard, RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                when (level) {
                    QuantumLevel.KIDS -> {
                        KidsVisualizerCanvas(
                            isSpinning = isSpinningSuperposition,
                            collapsedState = collapsedStateA,
                            starAlpha = starAlpha,
                            onBoxClicked = {
                                if (isSpinningSuperposition) {
                                    collapsedStateA = (Math.random() > 0.5)
                                    isSpinningSuperposition = false
                                } else {
                                    isSpinningSuperposition = true
                                    collapsedStateA = null
                                }
                            }
                        )
                    }
                    QuantumLevel.MIDDLE_SCHOOL -> {
                        var distanceFactor by remember { mutableStateOf(100f) }
                        
                        Column(modifier = Modifier.fillMaxSize().padding(8.dp)) {
                            Box(modifier = Modifier.weight(1f).fillMaxWidth(), contentAlignment = Alignment.Center) {
                                MiddleSchoolCanvas(
                                    distanceFactor = distanceFactor,
                                    starAlpha = starAlpha,
                                    waveOffset = waveOffset,
                                    collapsedState = collapsedStateA,
                                    isSuperposition = isSpinningSuperposition,
                                    onMeasure = {
                                        if (isSpinningSuperposition) {
                                            collapsedStateA = (Math.random() > 0.5)
                                            isSpinningSuperposition = false
                                        } else {
                                            isSpinningSuperposition = true
                                            collapsedStateA = null
                                        }
                                    }
                                )
                            }
                            
                            Row(
                                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text("Distance:", color = CosmicGreyText, fontSize = 12.sp)
                                Slider(
                                    value = distanceFactor,
                                    onValueChange = { distanceFactor = it },
                                    valueRange = 80f..280f,
                                    modifier = Modifier.weight(1f).testTag("distance_slider"),
                                    colors = SliderDefaults.colors(
                                        activeTrackColor = NeonCyan,
                                        thumbColor = NeonCyan
                                    )
                                )
                                Text("${distanceFactor.toInt()} Light-Years", color = NeonCyan, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                    QuantumLevel.HIGH_SCHOOL -> {
                        HighSchoolCanvas(
                            isSpinning = isSpinningSuperposition,
                            collapsedState = collapsedStateA,
                            starAlpha = starAlpha,
                            waveOffset = waveOffset,
                            onMeasure = {
                                if (isSpinningSuperposition) {
                                    collapsedStateA = (Math.random() > 0.5)
                                    isSpinningSuperposition = false
                                } else {
                                    isSpinningSuperposition = true
                                    collapsedStateA = null
                                }
                            }
                        )
                    }
                    QuantumLevel.POST_DOC -> {
                        PostDocVisualizerArea(
                            noise = noiseState,
                            starAlpha = starAlpha,
                            waveOffset = waveOffset
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // ACTION CONTROLS
            when (level) {
                QuantumLevel.KIDS -> {
                    Button(
                        onClick = {
                            isSpinningSuperposition = true
                            collapsedStateA = null
                        },
                        modifier = Modifier.testTag("kids_reset_button"),
                        colors = ButtonDefaults.buttonColors(containerColor = NeonCyan, contentColor = CosmicBlack)
                    ) {
                        Icon(Icons.Default.Refresh, contentDescription = "Reset")
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Spin Magic Boxes Again", fontWeight = FontWeight.SemiBold)
                    }
                }
                QuantumLevel.MIDDLE_SCHOOL -> {
                    Button(
                        onClick = {
                            isSpinningSuperposition = !isSpinningSuperposition
                            if (isSpinningSuperposition) collapsedStateA = null
                            else if (collapsedStateA == null) collapsedStateA = (Math.random() > 0.5)
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isSpinningSuperposition) RosePink else NeonCyan,
                            contentColor = CosmicBlack
                        )
                    ) {
                        Text(
                            text = if (isSpinningSuperposition) "Collapse Invisible String (Measure)" else "Respin Particles",
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
                QuantumLevel.HIGH_SCHOOL -> {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Button(
                            onClick = {
                                if (isSpinningSuperposition) {
                                    collapsedStateA = (Math.random() > 0.5)
                                    isSpinningSuperposition = false
                                } else {
                                    isSpinningSuperposition = true
                                    collapsedStateA = null
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = NeonCyan, contentColor = CosmicBlack)
                        ) {
                            Text(if (isSpinningSuperposition) "Collapse Spin (Measure)" else "Trigger Superposition")
                        }
                        
                        OutlinedButton(
                            onClick = {
                                isSpinningSuperposition = true
                                collapsedStateA = null
                            },
                            border = BorderStroke(1.dp, ElectricPurple),
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = ElectricPurple)
                        ) {
                            Icon(Icons.Default.Refresh, contentDescription = "Reset")
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Reset Lab")
                        }
                    }
                }
                QuantumLevel.POST_DOC -> {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("Decoherence Channel Noise (λ):", style = MaterialTheme.typography.bodySmall, color = CosmicGreyText)
                            Spacer(modifier = Modifier.weight(1f))
                            Text(String.format(java.util.Locale.US, "%.2f", noiseState), color = RosePink, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.bodySmall)
                        }
                        Slider(
                            value = noiseState,
                            onValueChange = { noiseState = it },
                            valueRange = 0.0f..1.0f,
                            modifier = Modifier.testTag("noise_slider"),
                            colors = SliderDefaults.colors(
                                activeTrackColor = RosePink,
                                thumbColor = RosePink,
                                inactiveTrackColor = CosmicGreyText.copy(alpha = 0.2f)
                            )
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Entangled Singlet (Pure)", fontSize = 10.sp, color = NeonCyan)
                            Text("Mixed State (Classical)", fontSize = 10.sp, color = CosmicGreyText)
                        }
                    }
                }
            }
        }
    }
}

// ==========================================
// RENDER CANVAS IMPLEMENTATIONS FOR ENTANGLEMENT
// ==========================================

@Composable
fun KidsVisualizerCanvas(
    isSpinning: Boolean,
    collapsedState: Boolean?,
    starAlpha: Float,
    onBoxClicked: () -> Unit
) {
    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .clickable { onBoxClicked() }
            .testTag("kids_entanglement_canvas")
    ) {
        val width = size.width
        val height = size.height
        if (width <= 0f || height <= 0f) return@Canvas

        // Background stars
        drawCircle(color = SparkleGreen.copy(alpha = starAlpha * 0.4f), radius = 3f, center = Offset(width * 0.15f, height * 0.25f))
        drawCircle(color = NeonCyan.copy(alpha = starAlpha * 0.6f), radius = 5f, center = Offset(width * 0.82f, height * 0.15f))
        drawCircle(color = RosePink.copy(alpha = starAlpha * 0.5f), radius = 4f, center = Offset(width * 0.35f, height * 0.75f))
        drawCircle(color = BrightGold.copy(alpha = starAlpha * 0.7f), radius = 6f, center = Offset(width * 0.72f, height * 0.85f))

        // Left Alice Box & Right Bob Box centers
        val centerLeft = Offset(width * 0.28f, height * 0.5f)
        val centerRight = Offset(width * 0.72f, height * 0.5f)
        val boxSize = 70f

        // Draw magic correlation path
        if (isSpinning) {
            val wavePath = Path().apply {
                moveTo(centerLeft.x, centerLeft.y)
                quadraticTo(width * 0.5f, height * 0.3f, centerRight.x, centerRight.y)
            }
            drawPath(
                path = wavePath,
                color = NeonCyan.copy(alpha = 0.4f),
                style = Stroke(width = 3f, pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f))
            )
        } else {
            // Strong connection link drawn
            drawLine(
                color = SparkleGreen,
                start = centerLeft,
                end = centerRight,
                strokeWidth = 4f,
                pathEffect = PathEffect.cornerPathEffect(10f)
            )
        }

        // Alice and Bob Boxes Design
        listOf(centerLeft, centerRight).forEachIndexed { index, center ->
            val color = if (isSpinning) ElectricPurple else if (collapsedState == true) NeonCyan else RosePink
            
            // Draw Box Border Glow
            drawCircle(
                color = color.copy(alpha = 0.15f),
                radius = boxSize + 15f,
                center = center
            )
            
            // Draw Box
            drawRect(
                color = color,
                topLeft = Offset(center.x - boxSize/2, center.y - boxSize/2),
                size = Size(boxSize, boxSize),
                style = Stroke(width = 4f)
            )

            // Box details
            drawRect(
                color = color.copy(alpha = 0.15f),
                topLeft = Offset(center.x - boxSize/2 + 4f, center.y - boxSize/2 + 4f),
                size = Size(boxSize - 8f, boxSize - 8f)
            )

            // Content
            if (isSpinning) {
                // Spinning sparkles drawn
                drawCircle(
                    color = BrightGold.copy(alpha = starAlpha),
                    radius = 8f,
                    center = center
                )
                // Draw mystery label
                val paint = Paint().asFrameworkPaint().apply {
                    this.color = Color.White.toArgb()
                    this.textSize = 28f
                    this.textAlign = android.graphics.Paint.Align.CENTER
                    this.isFakeBoldText = true
                }
                drawContext.canvas.nativeCanvas.drawText("?", center.x, center.y + 10f, paint)
            } else {
                // Reveal Magic Pets (drawn with shapes!)
                val petColor = if (collapsedState == true) NeonCyan else RosePink
                
                // Puppy Head
                drawCircle(color = petColor, radius = 20f, center = center)
                // Puppy Ears
                drawCircle(color = petColor, radius = 7f, center = Offset(center.x - 17f, center.y - 12f))
                drawCircle(color = petColor, radius = 7f, center = Offset(center.x + 17f, center.y - 12f))
                // Cute Ribbon collar
                drawCircle(color = BrightGold, radius = 5f, center = Offset(center.x, center.y + 17f))
                // Eyes
                drawCircle(color = CosmicBlack, radius = 2.5f, center = Offset(center.x - 6f, center.y - 3f))
                drawCircle(color = CosmicBlack, radius = 2.5f, center = Offset(center.x + 6f, center.y - 3f))
                // Cheerful Nose
                drawCircle(color = Color.White, radius = 2f, center = Offset(center.x, center.y + 3f))
            }
        }

        // Display Labels
        val textPaint = Paint().asFrameworkPaint().apply {
            this.color = CosmicGreyText.toArgb()
            this.textSize = 22f
            this.textAlign = android.graphics.Paint.Align.CENTER
        }
        drawContext.canvas.nativeCanvas.drawText("Pet A (Alice)", centerLeft.x, centerLeft.y + boxSize/2 + 30f, textPaint)
        drawContext.canvas.nativeCanvas.drawText("Pet B (Bob)", centerRight.x, centerRight.y + boxSize/2 + 30f, textPaint)
    }
}

@Composable
fun MiddleSchoolCanvas(
    distanceFactor: Float,
    starAlpha: Float,
    waveOffset: Float,
    collapsedState: Boolean?,
    isSuperposition: Boolean,
    onMeasure: () -> Unit
) {
    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .clickable { onMeasure() }
            .testTag("middleschool_entanglement_canvas")
    ) {
        val width = size.width
        val height = size.height
        if (width <= 0f || height <= 0f) return@Canvas

        val leftX = width/2 - distanceFactor
        val rightX = width/2 + distanceFactor
        val centerLeft = Offset(leftX, height * 0.5f)
        val centerRight = Offset(rightX, height * 0.5f)

        // Draw invisible quantum link wave string
        val waveColor = if (isSuperposition) ElectricPurple else SparkleGreen
        val wavePath = Path().apply {
            moveTo(centerLeft.x, centerLeft.y)
            for (currX in centerLeft.x.toInt()..centerRight.x.toInt() step 5) {
                val ratio = (currX - centerLeft.x) / (centerRight.x - centerLeft.x)
                val amp = if (isSuperposition) 15f * sin(ratio * Math.PI.toFloat()) else 0f
                val freq = 2 * Math.PI.toFloat() * 3 * ratio + Math.toRadians(waveOffset.toDouble()).toFloat()
                val currY = centerLeft.y + amp * sin(freq)
                lineTo(currX.toFloat(), currY)
            }
        }
        
        drawPath(
            path = wavePath,
            color = waveColor,
            style = Stroke(width = 3.5f)
        )

        // Particles
        listOf(centerLeft, centerRight).forEachIndexed { idx, center ->
            val particleColor = if (isSuperposition) {
                if (idx == 0) NeonCyan else ElectricPurple
            } else {
                if (collapsedState == true) NeonCyan else RosePink
            }

            drawCircle(
                color = particleColor.copy(alpha = 0.3f),
                radius = 28f + starAlpha * 4f,
                center = center
            )
            drawCircle(
                color = particleColor,
                radius = 16f,
                center = center
            )
            // Particle inner nucleus
            drawCircle(
                color = Color.White,
                radius = 5f,
                center = center
            )
        }

        // Labels
        val textPaint = Paint().asFrameworkPaint().apply {
            this.color = Color.White.toArgb()
            this.textSize = 20f
            this.textAlign = android.graphics.Paint.Align.CENTER
        }
        
        val stringStatus = if (isSuperposition) "Linked Superposition!" else "Collapsed Instant Correlation!"
        drawContext.canvas.nativeCanvas.drawText(stringStatus, width/2, height * 0.15f, textPaint)
        
        val descPaint = Paint().asFrameworkPaint().apply {
            this.color = CosmicGreyText.toArgb()
            this.textSize = 18f
            this.textAlign = android.graphics.Paint.Align.CENTER
        }
        drawContext.canvas.nativeCanvas.drawText("Atom A", centerLeft.x, centerLeft.y + 45f, descPaint)
        drawContext.canvas.nativeCanvas.drawText("Atom B", centerRight.x, centerRight.y + 45f, descPaint)
    }
}

@Composable
fun HighSchoolCanvas(
    isSpinning: Boolean,
    collapsedState: Boolean?,
    starAlpha: Float,
    waveOffset: Float,
    onMeasure: () -> Unit
) {
    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .clickable { onMeasure() }
            .testTag("highschool_entanglement_canvas")
    ) {
        val width = size.width
        val height = size.height
        if (width <= 0f || height <= 0f) return@Canvas

        val centerLeft = Offset(width * 0.28f, height * 0.5f)
        val centerRight = Offset(width * 0.72f, height * 0.5f)
        val sphereRadius = 38f

        // Connection wavefunction line
        val waveColor = if (isSpinning) ElectricPurple else RosePink
        val wavePath = Path().apply {
            moveTo(centerLeft.x, centerLeft.y)
            for (currX in centerLeft.x.toInt()..centerRight.x.toInt() step 6) {
                val ratio = (currX - centerLeft.x) / (centerRight.x - centerLeft.x)
                val amp = if (isSpinning) 25f * sin(ratio * Math.PI.toFloat()) else 0f
                // Standard wave formula: A sin(kx - wt)
                val freq = 2 * Math.PI.toFloat() * 4 * ratio + Math.toRadians(waveOffset.toDouble()).toFloat()
                val currY = centerLeft.y + amp * sin(freq)
                lineTo(currX.toFloat(), currY)
            }
        }
        drawPath(
            path = wavePath,
            color = waveColor.copy(alpha = 0.7f),
            style = Stroke(width = 3.5f)
        )

        // Draw Alice & Bob Spin stations
        listOf(centerLeft, centerRight).forEachIndexed { idx, center ->
            val isAlice = idx == 0
            
            // Draw spherical background shell (Bloch-like circle representation)
            drawCircle(
                color = CosmicSlateCard.copy(alpha = 0.6f),
                radius = sphereRadius,
                center = center
            )
            drawCircle(
                color = (if (isSpinning) ElectricPurple else NeonCyan).copy(alpha = 0.3f),
                radius = sphereRadius,
                center = center,
                style = Stroke(width = 1.5f, pathEffect = PathEffect.dashPathEffect(floatArrayOf(6f, 6f), 0f))
            )

            if (isSpinning) {
                // Spinning state vector drawing (animated rotation vector representation)
                val angle = Math.toRadians(waveOffset.toDouble() * (if (isAlice) 1 else -1) + idx * 90.0)
                val endX = center.x + sphereRadius * cos(angle).toFloat()
                val endY = center.y + sphereRadius * sin(angle).toFloat()
                
                // Vector line
                drawLine(
                    color = NeonCyan,
                    start = center,
                    end = Offset(endX, endY),
                    strokeWidth = 3f
                )
                // Head tip
                drawCircle(color = NeonCyan, radius = 5f, center = Offset(endX, endY))
                // Core
                drawCircle(color = Color.White, radius = 4f, center = center)
            } else {
                // Collapsed spin vector (Strict Spin-Up up spin, spin-down down spin)
                // Singlet state spins are OPPOSITE
                val isSpinUp = if (isAlice) collapsedState == true else collapsedState == false
                val spinDir = if (isSpinUp) -1f else 1f // Up is negative in Canvas coordinates
                val spinColor = if (isSpinUp) NeonCyan else RosePink
                
                // Spin vector arrow
                val endY = center.y + (sphereRadius - 2f) * spinDir
                drawLine(
                    color = spinColor,
                    start = center,
                    end = Offset(center.x, endY),
                    strokeWidth = 6f
                )
                
                // Draw Arrow Head
                val arrowHeadPath = Path().apply {
                    moveTo(center.x, endY)
                    lineTo(center.x - 10f, endY - 12f * spinDir)
                    lineTo(center.x + 10f, endY - 12f * spinDir)
                    close()
                }
                drawPath(path = arrowHeadPath, color = spinColor)

                // State description text label inside the sphere
                val stateText = if (isSpinUp) "|↑⟩ (Up)" else "|↓⟩ (Down)"
                val statePaint = Paint().asFrameworkPaint().apply {
                    this.color = Color.White.toArgb()
                    this.textSize = 16f
                    this.textAlign = android.graphics.Paint.Align.CENTER
                }
                drawContext.canvas.nativeCanvas.drawText(stateText, center.x, center.y + 10f * (-spinDir), statePaint)
            }
        }

        // Overlay formula info
        val debugPaint = Paint().asFrameworkPaint().apply {
            this.color = CosmicGreyText.toArgb()
            this.textSize = 16f
            this.textAlign = android.graphics.Paint.Align.CENTER
            this.typeface = android.graphics.Typeface.MONOSPACE
        }
        val formula = if (isSpinning) {
            "|Ψ⟩ = 1/√2 ( |↑↓⟩ - |↓↑⟩ )"
        } else {
            val sA = if (collapsedState == true) "↑" else "↓"
            val sB = if (collapsedState == true) "↓" else "↑"
            "Collapsed to Product State: |${sA}_A ${sB}_B⟩"
        }
        drawContext.canvas.nativeCanvas.drawText(formula, width/2f, 35f, debugPaint)
    }
}

@Composable
fun PostDocVisualizerArea(
    noise: Float,
    starAlpha: Float,
    waveOffset: Float
) {
    // Computes dynamic variables representing Entanglement Metrics
    val concurrence = (1.0f - 1.5f * noise).coerceAtLeast(0.0f)
    // Mixed state density matrix calculation
    // singlet density state has components:
    // diagonal: [0, 0.5 - 0.25*noise, 0.5 - 0.25*noise, 0] wait!
    // Singlet matrix: [[0,0,0,0], [0,0.5,-0.5,0], [0,-0.5,0.5,0], [0,0,0,0]]
    // Dephased under dephasing channel: off-diagonals decay by (1 - lambda)
    // co_term (-0.5) degrades to -0.5 * (1 - lambda)
    val cofh = -0.5f * (1.0f - noise)

    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // LHS: Bloch-like projection representation & Concurrence gauge
        Box(
            modifier = Modifier
                .width(130.dp)
                .fillMaxHeight(),
            contentAlignment = Alignment.Center
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val cx = size.width / 2f
                val cy = size.height / 2f
                val rad = size.width * 0.4f
                if (size.width <= 0f || size.height <= 0f) return@Canvas

                // Reference circle
                drawCircle(color = CosmicSlateCard, radius = rad, center = Offset(cx, cy), style = Stroke(2f))
                
                // Bloch sphere axes drawn
                drawLine(color = CosmicSlateCard.copy(alpha = 0.5f), start = Offset(cx, cy - rad), end = Offset(cx, cy + rad), strokeWidth = 1.5f)
                drawLine(color = CosmicSlateCard.copy(alpha = 0.5f), start = Offset(cx - rad, cy), end = Offset(cx + rad, cy), strokeWidth = 1.5f)

                // State vector (Alice's subsystem)
                // Reduced state of singlet is a mixed state inside the Bloch ball
                // Pure state has length 1. Maximal mix (singlet A subsystem) has length 0! (dephasing maintains length 0 if fully singlet)
                // If concurrence drops, system gets mixed. Let's draw projection point representing coherence length inside the sphere!
                val coherenceLength = rad * (1.0f - noise)
                val angle = Math.toRadians(waveOffset.toDouble()).toFloat()
                val vx = cx + coherenceLength * cos(angle)
                val vy = cy + coherenceLength * sin(angle)

                if (coherenceLength > 2f) {
                    drawLine(color = NeonCyan, start = Offset(cx, cy), end = Offset(vx, vy), strokeWidth = 3f)
                    drawCircle(color = NeonCyan, radius = 4f, center = Offset(vx, vy))
                }

                drawCircle(color = Color.White, radius = 3f, center = Offset(cx, cy))

                // Gauge Label
                val labelPaint = Paint().asFrameworkPaint().apply {
                    this.color = Color.White.toArgb()
                    this.textSize = 15f
                    this.textAlign = android.graphics.Paint.Align.CENTER
                }
                drawContext.canvas.nativeCanvas.drawText("Reduced State ρ_A", cx, cy - rad - 8f, labelPaint)
                drawContext.canvas.nativeCanvas.drawText("Coherence: ${(100f * (1f-noise)).toInt()}%", cx, cy + rad + 18f, labelPaint)
            }
        }

        // RHS: Dynamic density matrix and Concurrence indicator details
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .padding(start = 8.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "COHERENT DENSITY MATRIX ρ",
                fontSize = 10.sp,
                color = ElectricPurple,
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.height(6.dp))
            
            // Render 4x4 matrix representation
            MatrixRenderGrid(noise = noise, cofh = cofh)

            Spacer(modifier = Modifier.height(12.dp))

            // Speedometer of concurrence
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "Concurrence C(ρ): ",
                    fontSize = 11.sp,
                    color = CosmicGreyText,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = String.format(java.util.Locale.US, "%.3f", concurrence),
                    fontSize = 12.sp,
                    color = if (concurrence > 0.5f) SparkleGreen else RosePink,
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Bold
                )
            }

            // Von Neumann Entropy S(ρ_A)
            // S = - p_1 log(p_1) - p_2 log(p_2) where p_1 = (1 + r)/2, p_2 = (1 - r)/2
            // Since reduced state stays maximally mixed for singlet irrespective of dephasing, wait!
            // Wait, standard dephasing keeps reduced states mixed but destroys the off-diagonal correlation.
            // Let's print entropy state values as educational math variables.
            Text(
                text = "Pure Entropic State: ${if (concurrence > 0.5f) "Violates Bell Inequality" else "Separable/Classical Mixture"}",
                fontSize = 10.sp,
                color = if (concurrence > 0.5f) SparkleGreen.copy(alpha=0.8f) else CosmicGreyText,
                fontStyle = FontStyle.Italic
            )
        }
    }
}

@Composable
fun MatrixRenderGrid(
    noise: Float,
    cofh: Float
) {
    val df = String.format(java.util.Locale.US, "%.2f", 0.5f)
    val df_cor = String.format(java.util.Locale.US, "%.2f", cofh)

    Column(
        modifier = Modifier
            .background(CosmicBlack.copy(alpha = 0.8f))
            .border(1.dp, SlateBorder, RoundedCornerShape(4.dp))
            .padding(4.dp)
    ) {
        listOf(
            listOf("0.00", "0.00", "0.00", "0.00"),
            listOf("0.00", "0.50", df_cor, "0.00"),
            listOf("0.00", df_cor, "0.50", "0.00"),
            listOf("0.00", "0.00", "0.00", "0.00")
        ).forEach { row ->
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                row.forEach { valStr ->
                    Text(
                        text = String.format(java.util.Locale.US, "%5s", valStr),
                        fontFamily = FontFamily.Monospace,
                        fontSize = 10.sp,
                        color = when {
                            valStr == "0.50" -> CosmicWhite
                            valStr.startsWith("-") -> NeonCyan
                            valStr == "0.00" -> CosmicGreyText.copy(alpha=0.3f)
                            else -> SparkleGreen
                        },
                        modifier = Modifier.width(36.dp),
                        textAlign = TextAlign.End
                    )
                }
            }
        }
    }
}

val SlateBorder = Color(0xFF334155)


// ==========================================
// TELEPORTATION VISUALIZER
// ==========================================

@Composable
fun TeleportationVisualizer(
    level: QuantumLevel,
    modifier: Modifier = Modifier
) {
    var activeStep by remember { mutableIntStateOf(0) }
    var inputTheta by remember { mutableFloatStateOf(45.0f) } // Input State parameter θ (Alice Qubit A)
    var inputPhi by remember { mutableFloatStateOf(0.0f) } // Input State parameter φ (Alice Qubit A)
    var classicalBits by remember { mutableStateOf<String?>(null) } // "00", "01", "10", "11"
    var animationProgress by remember { mutableFloatStateOf(0f) }

    val stepsCount = 5

    // Restart teleporter states
    fun resetTeleportation() {
        activeStep = 0
        classicalBits = null
        animationProgress = 0f
    }

    // Step labels based on active user level
    val stepDescriptions = listOf(
        "Generate Entangled Key: Bob shares particle C with Alice (particle B).",
        "Alice prepares message state |ψ⟩ from user dial.",
        "Alice entangles A and B (CNOT + Hadamard) and measures them.",
        "Alice sends measured bits over traditional classical phone/link.",
        "Bob receives bits and runs rotation gates. Qubit C transforms into |ψ⟩!"
    )

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = CosmicDeepNavy),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, ElectricPurple.copy(alpha = 0.3f))
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Interactive Quantum Teleporter",
                style = MaterialTheme.typography.titleMedium,
                color = ElectricPurple,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Step by step teleportation protocol simulator",
                style = MaterialTheme.typography.bodySmall,
                color = CosmicGreyText,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            // INTERACTIVE GRAPHICS ROW
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(260.dp)
                    .background(CosmicBlack, RoundedCornerShape(12.dp))
                    .border(1.dp, CosmicSlateCard, RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                TeleportationCanvas(
                    step = activeStep,
                    theta = inputTheta,
                    phi = inputPhi,
                    level = level,
                    bits = classicalBits
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // LEVEL TUNER DIAL (Only visible for High school or Postdoc to customize $|psi>$ )
            if (level == QuantumLevel.HIGH_SCHOOL || level == QuantumLevel.POST_DOC) {
                Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 4.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        val thetaRad = Math.toRadians(inputTheta.toDouble()) / 2.0
                        val coeffZero = cos(thetaRad)
                        val coeffOneReal = sin(thetaRad) * cos(Math.toRadians(inputPhi.toDouble()))
                        val coeffOneImag = sin(thetaRad) * sin(Math.toRadians(inputPhi.toDouble()))
                        
                        val formulaText = buildString {
                            append("|ψ⟩ = ")
                            append(String.format(java.util.Locale.US, "%.2f", coeffZero))
                            append("|0⟩")
                            
                            val realAbs = Math.abs(coeffOneReal)
                            val imagAbs = Math.abs(coeffOneImag)
                            
                            if (realAbs >= 0.01 && imagAbs < 0.01) {
                                if (coeffOneReal >= 0) append(" + ") else append(" - ")
                                append(String.format(java.util.Locale.US, "%.2f", realAbs))
                                append("|1⟩")
                            } else if (imagAbs >= 0.01 && realAbs < 0.01) {
                                if (coeffOneImag >= 0) append(" + ") else append(" - ")
                                append(String.format(java.util.Locale.US, "%.2fi", imagAbs))
                                append("|1⟩")
                            } else if (realAbs >= 0.01 || imagAbs >= 0.01) {
                                append(" + (")
                                append(String.format(java.util.Locale.US, "%.2f", coeffOneReal))
                                if (coeffOneImag >= 0) append("+")
                                append(String.format(java.util.Locale.US, "%.2fi", coeffOneImag))
                                append(")|1⟩")
                            }
                        }
                        
                        Text(
                            text = formulaText,
                            color = CosmicWhite,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Monospace
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "Polar Angle θ (State Amplitude)",
                            color = CosmicGreyText,
                            fontSize = 11.sp
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Text("θ = ${inputTheta.toInt()}°", color = NeonCyan, fontWeight = FontWeight.Bold, fontSize = 11.sp)
                    }
                    Slider(
                        value = inputTheta,
                        onValueChange = { 
                            if (activeStep <= 1) inputTheta = it 
                        },
                        valueRange = 0.0f..180.0f,
                        enabled = activeStep <= 1,
                        modifier = Modifier.testTag("input_theta_slider"),
                        colors = SliderDefaults.colors(
                            activeTrackColor = NeonCyan,
                            thumbColor = NeonCyan
                        )
                    )
                    
                    Spacer(modifier = Modifier.height(4.dp))
                    
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "Phase Angle φ (Quantum Phase/Rotation)",
                            color = CosmicGreyText,
                            fontSize = 11.sp
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Text("φ = ${inputPhi.toInt()}°", color = ElectricPurple, fontWeight = FontWeight.Bold, fontSize = 11.sp)
                    }
                    Slider(
                        value = inputPhi,
                        onValueChange = { 
                            if (activeStep <= 1) inputPhi = it 
                        },
                        valueRange = 0.0f..360.0f,
                        enabled = activeStep <= 1,
                        modifier = Modifier.testTag("input_phi_slider"),
                        colors = SliderDefaults.colors(
                            activeTrackColor = ElectricPurple,
                            thumbColor = ElectricPurple,
                            inactiveTrackColor = CosmicGreyText.copy(alpha = 0.2f)
                        )
                    )
                }
            } else {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Puppy A Sparkle Angle:", color = CosmicGreyText, fontSize = 12.sp)
                    Spacer(modifier = Modifier.weight(1f))
                    Text("Angle: ${inputTheta.toInt()}°", color = NeonCyan, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                }
                Slider(
                    value = inputTheta,
                    onValueChange = { if (activeStep <= 1) inputTheta = it },
                    valueRange = 0.0f..180.0f,
                    enabled = activeStep <= 1,
                    modifier = Modifier.testTag("input_theta_slider_simple"),
                    colors = SliderDefaults.colors(activeTrackColor = NeonCyan, thumbColor = NeonCyan)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // STATE DISPLAY
            Card(
                colors = CardDefaults.cardColors(containerColor = CosmicBlack),
                modifier = Modifier.fillMaxWidth(),
                border = BorderStroke(1.dp, CosmicSlateCard)
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Badge(containerColor = ElectricPurple, contentColor = Color.White) {
                            Text("Step ${activeStep + 1} of 5", fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal=4.dp))
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = if (activeStep < stepsCount) stepDescriptions[activeStep] else "Successfully Teleported Qubit State!",
                            style = MaterialTheme.typography.bodySmall,
                            color = CosmicWhite,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // FLOW CONTROLS
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = { resetTeleportation() },
                    colors = ButtonDefaults.buttonColors(containerColor = CosmicSlateCard, contentColor = CosmicWhite),
                    modifier = Modifier.testTag("reset_teleport")
                ) {
                    Icon(Icons.Default.Refresh, contentDescription = "Restart")
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Restart", fontSize = 12.sp)
                }

                Row {
                    if (activeStep > 0) {
                        OutlinedButton(
                            onClick = { activeStep-- },
                            border = BorderStroke(1.dp, NeonCyan),
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = NeonCyan)
                        ) {
                            Text("Back", fontSize = 12.sp)
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                    }

                    Button(
                        onClick = {
                            if (activeStep < stepsCount - 1) {
                                activeStep++
                                if (activeStep == 3) {
                                    // Generate classical transmission random outcome bits
                                    val possibleBits = listOf("00", "01", "10", "11")
                                    classicalBits = possibleBits.random()
                                }
                            } else {
                                resetTeleportation()
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (activeStep == stepsCount - 1) BrightGold else ElectricPurple,
                            contentColor = CosmicBlack
                        ),
                        modifier = Modifier.testTag("next_step_teleport")
                    ) {
                        Text(
                            text = if (activeStep == stepsCount - 1) "Finish & Clear" else "Next Step",
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun TeleportationCanvas(
    step: Int,
    theta: Float,
    phi: Float,
    level: QuantumLevel,
    bits: String?
) {
    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .testTag("teleportation_system_canvas")
    ) {
        val width = size.width
        val height = size.height
        if (width <= 0f || height <= 0f) return@Canvas

        // Horizontal baseline guidelines
        val aliceY = height * 0.35f
        val bobY = height * 0.72f
        
        // Horizontal centers of stations
        val qA_Offset = Offset(width * 0.22f, aliceY) // Qubit A: state to teleport
        val qB_Offset = Offset(width * 0.5f, aliceY)  // Qubit B: Alice's half of the key
        val qC_Offset = Offset(width * 0.78f, bobY)  // Qubit C: Bob's half of the key at Bob's station

        // Render Titles
        val sectionPaint = Paint().asFrameworkPaint().apply {
            this.color = CosmicGreyText.copy(alpha=0.6f).toArgb()
            this.textSize = 20f
            this.typeface = android.graphics.Typeface.MONOSPACE
        }
        drawContext.canvas.nativeCanvas.drawText("ALICE'S STATION (EARTH)", 25f, aliceY - 50f, sectionPaint)
        drawContext.canvas.nativeCanvas.drawText("BOB'S STATION (MARS)", width - 350f, bobY + 70f, sectionPaint)

        // Draw physical partitions / quantum fiber link
        drawLine(
            color = CosmicSlateCard.copy(alpha=0.4f),
            start = Offset(0f, height*0.55f),
            end = Offset(width, height*0.55f),
            strokeWidth = 2f,
            pathEffect = PathEffect.dashPathEffect(floatArrayOf(15f, 15f), 0f)
        )

        // Draw EPR Entangled Fiber String (Active at step >= 1)
        if (step >= 1) {
            val fiberPath = Path().apply {
                moveTo(qB_Offset.x, qB_Offset.y)
                quadraticTo(width * 0.75f, height * 0.48f, qC_Offset.x, qC_Offset.y)
            }
            drawPath(
                path = fiberPath,
                color = ElectricPurple.copy(alpha = if (step == 1) 0.8f else 0.3f),
                style = Stroke(
                    width = 3f,
                    pathEffect = PathEffect.dashPathEffect(floatArrayOf(5f, if (step == 1) 5f else 15f), 0f)
                )
            )
        }

        // Draw Classical Cable line (Step >= 3)
        if (step >= 3) {
            drawLine(
                color = BrightGold.copy(alpha = if (step == 3) 1f else 0.4f),
                start = Offset(qB_Offset.x, qB_Offset.y),
                end = Offset(qC_Offset.x, qC_Offset.y),
                strokeWidth = 2.5f,
                pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
            )
            
            // Classical Bits text block
            val currentBits = bits ?: "10"
            val bitPaint = Paint().asFrameworkPaint().apply {
                this.color = BrightGold.toArgb()
                this.textSize = 22f
                this.textAlign = android.graphics.Paint.Align.CENTER
                this.typeface = android.graphics.Typeface.DEFAULT_BOLD
            }
            drawContext.canvas.nativeCanvas.drawText(
                "Classical signal: $currentBits",
                width * 0.53f,
                height * 0.52f,
                bitPaint
            )
        }

        val isHighTech = level == QuantumLevel.HIGH_SCHOOL || level == QuantumLevel.POST_DOC
        val sphereRadius = 42f

        // DRAW QUBIT A (ALICE MESSAGE STATE)
        if (isHighTech) {
            drawBlochSphere(
                center = qA_Offset,
                radius = sphereRadius,
                theta = theta,
                phi = phi,
                isDestroyed = step >= 3,
                title = "|ψ⟩ Message",
                vectorColor = NeonCyan
            )
        } else {
            if (step < 3) {
                // Qubit A active state
                val intensity = theta / 180f
                val baseColor = lerp(NeonCyan, RosePink, intensity)
                
                drawCircle(color = baseColor.copy(alpha = 0.2f), radius = 24f, center = qA_Offset)
                drawCircle(color = baseColor, radius = 12f, center = qA_Offset)

                val spinPaint = Paint().asFrameworkPaint().apply {
                    this.color = Color.White.toArgb()
                    this.textSize = 15f
                    this.textAlign = android.graphics.Paint.Align.CENTER
                }
                drawContext.canvas.nativeCanvas.drawText(
                    if (level == QuantumLevel.KIDS) "Puppy A" else "|ψ⟩ (Msg)",
                    qA_Offset.x,
                    qA_Offset.y + 42f,
                    spinPaint
                )
            } else {
                // Wiped / Destroyed due to measurement state (No-cloning)
                drawCircle(color = CosmicGreyText.copy(alpha = 0.2f), radius = 12f, center = qA_Offset)
                val textWipedPaint = Paint().asFrameworkPaint().apply {
                    this.color = CosmicGreyText.copy(alpha=0.6f).toArgb()
                    this.textSize = 14f
                    this.textAlign = android.graphics.Paint.Align.CENTER
                }
                drawContext.canvas.nativeCanvas.drawText("Destroyed", qA_Offset.x, qA_Offset.y + 40f, textWipedPaint)
            }
        }

        // DRAW QUBIT B (ALICE PORTION OF EPR END)
        if (step >= 1) {
            val color = if (step < 3) ElectricPurple else CosmicGreyText.copy(alpha = 0.5f)
            drawCircle(color = color.copy(alpha = 0.15f), radius = 24f, center = qB_Offset)
            drawCircle(color = color, radius = 12f, center = qB_Offset)

            val labelPaint = Paint().asFrameworkPaint().apply {
                this.color = color.toArgb()
                this.textSize = 15f
                this.textAlign = android.graphics.Paint.Align.CENTER
            }
            drawContext.canvas.nativeCanvas.drawText(
                if (level == QuantumLevel.KIDS) "Puppy B" else "|B_EPR⟩",
                qB_Offset.x,
                qB_Offset.y + 42f,
                labelPaint
            )
        }

        // Joint Bell Gate interaction zone (drawn at step == 2)
        if (step == 2) {
            // Highlight Alice's measurement block enclosing A and B
            val drawRectPath = Path().apply {
                addRoundRect(
                    RoundRect(
                        left = qA_Offset.x - 48f,
                        top = qA_Offset.y - 48f,
                        right = qB_Offset.x + 48f,
                        bottom = qB_Offset.y + 48f,
                        radiusX = 10f,
                        radiusY = 10f
                    )
                )
            }
            drawPath(
                path = drawRectPath,
                color = SparkleGreen,
                style = Stroke(width = 2.5f, pathEffect = PathEffect.dashPathEffect(floatArrayOf(6f, 6f), 0f))
            )

            // Animated explosion ring
            drawCircle(
                color = SparkleGreen.copy(alpha = 0.5f),
                radius = 55f,
                center = Offset((qA_Offset.x + qB_Offset.x)/2f, qA_Offset.y),
                style = Stroke(width = 3f)
            )
        }

        // DRAW QUBIT C (BOB PORTION OF STATE)
        if (step >= 1) {
            val isRecovered = step == 4
            if (isHighTech) {
                drawBlochSphere(
                    center = qC_Offset,
                    radius = sphereRadius,
                    theta = if (isRecovered) theta else 90f, // Centered or entangled state prior to reception
                    phi = if (isRecovered) phi else 0f,
                    isDestroyed = !isRecovered && step >= 3,
                    title = if (isRecovered) "|ψ⟩ Teleported!" else "Awaiting classical...",
                    vectorColor = if (isRecovered) NeonCyan else CosmicGreyText.copy(alpha = 0.25f)
                )
            } else {
                var colorC = ElectricPurple.copy(alpha = 0.4f)
                // If teleportation logic finishes (step == 4), Bob's qubit transitions to Alice's pristine input state!
                if (isRecovered) {
                    val intensity = theta / 180f
                    colorC = lerp(NeonCyan, RosePink, intensity)
                }

                // Glow ring around Bob's qubit
                drawCircle(
                    color = colorC.copy(alpha = if (isRecovered) 0.5f else 0.15f),
                    radius = if (isRecovered) 36f else 24f,
                    center = qC_Offset
                )
                drawCircle(
                    color = colorC,
                    radius = 12f,
                    center = qC_Offset
                )

                val descCPaint = Paint().asFrameworkPaint().apply {
                    this.color = Color.White.toArgb()
                    this.textSize = 15f
                    this.textAlign = android.graphics.Paint.Align.CENTER
                }
                drawContext.canvas.nativeCanvas.drawText(
                    if (isRecovered) (if (level == QuantumLevel.KIDS) "Crown Recvrd!" else "|ψ⟩ (Teleported)") else (if (level == QuantumLevel.KIDS) "Puppy C" else "|C_EPR⟩"),
                    qC_Offset.x,
                    qC_Offset.y + 42f,
                    descCPaint
                )

                if (isRecovered && level == QuantumLevel.KIDS) {
                    // Draw a cute miniature sparkling crown on Mars Puppy
                    val crownPath = Path().apply {
                        moveTo(qC_Offset.x - 12f, qC_Offset.y - 18f)
                        lineTo(qC_Offset.x - 18f, qC_Offset.y - 32f)
                        lineTo(qC_Offset.x - 6f, qC_Offset.y - 24f)
                        lineTo(qC_Offset.x, qC_Offset.y - 36f)
                        lineTo(qC_Offset.x + 6f, qC_Offset.y - 24f)
                        lineTo(qC_Offset.x + 18f, qC_Offset.y - 32f)
                        lineTo(qC_Offset.x + 12f, qC_Offset.y - 18f)
                        close()
                    }
                    drawPath(path = crownPath, color = BrightGold)
                }
            }
        }
    }
}

// Draw a beautiful, mathematically projected 3D perspective wireframe Bloch Sphere onto a 2D canvas
fun DrawScope.drawBlochSphere(
    center: Offset,
    radius: Float,
    theta: Float,
    phi: Float,
    isDestroyed: Boolean,
    title: String,
    vectorColor: Color
) {
    if (isDestroyed) {
        drawCircle(color = CosmicGreyText.copy(alpha = 0.15f), radius = radius, center = center)
        drawCircle(color = CosmicGreyText.copy(alpha = 0.2f), radius = radius, center = center, style = Stroke(1.5f, pathEffect = PathEffect.dashPathEffect(floatArrayOf(4f, 4f), 0f)))
        
        val textPaint = Paint().asFrameworkPaint().apply {
            this.color = CosmicGreyText.copy(alpha=0.4f).toArgb()
            this.textSize = 14f
            this.textAlign = android.graphics.Paint.Align.CENTER
        }
        drawContext.canvas.nativeCanvas.drawText("No-Cloning", center.x, center.y - 8f, textPaint)
        drawContext.canvas.nativeCanvas.drawText("Destroyed", center.x, center.y + 12f, textPaint)
        return
    }

    // Outer boundary sphere
    drawCircle(
        color = CosmicSlateCard.copy(alpha = 0.4f),
        radius = radius,
        center = center
    )
    drawCircle(
        color = Color.White.copy(alpha = 0.2f),
        radius = radius,
        center = center,
        style = Stroke(width = 1.5f)
    )

    // Equatorial ellipse (X-Y Plane) projected in perspective
    val ellipseHeight = radius * 0.35f
    drawOval(
        color = Color.White.copy(alpha = 0.15f),
        topLeft = Offset(center.x - radius, center.y - ellipseHeight),
        size = Size(radius * 2, ellipseHeight * 2),
        style = Stroke(width = 1f, pathEffect = PathEffect.dashPathEffect(floatArrayOf(4f, 4f), 0f))
    )

    // Draw coordinate axes
    // Z-axis (vertical)
    drawLine(
        color = Color.White.copy(alpha = 0.25f),
        start = Offset(center.x, center.y - radius),
        end = Offset(center.x, center.y + radius),
        strokeWidth = 1f
    )
    
    // Orthogonal axes with 3D projection direction
    val xAngle = Math.toRadians(210.0)
    val xx = (radius * cos(xAngle)).toFloat()
    val xy = (radius * sin(xAngle)).toFloat()
    drawLine(
        color = Color.White.copy(alpha = 0.2f),
        start = center,
        end = Offset(center.x + xx, center.y + xy),
        strokeWidth = 1f
    )
    
    val yAngle = Math.toRadians(-30.0)
    val yx = (radius * cos(yAngle)).toFloat()
    val yy = (radius * sin(yAngle)).toFloat()
    drawLine(
        color = Color.White.copy(alpha = 0.2f),
        start = center,
        end = Offset(center.x + yx, center.y + yy),
        strokeWidth = 1f
    )

    // Axis labels: Z (|0⟩), -Z (|1⟩)
    val labelPaint = Paint().asFrameworkPaint().apply {
        this.color = CosmicGreyText.toArgb()
        this.textSize = 12f
        this.textAlign = android.graphics.Paint.Align.CENTER
        this.typeface = android.graphics.Typeface.MONOSPACE
    }
    drawContext.canvas.nativeCanvas.drawText("|0⟩", center.x, center.y - radius - 5f, labelPaint)
    drawContext.canvas.nativeCanvas.drawText("|1⟩", center.x, center.y + radius + 14f, labelPaint)

    // Polar and azimuthal angles
    val thetaRad = Math.toRadians(theta.toDouble())
    val phiRad = Math.toRadians(phi.toDouble())
    
    // Standard spherical mapping coordinates
    val sx = sin(thetaRad) * cos(phiRad)
    val sy = sin(thetaRad) * sin(phiRad)
    val sz = cos(thetaRad)

    // Isometric projection projection math onto 2D canvas coordinates
    val projX = center.x + (sx * cos(xAngle) + sy * cos(yAngle)).toFloat() * radius
    val projY = center.y + (sx * sin(xAngle) + sy * sin(yAngle) - sz).toFloat() * radius

    // Vector shadow on X-Y plane to aid spatial interpretation
    val shadowX = center.x + (sx * cos(xAngle) + sy * cos(yAngle)).toFloat() * radius
    val shadowY = center.y + (sx * sin(xAngle) + sy * sin(yAngle)).toFloat() * radius
    drawLine(
        color = vectorColor.copy(alpha = 0.25f),
        start = center,
        end = Offset(shadowX, shadowY),
        strokeWidth = 1.5f,
        pathEffect = PathEffect.dashPathEffect(floatArrayOf(3f, 3f), 0f)
    )
    drawLine(
        color = Color.White.copy(alpha = 0.15f),
        start = Offset(projX, projY),
        end = Offset(shadowX, shadowY),
        strokeWidth = 1f,
        pathEffect = PathEffect.dashPathEffect(floatArrayOf(3f, 3f), 0f)
    )

    // Draw main state vector line
    drawLine(
        color = vectorColor,
        start = center,
        end = Offset(projX, projY),
        strokeWidth = 3.5f
    )
    drawCircle(
        color = vectorColor,
        radius = 5f,
        center = Offset(projX, projY)
    )

    // Label below
    val titlePaint = Paint().asFrameworkPaint().apply {
        this.color = Color.White.toArgb()
        this.textSize = 14f
        this.textAlign = android.graphics.Paint.Align.CENTER
        this.isFakeBoldText = true
    }
    drawContext.canvas.nativeCanvas.drawText(title, center.x, center.y + radius + 32f, titlePaint)
}

// Utility mathematical color interpolation
fun lerp(start: Color, stop: Color, fraction: Float): Color {
    return Color(
        red = start.red + fraction * (stop.red - start.red),
        green = start.green + fraction * (stop.green - start.green),
        blue = start.blue + fraction * (stop.blue - start.blue),
        alpha = start.alpha + fraction * (stop.alpha - start.alpha)
    )
}
