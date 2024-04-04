<h1 align="center">Card Info Finder App</h1>

<p align="center">
  <a href="https://www.android.com/"><img alt="Platform" src="https://img.shields.io/badge/Platform-Android-white"/></a>
  <a href="https://opensource.org/licenses/Apache-2.0"><img alt="License" src="https://img.shields.io/badge/License-Apache%202.0-blue.svg"/></a>
  <a href="https://android-arsenal.com/api?level=21"><img alt="API" src="https://img.shields.io/badge/API-21%2B-yellow.svg?style=flat"/></a>
</p>

## About
This is a Mobile application for users to fetch information about their card using the first 6 - 8 digits of the card. The information provided includes bank, country, card type, and card brand.

## Configurations
- Minimum SDK level 26
- Compile SDK version 34
- Version code 1
- Version name "1.0"

## Tech stack & Third-party libraries
- [Kotlin](https://kotlinlang.org/), [Coroutines](https://github.com/Kotlin/kotlinx.coroutines) + [Flow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/) for asynchronous operations and background processes.
- Jetpack
    - Lifecycle - Observe Android lifecycles and handle UI states upon the lifecycle changes.
    - ViewModel - Manages UI-related data holder and lifecycle awareness. Allows data to survive configuration changes such as screen rotations.
    - Compose - Manages states for user interface.
    - JUnit, Mockito -For writing test cases.
    - Hilt - Manage dependency injection.
- Architecture
    - MVVM Architecture (Model - View - ViewModel)
    - Repository Pattern
- [Retrofit2 & OkHttp3](https://github.com/square/retrofit) - Construct the REST APIs.
- [KotlinX Serializer] - A modern JSON library for Kotlin.
- [Timber](https://github.com/JakeWharton/timber) - A logger with a small, extensible API.
- [Truth](https://truth.dev) - A library for performing assertions in tests.
- [Material-Components](https://github.com/material-components/material-components-android) - Material design components for building user interfaces.

## App Architecture
The Card Info Finder app is based on the MVVM architecture and the Repository pattern.

![architecture](https://miro.medium.com/max/1400/1*anZUrs-wCLc5mEN0ElDaow.png)
* Side Note: Instead of emitting LiveData, we have chosen to emit StateFlow which similar to LiveData can be observed for any changes by views. This allows maintaining a consistent flow of data stream while leveraging on the power of Kotlin Flow to transform the data stream however we like.

<p align="center">
<a href="https://developer.android.com/topic/architecture/"><img alt="Webpage" src="https://miro.medium.com/v2/resize:fit:1400/format:webp/1*ol7iY_f4OiFSxO7qhfGqiw.png"/></a> 
</p>

## API
The following APIs were consumed in this project
- Binlist API

## Device Specification and SDK Integration
- The app can only be installed on Android devices with versions between Android 8 and 14.


## License
```
Miracle Ukaka retains exclusive rights to the use and ownership of this project.
```
