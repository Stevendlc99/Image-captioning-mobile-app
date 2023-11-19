# Sentiment Analysis with Google Photos Images

## Overview  
This project lays out an application for Android implement a system to detect emotions through image recognition from Google Photos repository. Finally, the program returns the images and the scores that got evaluated.

## User Guide 
1. Go to the `Android APK` folder and download the APK for android smartphones.
2. Copy the APK to your phone.
3. In your phone, search for the APK and install it. If your phone asks for confirmation or permission to install the apk, accept.
4. For login:
    Register with a google account to aunthenticate, if you do not have a google account, you must create one.
5. Select the photos from the Google Photos repository.
6. Begin the sentiment analysis and display the results.
7. If you want to delete your personal information, the app gives the option to do it.

## Importing the Project on Android Studio
1. Clone or download this repository:
     ```
     git clone (https://github.com/Zethearc/Software-Engineering.git)
     ```
2. Open Android Studio
3. Click on File -> New -> Import Project
     - **First Time using Android Studio:** Click Open an existing Android Studio project
4. Search for the directory `Sotfware-Enginnering.git` and select it.
5.  click OK and wait for it to sync.

The application will use the images to perform an analysis of the emotions displayed and will generate a tag for the images and a category to which it belongs.
1. **Inputs:** A batch of images.
2. **Source:** Google Photos or user’s phone gallery.
3. **Output:** A category and tag for each image. 
4. **Destination:** Main application interface.
5. **Requirements:** An initial batch of 8 photos. Photographs containing different elements on scene.

## User Guide for the APK
Android Studio configures new projects to deploy to Android Emulator or a connected device with a few clicks. Once the app is installed, you can use Apply Changes to deploy certain code and resource changes without building a new APK. To build and run the app, follow these steps:
1. In the toolbar, select the app from the run settings `drop-down menu`.
2. In the target device `drop-down menu`, select the device on which you want to run the app. 
If you do not have any device configured, you must connect a device via USB or create an AVD to use Android Emulator.

![1.2](https://github.com/Zethearc/Software-Engineering/blob/9fdeb8a3d9384ce729b694dd744d5b03db603caf/images/1.2.png)
   
3. Click on **Run**.

### How to connect to your device via USB
When everything is ready and the device is connected via USB, click Run in Android Studio to build and run the app on the device.
You can also use adb to execute commands as follows:
- Verify that the device is connected by executing the command `adb devices` from the directory `android_sdk/platform-tools/`. If it is connected, you will see the device in the list.
- Run any adb command with the `-d` flag to target the device.
Android Studio will warn you if you try to start the project on a device that has an associated error or warning. Iconography and stylistic changes differentiate between errors (device selections that result in a corrupted configuration) and warnings (device selections that may result in unexpected behavior, but can still be executed).

## User Guide for the Captioning Model (Dowload the file: https://drive.google.com/file/d/1jhwxlaBW_Vu1E4Otat5P3_acsqTBzXc6/view?usp=sharing)
1. Unzip the file `“captionmodel.rar”`.
2. Using the console access the captionmodel folder, then to the imagecaption folder.
3. Execute the command `"pip install -r requirements.txt"`.
4. Once all the requirements are installed, run the caption model with the command `"python caption2.py"` (for the first time, this could take a minute).
5. Once the capton model was executed with no errors, run the server with the command `"app.py"`
6. Wait for it to run. There will be IP addresses of the server, copy that one (any of those 2) in the app module that has as name `"MyAPI"` where it says base URL.
7. Compile and install the application, and the caption model should work.


## Demo of the App (Screenshots)
### Login
The first step in the App is to get login with your own credentials or by the Google Account. The user can choose one of them and get into the App. Once logged in, a screen will be displayed giving you permission to start uploading your photos.

![first](https://github.com/Zethearc/Software-Engineering/blob/1db71dfdba8272244ebf89042f0dca2c20f74a1d/images/first.jpeg)

![second](https://github.com/Zethearc/Software-Engineering/blob/9fdeb8a3d9384ce729b694dd744d5b03db603caf/images/second.jpeg)

![third](https://github.com/Zethearc/Software-Engineering/blob/9fdeb8a3d9384ce729b694dd744d5b03db603caf/images/third.jpeg)

### Select photos from Google Photos
The second step for the user is to upload their photos from Google Photos. For this the user must select the number of photos of his preference from Google Photos and upload them to the App. Remember to select the most relevant pictures about the user in order to obtain the best final analysis. You also have the option to remove photos one by one or click the remove all button.

![fourth](https://github.com/Zethearc/Software-Engineering/blob/9fdeb8a3d9384ce729b694dd744d5b03db603caf/images/fourth.jpeg)

![fifth](https://github.com/Zethearc/Software-Engineering/blob/9fdeb8a3d9384ce729b694dd744d5b03db603caf/images/fifth.jpeg)

### Sentiment Analysis
The third step for the user after registration is to begin sentiment analysis. Then, the sentiment analysis will start with the uploaded data. The final result is the display of a text string of the analysis and graphs corresponding to the result. The first thing that will be displayed is the captioning of the selected photo. After that, the program displays the percentages corresponding to positive, negative or neutral sentiments. The sentiment with the highest percentage corresponds to the final analysis calculated by the App.

![sixth](https://github.com/Zethearc/Software-Engineering/blob/9fdeb8a3d9384ce729b694dd744d5b03db603caf/images/sixth.jpeg)

![seventh](https://github.com/Zethearc/Software-Engineering/blob/9fdeb8a3d9384ce729b694dd744d5b03db603caf/images/seventh.jpeg)

## For Developers - Libraries needed to run the application code

  plugins   {   
      id 'com.android.application' version '7.2.1' apply false  
      id 'com.android.library' version '7.2.1' apply false  
      id 'org.jetbrains.kotlin.android' version '1.6.10' apply false  
      id "com.google.gms.google-services" version "4.3.13" apply false  
      id implementation 'com.google.firebase:firebase-analytics:21.1.0'  
      id implementation 'com.google.firebase:firebase-auth:21.0.6'  
  }
  
 ## Troubleshooting
Bugs can be reported in the issue tracker on our GitHub repo: https://github.com/Zethearc/Software-Engineering/issues4

## Authors of this project
Astudillo Jaime jaime.astudillo@yachaytech.edu.ec - [LinkedIn](https://www.linkedin.com/in/jaime-astudillo-664754228/)  
Cabezas Dario  dario.cabezas@yachaytech.edu.ec - [LinkedIn](https://www.linkedin.com/in/darioscabezas/)  
Camacho Jean   jean.camacho@yachaytech.edu.ec - [LinkedIn](https://www.linkedin.com/in/jean-camacho-126126212)  
De la Cruz Franklin franklin.de@yachaytech.edu.ec - [LinkedIn]   
Figueroa Saul. saul.figueroa@yachaytech.edu.ec - [LinkedIn]  
Moncada Claudia. claudia.moncada@yachaytech.edu.ec - [LinkedIn](https://www.linkedin.com/in/claudia-maria-moncada-da-silva-999a63248/)                        
Quelal Andres. andres.quelal@yachaytech.edu.ec - [LinkedIn]    
Quizhpe Edwin. edwin.quizhpe@yachaytech.edu.ec - [LinkedIn]  
Zapatier Luis. luis.zapatier@yachaytech.edu.ec - [LinkedIn]  
