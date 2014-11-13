package com.example.myfirstapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URLConnection;

import org.json.JSONException;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.domain.Usuario;
import com.example.utils.JsonAObjeto;
import com.example.utils.ObtenerConexion;

public class DisplayMessageActivity extends Activity {

	Usuario usuario;
	TextView outputText;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
	    Intent intent = getIntent();
	    try{
	    	String nombre = intent.getStringExtra(MainActivity.EXTRA_NOMBRE),
	    			email = intent.getStringExtra(MainActivity.EXTRA_EMAIL),
	    			password = intent.getStringExtra(MainActivity.EXTRA_PASSWORD);
	    	Usuario usuario = new Usuario();
	    	usuario.setNombre(nombre);
		    usuario.setEmail(email);
		    usuario.setPassword(password);
		    this.usuario = usuario;
	    }catch (Exception e){
	    	Log.d("Error", "Error al crear usuario");
	    	e.printStackTrace();
	    }
	    
	    setContentView(R.layout.activity_display_message);
	    outputText = (TextView) findViewById(R.id.outputTxt);
	    new PostTask().execute("http://10.0.2.2:8080/Servidor/CrearUsuarioServlet");

	}
	
	private class PostTask extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... params) {
			String url=params[0];
			StringBuffer output = new StringBuffer("");
			
			try{
				String json = JsonAObjeto.usuarioAJson(usuario);
				URLConnection connection = ObtenerConexion.getHttpConnection(url);
				OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
                out.write(json);
                out.close();
	            BufferedReader buffer = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	            String s = "";
	            while ((s = buffer.readLine()) != null)
	                output.append(s);
	            
	            Log.d("inputString", json);
	            
	            Log.d("doInBackground", "Fin");
	            
			} catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
				e.printStackTrace();
			}
            return output.toString();
		}
		
		@Override
		protected void onPostExecute(String output){
			try{
				Log.d("onPostExecute", output);
				outputText.setText(output);
			}catch(Exception e){
				e.printStackTrace();
			}
			
		}
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_display_message,
					container, false);
			return rootView;
		}
	}
}
