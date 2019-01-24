# Comic Kindle
## A simple comic book app for Udacity's [Android Developer Nanodegree Program](https://www.udacity.com/course/android-developer-nanodegree-by-google--nd801) capstone project.

This project demonstrates the use of [Comic Vine API](https://comicvine.gamespot.com/). The purpose is to create a database of comic issues, volumes, and characters then track them using lists. There are two types of lists: My Collection and Watchlist. My Collection allows users to save issues they have already read or own. The Watchlist allows users to watch volumes to track the release dates of new issues. The project makes use of java and xml and was built using [Android Studio](https://developer.android.com/studio/)

## Table of Contents

- [Preview](#Preview)
- [Download](#download)
- [How to run this application](#How-to-run-this-application)
- [Rubric](#rubric)

## Preview
![First Look](https://github.com/keldavis/comic-kindle/blob/master/screen%20shots/first%20look.gif)
![Issue Preview](https://github.com/keldavis/comic-kindle/blob/master/screen%20shots/issues%20to%20collection.gif)
![Calendar Preview](https://github.com/keldavis/comic-kindle/blob/master/screen%20shots/calendar%20to%20collection.gif)
![Volume Preview](https://github.com/keldavis/comic-kindle/blob/master/screen%20shots/volume%20to%20watchlist.gif)
![Character Preview](https://github.com/keldavis/comic-kindle/blob/master/screen%20shots/character.gif)

## Download
The files can be downloaded [here](https://github.com/keldavis/comic-kindle/archive/master.zip)

The entire project can be cloned using this link: https://github.com/keldavis/comic-kindle.git

## How to run this application
- Sign up at [Comic Vine](https://comicvine.gamespot.com/) to get an API key
- [Download or clone](#download) this repository
- Open app/build.gradle and put your API key in the build config field
[API Key preview](https://github.com/keldavis/comic-kindle/blob/master/screen%20shots/api_key.PNG)

## Rubric

#### Common Project Requirements
- [x] App conforms to common standards found in the [Android Nanodegree General Project Guidelines](http://udacity.github.io/android-nanodegree-guidelines/core.html)
- [x] App is written solely in the Java Programming Language

#### Core Platform Development
- [x] App integrates a third-party library.
- [x] App validates all input from servers and users. If data does not exist or is in the wrong format, the app logs this fact and does not crash.
- [x] App includes support for accessibility. That includes content descriptions, navigation using a D-pad, and, if applicable, non-audio versions of audio cues.
- [x] App keeps all strings in a strings.xml file and enables RTL layout switching on all layouts.
- [x] App provides a widget to provide relevant information to the user on the home screen.

#### Google Play Services
- [x] App integrates two or more Google services. Google service integrations can be a part of Google Play Services or Firebase.
- [x] Each service imported in the build.gradle is used in the app.
- [x] If Analytics is used, the app creates only one analytics instance.

#### Material Design
- [x] App theme extends AppCompat.
- [x] App uses an app bar and associated toolbars.
- [x] App uses standard and simple transitions between activities.

#### Building
- [x] App builds from a clean repository checkout with no additional configuration.
- [x] App builds and deploys using the installRelease Gradle task.
- [x] App is equipped with a signing configuration, and the keystore and passwords are included in the repository. Keystore is referred to by a relative path.
- [x] All app dependencies are managed by Gradle.

#### Data Persistence
- [x] App stores data locally either by implementing a ContentProvider OR using Firebase Realtime Database. No third party frameworks nor Room Persistence Library may be used.
- [x] If it regularly pulls or sends data to/from a web service or API, app updates data in its cache at regular intervals using a SyncAdapter or JobDispacter.
- [x] App uses a Loader to move its data to its views.