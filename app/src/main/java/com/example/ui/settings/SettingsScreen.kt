package com.example.ui.settings

import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.BatterySaver
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Home
import androidx.compose.foundation.BorderStroke
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Sensors
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.core.state.WeykyUiState
import com.example.ui.theme.JarvisHudBadge
import com.example.ui.theme.JarvisHudCard
import com.example.ui.theme.JarvisHudColors
import com.example.ui.theme.jarvisTacticalBackground
import java.util.Locale

@Composable
fun SettingsScreen(
  uiState: WeykyUiState,
  onBack: () -> Unit,
  onTogglePowerSaving: () -> Unit,
  onUpdateCoreScale: (Float) -> Unit,
  onSetLightMode: (Boolean) -> Unit = {},
  onUpdateBackgroundColor: (Long) -> Unit = {},
  onUpdateCardBorderColor: (Long) -> Unit = {},
  isNotificationAccessGranted: Boolean,
  onRequestDefaultLauncher: () -> Unit = {},
  onDownloadModel: (com.example.core.voice.model.VoiceModelInfo) -> Unit = {},
  onInstallModel: (com.example.core.voice.model.VoiceModelInfo) -> Unit = {},
  onInstallModelFromFile: (com.example.core.voice.model.VoiceModelInfo, Uri) -> Unit = { _, _ -> },
  onDeleteModel: (com.example.core.voice.model.VoiceModelInfo) -> Unit = {},
  onSelectActiveTtsModel: (com.example.core.voice.model.VoiceModelInfo) -> Unit = {},
  onSetHybridTtsEnabled: (Boolean) -> Unit = {},
  onSetCloudTtsVoice: (String) -> Unit = {},
  onTestVoice: () -> Unit = {},
  onTestVoiceWithKey: (String, String) -> Unit = { _, _ -> },
  onToggleFloatingOverlay: (Boolean) -> Unit = {},
  onToggleNotificationPrivacy: (Boolean) -> Unit = {},
  onToggleHeadsetButton: (Boolean) -> Unit = {},
  modifier: Modifier = Modifier
) {
  BackHandler { onBack() }
  val context = LocalContext.current
  var pendingModelForFileInstall by remember {
    mutableStateOf<com.example.core.voice.model.VoiceModelInfo?>(null)
  }
  val modelZipPickerLauncher = rememberLauncherForActivityResult(
    contract = ActivityResultContracts.GetContent()
  ) { uri: Uri? ->
    val targetModel = pendingModelForFileInstall
    if (uri != null && targetModel != null) {
      onInstallModelFromFile(targetModel, uri)
    }
    pendingModelForFileInstall = null
  }
  val isLight = uiState.isLightMode
  val bgColor = Color(uiState.backgroundColorHex)
  val borderColor = Color(uiState.cardBorderColorHex)
  val panelBg = JarvisHudColors.panelBg(isLight)
  val textPrimary = JarvisHudColors.textPrimary(isLight)
  val textSecondary = JarvisHudColors.textSecondary(isLight)

  Column(
    modifier = modifier
      .fillMaxSize()
      .jarvisTacticalBackground(
        backgroundColor = bgColor,
        accentColor = borderColor
      )
      .statusBarsPadding()
      .navigationBarsPadding()
      .padding(horizontal = 20.dp)
      .verticalScroll(rememberScrollState())
      .testTag("settings_screen_root"),
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .widthIn(max = 840.dp)
    ) {
      // Tactical Top Bar
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 12.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      JarvisHudBadge(
        accentColor = borderColor,
        containerColor = panelBg,
        cutSize = 8.dp
      ) {
        IconButton(
          onClick = onBack,
          modifier = Modifier
            .size(48.dp)
            .testTag("settings_back_button")
        ) {
          Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = "Back",
            tint = borderColor
          )
        }
      }
      Column(modifier = Modifier.padding(start = 12.dp)) {
        Text(
          text = "SYS.CONFIG // TACTICAL PARAMETERS",
          fontSize = 9.sp,
          fontFamily = FontFamily.Monospace,
          fontWeight = FontWeight.Bold,
          letterSpacing = 1.5.sp,
          color = borderColor.copy(alpha = 0.8f)
        )
        Text(
          text = "إعدادات WEYKY",
          fontSize = 20.sp,
          fontWeight = FontWeight.Bold,
          color = textPrimary
        )
      }
    }

    Spacer(modifier = Modifier.height(12.dp))

    // SECTION 1: Core Visual Behavior & HUD Theme Customization
    SettingsGroup(
      title = "مظهر النواة والواجهة التكتيكية (HUD)",
      codeTag = "MOD.01 // APERTURE & HUD",
      icon = Icons.Default.Palette,
      accentColor = borderColor,
      containerColor = panelBg,
      textPrimary = textPrimary
    ) {
      Text(
        text = "حجم النواة في الشاشة: ${(uiState.coreScale * 100).toInt()}%",
        fontSize = 14.sp,
        fontFamily = FontFamily.Monospace,
        color = textPrimary
      )
      Slider(
        value = uiState.coreScale,
        onValueChange = onUpdateCoreScale,
        valueRange = 0.30f..0.60f,
        colors = SliderDefaults.colors(
          thumbColor = borderColor,
          activeTrackColor = borderColor,
          inactiveTrackColor = if (isLight) Color(0xFFD0DFEC) else Color(0xFF0A1E36)
        ),
        modifier = Modifier.testTag("core_scale_slider")
      )

      Spacer(modifier = Modifier.height(12.dp))

      // Theme Mode Selector (Dark Mode vs Light Mode) right beside App Color selection
      Text(
        text = "نمط المظهر العام (الوضع الداكن / الوضع الفاتح):",
        fontSize = 13.sp,
        fontWeight = FontWeight.SemiBold,
        color = textPrimary
      )
      Spacer(modifier = Modifier.height(8.dp))
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
      ) {
        val modeShape = CutCornerShape(topStart = 10.dp, bottomEnd = 10.dp, topEnd = 2.dp, bottomStart = 2.dp)

        // Dark Mode Option
        Box(
          modifier = Modifier
            .weight(1f)
            .clip(modeShape)
            .background(if (!isLight) borderColor.copy(alpha = 0.16f) else panelBg)
            .border(
              width = if (!isLight) 2.dp else 1.dp,
              color = if (!isLight) borderColor else textSecondary.copy(alpha = 0.35f),
              shape = modeShape
            )
            .clickable { onSetLightMode(false) }
            .padding(horizontal = 12.dp, vertical = 10.dp)
            .testTag("dark_mode_button"),
          contentAlignment = Alignment.Center
        ) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            Icon(
              imageVector = Icons.Default.DarkMode,
              contentDescription = "Dark Mode",
              tint = if (!isLight) borderColor else textSecondary,
              modifier = Modifier.size(18.dp)
            )
            Text(
              text = "الوضع الداكن",
              fontSize = 13.sp,
              fontWeight = if (!isLight) FontWeight.Bold else FontWeight.Medium,
              color = if (!isLight) textPrimary else textSecondary
            )
            if (!isLight) {
              Icon(
                imageVector = Icons.Default.Check,
                contentDescription = null,
                tint = borderColor,
                modifier = Modifier.size(15.dp)
              )
            }
          }
        }

        // Light Mode Option
        Box(
          modifier = Modifier
            .weight(1f)
            .clip(modeShape)
            .background(if (isLight) borderColor.copy(alpha = 0.16f) else panelBg)
            .border(
              width = if (isLight) 2.dp else 1.dp,
              color = if (isLight) borderColor else textSecondary.copy(alpha = 0.35f),
              shape = modeShape
            )
            .clickable { onSetLightMode(true) }
            .padding(horizontal = 12.dp, vertical = 10.dp)
            .testTag("light_mode_button"),
          contentAlignment = Alignment.Center
        ) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            Icon(
              imageVector = Icons.Default.LightMode,
              contentDescription = "Light Mode",
              tint = if (isLight) borderColor else textSecondary,
              modifier = Modifier.size(18.dp)
            )
            Text(
              text = "الوضع الفاتح",
              fontSize = 13.sp,
              fontWeight = if (isLight) FontWeight.Bold else FontWeight.Medium,
              color = if (isLight) textPrimary else textSecondary
            )
            if (isLight) {
              Icon(
                imageVector = Icons.Default.Check,
                contentDescription = null,
                tint = borderColor,
                modifier = Modifier.size(15.dp)
              )
            }
          }
        }
      }

      Spacer(modifier = Modifier.height(14.dp))

      // Background Color Selector
      Text(
        text = "لون خلفية التطبيق (Background):",
        fontSize = 13.sp,
        fontWeight = FontWeight.SemiBold,
        color = textPrimary
      )
      Spacer(modifier = Modifier.height(8.dp))
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        JarvisHudColors.BackgroundPresets.forEach { (label, hexValue) ->
          val isSelected = uiState.backgroundColorHex == hexValue
          val swatchColor = Color(hexValue)
          val isLightSwatch = (swatchColor.red + swatchColor.green + swatchColor.blue) / 3f > 0.5f
          val swatchTextColor = if (isLightSwatch) JarvisHudColors.LightTextPrimary else JarvisHudColors.TextPrimary
          val swatchShape = CutCornerShape(topStart = 8.dp, bottomEnd = 8.dp, topEnd = 2.dp, bottomStart = 2.dp)
          Box(
            modifier = Modifier
              .clip(swatchShape)
              .background(swatchColor)
              .border(
                width = if (isSelected) 2.dp else 1.dp,
                color = if (isSelected) borderColor else Color(0x557CA5B8),
                shape = swatchShape
              )
              .clickable { onUpdateBackgroundColor(hexValue) }
              .padding(horizontal = 12.dp, vertical = 8.dp)
              .testTag("bg_color_option_${hexValue.toString(16)}")
          ) {
            Row(
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
              Box(
                modifier = Modifier
                  .size(14.dp)
                  .background(swatchColor)
                  .border(1.dp, if (isLightSwatch) Color.Black.copy(alpha = 0.4f) else Color.White.copy(alpha = 0.5f))
              )
              Text(
                text = label,
                fontSize = 12.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                color = if (isSelected) borderColor else swatchTextColor
              )
              if (isSelected) {
                Icon(
                  imageVector = Icons.Default.Check,
                  contentDescription = null,
                  tint = borderColor,
                  modifier = Modifier.size(14.dp)
                )
              }
            }
          }
        }
      }

      Spacer(modifier = Modifier.height(14.dp))

      // Card Border Color Selector
      Text(
        text = "لون إطار البطاقات التكتيكية (Card Border):",
        fontSize = 13.sp,
        fontWeight = FontWeight.SemiBold,
        color = textPrimary
      )
      Spacer(modifier = Modifier.height(8.dp))
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        JarvisHudColors.BorderPresets.forEach { (label, hexValue) ->
          val isSelected = uiState.cardBorderColorHex == hexValue
          val presetColor = Color(hexValue)
          val swatchShape = CutCornerShape(topStart = 8.dp, bottomEnd = 8.dp, topEnd = 2.dp, bottomStart = 2.dp)
          Box(
            modifier = Modifier
              .clip(swatchShape)
              .background(if (isLight) Color(0xFFEAF2F8) else Color(0xCC030A14))
              .border(
                width = if (isSelected) 2.dp else 1.dp,
                color = if (isSelected) presetColor else presetColor.copy(alpha = 0.4f),
                shape = swatchShape
              )
              .clickable { onUpdateCardBorderColor(hexValue) }
              .padding(horizontal = 12.dp, vertical = 8.dp)
              .testTag("border_color_option_${hexValue.toString(16)}")
          ) {
            Row(
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
              Box(
                modifier = Modifier
                  .size(14.dp)
                  .background(presetColor)
              )
              Text(
                text = label,
                fontSize = 12.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                color = if (isSelected) presetColor else textPrimary
              )
              if (isSelected) {
                Icon(
                  imageVector = Icons.Default.Check,
                  contentDescription = null,
                  tint = presetColor,
                  modifier = Modifier.size(14.dp)
                )
              }
            }
          }
        }
      }
    }

    Spacer(modifier = Modifier.height(16.dp))

    // SECTION 2: Power-Saving Behavior
    SettingsGroup(
      title = "كفاءة الطاقة",
      codeTag = "MOD.02 // REACTOR",
      icon = Icons.Default.BatterySaver,
      accentColor = borderColor,
      containerColor = panelBg,
      textPrimary = textPrimary
    ) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Column(modifier = Modifier.weight(1f)) {
          Text(
            text = "وضع توفير الطاقة المحسّن",
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = textPrimary
          )
          Text(
            text = "يهدئ سرعة تموجات النواة لتقليل استهلاك المعالج الرسومي",
            fontSize = 12.sp,
            color = textSecondary
          )
        }
        Switch(
          checked = uiState.isPowerSaving,
          onCheckedChange = { onTogglePowerSaving() },
          colors = SwitchDefaults.colors(
            checkedThumbColor = JarvisHudColors.PitchBlack,
            checkedTrackColor = borderColor,
            uncheckedTrackColor = if (isLight) Color(0xFFD0DFEC) else Color(0xFF0A1E36)
          ),
          modifier = Modifier.testTag("power_saving_switch")
        )
      }
    }

    Spacer(modifier = Modifier.height(16.dp))

    // SECTION 3: Context Engine Diagnostics
    SettingsGroup(
      title = "محرك السياق الذكي",
      codeTag = "MOD.03 // TELEMETRY",
      icon = Icons.Default.Sensors,
      accentColor = borderColor,
      containerColor = panelBg,
      textPrimary = textPrimary
    ) {
      val c = uiState.contextSnapshot
      Text(
        text = "الحالة الحالية: ${c.state.displayName} (${(c.confidence * 100).toInt()}% ثقة)",
        fontSize = 13.sp,
        fontFamily = FontFamily.Monospace,
        fontWeight = FontWeight.Bold,
        color = borderColor
      )
      Text(
        text = "الميلان الأمامي: ${String.format(Locale.US, "%.1f°", c.pitchDegrees)} | الجانبي: ${String.format(Locale.US, "%.1f°", c.rollDegrees)}",
        fontSize = 12.sp,
        fontFamily = FontFamily.Monospace,
        color = textSecondary
      )
      Text(
        text = "حساس التقارب: ${if (c.isProximityNear) "قريب (مغطى)" else "بعيد (مكشوف)"}",
        fontSize = 12.sp,
        fontFamily = FontFamily.Monospace,
        color = textSecondary
      )
    }

    Spacer(modifier = Modifier.height(16.dp))

    // SECTION 4: Default Launcher Role
    SettingsGroup(
      title = "وضع الواجهة الرئيسية",
      codeTag = "MOD.04 // HOME_OS",
      icon = Icons.Default.Home,
      accentColor = borderColor,
      containerColor = panelBg,
      textPrimary = textPrimary
    ) {
      val isDefault = uiState.launcherStatus.isDefaultLauncher
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Column(modifier = Modifier.weight(1f)) {
          Text(
            text = "التطبيق الرئيسي الافتراضي (ROLE_HOME)",
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = textPrimary
          )
          Text(
            text = if (isDefault) {
              "WEYKY يعمل حالياً كشاشة رئيسية افتراضية لهاتفك"
            } else {
              "يمكنك تعيين WEYKY كواجهة رئيسية عبر إعدادات Android الرسمية"
            },
            fontSize = 12.sp,
            color = if (isDefault) borderColor else textSecondary
          )
        }
        if (!isDefault) {
          Button(
            onClick = onRequestDefaultLauncher,
            colors = ButtonDefaults.buttonColors(containerColor = borderColor),
            shape = CutCornerShape(topStart = 8.dp, bottomEnd = 8.dp, topEnd = 2.dp, bottomStart = 2.dp),
            modifier = Modifier.testTag("request_default_launcher_button")
          ) {
            Text("تعيين كافتراضي", fontSize = 12.sp, color = JarvisHudColors.PitchBlack, fontWeight = FontWeight.Bold)
          }
        } else {
          JarvisHudBadge(
            accentColor = borderColor,
            containerColor = borderColor.copy(alpha = 0.2f),
            cutSize = 7.dp,
            modifier = Modifier.testTag("default_launcher_badge")
          ) {
            Text(
              text = "مفعل كافتراضي",
              fontSize = 12.sp,
              color = borderColor,
              fontWeight = FontWeight.Bold,
              modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
            )
          }
        }
      }
    }

    Spacer(modifier = Modifier.height(16.dp))

    // SECTION 5: Voice Engines & Offline Models
    SettingsGroup(
      title = "منظومة الصوت والنماذج المحلية",
      codeTag = "MOD.05 // VOICE_CORE",
      icon = Icons.Default.Info,
      accentColor = borderColor,
      containerColor = panelBg,
      textPrimary = textPrimary
    ) {
      val voiceStatus = uiState.voiceEngineStatus
      Text(
        text = "محرك الاستماع النشط (STT): ${voiceStatus?.sttEngine?.name ?: "جاري الفحص..."}",
        fontSize = 13.sp,
        fontFamily = FontFamily.Monospace,
        fontWeight = FontWeight.Bold,
        color = borderColor
      )
      Text(
        text = "محرك النطق (TTS): ${voiceStatus?.activeTtsPackage ?: "غير محدد"} (دعم العربية: ${if (voiceStatus?.isArabicTtsSupported == true) "مدعوم" else "غير مدعوم"})",
        fontSize = 12.sp,
        color = textSecondary
      )
      Text(
        text = "حالة الاتصال: ${if (voiceStatus?.isOnline == true) "متصل بالإنترنت" else "غير متصل (أوفلاين)"}",
        fontSize = 12.sp,
        color = if (voiceStatus?.isOnline == true) borderColor else JarvisHudColors.TacticalAmber
      )

      Spacer(modifier = Modifier.height(10.dp))

      // Hybrid Speech System Card
      JarvisHudCard(
        accentColor = if (uiState.isHybridTtsEnabled) borderColor else textSecondary,
        containerColor = JarvisHudColors.panelBgElevated(isLight),
        cutCornerSize = 8.dp,
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp).testTag("hybrid_tts_settings_card")
      ) {
        Column(modifier = Modifier.padding(12.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Column(modifier = Modifier.weight(1f)) {
              Text(
                text = "المنظومة الهجينة (Hybrid Neural Speech)",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = borderColor
              )
              Text(
                text = "نطق عصبي فائق الجودة (أونلاين) + احتياطي محلي فوري (أوفلاين)",
                fontSize = 11.sp,
                color = textSecondary
              )
            }
            Switch(
              checked = uiState.isHybridTtsEnabled,
              onCheckedChange = onSetHybridTtsEnabled,
              colors = SwitchDefaults.colors(
                checkedThumbColor = borderColor,
                checkedTrackColor = borderColor.copy(alpha = 0.3f)
              ),
              modifier = Modifier.testTag("hybrid_tts_switch")
            )
          }

          if (uiState.isHybridTtsEnabled) {
            Spacer(modifier = Modifier.height(10.dp))
            Text(
              text = "قائمة الأصوات العربية الفصيحة المتاحة:",
              fontSize = 13.sp,
              fontWeight = FontWeight.Bold,
              color = borderColor
            )
            Text(
              text = "اختر الصوت المعتمد لنطق WEYKY التفاعلي واستمع لتجربته:",
              fontSize = 11.sp,
              color = textSecondary
            )
            Spacer(modifier = Modifier.height(8.dp))

            Column(
              verticalArrangement = Arrangement.spacedBy(6.dp),
              modifier = Modifier.fillMaxWidth().testTag("arabic_voices_list")
            ) {
              com.example.core.voice.model.ArabicVoiceCatalog.VOICES.forEach { voice ->
                val isSelected = uiState.cloudTtsVoiceName == voice.voiceKey
                JarvisHudCard(
                  accentColor = if (isSelected) borderColor else borderColor.copy(alpha = 0.28f),
                  containerColor = if (isSelected) JarvisHudColors.panelBgElevated(isLight) else panelBg,
                  cutCornerSize = 8.dp,
                  onClick = { onSetCloudTtsVoice(voice.voiceKey) },
                  modifier = Modifier
                    .fillMaxWidth()
                    .testTag("voice_option_${voice.voiceKey}")
                ) {
                  Row(
                    modifier = Modifier
                      .fillMaxWidth()
                      .padding(horizontal = 12.dp, vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                  ) {
                    Row(
                      verticalAlignment = Alignment.CenterVertically,
                      horizontalArrangement = Arrangement.spacedBy(10.dp),
                      modifier = Modifier.weight(1f)
                    ) {
                      Text(
                        text = voice.flag,
                        fontSize = 20.sp
                      )
                      Column {
                        Text(
                          text = "${voice.flag} ${voice.name} (${voice.country}) - ${voice.gender}",
                          fontSize = 13.sp,
                          fontWeight = if (isSelected) FontWeight.Bold else FontWeight.SemiBold,
                          color = if (isSelected) borderColor else textPrimary
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                          text = if (isSelected) "✓ الصوت المعتمد حالياً" else "انقر للاختيار والتعيين",
                          fontSize = 11.sp,
                          color = if (isSelected) JarvisHudColors.NeonGreen else textSecondary
                        )
                      }
                    }

                    IconButton(
                      onClick = {
                        onSetCloudTtsVoice(voice.voiceKey)
                        onTestVoiceWithKey(voice.voiceKey, voice.previewText)
                      },
                      modifier = Modifier
                        .size(42.dp)
                        .testTag("test_voice_${voice.voiceKey}")
                    ) {
                      Box(
                        modifier = Modifier
                          .size(32.dp)
                          .clip(androidx.compose.foundation.shape.CircleShape)
                          .background(if (isSelected) borderColor.copy(alpha = 0.16f) else Color(0x22888888))
                          .border(1.dp, if (isSelected) borderColor else Color(0x44888888), androidx.compose.foundation.shape.CircleShape),
                        contentAlignment = Alignment.Center
                      ) {
                        Icon(
                          imageVector = Icons.Default.PlayArrow,
                          contentDescription = "استماع ${voice.name}",
                          tint = if (isSelected) borderColor else textSecondary,
                          modifier = Modifier.size(18.dp)
                        )
                      }
                    }
                  }
                }
              }
            }

            Spacer(modifier = Modifier.height(10.dp))
            OutlinedButton(
              onClick = onTestVoice,
              modifier = Modifier.fillMaxWidth().testTag("test_hybrid_voice_button"),
              shape = CutCornerShape(topStart = 8.dp, bottomEnd = 8.dp, topEnd = 2.dp, bottomStart = 2.dp),
              border = BorderStroke(1.dp, borderColor)
            ) {
              Icon(Icons.Default.PlayArrow, contentDescription = null, tint = borderColor, modifier = Modifier.size(16.dp))
              Spacer(modifier = Modifier.width(6.dp))
              Text("تجربة نطق الصوت الحالي الآن", fontSize = 12.sp, color = borderColor, fontWeight = FontWeight.Bold)
            }
          }
        }
      }

      val hasInstalledTts = com.example.core.voice.model.VoiceModelManager.CATALOG.any {
        it.type == com.example.core.voice.model.VoiceModelType.TTS &&
          uiState.voiceModelStatuses[it.id] is com.example.core.voice.model.ModelInstallStatus.Installed
      }

      if (hasInstalledTts) {
        JarvisHudCard(
          accentColor = JarvisHudColors.NeonGreen,
          containerColor = JarvisHudColors.panelBgElevated(isLight),
          cutCornerSize = 8.dp,
          modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
        ) {
          Row(
            modifier = Modifier.padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            Icon(
              imageVector = Icons.Default.Check,
              contentDescription = null,
              tint = JarvisHudColors.NeonGreen,
              modifier = Modifier.size(18.dp)
            )
            Text(
              text = "محرك النطق العصبي المحلي (Piper VITS) نشط ويعمل محلياً بالكامل دون الحاجة لخدمات أندرويد أو جوجل.",
              fontSize = 11.sp,
              color = textPrimary,
              fontWeight = FontWeight.Medium
            )
          }
        }
      } else {
        JarvisHudCard(
          accentColor = JarvisHudColors.TacticalAmber,
          containerColor = JarvisHudColors.panelBgElevated(isLight),
          cutCornerSize = 8.dp,
          modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
        ) {
          Column(modifier = Modifier.padding(10.dp)) {
            Text(
              text = "تنبيه لمستخدمي Huawei (ART-L29 / أجهزة بدون خدمات جوجل):",
              fontSize = 12.sp,
              fontWeight = FontWeight.Bold,
              color = JarvisHudColors.TacticalAmber
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
              text = "محرك أندرويد الافتراضي في الهاتف لا يدعم النطق العربي لغياب حزم جوجل. صُمم WEYKY بمحرك عصبي مدمج (Piper ONNX)؛ يرجى تنزيل نموذج «كريم» أو «النسائي» أدناه ليعمل النطق الفصيح محلياً وبدون إنترنت.",
              fontSize = 11.sp,
              color = textSecondary,
              lineHeight = 16.sp
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(10.dp))
      Text(
        text = "النماذج الصوتية للعمل أوفلاين بدون إنترنت (Piper & Vosk):",
        fontSize = 13.sp,
        fontWeight = FontWeight.Bold,
        color = borderColor
      )
      Text(
        text = "نماذج Piper VITS للنطق العصبي المحلي، ونماذج Vosk للاستماع والتعرف الصوتي دون اتصال:",
        fontSize = 11.sp,
        color = textSecondary
      )

      com.example.core.voice.model.VoiceModelManager.CATALOG.forEach { modelInfo ->
        val status = uiState.voiceModelStatuses[modelInfo.id] ?: com.example.core.voice.model.ModelInstallStatus.NotInstalled
        val engineBadgeText = if (modelInfo.type == com.example.core.voice.model.VoiceModelType.TTS) {
          "PIPER VITS // OFFLINE NEURAL TTS"
        } else {
          "VOSK ASR // OFFLINE SPEECH RECOGNITION"
        }
        val engineBadgeColor = if (modelInfo.type == com.example.core.voice.model.VoiceModelType.TTS) {
          JarvisHudColors.MagentaPulse
        } else {
          borderColor
        }
        JarvisHudCard(
          accentColor = borderColor,
          containerColor = JarvisHudColors.panelBgElevated(isLight),
          cutCornerSize = 10.dp,
          bracketLength = 10.dp,
          modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .testTag("voice_model_card_${modelInfo.id}")
        ) {
          Column(modifier = Modifier.padding(12.dp)) {
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Column(modifier = Modifier.weight(1f)) {
                Text(
                  text = engineBadgeText,
                  fontSize = 9.sp,
                  fontFamily = FontFamily.Monospace,
                  fontWeight = FontWeight.Bold,
                  letterSpacing = 1.sp,
                  color = engineBadgeColor
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                  text = modelInfo.nameArabic,
                  fontSize = 13.sp,
                  fontWeight = FontWeight.Bold,
                  color = textPrimary
                )
                Text(
                  text = "${modelInfo.sizeBytes / (1024 * 1024)} ميجابايت • ${modelInfo.license}",
                  fontSize = 11.sp,
                  fontFamily = FontFamily.Monospace,
                  color = textSecondary
                )
                if (modelInfo.type == com.example.core.voice.model.VoiceModelType.TTS) {
                  Text(
                    text = if (modelInfo.isFemaleVoice) "صوت نسائي (إماراتي / فصيح)" else "صوت رجالي (كريم - الأردن / فصيح)",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = borderColor
                  )
                }
                val statusLabel = when (status) {
                  is com.example.core.voice.model.ModelInstallStatus.Installed -> "الحالة: مثبت وجاهز للعمل بدون إنترنت"
                  is com.example.core.voice.model.ModelInstallStatus.Downloaded -> "الحالة: تم التنزيل • جاهز للتثبيت الآن"
                  is com.example.core.voice.model.ModelInstallStatus.Downloading -> "الحالة: جاري التنزيل (${status.progressPercent}%)..."
                  is com.example.core.voice.model.ModelInstallStatus.Extracting -> "الحالة: جاري التثبيت وفك الضغط..."
                  is com.example.core.voice.model.ModelInstallStatus.Error -> "الحالة: غير مثبت"
                  else -> "الحالة: غير مثبت"
                }
                Text(
                  text = statusLabel,
                  fontSize = 11.sp,
                  color = when (status) {
                    is com.example.core.voice.model.ModelInstallStatus.Installed -> JarvisHudColors.NeonGreen
                    is com.example.core.voice.model.ModelInstallStatus.Downloaded -> borderColor
                    is com.example.core.voice.model.ModelInstallStatus.Extracting -> JarvisHudColors.TacticalAmber
                    else -> textSecondary
                  }
                )
              }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Action Buttons Row: Both "تنزيل" (Download) and "تثبيت" (Install)
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.spacedBy(8.dp),
              verticalAlignment = Alignment.CenterVertically
            ) {
              when (status) {
                is com.example.core.voice.model.ModelInstallStatus.Installed -> {
                  val isTts = modelInfo.type == com.example.core.voice.model.VoiceModelType.TTS
                  val isTtsActive = isTts && (uiState.activeTtsModelId == modelInfo.id || uiState.activeTtsModelId.isEmpty())

                  if (isTts && !isTtsActive) {
                    Button(
                      onClick = { onSelectActiveTtsModel(modelInfo) },
                      colors = ButtonDefaults.buttonColors(containerColor = borderColor),
                      shape = CutCornerShape(6.dp),
                      modifier = Modifier
                        .weight(1f)
                        .testTag("activate_model_${modelInfo.id}")
                    ) {
                      Text("تفعيل هذا الصوت", fontSize = 11.sp, color = JarvisHudColors.PitchBlack, fontWeight = FontWeight.Bold)
                    }
                  } else {
                    JarvisHudBadge(
                      accentColor = JarvisHudColors.NeonGreen,
                      containerColor = JarvisHudColors.NeonGreen.copy(alpha = 0.14f),
                      cutSize = 6.dp,
                      modifier = Modifier.weight(1f)
                    ) {
                      Box(
                        modifier = Modifier
                          .fillMaxWidth()
                          .padding(vertical = 8.dp),
                        contentAlignment = Alignment.Center
                      ) {
                        Text(
                          text = if (isTts) "✓ مفعل حالياً للنطق" else "✓ مثبت ومفعل",
                          fontSize = 12.sp,
                          fontWeight = FontWeight.Bold,
                          color = JarvisHudColors.NeonGreen
                        )
                      }
                    }
                  }
                  Button(
                    onClick = { onDeleteModel(modelInfo) },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0x33FF2A54)),
                    shape = CutCornerShape(6.dp),
                    modifier = Modifier.testTag("delete_model_${modelInfo.id}")
                  ) {
                    Text("حذف", fontSize = 11.sp, color = JarvisHudColors.CrimsonAlert, fontWeight = FontWeight.Bold)
                  }
                }
                is com.example.core.voice.model.ModelInstallStatus.Downloading -> {
                  Text(
                    text = "جاري التنزيل: ${status.progressPercent}% (${status.bytesDownloaded / (1024 * 1024)} / ${status.totalBytes / (1024 * 1024)} MB)",
                    fontSize = 12.sp,
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Bold,
                    color = borderColor
                  )
                }
                is com.example.core.voice.model.ModelInstallStatus.Extracting -> {
                  Text(
                    text = "جاري التثبيت وفك الضغط...",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = JarvisHudColors.TacticalAmber
                  )
                }
                is com.example.core.voice.model.ModelInstallStatus.Downloaded -> {
                  // File is downloaded -> Primary "تثبيت" button + option to re-download
                  Button(
                    onClick = { onInstallModel(modelInfo) },
                    colors = ButtonDefaults.buttonColors(containerColor = JarvisHudColors.NeonGreen),
                    shape = CutCornerShape(topStart = 8.dp, bottomEnd = 8.dp, topEnd = 2.dp, bottomStart = 2.dp),
                    modifier = Modifier
                      .weight(1f)
                      .testTag("install_model_${modelInfo.id}")
                  ) {
                    Text("تثبيت النموذج الآن", fontSize = 12.sp, color = JarvisHudColors.PitchBlack, fontWeight = FontWeight.Bold)
                  }
                  Button(
                    onClick = { onDownloadModel(modelInfo) },
                    colors = ButtonDefaults.buttonColors(
                      containerColor = if (isLight) Color(0xFFDCE8F4) else Color(0xFF0A2239)
                    ),
                    shape = CutCornerShape(6.dp),
                    modifier = Modifier.testTag("redownload_model_${modelInfo.id}")
                  ) {
                    Text("إعادة تنزيل", fontSize = 11.sp, color = borderColor)
                  }
                }
                else -> {
                  // Both "تنزيل" (Download) and "تثبيت" (Install) buttons side-by-side
                  Button(
                    onClick = { onDownloadModel(modelInfo) },
                    colors = ButtonDefaults.buttonColors(containerColor = borderColor),
                    shape = CutCornerShape(topStart = 7.dp, bottomEnd = 7.dp, topEnd = 2.dp, bottomStart = 2.dp),
                    modifier = Modifier
                      .weight(1f)
                      .testTag("download_model_${modelInfo.id}")
                  ) {
                    Text("تنزيل", fontSize = 12.sp, color = JarvisHudColors.PitchBlack, fontWeight = FontWeight.Bold)
                  }

                  Button(
                    onClick = {
                      // If zip already exists on disk, install directly; otherwise open file picker to install from local ZIP
                      val downloadedZip = java.io.File(
                        java.io.File(context.filesDir, "voice_models"),
                        "${modelInfo.id}.zip"
                      )
                      if (downloadedZip.exists() && downloadedZip.length() > 1024L) {
                        onInstallModel(modelInfo)
                      } else {
                        pendingModelForFileInstall = modelInfo
                        try {
                          modelZipPickerLauncher.launch("application/zip")
                        } catch (_: Exception) {
                          onInstallModel(modelInfo)
                        }
                      }
                    },
                    colors = ButtonDefaults.buttonColors(
                      containerColor = JarvisHudColors.NeonGreen
                    ),
                    shape = CutCornerShape(topStart = 7.dp, bottomEnd = 7.dp, topEnd = 2.dp, bottomStart = 2.dp),
                    modifier = Modifier
                      .weight(1f)
                      .testTag("install_model_${modelInfo.id}")
                  ) {
                    Text("تثبيت", fontSize = 12.sp, color = JarvisHudColors.PitchBlack, fontWeight = FontWeight.Bold)
                  }
                }
              }
            }

            if (status is com.example.core.voice.model.ModelInstallStatus.Error) {
              Spacer(modifier = Modifier.height(6.dp))
              Text(
                text = "تنبيه: ${status.message}",
                fontSize = 11.sp,
                color = JarvisHudColors.CrimsonAlert
              )
            }
          }
        }
      }
    }

    Spacer(modifier = Modifier.height(16.dp))

    // SECTION 6: System Integrations & Permissions
    SettingsGroup(
      title = "تكامل النظام والصلاحيات",
      codeTag = "MOD.06 // SEC_CLEARANCE",
      icon = Icons.Default.Notifications,
      accentColor = borderColor,
      containerColor = panelBg,
      textPrimary = textPrimary
    ) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Column(modifier = Modifier.weight(1f)) {
          Text(
            text = "الوصول إلى الإشعارات",
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = textPrimary
          )
          Text(
            text = if (isNotificationAccessGranted) "مفعل (يمكن لـ WEYKY قراءة الإشعارات)" else "غير مفعل (اضغط للفتح)",
            fontSize = 12.sp,
            color = if (isNotificationAccessGranted) borderColor else JarvisHudColors.TacticalAmber
          )
        }
        Button(
          onClick = {
            try {
              val intent = Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
              }
              context.startActivity(intent)
            } catch (_: Exception) {}
          },
          colors = ButtonDefaults.buttonColors(containerColor = if (isLight) Color(0xFFDCE8F4) else Color(0xFF071A30)),
          shape = CutCornerShape(topStart = 8.dp, bottomEnd = 8.dp, topEnd = 2.dp, bottomStart = 2.dp),
          modifier = Modifier.testTag("open_notification_settings_button")
        ) {
          Text("الإعدادات", fontSize = 12.sp, color = borderColor)
        }
      }
    }

    Spacer(modifier = Modifier.height(16.dp))

    // SECTION 7: About WEYKY
    SettingsGroup(
      title = "حول WEYKY",
      codeTag = "SYS.INFO // CORE_SPEC",
      icon = Icons.Default.Info,
      accentColor = borderColor,
      containerColor = panelBg,
      textPrimary = textPrimary
    ) {
      Text(
        text = "WEYKY 1.1 // TACTICAL CORE",
        fontSize = 15.sp,
        fontFamily = FontFamily.Monospace,
        fontWeight = FontWeight.Bold,
        color = textPrimary
      )
      Spacer(modifier = Modifier.height(4.dp))
      Text(
        text = "\"One living intelligent core for the phone.\"",
        fontSize = 13.sp,
        fontFamily = FontFamily.Monospace,
        color = borderColor
      )
      Spacer(modifier = Modifier.height(4.dp))
      Text(
        text = "واجهة محيطة ذكية حقيقية تعتمد على بيانات حساسات ونظام Android الفعلية.",
        fontSize = 12.sp,
        color = textSecondary,
        lineHeight = 18.sp
      )
    }

    Spacer(modifier = Modifier.height(30.dp))
    }
  }
}

@Composable
private fun SettingsGroup(
  title: String,
  codeTag: String,
  icon: ImageVector,
  accentColor: Color = JarvisHudColors.HologramCyan,
  containerColor: Color = JarvisHudColors.PanelBg,
  textPrimary: Color = JarvisHudColors.TextPrimary,
  content: @Composable () -> Unit
) {
  JarvisHudCard(
    accentColor = accentColor,
    containerColor = containerColor,
    cutCornerSize = 14.dp,
    bracketLength = 14.dp,
    tabTitle = codeTag,
    modifier = Modifier.fillMaxWidth()
  ) {
    Column(modifier = Modifier.padding(16.dp)) {
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(bottom = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        Icon(
          imageVector = icon,
          contentDescription = title,
          tint = accentColor,
          modifier = Modifier.size(20.dp)
        )
        Text(
          text = title,
          fontSize = 15.sp,
          fontWeight = FontWeight.Bold,
          color = textPrimary
        )
      }
      content()
    }
  }
}
