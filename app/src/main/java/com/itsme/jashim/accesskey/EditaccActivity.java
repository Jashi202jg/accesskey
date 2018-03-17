package com.itsme.jashim.accesskey;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class EditaccActivity extends AppCompatActivity {

    List<String> listitems;
    String MY_PREFS_NAME = "MyPrefsFile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editacc);

        Intent intent = getIntent();
        final int position = intent.getIntExtra("pos", 0);

        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefsFile", MODE_PRIVATE);

        String accusrpd = sharedPreferences.getString("aup", "");

        String list[] = accusrpd.split(";");
        final List<String> parts = Arrays.asList(list);


        final AutoCompleteTextView act = (AutoCompleteTextView) findViewById(R.id.eacc_box);
        final EditText edt = (EditText) findViewById(R.id.eusr_box);
        final TextInputEditText til = (TextInputEditText) findViewById(R.id.epswd_box);

        act.setText(parts.get(position * 3));
        edt.setText(parts.get((position * 3) + 1));
        til.setText(parts.get((position * 3) + 2));
    }

       public void Esave(View view) {

            Intent intent = getIntent();
            int position = intent.getIntExtra("pos", 0);

          // Toast.makeText(getApplicationContext(),position+"", Toast.LENGTH_SHORT).show();
           final AutoCompleteTextView act = (AutoCompleteTextView) findViewById(R.id.eacc_box);
           final EditText edt = (EditText) findViewById(R.id.eusr_box);
           final TextInputEditText til = (TextInputEditText) findViewById(R.id.epswd_box);

           String accountname = act.getText().toString();
           String username = edt.getText().toString();
           String pd=til.getText().toString();

                if(accountname.trim().equals("")){
                    Toast.makeText(getApplicationContext(), "Account name field cannot be left empty", Toast.LENGTH_SHORT).show();
                    act.setError("This field is required");
                }
                else if(pd.trim().equals("")){
                    Toast.makeText(getApplicationContext(),"You must enter a password in the password field", Toast.LENGTH_LONG).show();
                }
                else {

                    SharedPreferences sharedPreferences = getSharedPreferences("MyPrefsFile",MODE_PRIVATE);

                    String accusrpd = sharedPreferences.getString("aup", "");

                    String list[] = accusrpd.split(";");
                    List<String > parts= Arrays.asList(list);

                    parts.set(position * 3,accountname);
                    parts.set((position*3)+1,username);
                    parts.set((position*3)+2,pd);

                    String strparts = TextUtils.join(";",parts);

                    SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                    editor.putString("aup",strparts);
                    editor.commit();
                    finish();
                    Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_SHORT).show();

                }
        }

        public void cubtn(View view){

            EditText edt = (EditText) findViewById(R.id.eusr_box);
            String username = edt.getText().toString();

            android.content.ClipboardManager clipboard = (android.content.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            android.content.ClipData clip = android.content.ClipData.newPlainText("Username", username);
            clipboard.setPrimaryClip(clip);
            Toast.makeText(getApplicationContext(), "Copied to Clipboard!", Toast.LENGTH_SHORT).show();
        }

        public void cpbtn(View view){

            TextInputEditText til = (TextInputEditText) findViewById(R.id.epswd_box);
            String password = til.getText().toString();

            android.content.ClipboardManager clipboard = (android.content.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            android.content.ClipData clip = android.content.ClipData.newPlainText("Password", password);
            clipboard.setPrimaryClip(clip);
            Toast.makeText(getApplicationContext(), "Copied to Clipboard!", Toast.LENGTH_SHORT).show();
        }
}
