package com.itsme.jashim.accesskey;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import static com.itsme.jashim.accesskey.Main2Activity.MY_PREFS_NAME;

public class loginfirst extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginfirst);
        setTitle("Login");

        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefsFile",MODE_PRIVATE);

        boolean reg = sharedPreferences.getBoolean("registered", false);

        if(reg){
            Intent intent = new Intent(loginfirst.this, login_normal.class);
            startActivity(intent);
            finish();
        }
    }

    public void Go(View view){

        TextInputEditText pswd= (TextInputEditText) findViewById(R.id.pass);
        TextInputEditText repswd= (TextInputEditText) findViewById(R.id.repass);

        String pd=pswd.getText().toString();
        String repd=repswd.getText().toString();

        if(pd.trim().equals(""))
            pswd.setError("This field is Required");
        else if(repd.trim().equals(""))
            repswd.setError("This field is required");
        else if(!pd.equals(repd))
            Toast.makeText(getApplicationContext(), "Passwords are NOT matching", Toast.LENGTH_SHORT).show();
        else if(pd.equals(repd) ){
            SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
            editor.putString("logpass",pd);
            editor.putBoolean("registered", true);
            editor.commit();

            Intent intent = new Intent(loginfirst.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

}
