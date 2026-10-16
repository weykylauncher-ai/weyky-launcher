package com.example.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.example.core.state.WeykyUiState
import com.example.device.apps.AppMatch
import com.example.ui.adaptive.AdaptiveLayoutMode
import com.example.ui.adaptive.CardDensity
import com.example.ui.adaptive.DeviceProfile
import com.example.ui.adaptive.rememberAdaptiveLayoutState
import com.example.ui.theme.JarvisHudColors
import com.example.ui.theme.jarvisTacticalBackground

@Composable
fun HomeScreen(
  uiState: WeykyUiState,
  onQuerySubmit: (String) -> Unit,
  onQueryChange: (String) -> Unit,
  onOpenSettings: () -> Unit,
  onRequestPermission: (String) -> Unit,
  onToggleVoice: () -> Unit,
  onConfirmOption: (String) -> Unit,
  onToggleAppDrawer: () -> Unit = {},
  onLaunchApp: (AppMatch) -> Unit = {},
  onRequestDefaultLauncher: () -> Unit = {},
  onToggleMediaPlayback: () -> Unit = { onQuerySubmit("أوقف الموسيقى") },
  onNextMediaTrack: () -> Unit = { onQuerySubmit("الأغنية التالية") },
  onPreviousMediaTrack: () -> Unit = { onQuerySubmit("الأغنية السابقة") },
  modifier: Modifier = Modifier
) {
  val keyboardController = LocalSoftwareKeyboardController.current
  val isLight = uiState.isLightMode
  val bgColor = Color(uiState.backgroundColorHex)
  val borderColor = Color(uiState.cardBorderColorHex)
  val panelBg = JarvisHudColors.panelBg(isLight)
  val panelBgElevated = JarvisHudColors.panelBgElevated(isLight)
  val textPrimary = JarvisHudColors.textPrimary(isLight)
  val textSecondary = JarvisHudColors.textSecondary(isLight)
  val textMuted = JarvisHudColors.textMuted(isLight)

  BoxWithConstraints(
    modifier = modifier
      .fillMaxSize()
      .jarvisTacticalBackground(
        backgroundColor = bgColor,
        accentColor = borderColor
      )
      .statusBarsPadding()
      .navigationBarsPadding()
      .imePadding()
      .testTag("home_screen_root")
  ) {
    // Resolve dynamic adaptive layout profile for current window bounds
    val deviceProfile = rememberAdaptiveLayoutState(
      containerWidthDp = maxWidth,
      containerHeightDp = maxHeight
    )

    when (deviceProfile.layoutMode) {
      AdaptiveLayoutMode.PHONE_LANDSCAPE -> {
        PhoneLandscapeHomeScreen(
          uiState = uiState,
          deviceProfile = deviceProfile,
          borderColor = borderColor,
          panelBg = panelBg,
          panelBgElevated = panelBgElevated,
          textPrimary = textPrimary,
          textSecondary = textSecondary,
          textMuted = textMuted,
          isLight = isLight,
          onQuerySubmit = onQuerySubmit,
          onQueryChange = onQueryChange,
          onOpenSettings = onOpenSettings,
          onRequestPermission = onRequestPermission,
          onToggleVoice = onToggleVoice,
          onConfirmOption = onConfirmOption,
          onToggleAppDrawer = onToggleAppDrawer,
          onToggleMediaPlayback = onToggleMediaPlayback,
          onNextMediaTrack = onNextMediaTrack,
          onPreviousMediaTrack = onPreviousMediaTrack,
          keyboardController = keyboardController
        )
      }
      AdaptiveLayoutMode.TABLET_EXPANDED,
      AdaptiveLayoutMode.FOLDABLE_BOOK -> {
        TabletExpandedHomeScreen(
          uiState = uiState,
          deviceProfile = deviceProfile,
          borderColor = borderColor,
          panelBg = panelBg,
          panelBgElevated = panelBgElevated,
          textPrimary = textPrimary,
          textSecondary = textSecondary,
          textMuted = textMuted,
          isLight = isLight,
          onQuerySubmit = onQuerySubmit,
          onQueryChange = onQueryChange,
          onOpenSettings = onOpenSettings,
          onRequestPermission = onRequestPermission,
          onToggleVoice = onToggleVoice,
          onConfirmOption = onConfirmOption,
          onToggleAppDrawer = onToggleAppDrawer,
          onToggleMediaPlayback = onToggleMediaPlayback,
          onNextMediaTrack = onNextMediaTrack,
          onPreviousMediaTrack = onPreviousMediaTrack,
          keyboardController = keyboardController
        )
      }
      AdaptiveLayoutMode.FOLDABLE_TABLETOP -> {
        FoldableTabletopHomeScreen(
          uiState = uiState,
          deviceProfile = deviceProfile,
          borderColor = borderColor,
          panelBg = panelBg,
          panelBgElevated = panelBgElevated,
          textPrimary = textPrimary,
          textSecondary = textSecondary,
          textMuted = textMuted,
          isLight = isLight,
          onQuerySubmit = onQuerySubmit,
          onQueryChange = onQueryChange,
          onOpenSettings = onOpenSettings,
          onRequestPermission = onRequestPermission,
          onToggleVoice = onToggleVoice,
          onConfirmOption = onConfirmOption,
          onToggleAppDrawer = onToggleAppDrawer,
          onToggleMediaPlayback = onToggleMediaPlayback,
          onNextMediaTrack = onNextMediaTrack,
          onPreviousMediaTrack = onPreviousMediaTrack,
          keyboardController = keyboardController
        )
      }
      AdaptiveLayoutMode.SPLIT_SCREEN_COMPACT -> {
        SplitScreenCompactHomeScreen(
          uiState = uiState,
          deviceProfile = deviceProfile,
          borderColor = borderColor,
          panelBg = panelBg,
          panelBgElevated = panelBgElevated,
          textPrimary = textPrimary,
          textSecondary = textSecondary,
          textMuted = textMuted,
          isLight = isLight,
          onQuerySubmit = onQuerySubmit,
          onQueryChange = onQueryChange,
          onOpenSettings = onOpenSettings,
          onRequestPermission = onRequestPermission,
          onToggleVoice = onToggleVoice,
          onConfirmOption = onConfirmOption,
          onToggleAppDrawer = onToggleAppDrawer,
          onToggleMediaPlayback = onToggleMediaPlayback,
          onNextMediaTrack = onNextMediaTrack,
          onPreviousMediaTrack = onPreviousMediaTrack,
          keyboardController = keyboardController
        )
      }
      AdaptiveLayoutMode.PHONE_PORTRAIT -> {
        PhonePortraitHomeScreen(
          uiState = uiState,
          deviceProfile = deviceProfile,
          borderColor = borderColor,
          panelBg = panelBg,
          panelBgElevated = panelBgElevated,
          textPrimary = textPrimary,
          textSecondary = textSecondary,
          textMuted = textMuted,
          isLight = isLight,
          onQuerySubmit = onQuerySubmit,
          onQueryChange = onQueryChange,
          onOpenSettings = onOpenSettings,
          onRequestPermission = onRequestPermission,
          onToggleVoice = onToggleVoice,
          onConfirmOption = onConfirmOption,
          onToggleAppDrawer = onToggleAppDrawer,
          onToggleMediaPlayback = onToggleMediaPlayback,
          onNextMediaTrack = onNextMediaTrack,
          onPreviousMediaTrack = onPreviousMediaTrack,
          keyboardController = keyboardController
        )
      }
    }

    if (uiState.isAppDrawerOpen) {
      AppSurfaceOverlay(
        apps = uiState.installedApps,
        deviceProfile = deviceProfile,
        isLightMode = isLight,
        backgroundColor = bgColor,
        borderColor = borderColor,
        onClose = onToggleAppDrawer,
        onLaunchApp = onLaunchApp
      )
    }
  }
}

