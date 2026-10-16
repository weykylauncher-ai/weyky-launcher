package com.example.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.device.apps.AppMatch
import com.example.ui.adaptive.DeviceProfile
import com.example.ui.theme.JarvisHudBadge
import com.example.ui.theme.JarvisHudCard
import com.example.ui.theme.JarvisHudColors
import com.example.ui.theme.jarvisTacticalBackground

@Composable
fun AppSurfaceOverlay(
  apps: List<AppMatch>,
  deviceProfile: DeviceProfile,
  isLightMode: Boolean,
  backgroundColor: Color,
  borderColor: Color,
  onClose: () -> Unit,
  onLaunchApp: (AppMatch) -> Unit,
  modifier: Modifier = Modifier
) {
  val panelBg = JarvisHudColors.panelBg(isLightMode)
  val panelBgElevated = JarvisHudColors.panelBgElevated(isLightMode)
  val textPrimary = JarvisHudColors.textPrimary(isLightMode)
  val textSecondary = JarvisHudColors.textSecondary(isLightMode)

  var searchQuery by remember { mutableStateOf("") }
  val filteredApps = remember(searchQuery, apps) {
    if (searchQuery.isBlank()) {
      apps.sortedBy { it.label.lowercase() }
    } else {
      apps.filter {
        it.label.contains(searchQuery, ignoreCase = true) ||
          it.packageName.contains(searchQuery, ignoreCase = true)
      }.sortedBy { it.label.lowercase() }
    }
  }

  // Adaptive grid columns based on window width
  val gridColumns = when {
    deviceProfile.windowWidthDp >= 900.dp -> 3
    deviceProfile.windowWidthDp >= 600.dp -> 2
    else -> 1
  }

  Surface(
    modifier = modifier
      .fillMaxSize()
      .testTag("app_surface_overlay"),
    color = backgroundColor.copy(alpha = 0.97f)
  ) {
    Column(
      modifier = Modifier
        .fillMaxSize()
        .jarvisTacticalBackground(backgroundColor = backgroundColor, accentColor = borderColor)
        .padding(
          horizontal = deviceProfile.contentPaddingHorizontalDp,
          vertical = deviceProfile.contentPaddingVerticalDp + 8.dp
        )
    ) {
      // Tactical App Matrix Header
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(bottom = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Column {
          Text(
            text = "SYS.MODULES // INSTALLED APPS",
            fontSize = 10.sp,
            fontFamily = FontFamily.Monospace,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.5.sp,
            color = borderColor
          )
          Text(
            text = "التطبيقات المثبتة (${apps.size})",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = textPrimary
          )
        }
        JarvisHudBadge(
          accentColor = JarvisHudColors.CrimsonAlert,
          containerColor = if (isLightMode) Color(0xFFFFE4EA) else Color(0xD91A040A),
          cutSize = 8.dp
        ) {
          IconButton(
            onClick = onClose,
            modifier = Modifier
              .size(48.dp)
              .testTag("close_app_drawer_button")
          ) {
            Icon(
              imageVector = Icons.Default.Close,
              contentDescription = "Close",
              tint = JarvisHudColors.CrimsonAlert
            )
          }
        }
      }

      // Tactical Search Box
      OutlinedTextField(
        value = searchQuery,
        onValueChange = { searchQuery = it },
        placeholder = {
          Text("ابحث عن تطبيق...", color = textSecondary, fontSize = 14.sp)
        },
        leadingIcon = {
          Icon(Icons.Default.Search, contentDescription = null, tint = borderColor)
        },
        trailingIcon = {
          if (searchQuery.isNotEmpty()) {
            IconButton(onClick = { searchQuery = "" }, modifier = Modifier.size(48.dp)) {
              Icon(Icons.Default.Close, contentDescription = "Clear", tint = textSecondary)
            }
          }
        },
        singleLine = true,
        colors = OutlinedTextFieldDefaults.colors(
          focusedContainerColor = panelBg,
          unfocusedContainerColor = panelBgElevated,
          focusedBorderColor = borderColor,
          unfocusedBorderColor = borderColor.copy(alpha = 0.35f),
          focusedTextColor = textPrimary,
          unfocusedTextColor = textPrimary
        ),
        shape = CutCornerShape(12.dp),
        modifier = Modifier
          .fillMaxWidth()
          .testTag("app_search_field")
      )

      Spacer(modifier = Modifier.height(12.dp))

      // Adaptive Multi-Column Apps Grid
      LazyVerticalGrid(
        columns = GridCells.Fixed(gridColumns),
        modifier = Modifier
          .fillMaxWidth()
          .weight(1f)
          .testTag("apps_list"),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
      ) {
        items(filteredApps, key = { it.packageName }) { app ->
          JarvisHudCard(
            accentColor = borderColor,
            containerColor = panelBg,
            cutCornerSize = 12.dp,
            onClick = { onLaunchApp(app) },
            modifier = Modifier
              .fillMaxWidth()
              .testTag("app_item_${app.packageName}")
          ) {
            Row(
              modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 12.dp),
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.SpaceBetween
            ) {
              Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
              ) {
                JarvisHudBadge(
                  accentColor = borderColor,
                  containerColor = panelBgElevated,
                  cutSize = 6.dp,
                  modifier = Modifier.size(38.dp)
                ) {
                  Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                      text = app.label.firstOrNull()?.uppercase() ?: "?",
                      fontSize = 15.sp,
                      fontFamily = FontFamily.Monospace,
                      fontWeight = FontWeight.Bold,
                      color = borderColor
                    )
                  }
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                  Text(
                    text = app.label,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = textPrimary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                  )
                  Text(
                    text = app.packageName,
                    fontSize = 11.sp,
                    fontFamily = FontFamily.Monospace,
                    color = textSecondary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                  )
                }
              }
              Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = null,
                tint = borderColor,
                modifier = Modifier.size(18.dp)
              )
            }
          }
        }
      }
    }
  }
}
