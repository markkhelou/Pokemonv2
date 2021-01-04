Table of Contents
=================

- [Pokémon V2](#pok-mon-v2)
  * [Requirements](#requirements)
- [App Architecture](#app-architecture)
  * [Model/Entities](#model-entities)
  * [Data layer](#data-layer)
  * [Usecases/Interactors](#usecases-interactors)
  * [Presentation layer](#presentation-layer)
- [Getting Started](#getting-started)
  * [Checkout the Code](#checkout-the-code)
  * [Major Libraries / Tools](#major-libraries---tools)
- [Setting up Prerequisites](#setting-up-prerequisites)
  * [Fastlane Setup](#fastlane-setup)
- [Running Quality Gates and Deployment Commands](#running-quality-gates-and-deployment-commands)
  * [Linting](#linting)
  * [Testing](#testing)
    + [Local unit tests](#local-unit-tests)
    + [Instrumented tests](#instrumented-tests)
    + [Running the Unit Tests](#running-the-unit-tests)
  * [Test Coverage](#test-coverage)
- [CI-CD - Github actions](#ci-cd---github-actions)
   
# Pokémon V2
Pokémon V2 is simple app to hit the Pokeapi and show a list of pokemons, that shows details when items on the list are tapped (a typical master/detail app).

![alt text](https://github.com/oudaykhaled/Pokemonv2/blob/master/demo.gif?raw=true)

We'll be using the most viewed section of this API.

Two APIs to consider here:

1- Api to fetch pokemons list
[https://pokeapi.co/api/v2/pokemon?limit={limit}&offset={offset}](https://pokeapi.co/api/v2/pokemon?limit=10&offset=0)

2- API to fetch pokemon details
[https://pokeapi.co/api/v2/pokemon/{pokemonID}](https://pokeapi.co/api/v2/pokemon/1)

This is a full RESTful API linked to an extensive database detailing everything about the Pokémon main game series. For more information about this APIs you can check this [documentation](https://pokeapi.co/docs/v2).

## Requirements
- Android Studio
- Java 8 - Ensure JAVA_HOME environment variable is set

# App Architecture

This sample follows the [clean architecture](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html) by [Robert C. Martin](https://en.wikipedia.org/wiki/Robert_C._Martin) along with [MVVM design pattern](https://en.wikipedia.org/wiki/Model%E2%80%93view%E2%80%93viewmodel).

## Model/Entities
  The model is the domain object. It represents the actual data and/or information we are dealing with. An example of a model might be a contact (containing name, phone number, address, etc) or the characteristics of a live streaming publishing point.

The key to remember with the model is that it holds the information, but not behaviors or services that manipulate the information. It is not responsible for formatting text to look pretty on the screen, or fetching a list of items from a remote server (in fact, in that list, each item would most likely be a model of its own). Business logic is typically kept separate from the model, and encapsulated in other classes that act on the model.

## Data layer
Provide all required data to the repository in form of models/entities.
##### Remote data source
Manage all server/external API calls.
##### Local data source
Manage all local data storage, example SQLite implementation, Room, Realm...
##### Volatile data source
Manage temporary storage.
##### Repository
The decision maker class when it comes to manage data CRUD operations. Operations can be done in this layer is caching mechanism, manage consecutive api calls etc...

## Usecases/Interactors
Represents concepts of the business, information about the current situation and business rules.

## Presentation layer
###ViewModel
ViewModel responsible for presentation logic.
###View
Views can be an android Activities, Fragments or any custom views. This layer does not contain any business logic which is the responsibility of usecase neither UI logic which is the responsibility of ViewModel.


# Getting Started

This repository implements the following quality gates:

Build Pipeline

- Static code checks: running [lint](https://developer.android.com/studio/write/lint) to check the code for any issues.
- Unit testing: running the [unit tests](https://developer.android.com/training/testing/)
- UI unit testing: running the [AndroidTest along with mockito](https://developer.android.com/training/testing/espresso)
- Code coverage: generating code coverage reports using the [JaCoCo gradle plugin](https://docs.gradle.org/current/userguide/jacoco_plugin.html)

These steps can be run manually or using a Continous Integration tool such as [Jenkins](https://jenkins.io/). This project use Github actions for CI/CD, you can check this [documentation](https://docs.github.com/en) for more information.


## Checkout the Code

Checkout and run the code
```bash
git clone https://github.com/oudaykhaled/Pokemonv2.git
cd pokemonv2
```

## Major Libraries / Tools

| Category                        	| Library/Tool   	| Link                                                       	|
|---------------------------------	|----------------	|------------------------------------------------------------	|
| Development                     	| Android - Java 	| https://developer.android.com/guide/                       	|
| Build & Dependencies Management 	| Gradle         	| https://developer.android.com/studio/build/               	|
| Unit Testing                    	| JUnit          	| https://developer.android.com/training/testing             	|
| Code Coverage                   	| JaCoCo         	| https://docs.gradle.org/current/userguide/jacoco_plugin.html|
| Static Code Check               	| Gradle Lint    	| https://developer.android.com/studio/write/lint           	|
| Functional Testing              	| Espresso         	| https://developer.android.com/training/testing/espresso                          	|
| Dependency injection               	| Hilt         	| https://developer.android.com/training/dependency-injection/hilt-android                                          	|
| Continous Integration            	| Github actions         | https://docs.github.com/en                                          	|

# Setting up Prerequisites
## Fastlane Setup

To setup Fastlane please read the [README.md](./fastlane/README.md) file inside the `./fastlane` folder

# Running Quality Gates and Deployment Commands
## Linting

`./gradlew lint`

or using Fastlane:

`fastlane lint`

Linting results are available at `pokemonv2/app/build/reports/lint-results.html`

![alt text](https://github.com/oudaykhaled/Pokemonv2/blob/master/lint%20report.PNG?raw=true)

## Testing
Tests in Android are separated into 2 types:

### Local unit tests

Located at `pokemonv2/app/src/test/java/` - These are tests that run on your machine’s local Java Virtual Machine (JVM). Use these tests to minimize execution time when your tests have no Android framework dependencies or when you can mock the Android framework dependencies.

### Instrumented tests

Located at `pokemonv2/app/src/androidTest/java/` - These are tests that run on a hardware device or emulator. These tests have access to Instrumentation APIs, give you access to information such as the Context of the app you are testing, and let you control the app under test from your test code. Use these tests when writing integration and functional UI tests to automate user interaction, or when your tests have Android dependencies that mock objects cannot satisfy.

### Running the Unit Tests

Unit testing for Android applications is fully explained in the [Android documentation](https://developer.android.com/training/testing/). In this repository, jUnit test case has been written for Presenter

From the commandline run:

`./gradlew clean test`

or using Fastlane:

`fastlane tests`

Unit tests results are available at 

`pokemonv2/app/build/reports/tests/testDebugUnitTest/index.html`

From Android Studio

* Right Clicking on the Class and select "Run <test class>
* To see the coverage we have t the select "Run <test class> with Coverage"
![alt text](https://github.com/oudaykhaled/Pokemonv2/blob/master/unit%20test.PNG?raw=true)
 
## Test Coverage

The test coverage uses the [JaCoCo](http://www.eclemma.org/jacoco/) library

From the commandline

`./gradlew clean jacocoTestReport`

Test coverage results are available at 

`pokemonv2/app/build/reports/jacoco/jacocoTestReport/html/index.html`

* Run the following command from the project root folder

```bash
./gradlew --info sonarqube
```

# CI-CD - Github actions

This repo contains a [workflows file](./.github/workflows/main.yml) , which is used to define a **declarative pipeline** for CI-CD to build the code, run the quality gates, code coverage.

Here is an example structure of the Jenkinsfile declarative pipeline:

```
name: CI

on:
  push:
    branches: [ master ]
  pull_request:
    branches: [ master ]

  workflow_dispatch:

jobs:
  build:
    runs-on: ubuntu-latest

    steps:
      - uses: actions/checkout@v2

      - name: Permission
        run: chmod +x gradlew

      - name: Test
        run: ./gradlew test
       
      - name: Lint
        run: ./gradlew lint
       
      - name: Build
        run: ./gradlew build

```
![alt text](https://github.com/oudaykhaled/Pokemonv2/blob/master/ci-cd.PNG?raw=true)

Below is an illustration of the pipeline that Github actions will execute

![Build Pipeline](./jenkins/pipeline.png "Build Pipeline")


Apache License, Version 2.0
http://www.apache.org/licenses/LICENSE-2.0
