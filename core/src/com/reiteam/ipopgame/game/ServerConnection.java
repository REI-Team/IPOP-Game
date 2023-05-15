package com.reiteam.ipopgame.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.czyzby.websocket.WebSocket;
import com.github.czyzby.websocket.WebSocketListener;
import com.github.czyzby.websocket.WebSockets;
import com.reiteam.ipopgame.MainGame;
import com.reiteam.ipopgame.game.Components.Totem;

import java.util.ArrayList;
import java.util.Map;

public class ServerConnection {
    private WebSocket socket;
    private String address;
    private int port;
    private String id="";

    public ServerConnection(String address,int port){
        this.address=address;
        this.port=port;
        //Creating websocket connection
        if(Gdx.app.getType()== Application.ApplicationType.Android )
            // en Android el host és accessible per 10.0.2.2
            this.address = "nodejs-api-production-a765.up.railway.app";
        socket = WebSockets.newSocket(WebSockets.toSecureWebSocketUrl(this.address, this.port));
        socket.setSendGracefully(false);
        socket.addListener((WebSocketListener) new MyWSListener());
        socket.connect();
        if(socket!=null){
            MainGame.gameLogs.add("INFO","Successfuly connected to the server");
        }else{
            MainGame.gameLogs.add("ERROR","We cannot create the connection");
        }

    }
    public void send(String message){
        socket.send(message);
    }
    public void updateTotems(Map<String, Object> players){
        MultiplayerScreen.getInstance().clearTotems();
        Gdx.app.log("Etiqueta totem", players.toString());
        for (Map.Entry<String, Object> playerEntry: players.entrySet()) {
            Map<String, Object> player = (Map<String, Object>) playerEntry.getValue();
            ArrayList<Map<String, Object>> totems = (ArrayList<Map<String, Object>>) player.get("totems");

            for (int i = 0; i < totems.size(); i++) {
                Map<String, Object> totem = (Map<String, Object>) totems.get(i).get("totem");
                Map<String, Object> pos = (Map<String, Object>) totems.get(i).get("position");
                //String x = String.valueOf(pos.get("x"));
                float x = Float.parseFloat(String.valueOf(pos.get("x")));
                float y = Float.parseFloat(String.valueOf(pos.get("y")));
                Gdx.app.postRunnable(new Runnable() {
                    @Override
                    public void run() {
                        MultiplayerScreen.getInstance().addtotem(String.valueOf(totem.get("id")),playerEntry.getKey(),String.valueOf(totem.get("name")),x,y);
                    }
                });

            }
            ArrayList<Map<String, Object>> traps = (ArrayList<Map<String, Object>>) player.get("traps");

            for (int i = 0; i < traps.size(); i++) {
                Map<String, Object> totem = (Map<String, Object>) traps.get(i).get("totem");
                Map<String, Object> pos = (Map<String, Object>) traps.get(i).get("position");
                //String x = String.valueOf(pos.get("x"));
                float x = Float.parseFloat(String.valueOf(pos.get("x")));
                float y = Float.parseFloat(String.valueOf(pos.get("y")));
                Gdx.app.postRunnable(new Runnable() {
                    @Override
                    public void run() {
                        MultiplayerScreen.getInstance().addtotem(String.valueOf(totem.get("id")),playerEntry.getKey(),String.valueOf(totem.get("name")),x,y);
                    }
                });

            }
        }
    }
    public void updatePlayers(Map<String, Object> players){

        Gdx.app.log("Players pos", players.toString());
        for (Map.Entry<String, Object> playerEntry: players.entrySet()) {
            if(!playerEntry.getKey().equals(MainGame.userID)){
                MultiplayerScreen.getInstance().clearPlayers();
                Map<String, Object> player = (Map<String, Object>) playerEntry.getValue();
                float x = Float.parseFloat(String.valueOf(player.get("x")));
                float y = Float.parseFloat(String.valueOf(player.get("y")));
                Gdx.app.postRunnable(new Runnable() {
                    @Override
                    public void run() {
                        MultiplayerScreen.getInstance().clearTotems();
                        MultiplayerScreen.getInstance().addPlayer(x,y);
                    }
                });
            }


        }
    }
    public void setID(String id){
        this.id=id;
    }
    public void close(){
        this.socket.close();
    }

    class MyWSListener implements WebSocketListener {

        @Override
        public boolean onOpen(WebSocket webSocket) {
            System.out.println("Opening...");
            try {
                ObjectMapper mapper = new ObjectMapper();
                ObjectNode rootNode = mapper.createObjectNode();
                rootNode.put("type", "setPlayer");
                rootNode.put("username", MainGame.username);
                rootNode.put("grade", MainGame.grade);
                String jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(rootNode);
                socket.send(jsonString);
                MainGame.gameLogs.add("INFO", "Player sended to server");
            }catch (Exception e){
                e.printStackTrace();
            }

            return false;
        }

        @Override
        public boolean onClose(WebSocket webSocket, int closeCode, String reason) {
            System.out.println("Closing...");
            return false;
        }

        @Override
        public boolean onMessage(WebSocket webSocket, String packet) {
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                Map<String, Object> reciv = objectMapper.readValue(packet, Map.class);
                Gdx.app.log("Etiqueta", packet);
                if(reciv.get("type").equals("connectionTest")){
                    MainGame.userID=String.valueOf(reciv.get("player"));
                }else if(reciv.get("type").equals("totems")){
                    updateTotems((Map<String, Object>) reciv.get("message"));
                }
                else if(reciv.get("type").equals("stats")){
                    MainGame.error=Integer.parseInt(String.valueOf(reciv.get("errors")));
                    MainGame.success=Integer.parseInt(String.valueOf(reciv.get("hits")));
                }
                else if(reciv.get("type").equals("positions")){
                    updatePlayers((Map<String, Object>) reciv.get("message"));
                }
            }catch (Exception e){
                MainGame.gameLogs.add("ERROR", "Error reading the incomming message");
                e.printStackTrace();
            }

            return false;
        }

        @Override
        public boolean onMessage(WebSocket webSocket, byte[] packet) {
            System.out.println("Message:");
            return false;
        }

        @Override
        public boolean onError(WebSocket webSocket, Throwable error) {
            System.out.println("ERROR:"+error.toString());
            MainGame.gameLogs.add("ERROR", "We cannot create the server connection");
            return false;
        }
    }
}
