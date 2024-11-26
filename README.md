[flow_diagram.txt](https://github.com/user-attachments/files/17923058/flow_diagram.txt)Android Boilerplate setup for Jetpack compose, navigation, hilt, retrofit with moshi, room, shared preference, coroutine.

Support debounce, api level caching and offline support

Uses open source imdb api: https://omdbapi.com/

<img width="332" alt="Screenshot 2024-11-26 at 10 28 07 PM" src="https://github.com/user-attachments/assets/c227c3c4-e822-401c-abd6-cb4328e0b76a">
<img width="329" alt="Screenshot 2024-11-26 at 10 27 53 PM" src="https://github.com/user-attachments/assets/dc4a2bba-2d8f-4b2f-bb20-c1a841bc1ca0">
<img width="330" alt="Screenshot 2024-11-26 at 10 27 33 PM" src="https://github.com/user-attachments/assets/2873b901-15db-463c-9e90-241fe89ee24f">
<img width="336" alt="Screenshot 2024-11-26 at 10 26 52 PM" src="https://github.com/user-attachments/assets/a93b1606-fe3e-423e-92b4-57422be424e0">


Basic flow diagram of functioning:

[Uploading flow_diagram.t{\rtf1\ansi\ansicpg1252\cocoartf2820
\cocoatextscaling0\cocoaplatform0{\fonttbl\f0\fnil\fcharset0 .AppleSystemUIFontMonospaced-Regular;}
{\colortbl;\red255\green255\blue255;\red155\green162\blue177;\red197\green136\blue83;\red184\green93\blue213;
}
{\*\expandedcolortbl;;\cssrgb\c67059\c69804\c74902;\cssrgb\c81961\c60392\c40000;\cssrgb\c77647\c47059\c86667;
}
\paperw11900\paperh16840\margl1440\margr1440\vieww11520\viewh8400\viewkind0
\pard\tx560\tx1120\tx1680\tx2240\tx2800\tx3360\tx3920\tx4480\tx5040\tx5600\tx6160\tx6720\pardirnatural\partightenfactor0

\f0\fs26 \cf2           +-----------------------------+\
          |         MainActivity        |\
          +-----------------------------+\
                    |\
                    |\
                    v\
           +--------------------------+\
           |      ConnectivityObserver |\
           +--------------------------+\
                    |\
           Check Network Status\
                    |\
      +-------------------------------+\
      |                               |\
      |                               v\
      |                    +-------------------+\
      |                    |   Show Snackbar   |\
      |                    | (No internet msg) |\
      |                    +-------------------+\
      |                               |\
      |                               |\
      +-------------------------------+\
                    |\
                    |\
                    v\
          +-----------------------------+\
          |        Home Screen          |\
          +-----------------------------+\
               |               |\
               |               |\
               v               |\
     Debounced TextField       |\
       User Input              |\
               |               |\
        [Debounce API Call]    |\
               |               |\
               v               |\
        +------------------+    |\
        |  MovieViewModel  |    |\
        +------------------+    |\
               |               |\
               v               |\
         Trigger Repository    |\
               |               |\
               v               |\
       +--------------------+   |\
       |  MovieRepository   |   |\
       +--------------------+   |\
               |               |\
               v               |\
         +---------------+     |\
         |   Retrofit    |     |\
         |  (w/ Cache)   |     |\
         +---------------+     |\
               |               |\
    Fetch Data |               | Populate Grid (3xN) View\
               |               |\
               v               |\
        +---------------------+ |\
        |      Response       | |\
        |  Cached (\cf3 5\cf2  mins)    | |\
        +---------------------+ |\
               |               |\
               v               |\
    +------------------------+  |\
    | ViewModel (Collects    |  |\
    | Flow)                  |  |\
    +------------------------+  |\
               |               |\
               v               |\
         Compose UI Grid       |\
     Display Results (3xN)     |\
               |               |\
               v               v\
      +---------------------------+\
      |   User Clicks on Movie    |\
      +---------------------------+\
               |\
               v\
+-------------------------+                 +-------------------------+\
| Save Movie to           |  Navigate       |         Detail          |\
| SharedPreferences       |---------------> |         Screen          |\
+-------------------------+                 +-------------------------+\
               |                                       |\
               |                                       |\
               v                                       |\
  Save as Recently Viewed                              |\
               |                                       |\
+-------------------------+                            |\
| ViewModel Calls Repo    |                            |\
| \cf4 for\cf2  Movie Detail        |                            |\
+-------------------------+                            |\
               |                                       |\
               v                                       |\
+-------------------------+                            |\
|   MovieRepository       |                            |\
+-------------------------+                            |\
               |                                       |\
               v                                       |\
      +---------------+                                |\
      |   Retrofit    |                                |\
      |  (w/ Cache)   |                                |\
      +---------------+                                |\
               |                                       |\
               v                                       |\
         +----------------+                            |\
         |   Fetch Data   |                            |\
         |  (from Cache)  |                            |\
         +----------------+                            |\
               |                                       |\
               v                                       |\
+-----------------------------+                        |\
| Save Movie Detail to Room   |                        |\
| (Offline Access)            |                        |\
+-----------------------------+                        |\
               |                                       |\
               v                                       |\
+-----------------------------+                        |\
|       Display Detail        |                        |\
|   Title, Rating, Info, etc. |                        |\
+-----------------------------+                        |\
                                                      |\
               |                                       |\
               v                                       |\
    Offline Support: Fetch                            |\
    from Room \cf4 if\cf2  Internet                            |\
    is Not Available                                 |}xt…]()
