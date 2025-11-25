package com.example.lecturetranscriber

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.outlined.Assignment
import androidx.compose.material.icons.filled.CloudUpload
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.outlined.GraphicEq
import androidx.compose.material.icons.outlined.Mic
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lecturetranscriber.ui.theme.AccentGreen
import com.example.lecturetranscriber.ui.theme.AccentRed
import com.example.lecturetranscriber.ui.theme.DeepViolet
import com.example.lecturetranscriber.ui.theme.DividerGrey
import com.example.lecturetranscriber.ui.theme.GradientBackground
import com.example.lecturetranscriber.ui.theme.LectureTranscriberTheme
import com.example.lecturetranscriber.ui.theme.LightGrey
import com.example.lecturetranscriber.ui.theme.PendingOrange
import com.example.lecturetranscriber.ui.theme.RoyalBlue
import com.example.lecturetranscriber.ui.theme.SubtitleGrey
import com.example.lecturetranscriber.ui.theme.TextGrey

class LectureTranscriberActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { LectureTranscriberApp() }
    }
}

@Composable
fun LectureTranscriberApp() {
    LectureTranscriberTheme {
        var selected by remember { mutableStateOf(BottomNavDestination.Home) }

        Scaffold(
            modifier = Modifier.fillMaxSize(),
            bottomBar = {
                BottomNavigationBar(
                    selected = selected,
                    onSelected = { selected = it }
                )
            }
        ) { paddingValues ->
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                when (selected) {
                    BottomNavDestination.Home -> HomeScreen(
                        onNavigateTo = { selected = it }
                    )
                    BottomNavDestination.Record -> RecordingScreen(
                        onBack = { selected = BottomNavDestination.Home },
                        onStop = { selected = BottomNavDestination.History },
                        onPause = { /* Handle pause logic */ }
                    )
                    BottomNavDestination.History -> HistoryScreen(
                        onBack = { selected = BottomNavDestination.Home }
                    )
                }
            }
        }
    }
}

private enum class BottomNavDestination(val label: String, val icon: ImageVector) {
    Home("Home", Icons.Filled.Home),
    Record("Record", Icons.Filled.Mic),
    History("History", Icons.Filled.History)
}

@Composable
private fun BottomNavigationBar(
    selected: BottomNavDestination,
    onSelected: (BottomNavDestination) -> Unit
) {
    NavigationBar(
        modifier = Modifier.navigationBarsPadding()
    ) {
        BottomNavDestination.entries.forEach { destination ->
            NavigationBarItem(
                selected = destination == selected,
                onClick = { onSelected(destination) },
                icon = {
                    Icon(
                        imageVector = destination.icon,
                        contentDescription = destination.label
                    )
                },
                label = { Text(destination.label) }
            )
        }
    }
}

@Composable
private fun HomeScreen(
    onNavigateTo: (BottomNavDestination) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = GradientBackground)
            .padding(horizontal = 24.dp, vertical = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Box(
            modifier = Modifier
                .size(180.dp)
                .background(
                    color = Color.White.copy(alpha = 0.15f),
                    shape = RoundedCornerShape(48.dp)
                )
                .border(
                    BorderStroke(1.dp, Color.White.copy(alpha = 0.2f)),
                    shape = RoundedCornerShape(48.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Outlined.Mic,
                contentDescription = "Hero mic",
                tint = Color.White,
                modifier = Modifier.size(96.dp)
            )
        }
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = "Lecture Transcribing",
            style = MaterialTheme.typography.displaySmall,
            color = Color.White,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Turn lectures into organized notes",
            style = MaterialTheme.typography.bodyLarge,
            color = Color.White.copy(alpha = 0.8f),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(32.dp))
        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            FeatureRow(
                icon = Icons.Outlined.Mic,
                title = "Record Lectures",
                subtitle = "Capture audio with high quality",
                onClick = { onNavigateTo(BottomNavDestination.Record) }
            )
            FeatureRow(
                icon = Icons.Outlined.GraphicEq,
                title = "Summarize Smarter",
                subtitle = "AI helps highlight the essentials",
                onClick = { onNavigateTo(BottomNavDestination.History) }
            )
            FeatureRow(
                icon = Icons.AutoMirrored.Outlined.Assignment,
                title = "Export Notes",
                subtitle = "Share transcripts anywhere",
                onClick = { onNavigateTo(BottomNavDestination.History) }
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        TextButton(
            onClick = { onNavigateTo(BottomNavDestination.Record) },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(32.dp)
                ),
            colors = ButtonDefaults.textButtonColors(
                contentColor = RoyalBlue,
                containerColor = Color.Transparent
            )
        ) {
            Text(
                text = "Get Started",
                style = MaterialTheme.typography.labelLarge.copy(fontSize = 18.sp)
            )
        }
    }
}

