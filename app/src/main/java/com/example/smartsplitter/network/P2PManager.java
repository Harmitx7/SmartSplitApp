package com.example.smartsplitter.network;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class P2PManager {
    private static final String TAG = "P2PManager";
    private static final int DISCOVERY_PORT = 8888;
    private static final int DATA_PORT = 8889;
    private static final String DISCOVERY_MSG = "SMART_SPLIT_DISCOVER";

    private Context context;
    private ExecutorService executor = Executors.newCachedThreadPool();
    private boolean isRunning = false;
    private Set<String> discoveredIps = java.util.Collections.synchronizedSet(new HashSet<>());
    private Gson gson = new Gson();
    private OnDataReceivedListener dataListener;

    public interface OnDataReceivedListener {
        void onDataReceived(String type, String jsonPayload);
    }

    public P2PManager(Context context, OnDataReceivedListener listener) {
        this.context = context;
        this.dataListener = listener;
    }

    public void start() {
        isRunning = true;
        startDiscoveryServer();
        startDataServer();
        startDiscoveryBroadcaster();
    }

    public void stop() {
        isRunning = false;
        // Cleanup sockets if needed
    }

    // 1. UDP Discovery Server (Listen for "I am here")
    private void startDiscoveryServer() {
        executor.execute(() -> {
            try (DatagramSocket socket = new DatagramSocket(DISCOVERY_PORT)) {
                socket.setBroadcast(true);
                byte[] buf = new byte[1024];
                while (isRunning) {
                    DatagramPacket packet = new DatagramPacket(buf, buf.length);
                    socket.receive(packet);
                    String msg = new String(packet.getData(), 0, packet.getLength());
                    if (msg.equals(DISCOVERY_MSG)) {
                        String ip = packet.getAddress().getHostAddress();
                        if (!discoveredIps.contains(ip) && !isLocalIp(ip)) {
                            Log.d(TAG, "Discovered Peer: " + ip);
                            discoveredIps.add(ip);
                        }
                    }
                }
            } catch (Exception e) {
                Log.e(TAG, "Discovery Server Error", e);
            }
        });
    }

    // 2. UDP Broadcaster (Say "I am here")
    private void startDiscoveryBroadcaster() {
        executor.execute(() -> {
            try (DatagramSocket socket = new DatagramSocket()) {
                socket.setBroadcast(true);
                byte[] buf = DISCOVERY_MSG.getBytes();
                while (isRunning) {
                    try {
                        InetAddress broadcastAddr = InetAddress.getByName("255.255.255.255");
                        DatagramPacket packet = new DatagramPacket(buf, buf.length, broadcastAddr, DISCOVERY_PORT);
                        socket.send(packet);
                        Thread.sleep(5000); // Broadcast every 5 seconds
                    } catch (Exception e) {
                        Log.e(TAG, "Broadcast Error", e);
                    }
                }
            } catch (Exception e) {
                Log.e(TAG, "Broadcaster setup failed", e);
            }
        });
    }

    // 3. TCP Data Server (Listen for Sync Data)
    private void startDataServer() {
        executor.execute(() -> {
            try (ServerSocket serverSocket = new ServerSocket(DATA_PORT)) {
                while (isRunning) {
                    Socket client = serverSocket.accept();
                    handleIncomingData(client);
                }
            } catch (IOException e) {
                Log.e(TAG, "Data Server Error", e);
            }
        });
    }

    private void handleIncomingData(Socket client) {
        executor.execute(() -> {
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                String line = in.readLine();
                if (line != null) {
                    SyncPacket packet = gson.fromJson(line, SyncPacket.class);
                    if (dataListener != null && packet != null) {
                        new Handler(Looper.getMainLooper())
                                .post(() -> dataListener.onDataReceived(packet.type, packet.payload));
                    }
                }
                client.close();
            } catch (Exception e) {
                Log.e(TAG, "Handling Data Error", e);
            }
        });
    }

    // 4. Send Data to All Peers
    public void broadcastData(String type, Object entity) {
        String payload = gson.toJson(entity);
        SyncPacket packet = new SyncPacket(type, payload);
        String json = gson.toJson(packet);

        synchronized (discoveredIps) {
            for (String ip : discoveredIps) {
                sendToIp(ip, json);
            }
        }
    }

    private void sendToIp(String ip, String json) {
        executor.execute(() -> {
            try (Socket socket = new Socket(ip, DATA_PORT)) {
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                out.println(json);
            } catch (Exception e) {
                Log.e(TAG, "Send Error to " + ip, e);
                // If fail, remove IP? Maybe.
            }
        });
    }

    private boolean isLocalIp(String ip) {
        // Simple check if IP equals local host (can be improved)
        return false;
    }

    public static class SyncPacket {
        public String type; // "GROUP", "MEMBER", "EXPENSE"
        public String payload;

        public SyncPacket(String type, String payload) {
            this.type = type;
            this.payload = payload;
        }
    }
}
