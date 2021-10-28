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


package com.shakingearthdigital.harmonyos.speed.test.demo.slice;

import com.shakingearthdigital.harmonyos.speed.test.demo.DeviceUtils;
import com.shakingearthdigital.harmonyos.speed.test.demo.MainAbility;
import com.shakingearthdigital.harmonyos.speed.test.demo.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Button;
import ohos.agp.components.Text;
import ohos.agp.utils.Color;
import ohos.eventhandler.EventHandler;
import ohos.eventhandler.EventRunner;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;
import ohos.net.NetHandle;
import ohos.net.NetManager;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.TimeUnit;

public class MainAbilitySlice extends AbilitySlice {
    private Text networkLabel;
    private Text downloadTime;
    private Text downloadSize;
    private Button startButton;
    private Button killAppButton;
    private EventHandler eventHandler;
    private EventRunner threadRunner;
    private EventHandler threadHandler;
    private HiLogLabel label = new HiLogLabel(HiLog.LOG_APP, 0x0224F,"NetworkCalls"); //MY_MODULE=0x00201

    public static String downloadFile = "";
    private long startTime;
    private NetManager networkManager;
    private HttpURLConnection connection;
    private int currSize;

    public enum PlayState{
        IDLE,RUNNING,FINISHED,ERROR
    }
    private PlayState state;

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_main);
        bindUIComponents();
        threadRunner = EventRunner.create("NetworkThread");
        threadHandler = new EventHandler(threadRunner);
        networkManager = NetManager.getInstance(this);
        eventHandler = new EventHandler(EventRunner.getMainEventRunner());
        threadHandler.postTask(this::startDownload);
        runNetworkInfo();
    }

    private void startDownload() {
        eventHandler.postTask(()-> {
            resetTimer();
            setState(PlayState.RUNNING);
            runTimer();
        });

        try {
            NetHandle handle = networkManager.getDefaultNet();
            URLConnection urlConnection = handle.openConnection(new URL(downloadFile),
                    java.net.Proxy.NO_PROXY);
            if (urlConnection instanceof HttpURLConnection) {
                connection = (HttpURLConnection) urlConnection;
            }
            connection.setRequestMethod("GET");
            connection.connect();

            IOException error = null;
            InputStream in = null;
            try {
                in = connection.getInputStream();
                byte[] buffer = new byte[1024];
                int length;
                while ((length = in.read(buffer)) != -1) {
                    currSize += length;
                }
            }
            catch (IOException e){
                error = e;
            }
            finally {
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException ignored) {
                    }
                }
            }
            if(error != null) throw error;
            eventHandler.postTask(()-> {
                setState(PlayState.FINISHED);
            });
        } catch (IOException e) {
            eventHandler.postTask(()-> setState(PlayState.ERROR));
            e.printStackTrace();
        } finally {
            connection.disconnect();
        }
    }

    private void runNetworkInfo(){
        String connectionType = DeviceUtils.getConnectionType(this);
        networkLabel.setText(String.format("Network: %s", connectionType));
        eventHandler.postTask(this::runNetworkInfo, 3000);
    }

    private void resetTimer() {
        currSize = 0;
        startTime = System.currentTimeMillis();
    }

    private void runTimer() {
        long duration = System.currentTimeMillis()-startTime;
        downloadTime.setText(String.format("Download Time: %.2f", duration/1000f));
        downloadSize.setText(String.format("Size: %.2f MB", currSize/1048576f));
        if(state == PlayState.RUNNING){
            eventHandler.postTask(this::runTimer, 40);
        }
    }

    private void cancelDownload(){
        setState(PlayState.IDLE);
        try {
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setState(PlayState state){
        switch (state){
            case IDLE:
                startButton.setTextColor(Color.WHITE);
                startButton.setText("Start");
                downloadSize.setText("Size: --");
                break;
            case RUNNING:
                startButton.setTextColor(Color.CYAN);
                startButton.setText("Cancel");
                downloadSize.setText("Size: --");
                break;
            case FINISHED:
                startButton.setTextColor(Color.GREEN);
                startButton.setText("Restart");
                break;
            case ERROR:
                downloadSize.setText("Size: --");
                startButton.setTextColor(Color.RED);
                startButton.setText("Restart");
                break;
        }
        this.state = state;
    }

    private void bindUIComponents() {
        networkLabel = (Text) findComponentById(ResourceTable.Id_networkLabel);
        downloadTime = (Text) findComponentById(ResourceTable.Id_timeToDownload);
        downloadSize = (Text) findComponentById(ResourceTable.Id_downloadSize);
        startButton = (Button) findComponentById(ResourceTable.Id_retryButton);
        killAppButton = (Button) findComponentById(ResourceTable.Id_quitButton);
        startButton.setClickedListener((unused)->{
            switch (state){
                case IDLE:
                case FINISHED:
                case ERROR:
                    setState(PlayState.RUNNING);
                    threadHandler.postTask(this::startDownload);
                    break;
                case RUNNING:
                    cancelDownload();
                    setState(PlayState.IDLE);
                    break;
            }
        });
        killAppButton.setClickedListener((unused)->{
            System.exit(0);
        });
    }

    @Override
    public void onActive() {
        super.onActive();
    }

    @Override
    public void onForeground(Intent intent) {
        super.onForeground(intent);
    }
}
