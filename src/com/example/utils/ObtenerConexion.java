package com.example.utils;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

public class ObtenerConexion {
	
	public static URLConnection getHttpConnection(String urlString) throws IOException {
	    URL url = new URL(urlString);
	    URLConnection connection = url.openConnection();
	
	    try {
	        connection.setDoOutput(true);
	    } catch (Exception ex) {
	        ex.printStackTrace();
	        return null;
	    }
	    return connection;
	}
}
