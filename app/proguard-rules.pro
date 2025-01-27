# Mantener las clases con anotaciones de Kotlin
-keepattributes *Annotation*

# Mantener las clases de Kotlin necesarias
-keep class kotlin.** { *; }
-dontwarn kotlin.**

# Mantener las clases de Gson
-keep class com.google.gson.** { *; }
-dontwarn com.google.gson.**

# Koin
-keep class org.koin.** { *; }
-dontwarn org.koin.**

# Ktor
-keep class io.ktor.** { *; }
-dontwarn io.ktor.**

# Firebase
-keep class com.google.firebase.** { *; }
-dontwarn com.google.firebase.**

# Coil
-keep class coil.** { *; }
-dontwarn coil.**

# Accompanist
-keep class com.google.accompanist.** { *; }
-dontwarn com.google.accompanist.**

# Evitar la eliminación de clases utilizadas por la reflexión
-keep class androidx.lifecycle.** { *; }
-keep class androidx.activity.** { *; }
-keep class androidx.compose.** { *; }

# Mantener las clases que extienden de Activity, Service, etc.
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider

# Evitar la eliminación de métodos utilizados por Jetpack Compose
-keep class androidx.compose.** { *; }
-dontwarn androidx.compose.**

# Mantener las clases de serialización de Kotlinx
-keep class kotlinx.serialization.** { *; }
-dontwarn kotlinx.serialization.**

# Mantener las clases de Firebase Crashlytics
-keep class com.google.firebase.crashlytics.** { *; }
-dontwarn com.google.firebase.crashlytics.**

# Mantener las clases de SLF4J
-keep class org.slf4j.** { *; }
-dontwarn org.slf4j.**
