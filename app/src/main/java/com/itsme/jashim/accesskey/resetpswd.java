package com.itsme.jashim.accesskey;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import static com.itsme.jashim.accesskey.Main2Activity.MY_PREFS_NAME;

public class resetpswd extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resetpswd);

    }

    public void Goreset(View view){
        TextInputEditText cpswd= (TextInputEditText) findViewById(R.id.cpass);
        TextInputEditText pswd= (TextInputEditText) findViewById(R.id.pass);
        TextInputEditText repswd= (TextInputEditText) findViewById(R.id.repass);

        String cpd=cpswd.getText().toString();
        String pd=pswd.getText().toString();
        String repd=repswd.getText().toString();

        if (cpd.trim().equals(""))
            cpswd.setError("This field is Required");
        else if(pd.trim().equals(""))
            pswd.setError("This field is Required");
        else if(repd.trim().equals(""))
            repswd.setError("This field is required");
        else if(!pd.equals(repd))
            Toast.makeText(getApplicationContext(), "Passwords are NOT matching", Toast.LENGTH_SHORT).show();

        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefsFile",MODE_PRIVATE);

        String storedpass = sharedPreferences.getString("logpass", "");

        if(!cpd.equals(storedpass)) {
            Toast.makeText(getApplicationContext(), "Sorry, wrong Password!", Toast.LENGTH_SHORT).show();
            cpswd.setError("Wrong current password");
        }
        if(cpd.equals(storedpass) && pd.equals(repd)){
            SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
            editor.putString("logpass",pd);
            editor.putBoolean("registered", true);
            editor.commit();
            Toast.makeText(getApplicationContext(), "Your password has been reset successfully!", Toast.LENGTH_SHORT).show();
            finish();
        }

    }

}