@Composable
private fun FeatureRow(
    icon: ImageVector,
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = Color.White.copy(alpha = 0.12f),
                shape = RoundedCornerShape(24.dp)
            )
            .clickable(onClick = onClick)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(56.dp)
                .background(
                    color = Color.White.copy(alpha = 0.18f),
                    shape = RoundedCornerShape(16.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = Color.White
            )
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White
                )
            )
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = Color.White.copy(alpha = 0.8f)
                )
            )
        }
    }
}

@Composable
private fun RecordingScreen(
    onBack: () -> Unit,
    onStop: () -> Unit,
    onPause: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 20.dp, vertical = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        RecordingHeader(onBack = onBack)
        Spacer(modifier = Modifier.height(40.dp))
        Text(
            text = "00:00",
            style = MaterialTheme.typography.displaySmall.copy(
                fontSize = 48.sp,
                color = Color(0xFF1B1E28)
            )
        )
        Text(
            text = "Recording time",
            style = MaterialTheme.typography.bodyMedium.copy(color = SubtitleGrey)
        )
        Spacer(modifier = Modifier.height(32.dp))
        WaveformVisualizer()
        Spacer(modifier = Modifier.height(40.dp))
        RecordingButton()
        Spacer(modifier = Modifier.height(32.dp))
        UploadCard()
        Spacer(modifier = Modifier.weight(1f))
        PlaybackControls(onStop = onStop, onPause = onPause)
    }
}

@Composable
private fun RecordingHeader(
    onBack: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onBack) {
            Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
        }
        Text(
            text = "New Recording",
            style = MaterialTheme.typography.headlineSmall
        )
        Icon(imageVector = Icons.Filled.Settings, contentDescription = "Settings")
    }
}

@Composable
private fun WaveformVisualizer() {
    val bars = listOf(20, 48, 32, 56, 28, 44, 18, 60, 36, 50)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        bars.forEachIndexed { _, height ->
            val gradient = Brush.verticalGradient(
                colors = listOf(RoyalBlue, DeepViolet),
                startY = 0f,
                endY = height * 10f
            )
            Box(
                modifier = Modifier
                    .width(8.dp)
                    .height(height.dp)
                    .background(brush = gradient, shape = RoundedCornerShape(4.dp))
            )
        }
    }
}

@Composable
private fun RecordingButton() {
    Box(
        modifier = Modifier
            .size(220.dp),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(220.dp)
                .background(
                    color = AccentRed.copy(alpha = 0.12f),
                    shape = CircleShape
                )
        )
        Box(
            modifier = Modifier
                .size(160.dp)
                .shadow(16.dp, CircleShape, clip = false)
                .background(color = AccentRed, shape = CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Outlined.Mic,
                contentDescription = "Record",
                tint = Color.White,
                modifier = Modifier.size(64.dp)
            )
        }
    }
}

@Composable
private fun UploadCard() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                BorderStroke(2.dp, color = DividerGrey),
                shape = RoundedCornerShape(20.dp)
            )
            .padding(horizontal = 24.dp, vertical = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Filled.CloudUpload,
            contentDescription = "Upload",
            tint = RoyalBlue,
            modifier = Modifier.size(42.dp)
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = "Or upload an audio file",
            style = MaterialTheme.typography.bodyLarge.copy(color = TextGrey)
        )
        Text(
            text = "Browse Files",
            color = RoyalBlue,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun PlaybackControls(
    onStop: () -> Unit,
    onPause: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        FilledIconButton(
            onClick = onPause,
            modifier = Modifier.size(64.dp),
            colors = IconButtonDefaults.filledTonalIconButtonColors(containerColor = LightGrey)
        ) {
            Icon(imageVector = Icons.Filled.Timer, contentDescription = "Pause")
        }
        FilledIconButton(
            onClick = onStop,
            modifier = Modifier.size(64.dp),
            colors = IconButtonDefaults.filledIconButtonColors(containerColor = RoyalBlue)
        ) {
            Icon(imageVector = Icons.Filled.Mic, contentDescription = "Stop", tint = Color.White)
        }
    }
}

