// added ben-manes Versions plugin to check deps version upgrades. run gradle JourneyPlanner:dependecyUpdates

object BuildCfg {
    const val compileSdk = 28
    const val minSdk = 19
    const val targetSdk = 28
    const val buildTools = "28.0.3"
}

object Versions {
    const val supportLibrary = "28.0.0"
    const val playServices = "11.4.0"
    const val atsl = "1.0.2"
    const val kotlin = "1.3.21"
    const val okhttp = "3.12.1"
    const val retrofit = "2.5.0"
    const val dagger = "2.20"
    const val multidex = "1.0.3"
    const val stetho = "1.5.0"
    const val butterknife = "8.8.1"
    const val appCenter = "1.0.0"
    const val room = "1.1.1"
}

object Deps {

    const val kotlin_stdlib_jdk = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"
    const val kotlin_stdlib_common = "org.jetbrains.kotlin:kotlin-stdlib-common:${Versions.kotlin}"
    const val kotlin_test_common = "org.jetbrains.kotlin:kotlin-test-common:${Versions.kotlin}"
    const val kotlin_test_annotations = "org.jetbrains.kotlin:kotlin-test-annotations-common:${Versions.kotlin}"
    const val kotlin_test_jdk = "org.jetbrains.kotlin:kotlin-test:${Versions.kotlin}"
    const val kotlin_test_junit = "org.jetbrains.kotlin:kotlin-test-junit:${Versions.kotlin}"
    const val kotlin_test_reflect = "org.jetbrains.kotlin:kotlin-reflect:${Versions.kotlin}"

    const val android_support_annotations = "com.android.support:support-annotations:${Versions.supportLibrary}"
    const val android_support_recyclerView = "com.android.support:recyclerview-v7:${Versions.supportLibrary}"
    const val android_support_appCompat = "com.android.support:appcompat-v7:${Versions.supportLibrary}"
    const val android_support_cardView = "com.android.support:cardview-v7:${Versions.supportLibrary}"
    const val android_support_design = "com.android.support:design:${Versions.supportLibrary}"
    const val android_support_preferencev7 = "com.android.support:preference-v7:${Versions.supportLibrary}"
    const val android_support_preferencev14 = "com.android.support:preference-v14:${Versions.supportLibrary}"
    const val android_support_constraintLayout = "com.android.support.constraint:constraint-layout:1.1.3"
    const val android_support_multidex = "com.android.support:multidex:1.0.3"

    const val android_playservices_maps = "com.google.android.gms:play-services-maps:${Versions.playServices}"
    const val android_playservices_location = "com.google.android.gms:play-services-location:${Versions.playServices}"
    const val android_playservices_analytics = "com.google.android.gms:play-services-analytics:${Versions.playServices}"
    const val android_playservices_tagmanager = "com.google.android.gms:play-services-tagmanager:${Versions.playServices}"
    const val android_playservices_gcm = "com.google.android.gms:play-services-gcm:${Versions.playServices}"

    const val android_firebase_core = "com.google.firebase:firebase-core:${Versions.playServices}"
    const val android_firebase_messaging = "com.google.firebase:firebase-messaging:${Versions.playServices}"

    const val android_utils_maps = "com.google.maps.android:android-maps-utils:0.4.3"

    const val android_test_runner = "com.android.support.test:runner:${Versions.atsl}"
    const val android_test_rules = "com.android.support.test:rules:${Versions.atsl}"
    const val android_test_espresso = "com.android.support.test.espresso:espresso-core:3.0.2"
    const val android_test_spoon = "com.squareup.spoon:spoon-client:1.7.0"
    const val android_test_assertj = "com.squareup.assertj:assertj-android:1.1.1"

    const val okhttp_client = "com.squareup.okhttp3:okhttp:${Versions.okhttp}"
    const val okhttp_logging = "com.squareup.okhttp3:logging-interceptor:${Versions.okhttp}"
    const val okhttp_mockWebServer = "com.squareup.okhttp3:mockwebserver:${Versions.okhttp}"
    const val okhttp_picasso = "com.jakewharton.picasso:picasso2-okhttp3-downloader:1.1.0"

    const val retrofit_client = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val retrofit_converterGson = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"
    const val retrofit_rxjavaAdapter = "com.squareup.retrofit2:adapter-rxjava2:${Versions.retrofit}"

    const val dagger_runtime = "com.google.dagger:dagger:${Versions.dagger}"
    const val dagger_compiler = "com.google.dagger:dagger-compiler:${Versions.dagger}"
    const val dagger_android_runtime = "com.google.dagger:dagger-android:${Versions.dagger}"
    const val dagger_android_runtime_support = "com.google.dagger:dagger-android-support:${Versions.dagger}"
    const val dagger_android_compiler = "com.google.dagger:dagger-android-processor:${Versions.dagger}"

