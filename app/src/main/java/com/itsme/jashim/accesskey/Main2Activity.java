package com.itsme.jashim.accesskey;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import static android.content.Context.MODE_PRIVATE;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        AutoCompleteTextView textView = (AutoCompleteTextView) findViewById(R.id.acc_box);
        String[] accounts = getResources().getStringArray(R.array.account_array);
        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, accounts);
        textView.setAdapter(adapter);
    }

    public static final String MY_PREFS_NAME = "MyPrefsFile";

    public void save(View view)
    {

        AutoCompleteTextView acc= (AutoCompleteTextView) findViewById(R.id.acc_box);
        EditText usr= (EditText) findViewById(R.id.usr_box);
        TextInputEditText pswd= (TextInputEditText) findViewById(R.id.pswd_box);

        String accountname = acc.getText().toString();
        String username = usr.getText().toString();
        String pd=pswd.getText().toString();

        if(accountname.trim().equals("")){
            Toast.makeText(getApplicationContext(), "Account name field cannot be left empty", Toast.LENGTH_SHORT).show();
            acc.setError("This field is required");
        }
        else if(pd.trim().equals("")){
            Toast.makeText(getApplicationContext(),"You must enter a password in the password field", Toast.LENGTH_SHORT).show();
        }
        else {
            SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
            SharedPreferences sharedPreferences = getSharedPreferences("MyPrefsFile",MODE_PRIVATE);
            String previous = sharedPreferences.getString("aup", "");
            editor.putString("aup", accountname+";"+username+";"+pd+";"+previous);
            editor.commit();

            finish();
            Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_SHORT).show();
        }
    }

}
