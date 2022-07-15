import org.gradle.api.artifacts.dsl.DependencyHandler

object AppDependencies {
    // Std lib
    val kotlinStdLib = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"

    // Android ui
    private val appcompat = "androidx.appcompat:appcompat:${Versions.appcompat}"
    private val coreKtx = "androidx.core:core-ktx:${Versions.coreKtx}"
    private val constraintLayout =
        "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
    private const val materialComponents =
        "com.google.android.material:material:${Versions.materialComponents}"

    //RxKotlin/RxJava
    private const val rxJava = "io.reactivex.rxjava2:rxjava:${Versions.rxJava}"
    private const val rxAndroid = "io.reactivex.rxjava2:rxandroid:${Versions.rxAndroid}"
    private const val rxKotlin = "io.reactivex.rxjava2:rxkotlin:${Versions.rxKotlin}"

    //Dagger Injection
    private const val dagger = "com.google.dagger:dagger:${Versions.dagger}"
    private const val daggerCompiler = "com.google.dagger:dagger-compiler:${Versions.dagger}"
    private const val daggerSupport = "com.google.dagger:dagger-android-support:${Versions.dagger}"
    private const val daggerProcessor = "com.google.dagger:dagger-android-processor:${Versions.dagger}"

    //Test libs
    private val junit = "junit:junit:${Versions.junit}"
    private val extJUnit = "androidx.test.ext:junit:${Versions.extJunit}"
    private val espressoCore = "androidx.test.espresso:espresso-core:${Versions.espresso}"

    val appLibraries = arrayListOf<String>().apply {
        add(kotlinStdLib)
        add(coreKtx)
        add(appcompat)
        add(constraintLayout)
        add(materialComponents)
        add(rxJava)
        add(rxAndroid)
        add(rxKotlin)
        add(dagger)
        add(daggerSupport)
    }

    val appAnnotationProcessors = arrayListOf<String>().apply {
        add(daggerCompiler)
        add(daggerProcessor)
    }

    val mviCoreDependencies = arrayListOf<String>().apply {
        add(kotlinStdLib)
        add(coreKtx)
        add(appcompat)
        add(constraintLayout)
        add(materialComponents)
        add(rxJava)
        add(rxAndroid)
        add(rxKotlin)
    }

    val androidTestLibraries = arrayListOf<String>().apply {
        add(extJUnit)
        add(espressoCore)
    }

    val testLibraries = arrayListOf<String>().apply {
        add(junit)
    }
}

// Util functions for adding the different type dependencies from build.gradle file
fun DependencyHandler.kapt(list: List<String>) {
    list.forEach { dependency ->
        add("kapt", dependency)
    }
}

fun DependencyHandler.implementation(list: List<String>) {
    list.forEach { dependency ->
        add("implementation", dependency)
    }
}

fun DependencyHandler.androidTestImplementation(list: List<String>) {
    list.forEach { dependency ->
        add("androidTestImplementation", dependency)
    }
}

fun DependencyHandler.testImplementation(list: List<String>) {
    list.forEach { dependency ->
        add("testImplementation", dependency)
    }
}