/**
 * Standard Phone Portrait Composition.
 * Prioritizes clear visual hierarchy, vertical flow, and easy one-handed touch ergonomics.
 */
@Composable
private fun PhonePortraitHomeScreen(
  uiState: WeykyUiState,
  deviceProfile: DeviceProfile,
  borderColor: Color,
  panelBg: Color,
  panelBgElevated: Color,
  textPrimary: Color,
  textSecondary: Color,
  textMuted: Color,
  isLight: Boolean,
  onQuerySubmit: (String) -> Unit,
  onQueryChange: (String) -> Unit,
  onOpenSettings: () -> Unit,
  onRequestPermission: (String) -> Unit,
  onToggleVoice: () -> Unit,
  onConfirmOption: (String) -> Unit,
  onToggleAppDrawer: () -> Unit,
  onToggleMediaPlayback: () -> Unit,
  onNextMediaTrack: () -> Unit,
  onPreviousMediaTrack: () -> Unit,
  keyboardController: androidx.compose.ui.platform.SoftwareKeyboardController?
) {
  val dynamicCoreSize = deviceProfile.calculateAdaptiveCoreSize(uiState.coreScale)

  Column(
    modifier = Modifier
      .fillMaxSize()
      .padding(
        horizontal = deviceProfile.contentPaddingHorizontalDp,
        vertical = deviceProfile.contentPaddingVerticalDp
      ),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.SpaceBetween
  ) {
    // TOP SECTION: Weather Card + Date Card in one horizontal row
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .widthIn(max = deviceProfile.maxCardWidthDp),
      verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
      HomeTopRowCards(
        uiState = uiState,
        borderColor = borderColor,
        panelBg = panelBg,
        textPrimary = textPrimary,
        textSecondary = textSecondary,
        textMuted = textMuted,
        density = deviceProfile.cardDensity,
        onQuerySubmit = onQuerySubmit
      )

      HomeMusicCard(
        uiState = uiState,
        borderColor = borderColor,
        panelBgElevated = panelBgElevated,
        textPrimary = textPrimary,
        textSecondary = textSecondary,
        onToggleMediaPlayback = onToggleMediaPlayback,
        onNextMediaTrack = onNextMediaTrack,
        onPreviousMediaTrack = onPreviousMediaTrack
      )

      HomeContextAlertCard(
        uiState = uiState,
        borderColor = borderColor,
        onQuerySubmit = onQuerySubmit
      )
    }

    // CENTER SECTION: Living WEYKY Visual Core
    HomeCoreSection(
      uiState = uiState,
      coreSizeDp = dynamicCoreSize,
      onToggleVoice = onToggleVoice,
      modifier = Modifier
        .weight(1f, fill = true)
        .padding(vertical = 4.dp)
    )

    // BOTTOM SECTION: Response Card and Bottom Control Row (Settings + Input + Apps)
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .widthIn(max = deviceProfile.maxCardWidthDp),
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.spacedBy(deviceProfile.cardSpacingDp)
    ) {
      HomeResponseCard(
        uiState = uiState,
        borderColor = borderColor,
        panelBg = panelBg,
        panelBgElevated = panelBgElevated,
        textPrimary = textPrimary,
        onQuerySubmit = onQuerySubmit,
        onConfirmOption = onConfirmOption,
        onRequestPermission = onRequestPermission
      )

      HomeBottomControlRow(
        uiState = uiState,
        borderColor = borderColor,
        panelBg = panelBg,
        panelBgElevated = panelBgElevated,
        textPrimary = textPrimary,
        textSecondary = textSecondary,
        isLight = isLight,
        onQueryChange = onQueryChange,
        onQuerySubmit = onQuerySubmit,
        onToggleVoice = onToggleVoice,
        onOpenSettings = onOpenSettings,
        onToggleAppDrawer = onToggleAppDrawer,
        keyboardController = keyboardController
      )
    }
  }
}

