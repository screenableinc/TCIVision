package com.screenable.tcivision;

import android.content.Context;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;


import java.net.URISyntaxException;

public class SocketConnect {
    public static Socket socket;
    public static Boolean isConnected;

    public void SocketConnect(String event, String accessToken, String electionId, Context context) {
        try {

            IO.Options options = new IO.Options();
            options.query = "electionId=" + electionId + "&accessToken=" + accessToken + "&event=" + event;
            socket = IO.socket(CONFIG.ip + "/", options).connect();
            socket.on("false", new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    isConnected = false;

                }
            })
                    .on("true", new Emitter.Listener() {
                        @Override
                        public void call(Object... args) {
//                    alert user
                            isConnected = true;
                        }
                    });


        } catch (URISyntaxException e) {
                isConnected=false;

        }
    }

    public void vote(String key) {
        socket.emit(key, "");
    };

//    private Emitter.Listener on = new Emitter.Listener() {
//        @Override
//        public void call(final Object... args) {
//            getActivity().runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    JSONObject data = (JSONObject) args[0];
//                    String username;
//                    String message;
//                    try {
//                        username = data.getString("username");
//                        message = data.getString("message");
//                    } catch (JSONException e) {
//                        return;
//                    }
//
//                    // add the message to view
//                    addMessage(username, message);
//                }
//            });
//        }
//
//    public static boolean connect(){
//
//        return false;
//    }
//}
}
