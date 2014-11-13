package com.example.myfirstapp;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class MainActivity extends Activity {
	
	public final static String EXTRA_NOMBRE = "com.example.myfirstapp.NOMBRE";
	public final static String EXTRA_EMAIL = "com.example.myfirstapp.EMAIL";
	public final static String EXTRA_PASSWORD = "com.example.myfirstapp.PASSWORD";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
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
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }
    }
    
    public void guardarDatos(View v){
    	Intent intent = new Intent(this, DisplayMessageActivity.class);
	    EditText etNombre = (EditText) findViewById(R.id.etNombre);
	    EditText etEmail = (EditText) findViewById(R.id.etEmail);
	    EditText etPassword = (EditText) findViewById(R.id.etPassword);
	    String nombre = etNombre.getText().toString() , 
	    		email = etEmail.getText().toString() ,
	    		password = etPassword.getText().toString();
	    if(!(nombre.trim().equals("") || email.trim().equals("") || password.trim().equals(""))){
	    	Log.d("Se enviaran datos de main", "nombre+email+password");
		    intent.putExtra(EXTRA_NOMBRE, nombre);
		    intent.putExtra(EXTRA_EMAIL, email);
		    intent.putExtra(EXTRA_PASSWORD, password);
		    startActivity(intent);
	    }
    }
    
    public void yaRegistrado(View v){
    	Intent intent = new Intent(this, IngresarEmailActivity.class);
    	startActivity(intent);
    }
    
    
}
