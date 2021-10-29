# HarmonyOS-Watch-Network-Speed-Test-Demo 

# Introduction
This demo provides the network speed test on Huawei HarmonyOS Watch 3. When the watch is connected to an Android or HarmonyOS phone that has Huawei Health App installed through Bluetooth, the demo app measures the download speed from a xxMB source file via the Bluetooth connection. On the other hand, when the watch is connected to WiFi, the demo app measures the download speed from the source file via the WiFi network.      

# Installation requirements
Install Huawei DevEco Studio and set up the DevEco Studio development environment. The DevEco Studio development environment needs to depend on the network environment. It needs to be connected to the network to ensure the normal use of the tool.The development environment can be configured according to the following two situations: 1) If you can directly access the Internet, just download the HarmonyOS SDK; 2) If the network cannot access the Internet directly, it can be accessed through a proxy server • Generate secret key and apply for certificate

# User guide 
Download this Project and open DevEco Studio, click File> Open> Then select and open this Project • Click Build> Build App(s)/Hap(s)>Build Debug Hap(s) to compile the hap package.  Then Click Run> Run 'entry' to run the hap package.
Note that you can choose to run the hap package on the simulator or the Huawei watch 3. If you run it on the watch, you need to configure the signature and certificate information in the project's File> Project Structure> Modules> Signing Configs.

You need to add a download source file in the public static String downloadFile = " "; //TODO replace with a URL to download, in entry/src/main/.../slice/MainAbilitySlice.java

# License
The demo code is licensed under the Apache License, version 2.0.
All copyright belong to Lolay Inc.

# Screenshots of demo


![BTdata3](https://user-images.githubusercontent.com/88169365/139357863-52848400-d375-4117-b85a-5f62743768a8.PNG)
![BTdata2](https://user-images.githubusercontent.com/88169365/139357881-bcb57b87-0bc3-4754-930a-86fb3bc00947.PNG)
![WFdata1](https://user-images.githubusercontent.com/88169365/139357909-0e971cf2-cb65-4d29-bdf0-d9e63c3a0a28.PNG)
![WFdata2](https://user-images.githubusercontent.com/88169365/139357925-9bf0ef72-9afd-4d0a-aa66-2572cb0fcaec.PNG)
