# Movie Watchlist App

## Overview

Welcome to the Movie Watchlist App repository. This repository contains the source code for the Movie Watchlist App, an Android application for managing movie watchlists. Please be aware that this code is provided solely for reference purposes and is not open for modification or redistribution without prior written consent.
This app was developed using Android Studio with Java as the backend, it makes use of The Movie Database API and libraries like Volley, Glide, Room, and BlurView.






[Preview Link](https://appetize.io/app/zdjbkdem7cmvfy46onskkah4uu?device=pixel6&osVersion=12.0&scale=75&audio=true)

![1](https://github.com/ananthakrishnanp/movi/assets/94164901/71d8109f-d8ae-41ae-8812-abd5f5c40dba)
![2](https://github.com/ananthakrishnanp/movi/assets/94164901/29af74de-95b0-4a1e-9289-2d9d273ec0af)

NOTE: For the app to work you need to add The movie database api key in local.properties as API_KEY=yourapikey and following changes in the build.gradle.kts file.

### build.gradle.kts

```kotlin

  
import java.util.Properties;
import java.io.FileInputStream;

plugins {
    id("com.android.application")
}

android {
    namespace = "com.example.movie"
    compileSdk = 33
    
    fun loadApiKey(): String {
        val localPropertiesFile = rootProject.file("local.properties")
        val localProperties = Properties()
        localProperties.load(FileInputStream(localPropertiesFile))
        return localProperties.getProperty("API_KEY", "")
    }

    defaultConfig {
      ...
        buildConfigField("String", "API_KEY", "\"${loadApiKey()}\"")
      ...

    }
//rest of code

dependencies {
  
    implementation("androidx.room:room-runtime:2.5.2")
    annotationProcessor("androidx.room:room-compiler:2.5.2")
    implementation("com.github.Dimezis:BlurView:version-2.0.3")
    implementation("com.android.volley:volley:1.2.1")
    implementation("com.github.Dimezis:BlurView:version-2.0.2")
    implementation("com.airbnb.android:lottie:6.1.0")
    implementation("com.github.bumptech.glide:glide:4.15.1")
    annotationProcessor("com.github.bumptech.glide:compiler:4.15.1")

}
```


## Usage

You are invited to explore and review the code to gain insights into how the Movie Watchlist App was developed. However, it's important to understand and adhere to the following usage guidelines:

1. **Reference Only**: This code is made available exclusively for educational and reference purposes. You may study it, learn from it, and use it as a source of inspiration for your own projects.

2. **Modification Prohibited**: You are not permitted to modify, adapt, or build upon this codebase in any way. Any attempts to make changes to the code are strictly prohibited.

3. **No Redistribution**: You may not distribute or share this code, in whole or in part, with others. This includes public repositories, code-sharing platforms, or any other means of distribution.

4. **Commercial Use**: Any use of this code for commercial purposes, including but not limited to creating derivative works, selling, or incorporating it into a commercial application, is expressly forbidden without obtaining explicit written permission.

## Contact

If you have questions, inquiries, or requests related to the code or its usage, please contact [ananthumukkathu@gmail.com](mailto:ananthumukkathu@gmail.com) for further information.

We appreciate your understanding and respect for these usage guidelines.

Happy exploring!