@Composable
private fun HistoryScreen(
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = LightGrey)
            .padding(vertical = 24.dp)
    ) {
        HistoryHeader(onBack = onBack)
        Spacer(modifier = Modifier.height(16.dp))
        HistoryFilterRow()
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            historyGrouped.entries.forEach { (section, entries) ->
                item {
                    Text(
                        text = section,
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = SubtitleGrey,
                            fontWeight = FontWeight.SemiBold
                        ),
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                }
                items(entries) { item ->
                    HistoryCard(item)
                }
            }
        }
    }
}

@Composable
private fun HistoryHeader(
    onBack: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onBack) {
            Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
        }
        Text(
            text = "History",
            style = MaterialTheme.typography.headlineSmall
        )
        Icon(imageVector = Icons.Outlined.Search, contentDescription = "Search")
    }
}

@Composable
private fun HistoryFilterRow() {
    val filters = listOf("All", "Today", "This Week", "This Month")
    var selected by remember { mutableStateOf(filters.first()) }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        filters.forEach { filter ->
            val isSelected = filter == selected
            Text(
                text = filter,
                modifier = Modifier
                    .background(
                        color = if (isSelected) RoyalBlue else Color.White,
                        shape = RoundedCornerShape(32.dp)
                    )
                    .border(
                        BorderStroke(1.dp, color = DividerGrey),
                        shape = RoundedCornerShape(32.dp)
                    )
                    .clickable { selected = filter }
                    .padding(horizontal = 20.dp, vertical = 10.dp),
                color = if (isSelected) Color.White else TextGrey,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
            )
        }
    }
}

private val historyGrouped = linkedMapOf(
    "Today" to listOf(
        HistoryItem(
            course = "PSPA210",
            words = "15,000 words",
            time = "2:34 PM",
            duration = "01:14:26",
            status = HistoryStatus.Completed
        ),
        HistoryItem(
            course = "ENG310",
            words = "8,500 words",
            time = "10:10 AM",
            duration = "00:32:18",
            status = HistoryStatus.Pending
        )
    ),
    "Yesterday" to listOf(
        HistoryItem(
            course = "MATH130",
            words = "12,340 words",
            time = "4:12 PM",
            duration = "00:58:03",
            status = HistoryStatus.Completed
        )
    )
)

@Composable
private fun HistoryCard(item: HistoryItem) {
    ElevatedCard(
        shape = RoundedCornerShape(24.dp),
        modifier = Modifier.fillMaxWidth(),
        colors = androidx.compose.material3.CardDefaults.elevatedCardColors(
            containerColor = Color.White
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            StatusIcon(item.status)
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = item.course,
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold)
                )
                Text(
                    text = item.words,
                    style = MaterialTheme.typography.bodyMedium.copy(color = TextGrey)
                )
                Text(
                    text = item.time,
                    style = MaterialTheme.typography.bodySmall.copy(color = SubtitleGrey)
                )
            }
            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = item.duration,
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold)
                )
                StatusBadge(item.status)
            }
        }
    }
}

@Composable
private fun StatusIcon(status: HistoryStatus) {
    val (bg, icon) = when (status) {
        HistoryStatus.Completed -> Color(0xFFE0F5E8) to Icons.Filled.History
        HistoryStatus.Pending -> Color(0xFFFFF0E0) to Icons.Filled.Timer
    }
    Box(
        modifier = Modifier
            .size(56.dp)
            .background(color = bg, shape = CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Icon(imageVector = icon, contentDescription = null, tint = RoyalBlue)
    }
}

@Composable
private fun StatusBadge(status: HistoryStatus) {
    val (label, background, textColor) = when (status) {
        HistoryStatus.Completed -> Triple("Completed", Color(0xFFE0F5E8), AccentGreen)
        HistoryStatus.Pending -> Triple("Pending", Color(0xFFFFF0E0), PendingOrange)
    }
    Text(
        text = label,
        color = textColor,
        modifier = Modifier
            .background(color = background, shape = RoundedCornerShape(24.dp))
            .padding(horizontal = 16.dp, vertical = 6.dp),
        style = MaterialTheme.typography.labelLarge
    )
}

private data class HistoryItem(
    val course: String,
    val words: String,
    val time: String,
    val duration: String,
    val status: HistoryStatus
)

private enum class HistoryStatus {
    Completed,
    Pending
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun TranscriberPreview() {
    LectureTranscriberApp()
}