/**
 * Phone Landscape Composition.
 * Side-by-side arrangement: Core on the left; scrollable tactical HUD controls on the right.
 * Eliminates vertical clipping and squashing when available window height is constrained.
 */
@Composable
private fun PhoneLandscapeHomeScreen(
  uiState: WeykyUiState,
  deviceProfile: DeviceProfile,
  borderColor: Color,
  panelBg: Color,
  panelBgElevated: Color,
  textPrimary: Color,
  textSecondary: Color,
  textMuted: Color,
  isLight: Boolean,
  onQuerySubmit: (String) -> Unit,
  onQueryChange: (String) -> Unit,
  onOpenSettings: () -> Unit,
  onRequestPermission: (String) -> Unit,
  onToggleVoice: () -> Unit,
  onConfirmOption: (String) -> Unit,
  onToggleAppDrawer: () -> Unit,
  onToggleMediaPlayback: () -> Unit,
  onNextMediaTrack: () -> Unit,
  onPreviousMediaTrack: () -> Unit,
  keyboardController: androidx.compose.ui.platform.SoftwareKeyboardController?
) {
  val dynamicCoreSize = deviceProfile.calculateAdaptiveCoreSize(uiState.coreScale)

  Row(
    modifier = Modifier
      .fillMaxSize()
      .padding(
        horizontal = deviceProfile.contentPaddingHorizontalDp,
        vertical = deviceProfile.contentPaddingVerticalDp
      ),
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.spacedBy(14.dp)
  ) {
    // LEFT PANE: Dedicated Visual Core (Centered, unclipped)
    Box(
      modifier = Modifier
        .weight(0.42f)
        .fillMaxHeight(),
      contentAlignment = Alignment.Center
    ) {
      HomeCoreSection(
        uiState = uiState,
        coreSizeDp = dynamicCoreSize,
        onToggleVoice = onToggleVoice
      )
    }

    // RIGHT PANE: Tactical HUD & Controls (Scrollable to prevent clipping)
    Column(
      modifier = Modifier
        .weight(0.58f)
        .fillMaxHeight()
        .verticalScroll(rememberScrollState()),
      verticalArrangement = Arrangement.spacedBy(6.dp),
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      HomeTopRowCards(
        uiState = uiState,
        borderColor = borderColor,
        panelBg = panelBg,
        textPrimary = textPrimary,
        textSecondary = textSecondary,
        textMuted = textMuted,
        density = CardDensity.COMPACT,
        onQuerySubmit = onQuerySubmit
      )

      HomeMusicCard(
        uiState = uiState,
        borderColor = borderColor,
        panelBgElevated = panelBgElevated,
        textPrimary = textPrimary,
        textSecondary = textSecondary,
        onToggleMediaPlayback = onToggleMediaPlayback,
        onNextMediaTrack = onNextMediaTrack,
        onPreviousMediaTrack = onPreviousMediaTrack
      )

      HomeContextAlertCard(
        uiState = uiState,
        borderColor = borderColor,
        onQuerySubmit = onQuerySubmit
      )

      HomeResponseCard(
        uiState = uiState,
        borderColor = borderColor,
        panelBg = panelBg,
        panelBgElevated = panelBgElevated,
        textPrimary = textPrimary,
        onQuerySubmit = onQuerySubmit,
        onConfirmOption = onConfirmOption,
        onRequestPermission = onRequestPermission
      )

      HomeBottomControlRow(
        uiState = uiState,
        borderColor = borderColor,
        panelBg = panelBg,
        panelBgElevated = panelBgElevated,
        textPrimary = textPrimary,
        textSecondary = textSecondary,
        isLight = isLight,
        onQueryChange = onQueryChange,
        onQuerySubmit = onQuerySubmit,
        onToggleVoice = onToggleVoice,
        onOpenSettings = onOpenSettings,
        onToggleAppDrawer = onToggleAppDrawer,
        keyboardController = keyboardController
      )
    }
  }
}

