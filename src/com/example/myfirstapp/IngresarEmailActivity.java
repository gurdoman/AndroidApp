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

public class IngresarEmailActivity extends Activity {
	
	public final static String EXTRA_NOMBRE = "com.example.myfirstapp.ingresaremailactivity.NOMBRE";
	public final static String EXTRA_EMAIL = "com.example.myfirstapp.ingresaremailactivity.EMAIL";
	public final static String EXTRA_PASSWORD = "com.example.myfirstapp.ingresaremailactivity.PASSWORD";
	
	Usuario usuario;
	TextView outputText;
	Intent intent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ingresar_email);
		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
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
			View rootView = inflater.inflate(R.layout.fragment_ingresar_email,
					container, false);
			return rootView;
		}
	}
	
	public void enviarDatos(View v){
		
    	intent = new Intent(this, ModificarUsuarioActivity.class);
    	
	    EditText etEmail = (EditText) findViewById(R.id.etEmail_2),
	    		 etPassword = (EditText) findViewById(R.id.etPassword_2);
	    
	    Usuario usuario = new Usuario();
	    usuario.setEmail(etEmail.getText().toString());
	    usuario.setPassword(etPassword.getText().toString());
	    this.usuario = usuario;
	    
	    outputText = (TextView) findViewById(R.id.mensajeServidor);
	    
	    outputText.setText("Buscando al usuario ....");
	    new PostTask().execute("http://10.0.2.2:8080/Servidor/BuscarUsuarioServlet");
 
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
				
				Usuario u = JsonAObjeto.jsonAUsuario(output);
				
				intent.putExtra(EXTRA_NOMBRE, u.getNombre());
			    intent.putExtra(EXTRA_EMAIL, u.getEmail());
			    intent.putExtra(EXTRA_PASSWORD, u.getPassword());
			    
			    startActivity(intent);
				
				
			}catch(Exception e){
				e.printStackTrace();
				outputText.setText("El email o password es incorrecto");
			}
			
		}
	}
}
