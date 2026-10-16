# WEYKY - Android Assistant Project Handover

## Overview
WEYKY is an intelligent phone core and living ambient layer for Android, providing real-time device intelligence, natural language intents, and contextual awareness.

## Status
- Compilation: Succeeded (`BUILD SUCCESSFUL`)
- Local Robolectric Tests: Passing
- Main Activity: `com.example.MainActivity` (Connected to `WeykyCoreView`, `UnifiedVoiceManager`, `IntentParser`, `IntentExecutor`)

## Key Architecture
1. **Core Intelligence & Reasoning**: `com.example.core.intelligence` & `com.example.core.context`
2. **Intent Execution & Handlers**: `com.example.core.intent` (App, Audio, Battery, Call, Context, Flashlight, Media, Network, Notification, Settings, StorageMemory, Weather)
3. **Voice Engine & Neural TTS**: `com.example.core.voice` (Vosk offline STT, Arabic text diacritizer, Edge & Local neural TTS)
4. **Device Controls**: `com.example.device` (Battery, Network, Storage, Memory, Flashlight, Media, Calls, Apps, Weather)
5. **UI & Theme**: `com.example.rendering.renderer.WeykyCoreView` & `com.example.ui.theme.JarvisHudComponents`