/**
 * Tablet & Expanded Window Composition.
 * Dual-pane tactical dashboard:
 * - Left Pane: System Action Bar, Full-fidelity Visual Core, and Response / Command Bar.
 * - Right Pane: Expanded Telemetry, Music Player, Events, and Intelligence Hub.
 */
@Composable
private fun TabletExpandedHomeScreen(
  uiState: WeykyUiState,
  deviceProfile: DeviceProfile,
  borderColor: Color,
  panelBg: Color,
  panelBgElevated: Color,
  textPrimary: Color,
  textSecondary: Color,
  textMuted: Color,
  isLight: Boolean,
  onQuerySubmit: (String) -> Unit,
  onQueryChange: (String) -> Unit,
  onOpenSettings: () -> Unit,
  onRequestPermission: (String) -> Unit,
  onToggleVoice: () -> Unit,
  onConfirmOption: (String) -> Unit,
  onToggleAppDrawer: () -> Unit,
  onToggleMediaPlayback: () -> Unit,
  onNextMediaTrack: () -> Unit,
  onPreviousMediaTrack: () -> Unit,
  keyboardController: androidx.compose.ui.platform.SoftwareKeyboardController?
) {
  val dynamicCoreSize = deviceProfile.calculateAdaptiveCoreSize(uiState.coreScale)

  Row(
    modifier = Modifier
      .fillMaxSize()
      .padding(
        horizontal = deviceProfile.contentPaddingHorizontalDp,
        vertical = deviceProfile.contentPaddingVerticalDp
      ),
    horizontalArrangement = Arrangement.spacedBy(20.dp)
  ) {
    // LEFT PANE: Core Interaction & Direct Input
    Column(
      modifier = Modifier
        .weight(1f)
        .fillMaxHeight(),
      verticalArrangement = Arrangement.SpaceBetween,
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      HomeTopRowCards(
        uiState = uiState,
        borderColor = borderColor,
        panelBg = panelBg,
        textPrimary = textPrimary,
        textSecondary = textSecondary,
        textMuted = textMuted,
        density = deviceProfile.cardDensity,
        onQuerySubmit = onQuerySubmit
      )

      Box(
        modifier = Modifier
          .weight(1f)
          .fillMaxWidth(),
        contentAlignment = Alignment.Center
      ) {
        HomeCoreSection(
          uiState = uiState,
          coreSizeDp = dynamicCoreSize,
          onToggleVoice = onToggleVoice
        )
      }

      Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
      ) {
        HomeResponseCard(
          uiState = uiState,
          borderColor = borderColor,
          panelBg = panelBg,
          panelBgElevated = panelBgElevated,
          textPrimary = textPrimary,
          onQuerySubmit = onQuerySubmit,
          onConfirmOption = onConfirmOption,
          onRequestPermission = onRequestPermission
        )

        HomeBottomControlRow(
          uiState = uiState,
          borderColor = borderColor,
          panelBg = panelBg,
          panelBgElevated = panelBgElevated,
          textPrimary = textPrimary,
          textSecondary = textSecondary,
          isLight = isLight,
          onQueryChange = onQueryChange,
          onQuerySubmit = onQuerySubmit,
          onToggleVoice = onToggleVoice,
          onOpenSettings = onOpenSettings,
          onToggleAppDrawer = onToggleAppDrawer,
          keyboardController = keyboardController
        )
      }
    }

    // RIGHT PANE: Telemetry & Activity Stream
    Column(
      modifier = Modifier
        .weight(1.05f)
        .fillMaxHeight()
        .verticalScroll(rememberScrollState()),
      verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
      HomeTelemetryCard(
        uiState = uiState,
        borderColor = borderColor,
        panelBg = panelBg,
        textPrimary = textPrimary,
        textSecondary = textSecondary,
        textMuted = textMuted,
        density = CardDensity.EXPANDED,
        onQuerySubmit = onQuerySubmit
      )

      HomeMusicCard(
        uiState = uiState,
        borderColor = borderColor,
        panelBgElevated = panelBgElevated,
        textPrimary = textPrimary,
        textSecondary = textSecondary,
        onToggleMediaPlayback = onToggleMediaPlayback,
        onNextMediaTrack = onNextMediaTrack,
        onPreviousMediaTrack = onPreviousMediaTrack
      )

      HomeContextAlertCard(
        uiState = uiState,
        borderColor = borderColor,
        onQuerySubmit = onQuerySubmit
      )
    }
  }
}

