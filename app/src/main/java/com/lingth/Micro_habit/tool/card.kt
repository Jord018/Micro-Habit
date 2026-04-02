package com.lingth.Micro_habit.tool

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lingth.Micro_habit.model.Habit
import com.lingth.Micro_habit.ui.GlassCard
import com.lingth.Micro_habit.ui.IconBox

@Composable
fun HabitCard(
    habit: Habit,
    onToggle: () -> Unit
) {
    GlassCard(modifier = Modifier.fillMaxWidth()) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconBox(habit.icon, habit.color)
            Column(modifier = Modifier.weight(1f).padding(horizontal = 12.dp)) {
                Text(habit.title, fontWeight = FontWeight.Bold)
                Text(habit.description, fontSize = 12.sp, color = Color.Gray)
            }
            // Switch หรือ Checkbox
            Switch(checked = habit.isCompleted, onCheckedChange = { onToggle() })
        }
    }
}
