Android Boilerplate setup for Jetpack compose, navigation, hilt, retrofit with moshi, room, shared preference, coroutine.

Support debounce, api level caching and offline support

Uses open source imdb api: https://omdbapi.com/

<img width="332" alt="Screenshot 2024-11-26 at 10 28 07 PM" src="https://github.com/user-attachments/assets/c227c3c4-e822-401c-abd6-cb4328e0b76a">
<img width="329" alt="Screenshot 2024-11-26 at 10 27 53 PM" src="https://github.com/user-attachments/assets/dc4a2bba-2d8f-4b2f-bb20-c1a841bc1ca0">
<img width="330" alt="Screenshot 2024-11-26 at 10 27 33 PM" src="https://github.com/user-attachments/assets/2873b901-15db-463c-9e90-241fe89ee24f">
<img width="336" alt="Screenshot 2024-11-26 at 10 26 52 PM" src="https://github.com/user-attachments/assets/a93b1606-fe3e-423e-92b4-57422be424e0">


Basic flow diagram of functioning:
          +-----------------------------+
          |         MainActivity        |
          +-----------------------------+
                    |
                    |
                    v
           +--------------------------+
           |      ConnectivityObserver |
           +--------------------------+
                    |
           Check Network Status
                    |
      +-------------------------------+
      |                               |
      |                               v
      |                    +-------------------+
      |                    |   Show Snackbar   |
      |                    | (No internet msg) |
      |                    +-------------------+
      |                               |
      |                               |
      +-------------------------------+
                    |
                    |
                    v
          +-----------------------------+
          |        Home Screen          |
          +-----------------------------+
               |               |
               |               |
               v               |
     Debounced TextField       |
       User Input              |
               |               |
        [Debounce API Call]    |
               |               |
               v               |
        +------------------+    |
        |  MovieViewModel  |    |
        +------------------+    |
               |               |
               v               |
         Trigger Repository    |
               |               |
               v               |
       +--------------------+   |
       |  MovieRepository   |   |
       +--------------------+   |
               |               |
               v               |
         +---------------+     |
         |   Retrofit    |     |
         |  (w/ Cache)   |     |
         +---------------+     |
               |               |
    Fetch Data |               | Populate Grid (3xN) View
               |               |
               v               |
        +---------------------+ |
        |      Response       | |
        |  Cached (5 mins)    | |
        +---------------------+ |
               |               |
               v               |
    +------------------------+  |
    | ViewModel (Collects    |  |
    | Flow)                  |  |
    +------------------------+  |
               |               |
               v               |
         Compose UI Grid       |
     Display Results (3xN)     |
               |               |
               v               v
      +---------------------------+
      |   User Clicks on Movie    |
      +---------------------------+
               |
               v
+-------------------------+                 +-------------------------+
| Save Movie to           |  Navigate       |         Detail          |
| SharedPreferences       |---------------> |         Screen          |
+-------------------------+                 +-------------------------+
               |                                       |
               |                                       |
               v                                       |
  Save as Recently Viewed                              |
               |                                       |
+-------------------------+                            |
| ViewModel Calls Repo    |                            |
| for Movie Detail        |                            |
+-------------------------+                            |
               |                                       |
               v                                       |
+-------------------------+                            |
|   MovieRepository       |                            |
+-------------------------+                            |
               |                                       |
               v                                       |
      +---------------+                                |
      |   Retrofit    |                                |
      |  (w/ Cache)   |                                |
      +---------------+                                |
               |                                       |
               v                                       |
         +----------------+                            |
         |   Fetch Data   |                            |
         |  (from Cache)  |                            |
         +----------------+                            |
               |                                       |
               v                                       |
+-----------------------------+                        |
| Save Movie Detail to Room   |                        |
| (Offline Access)            |                        |
+-----------------------------+                        |
               |                                       |
               v                                       |
+-----------------------------+                        |
|       Display Detail        |                        |
|   Title, Rating, Info, etc. |                        |
+-----------------------------+                        |
                                                      |
               |                                       |
               v                                       |
    Offline Support: Fetch                            |
    from Room if Internet                            |
    is Not Available                                 |