/**
 * Foldable Tabletop Posture Composition (Device partially folded with horizontal hinge).
 * Top display half: Viewing pane (Telemetry & prominent Core).
 * Bottom display half: Interaction pane (Response, Ask Weyky, Mic, and App access).
 */
@Composable
private fun FoldableTabletopHomeScreen(
  uiState: WeykyUiState,
  deviceProfile: DeviceProfile,
  borderColor: Color,
  panelBg: Color,
  panelBgElevated: Color,
  textPrimary: Color,
  textSecondary: Color,
  textMuted: Color,
  isLight: Boolean,
  onQuerySubmit: (String) -> Unit,
  onQueryChange: (String) -> Unit,
  onOpenSettings: () -> Unit,
  onRequestPermission: (String) -> Unit,
  onToggleVoice: () -> Unit,
  onConfirmOption: (String) -> Unit,
  onToggleAppDrawer: () -> Unit,
  onToggleMediaPlayback: () -> Unit,
  onNextMediaTrack: () -> Unit,
  onPreviousMediaTrack: () -> Unit,
  keyboardController: androidx.compose.ui.platform.SoftwareKeyboardController?
) {
  val dynamicCoreSize = deviceProfile.calculateAdaptiveCoreSize(uiState.coreScale)

  Column(
    modifier = Modifier
      .fillMaxSize()
      .padding(
        horizontal = deviceProfile.contentPaddingHorizontalDp,
        vertical = deviceProfile.contentPaddingVerticalDp
      )
  ) {
    // TOP HALF: Viewing Half
    Column(
      modifier = Modifier
        .weight(1f)
        .fillMaxWidth(),
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.SpaceBetween
    ) {
      HomeTopRowCards(
        uiState = uiState,
        borderColor = borderColor,
        panelBg = panelBg,
        textPrimary = textPrimary,
        textSecondary = textSecondary,
        textMuted = textMuted,
        density = CardDensity.COMPACT,
        onQuerySubmit = onQuerySubmit
      )

      Box(
        modifier = Modifier.weight(1f),
        contentAlignment = Alignment.Center
      ) {
        HomeCoreSection(
          uiState = uiState,
          coreSizeDp = dynamicCoreSize,
          onToggleVoice = onToggleVoice
        )
      }
    }

    Spacer(modifier = Modifier.height(12.dp))

    // BOTTOM HALF: Interaction Half
    Column(
      modifier = Modifier
        .weight(1f)
        .fillMaxWidth()
        .verticalScroll(rememberScrollState()),
      verticalArrangement = Arrangement.spacedBy(8.dp),
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      HomeMusicCard(
        uiState = uiState,
        borderColor = borderColor,
        panelBgElevated = panelBgElevated,
        textPrimary = textPrimary,
        textSecondary = textSecondary,
        onToggleMediaPlayback = onToggleMediaPlayback,
        onNextMediaTrack = onNextMediaTrack,
        onPreviousMediaTrack = onPreviousMediaTrack
      )

      HomeContextAlertCard(
        uiState = uiState,
        borderColor = borderColor,
        onQuerySubmit = onQuerySubmit
      )

      HomeResponseCard(
        uiState = uiState,
        borderColor = borderColor,
        panelBg = panelBg,
        panelBgElevated = panelBgElevated,
        textPrimary = textPrimary,
        onQuerySubmit = onQuerySubmit,
        onConfirmOption = onConfirmOption,
        onRequestPermission = onRequestPermission
      )

      HomeBottomControlRow(
        uiState = uiState,
        borderColor = borderColor,
        panelBg = panelBg,
        panelBgElevated = panelBgElevated,
        textPrimary = textPrimary,
        textSecondary = textSecondary,
        isLight = isLight,
        onQueryChange = onQueryChange,
        onQuerySubmit = onQuerySubmit,
        onToggleVoice = onToggleVoice,
        onOpenSettings = onOpenSettings,
        onToggleAppDrawer = onToggleAppDrawer,
        keyboardController = keyboardController
      )
    }
  }
}

