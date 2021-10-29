# Speed Test Demo #

The goal of this HarmonyOS Watch app is to determine network speed on each connection, and to debug when each network connection is used.

This will display 3 different network connection types that the watch is using to get its internet:

- cellular (if the in use network is the watch sim card)

- wifi (if the in use network is the WiFi network that the watch is directly connected to)

- bluetooth (if the in use network is the bluetooth connection to the paired device)

## Running the app ##

- First off, create a debug key for said application, and optionally change the package name.
- Replace the TODO in `MainAbilitySlice.java:37` to point to a file that you control
- Launch app

### App overview ###

- When the app launches, it will start to download the file. While it is downloading, the top button will be blue. 
If the download is cancelled or an error occurs, the button will turn red.

- The app will display how long the download takes, and displays how much has been downloaded so far.

- While the app is downloading something, it does not actually save the data anywhere.

- When the download finishes, hitting restart will restart the download

- Hitting force stop will force the system to kill the app

### Downloading ###

Downloading uses HttpURLConnection.java via the `ohos.net.NetManager` default `NetHandle`

Downloading occurs in `MainAbilitySlice`