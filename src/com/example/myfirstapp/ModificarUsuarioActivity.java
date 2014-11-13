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
import android.widget.EditText;
import android.widget.TextView;

import com.example.domain.Usuario;
import com.example.utils.JsonAObjeto;
import com.example.utils.ObtenerConexion;

public class ModificarUsuarioActivity extends Activity {
	
	public final static String EXTRA_RESPUESTA = "com.example.myfirstapp.modificarusuarioactivity.RESPUESTA";
	private String nombre, email, password;
	private Usuario usuario;
	TextView outputText;
	Intent intent;
	EditText etNombre, etEmail, etPassword;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_modificar_usuario);
		
		Intent intent = getIntent();
	    
		try{
	    	this.nombre = intent.getStringExtra(IngresarEmailActivity.EXTRA_NOMBRE);
	    	this.email = intent.getStringExtra(IngresarEmailActivity.EXTRA_EMAIL);
	    	this.password = intent.getStringExtra(IngresarEmailActivity.EXTRA_PASSWORD);
	    	
	    	this.etNombre = (EditText) findViewById(R.id.etNombre_3);
	    	this.etEmail = (EditText) findViewById(R.id.etEmail_3);
	    	this.etPassword = (EditText) findViewById(R.id.etPassword_3);
	    	
	    	etNombre.setText(nombre);
	    	etEmail.setText(email);
	    	etPassword.setText(password);
	    	
	    }catch (Exception e){
	    	Log.d("Error", "Error al crear usuario");
	    	e.printStackTrace();
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
			View rootView = inflater.inflate(
					R.layout.fragment_modificar_usuario, container, false);
			return rootView;
		}
	}
	
	public boolean iniciar(View v){
		
		intent = new Intent(this, UsuarioModificadoActivity.class);
    	
	    EditText etNombre = (EditText) findViewById(R.id.etNombre_3),
	    		 etEmail = (EditText) findViewById(R.id.etEmail_3),
	    		 etPassword = (EditText) findViewById(R.id.etPassword_3);
	    String nombre = etNombre.getText().toString(),
	    		email = etEmail.getText().toString(),
	    		password = etPassword.getText().toString();
	    
	    outputText = (TextView) findViewById(R.id.mensajeServidor_2);
	    
	    if(!(nombre.trim().equals("") || email.trim().equals("") || password.trim().equals(""))){
	    	Usuario usuario = new Usuario();
	    	usuario.setNombre(nombre);
	    	usuario.setEmail(email);
	    	usuario.setPassword(password);
	    	
	    	this.usuario = usuario;
	    	
	    	return true;
	    	
	    }else{
	    	return false;
	    }
		
	}
	
	public void modificarDatos(View v){
		if (iniciar(v)){
			
			outputText.setText("Modificando al usuario ....");
		    new PostTask().execute("http://10.0.2.2:8080/Servidor/ModificarUsuarioServlet");
		    
		}else {
			outputText.setText("Hay errores en el formulario");
		}
	}
	
	public void borrarDatos(View v){
		if (iniciar(v)){
			
			outputText.setText("Borrando al usuario ....");
		    new PostTask().execute("http://10.0.2.2:8080/Servidor/EliminarUsuarioServlet");
		    
		}else {
			outputText.setText("Hay errores en el formulario");
		}
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
	            
	            Log.d("ModificarUsuarioActivity doInBackground", "Fin");
	            
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
				
				intent.putExtra(EXTRA_RESPUESTA, output);
			    
			    startActivity(intent);
			    
			}catch(Exception e){
				e.printStackTrace();
				outputText.setText("Se presento un problema, intente de nuevo.");
			}
			
		}
	}
}