/**
 * Split-Screen Compact Composition.
 * Handles split-screen, resized windows, or multi-window mode where height or width is tight.
 */
@Composable
private fun SplitScreenCompactHomeScreen(
  uiState: WeykyUiState,
  deviceProfile: DeviceProfile,
  borderColor: Color,
  panelBg: Color,
  panelBgElevated: Color,
  textPrimary: Color,
  textSecondary: Color,
  textMuted: Color,
  isLight: Boolean,
  onQuerySubmit: (String) -> Unit,
  onQueryChange: (String) -> Unit,
  onOpenSettings: () -> Unit,
  onRequestPermission: (String) -> Unit,
  onToggleVoice: () -> Unit,
  onConfirmOption: (String) -> Unit,
  onToggleAppDrawer: () -> Unit,
  onToggleMediaPlayback: () -> Unit,
  onNextMediaTrack: () -> Unit,
  onPreviousMediaTrack: () -> Unit,
  keyboardController: androidx.compose.ui.platform.SoftwareKeyboardController?
) {
  val dynamicCoreSize = deviceProfile.calculateAdaptiveCoreSize(uiState.coreScale)

  Column(
    modifier = Modifier
      .fillMaxSize()
      .padding(horizontal = 8.dp, vertical = 4.dp)
      .verticalScroll(rememberScrollState()),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.spacedBy(6.dp)
  ) {
    HomeTopRowCards(
      uiState = uiState,
      borderColor = borderColor,
      panelBg = panelBg,
      textPrimary = textPrimary,
      textSecondary = textSecondary,
      textMuted = textMuted,
      density = CardDensity.COMPACT,
      onQuerySubmit = onQuerySubmit
    )

    HomeCoreSection(
      uiState = uiState,
      coreSizeDp = dynamicCoreSize,
      onToggleVoice = onToggleVoice,
      modifier = Modifier.padding(vertical = 2.dp)
    )

    HomeMusicCard(
      uiState = uiState,
      borderColor = borderColor,
      panelBgElevated = panelBgElevated,
      textPrimary = textPrimary,
      textSecondary = textSecondary,
      onToggleMediaPlayback = onToggleMediaPlayback,
      onNextMediaTrack = onNextMediaTrack,
      onPreviousMediaTrack = onPreviousMediaTrack
    )

    HomeContextAlertCard(
      uiState = uiState,
      borderColor = borderColor,
      onQuerySubmit = onQuerySubmit
    )

    HomeResponseCard(
      uiState = uiState,
      borderColor = borderColor,
      panelBg = panelBg,
      panelBgElevated = panelBgElevated,
      textPrimary = textPrimary,
      onQuerySubmit = onQuerySubmit,
      onConfirmOption = onConfirmOption,
      onRequestPermission = onRequestPermission
    )

    HomeBottomControlRow(
      uiState = uiState,
      borderColor = borderColor,
      panelBg = panelBg,
      panelBgElevated = panelBgElevated,
      textPrimary = textPrimary,
      textSecondary = textSecondary,
      isLight = isLight,
      onQueryChange = onQueryChange,
      onQuerySubmit = onQuerySubmit,
      onToggleVoice = onToggleVoice,
      onOpenSettings = onOpenSettings,
      onToggleAppDrawer = onToggleAppDrawer,
      keyboardController = keyboardController
    )
  }
}
