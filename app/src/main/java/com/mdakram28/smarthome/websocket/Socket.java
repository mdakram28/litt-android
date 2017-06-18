package com.mdakram28.smarthome.websocket;

import android.util.Log;

import com.google.gson.Gson;
import com.mdakram28.smarthome.listeners.ControlStateListener;
import com.mdakram28.smarthome.listeners.SocketInitListener;
import com.mdakram28.smarthome.util.Preferences;
import com.mdakram28.smarthome.listeners.SocketConnectedListener;
import com.mdakram28.smarthome.listeners.SocketDataListener;
import com.mdakram28.smarthome.websocket.models.Command;
import com.mdakram28.smarthome.websocket.models.Control;
import com.mdakram28.smarthome.websocket.models.SocketRequest;
import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;
import com.neovisionaries.ws.client.WebSocketException;
import com.neovisionaries.ws.client.WebSocketFactory;
import com.neovisionaries.ws.client.WebSocketFrame;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mdakram28 on 13/6/17.
 */

public class Socket extends WebSocketAdapter {
    private static Socket socket = new Socket();
    WebSocket ws;
    WebSocketFactory wsFactory;
    Gson gson;
    private static boolean isProcessing = false;

    public HashMap<String,List<SocketDataListener>> listeners = new HashMap<String,List<SocketDataListener>>();
    public ArrayList<SocketConnectedListener> connectedListeners = new ArrayList<SocketConnectedListener>();
    public HashMap<String, ControlStateListener> stateListeners = new HashMap<>();

    public static void setInitListener(SocketInitListener initListener) {
        Socket.initListener = initListener;
    }

    public static SocketInitListener initListener;

    private Socket() {
        wsFactory = new WebSocketFactory();
        gson = new Gson();
    }

    public void init(){
        isProcessing = true;
        ControlsPersistence.init(this);
        NotificationPersistence.init(this);
        RoomConfigPersistence.init(this);
    }

    public static Socket getSocket() {
        return socket;
    }

    public void addTopicListener(String topic, SocketDataListener listener){
        List<SocketDataListener> topicListeners;
        if(!listeners.containsKey(topic)){
            topicListeners = new ArrayList<>();
            listeners.put(topic,topicListeners);
        }else{
            topicListeners = listeners.get(topic);
        }
        topicListeners.add(listener);
    }
    public void addConnectedListener(SocketConnectedListener listener){
        connectedListeners.add(listener);
        if(ws!=null && ws.isOpen()){
            listener.onConnect();
        }
    }
    public void setStateListener(Control control, ControlStateListener listener){
        stateListeners.put(control.deviceId,listener);
    }

    public void connect(String token) throws IOException {
        ws = wsFactory.createSocket(Preferences.websocketServer + "?token=" + token);
        ws.addListener(this);
        ws.connectAsynchronously();
    }

    @Override
    public void onConnected(WebSocket websocket, Map<String, List<String>> headers) throws Exception {
        for(SocketConnectedListener listener : connectedListeners){
            listener.onConnect();
        }
    }

    @Override
    public void onConnectError(WebSocket websocket, WebSocketException exception) throws Exception {
        System.out.println("Unable to connect");
        super.onConnectError(websocket, exception);
    }

    @Override
    public void onDisconnected(WebSocket websocket, WebSocketFrame serverCloseFrame, WebSocketFrame clientCloseFrame, boolean closedByServer) throws Exception {
        ws.connectAsynchronously();
    }

    @Override
    public void onTextMessage(WebSocket websocket, String text) throws Exception {
        Log.d("WS-DATA", text);
        JSONObject jsonObject = new JSONObject(text);
        String type = jsonObject.getString("type");
        if(type.equalsIgnoreCase("stateChange")){
            String device = jsonObject.getJSONObject("data").getString("device");
            String state = jsonObject.getJSONObject("data").getString("state");
            if(stateListeners.containsKey(device)){
                stateListeners.get(device).controlStateReceived(device,state);
            }
        }
        if(listeners.containsKey(type)){
            for(SocketDataListener listener : listeners.get(type)){
                listener.onDataReceived(type, jsonObject.get("data"));
            }
            if(isProcessing)
                tryInit();
        }
    }

    public void sendControlCommand(String controlId, String command){
        if(!ws.isOpen()){
            return;
        }
        Command req = new Command(controlId, command);
        String json = gson.toJson(req);
        ws.sendText(json);
        System.out.println(json);
    }

    public void requestRoomConfig() {
        if(!ws.isOpen()){
            return;
        }
        SocketRequest req = new SocketRequest("requestData", "roomConfig");
        String json = gson.toJson(req);
        ws.sendText(json);
        System.out.println(json);
    }

    public void requestDeviceConfig(){
        if(!ws.isOpen()){
            return;
        }
        SocketRequest req = new SocketRequest("requestData", "deviceConfig");
        String json = gson.toJson(req);
        ws.sendText(json);
        System.out.println(json);
    }

    public static void tryInit(){
        if(isProcessing && ControlsPersistence.isInit() && NotificationPersistence.isInit()
                && RoomConfigPersistence.isInit()){
            if(initListener != null){
                initListener.onInit();
            }
            isProcessing = false;
        }
    }
}
