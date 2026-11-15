# Steady

Steady is a calm, practical teacher for people who want to feel more confident using their phone.

It teaches everyday skills — calls, messages, photos, Maps and settings — alongside practical lessons about scams and suspicious messages.

The first version is Android-first and intentionally simple.

## Product principles

- Teach people to do things themselves.
- Use plain language instead of technical jargon.
- Never shame someone for not knowing something.
- Teach safety as part of everyday phone use.
- Prefer practical tasks over long explanations.
- Make help available when something goes wrong.
- Do not pretend a security check is more capable than it really is.

## Current prototype

The Android prototype includes:

- Home screen with a clear next lesson
- Learn curriculum grouped by topic
- Lesson flow
- Safety section
- Help section
- Initial curriculum covering phone basics, communication, photos, everyday tools and scam awareness

The curriculum currently lives in MainActivity.kt and should move into a proper local content model as the app grows.

## Build

Open the project in Android Studio and let Gradle sync.

Then run the app configuration on an Android device or emulator.

## Roadmap

1. Device-specific guidance for common Android phones
2. Real interactive exercises rather than instruction-only steps
3. Progress and local persistence
4. Accessibility-first settings
5. Screenshot/image-based guidance
6. "What happened?" troubleshooting flows
7. Optional trusted-family support
8. More safety scenarios and regularly reviewed scam examples

Steady is not an antivirus. Its job is to make people more capable, more cautious and less alone when using their phones.
