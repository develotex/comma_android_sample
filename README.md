# **Comma SDK (by Develotex)**

[![Maven Central](https://img.shields.io/maven-central/v/io.develotex/comma.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22io.develotex%22%20AND%20a:%22comma%22)

`Comma SDK` is a simple solution to integrate audio and video calls into your applications.
Contacts us via email develotex@gmail.com to get your own api keys.
Getting api keys automatically on develotex.io - coming soon.

## **Using this sample**

Don't forget to add your `google-services.json` to `%PROJECT%/comma_sample` directory if you want to test this sample.

## **Integration**

Keep in mind that `Comma SDK` has min API level 21.

In Project `build.gradle`:
```groovy
repositories {
    mavenCentral()
}
```

In module `build.gradle`:
```groovy
compile 'io.develotex:comma:*LATEST_VERSION*'
```

## **Initialization**
There is two ways how to initialize `Comma` instance when your app started:

First way:
```java
    new Comma.Builder(applicationContext)
        .librarySideAuth(
            YOUR_API_ID,
            YOUR_API_KEY,
            YOUR_APP_ID,
            USER_ID,
            USER_NAME,
            PUSH_TOKEN)
        .setCaptureConfig(CaptureConfig.FHD)
        .setConnectionListener(new ConnectionListener() {...})
        .build();
```

To use next method, learn more how to generate `deviceId` and `deviceSecret` using `Comma REST API`.
After it you can save those values on your backend.
```java
    new Comma.Builder(applicationContext)
        .auth(
            DEVICE_ID,
            DEVICE_SECRET,
            PUSH_TOKEN)
        .setCaptureConfig(CaptureConfig.FHD)
        .setConnectionListener(new ConnectionListener() {...})
        .build();
```

When Comma Instance ready to use, you will get `onStarted(incomingCall)` callback in your `ConnectionListener`
**Incoming call is not null when you have unanswered incoming call after initialization**

When you do not need to use it anymore, do not forget to close connection by:
```java
    Comma.getInstance().terminate();
``` 
After it you will get `onStopped` callback in your `ConnectionListeners`

**DO NOT FORGET**
When current user logs out, call it:
```java
    Comma.getInstance().deleteDevice();
```
After it you will get `onStopped` callback in your `ConnectionListener`

## **Callbacks**

For listening incoming calls you can use `IncomingCallListener`.

To adding listener:
```java
    Comma.getInstance().addIncomingCallListener(new IncomingCallListener() {...});
``` 

To removing listener:
```java
    Comma.getInstance().removeIncomingCallListener(existingListener);
``` 

Available `IncomingCallListener` callbacks:
```java
    public void onIncomingCall(IncomingCall incomingCall) {
        // when you have active unanswered incoming call 
    }
```

## **Outgoing call**

Calling only audio:
```java
    Comma.getInstance().callAudio(USER_ID, new CallListener() {...});
```
Calling with video:
```java
    Comma.getInstance().callVideo(USER_ID, new CallListener() {...});
```

## **Incoming call**

* When app is not active you can check incoming call push in your `FirebaseMessagingService` like this:
```java
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        if(Comma.getCallPush(remoteMessage.getData()) != null) {
            // this is an incoming call push
            // 1. start application
            // 2. initialize library
            // 3. get onIncomingCall callback immediately
        }
    }
```

* When app is an active your will get `onIncomingCall` callback in yours `IncomingCallListeners`

To answer an incoming call:
```java
    Comma.getInstance().answerCall(isVideoAllowed, new CallListener() {...});
```

Or reject it simply with:
```java
    Comma.getInstance().rejectCall();
```

## **Active call**

When you are already in active call you can use those methods.

* To end the call: 
```java
    Comma.getInstance().hangUp();
```

* To mute or unmute your microphone: 
```java
    Comma.getInstance().mute();
    // or
    Comma.getInstance().unmute();
```

* To set speaker status: 
```java
    Comma.getInstance().setSpeakerEnabled(isEnabled);
```

* When in video call: 
```java
    Comma.getInstance().playVideo();
    // or
    Comma.getInstance().pauseVideo();
```

* To rotate camera: 
```java
    Comma.getInstance().changeCamera();
```

* To put video sources in your layout: 
```java
    Comma.getInstance().attachLocalSourceToContainer(viewGroupContainer);
    // or
    Comma.getInstance().attachRemoteSourceToContainer(viewGroupContainer);
```

## **Call callbacks**

Available `CallListener` callbacks:
```java
    public void onConnecting() {
        // when connection is establish after call methods callAuduo, callVideo or answerCall
    }

    public void onConnected(Boolean isVideoRequested) {
        // when connection is established and you can use all methods above for active calls
    }

    public void onDisconnected() {
        // when call has ended and connection is closed
    }

    public void onError(String error) {
        // on error occured
    }

    public void onLog(String log) {
        // log messages
    }
```

## **What's new**
* **1.0.1**
    * Just released

## **License**

[Click to watch Comma Sdk License](https://develotex.io/legal/iOS-Android-Comma-LICENSE.txt)