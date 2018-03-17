package com.itsme.jashim.accesskey;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

public class login_normal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_normal);
        setTitle("Login");
    }

    public void Gonorm(View view){

        TextInputEditText pswd= (TextInputEditText) findViewById(R.id.passnorm);

        String pd=pswd.getText().toString();

        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefsFile",MODE_PRIVATE);

        String storedpass = sharedPreferences.getString("logpass", "");

        if(pd.trim().equals(""))
            pswd.setError("This field is Required");

        else if(pd.equals(storedpass)){
            Intent intent = new Intent(login_normal.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        else if(!pd.equals(storedpass))
            Toast.makeText(getApplicationContext(), "Sorry, Wrong password!", Toast.LENGTH_SHORT).show();

    }

}
