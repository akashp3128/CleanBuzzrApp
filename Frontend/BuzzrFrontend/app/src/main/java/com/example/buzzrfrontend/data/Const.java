package com.example.buzzrfrontend.data;

public class Const {
    // IMPORTANT: Uncomment the URL that works for your setup
    
    // For Android Emulator - points to localhost of the computer hosting the emulator
    public static final String URL = "http://10.0.2.2:8081"; 
    
    // For Android 9+ physical devices - use your computer's actual IP address on your network
    // public static final String URL = "http://192.168.1.100:8081"; // Replace with your actual IP
    
    // For testing without a server - use this mock endpoint (doesn't actually connect anywhere)
    // public static final String URL = "http://localhost:8081";
    
    // API endpoints
    public static final String listPersonsEndpoint = "/persons";
    
    // Login debugging
    public static final boolean ENABLE_DEBUG_USER = true; // Set to false in production
    
    // EMERGENCY LOGIN BYPASS - SET THIS TO TRUE FOR GUARANTEED LOGIN
    public static final boolean FORCE_LOGIN_SUCCESS = true;
    
    // LOCAL MODE - if true, completely bypasses server communication
    public static final boolean LOCAL_MODE = true;
    
    // Emergency login credentials - these will always work when FORCE_LOGIN_SUCCESS is true
    public static final String EMERGENCY_USERNAME = "admin";
    public static final String EMERGENCY_PASSWORD = "admin123";
}