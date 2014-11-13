package com.example.utils;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.domain.Usuario;

public class JsonAObjeto {
	
	public static Usuario jsonAUsuario(String jsonStr) throws JSONException{
		
		JSONObject json = new JSONObject(jsonStr);
		
		Usuario usuario = new Usuario();
		
		usuario.setNombre(json.getString("nombre"));
		usuario.setPassword(json.getString("password"));
		usuario.setEmail(json.getString("email"));
		
		return usuario;
	}
	
	public static String usuarioAJson (Usuario usuario) throws JSONException{
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("nombre", usuario.getNombre());
		jsonObj.put("password", usuario.getPassword());
		jsonObj.put("email", usuario.getEmail());
		
		return jsonObj.toString();
	}

}
