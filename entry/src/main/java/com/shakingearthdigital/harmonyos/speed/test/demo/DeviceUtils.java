/*

* Copyright 2021. Lolay Inc. All rights reserved.

* Licensed under the Apache License, Version 2.0 (the "License");

* you may not use this file except in compliance with the License.

* You may obtain a copy of the License at

* http://www.apache.org/licenses/LICENSE-2.0

* Unless required by applicable law or agreed to in writing, software

* distributed under the License is distributed on an "AS IS" BASIS,

* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.

* See the License for the specific language governing permissions and

* limitations under the License.

*/

package com.shakingearthdigital.harmonyos.speed.test.demo;

import ohos.aafwk.ability.AbilityPackage;
import ohos.app.Context;
import ohos.media.audio.AudioDeviceDescriptor;
import ohos.media.audio.AudioManager;
import ohos.net.NetCapabilities;
import ohos.net.NetHandle;
import ohos.net.NetManager;
import ohos.telephony.SimInfoManager;

import java.util.Locale;

public class DeviceUtils {

    /**
     * Get the primary network connection. Ignore the fact that there could be multiple connections
     *
     * @param context Application context
     * @return Returns result of {{@link DeviceUtils#getLabel(ohos.net.NetCapabilities)}}
     */
    public static String getConnectionType(Context context) {
        return getLabel(getCurrentConnectionCapabilities(context));
    }

    /**
     * Get the primary network connection. Ignore the fact that there could be multiple connections
     * @param context Application context
     * @return current connection as {@link NetCapabilities}
     */
    public static NetCapabilities getCurrentConnectionCapabilities(Context context){
        NetManager netManager = NetManager.getInstance(context);
        NetHandle handle = netManager.getAppNet();
        if(handle == null) handle = netManager.getDefaultNet();
        NetCapabilities props = netManager.getNetCapabilities(handle);
        return props;
    }

    public static void switchToFastestNetwork(Context context){
        //prefer a connection that is NOT bluetooth. Reset it if needed
        if(!DeviceUtils.setPreferredNetwork(context, NetCapabilities.BEARER_WIFI)){
            if(!DeviceUtils.setPreferredNetwork(context, NetCapabilities.BEARER_CELLULAR)){
                DeviceUtils.reset(context);
            }
        }
    }

    private static void reset(Context context) {
        NetManager netManager = NetManager.getInstance(context);
        netManager.setAppNet(null);
    }

    public static boolean setPreferredNetwork(Context context, int netCapability){
        NetManager netManager = NetManager.getInstance(context);
        NetCapabilities props = netManager.getNetCapabilities(netManager.getDefaultNet());
        NetHandle[] nets = netManager.getAllNets();
        if(!props.hasBearer(netCapability)){
            //attempt to scan for other connected types to use instead
            for (NetHandle handle : nets) {
                NetCapabilities capabilities = netManager.getNetCapabilities(handle);
                if (capabilities.hasBearer(netCapability)) {
                    netManager.setAppNet(handle);
                    return true;
                }
            }
            return false;
        }
        else{
            return true;
        }
    }

    /**
     * Get a display string for a network connection
     * @param capabilities A specific network capability
     * @return cell, wifi, bluetooth, lowpan, unknown
     */
    public static String getLabel(NetCapabilities capabilities){
        if(capabilities == null) return "unknown";
        if(capabilities.hasBearer(NetCapabilities.BEARER_BLUETOOTH)){
            return "bluetooth";
        }
        if(capabilities.hasBearer(NetCapabilities.BEARER_CELLULAR)){
            return "cell";
        }
        if(capabilities.hasBearer(NetCapabilities.BEARER_LOWPAN)){
            return "lowpan";
        }
        if(capabilities.hasBearer(NetCapabilities.BEARER_WIFI)){
            return "wifi";
        }
        return "unknown";
    }
}