    const val butterknife_runtime = "com.jakewharton:butterknife:${Versions.butterknife}"
    const val butterknife_compiler = "com.jakewharton:butterknife-compiler:${Versions.butterknife}"
    const val butterknife_reflect = "com.jakewharton:butterknife-reflect:${Versions.butterknife}"

    const val stetho_runtime = "com.facebook.stetho:stetho:${Versions.stetho}"
    const val stetho_okhttp = "com.facebook.stetho:stetho-okhttp:${Versions.stetho}"

    const val appcenter_analytics = "com.microsoft.appcenter:appcenter-analytics:${Versions.appCenter}"
    const val appcenter_crashes = "com.microsoft.appcenter:appcenter-crashes:${Versions.appCenter}"

    const val medialib_core = "com.shureview:medialib-core:2.0.3@aar"
    const val medialib_exo = "com.shureview:medialib-exo:2.0.3@aar"
    const val exo_dash = "com.google.android.exoplayer:exoplayer-dash:r2.4.3"
    const val exo_ui = "com.google.android.exoplayer:exoplayer-ui:r2.4.3"


    const val utils_javaxAnnotation = "javax.annotation:javax.annotation-api:1.2"
    const val utils_picasso = "com.squareup.picasso:picasso:2.5.2"
    const val utils_timber = "com.jakewharton.timber:timber:4.7.1"

    const val utils_rxandroid = "io.reactivex.rxjava2:rxandroid:2.1.0"
    const val utils_rxjava = "io.reactivex.rxjava2:rxjava:2.2.0"
    const val utils_rx_binding = "com.jakewharton.rxbinding2:rxbinding:2.1.1"
    const val utils_rx_binding_support = "com.jakewharton.rxbinding2:rxbinding-support-v4:2.1.1"
    const val utils_rx_binding_appcompat = "com.jakewharton.rxbinding2:rxbinding-appcompat-v7:2.1.1"

    const val utils_gson = "com.google.code.gson:gson:2.8.5"


    const val utils_threetenabp = "com.jakewharton.threetenabp:threetenabp:1.1.0"
    const val utils_libphonenumber = "com.googlecode.libphonenumber:libphonenumber:8.8.2"
    const val utils_bottombar = "com.roughike:bottom-bar:1.4.0.1"
    const val utils_pageindicatorview = "com.romandanylyk:pageindicatorview:0.1.1"
    const val utils_stickyheadersrecyclerview = "com.timehop.stickyheadersrecyclerview:library:0.4.3@aar"
    const val utils_qrcode = "com.google.zxing:core:3.3.3"
    const val utils_apptentive = "com.apptentive:apptentive-android:3.4.1" // todo breaking upgrade to 4.1.2
    const val utils_appsflyer = "com.appsflyer:af-android-sdk:4.7.1@aar"
    const val utils_materialSpinner = "com.github.ganfra:material-spinner:1.1.1"
    const val utils_securePreferences = "com.scottyab:secure-preferences-lib:0.1.4"
    const val utils_hockeyapp = "net.hockeyapp.android:HockeySDK:5.0.4"
    const val utils_progressbar = "com.wang.avi:library:2.1.3"
    const val utils_crashlytics = "com.crashlytics.sdk.android:crashlytics:2.9.5@aar"

    const val test_junit = "junit:junit:4.12"
    const val test_nhaarman = "com.nhaarman:mockito-kotlin:1.5.0"
    const val test_mockito = "org.mockito:mockito-core:2.11.0"
    const val test_mockito_inline = "org.mockito:mockito-inline:2.11.0"
    const val test_assertj = "org.assertj:assertj-core:3.2.0"
    const val test_robolectric = "org.robolectric:robolectric:3.4.2"
    const val test_robolectricMultidex = "org.robolectric:shadows-multidex:3.4-rc2"
    const val test_json = "org.json:json:20140107"
    const val test_dexmaker = "com.google.dexmaker:dexmaker:1.2"
    const val test_dexmaker_mockito = "com.google.dexmaker:dexmaker-mockito:1.2"

    const val android_arch_room_runtime = "android.arch.persistence.room:runtime:${Versions.room}"
    const val android_arch_room_compiler = "android.arch.persistence.room:compiler:${Versions.room}"
    const val android_arch_room_testing = "android.arch.persistence.room:testing:${Versions.room}"
    const val android_arch_room_testing_core = "android.arch.core:core-testing:${Versions.room}"
}
