# HarmonyOS-Watch-Network-Speed-Test-Demo 

# Introduction
This demo provides the network speed test on Huawei HarmonyOS Watch 3. When an Android or HarmonyOS phone that has Huawei Health App installed is connected the watch through Bluetooth, the demo app measures the data speed of the Bluetooth connection. On the other hand, when the watch has WiFi connected, the demo app measures the data speed of the WiFi network.      

# Installation requirements
Install Huawei DevEco Studio and set up the DevEco Studio development environment. The DevEco Studio development environment needs to depend on the network environment. It needs to be connected to the network to ensure the normal use of the tool.The development environment can be configured according to the following two situations: 1) If you can directly access the Internet, just download the HarmonyOS SDK; 2) If the network cannot access the Internet directly, it can be accessed through a proxy server • Generate secret key and apply for certificate

# User guide 
Download this Project and open DevEco Studio, click File> Open> Then select and open this Project • Click Build> Build App(s)/Hap(s)>Build Debug Hap(s) to compile the hap package.  Then Click Run> Run 'entry' to run the hap package.
Note that you can choose to run the hap package on the simulator or the Huawei watch 3. If you run it on the watch, you need to configure the signature and certificate information in the project's File> Project Structure> Modules> Signing Configs.

You need to add a source file (xxx) in the public static String downloadFile = "https://xxx" in entry/src/main/.../slice/MainAbilitySlice.java

# Screenshots of demo

![BTspeed1](https://user-images.githubusercontent.com/88169365/139187631-2da6304e-6a29-45b0-a5cc-080fd032361f.png)
![BTspeed2](https://user-images.githubusercontent.com/88169365/139187641-a8a90dfb-f353-42c3-b237-dde29289b189.png)
![WiFispeed1](https://user-images.githubusercontent.com/88169365/139187658-38a36a43-598c-4bd4-841a-ac9309dddcf9.png)
![WiFispeed2](https://user-images.githubusercontent.com/88169365/139187668-f6b8167e-dff5-4ea5-88ae-7f51d03d01f6.png)
