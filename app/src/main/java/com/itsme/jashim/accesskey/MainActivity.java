package com.itsme.jashim.accesskey;

import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;
import static com.itsme.jashim.accesskey.R.id.account_list;


public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    ArrayList<String> listitems;
    ArrayAdapter adapter;
    ListView listView;
    String MY_PREFS_NAME = "MyPrefsFile";
    ArrayList<String> search_result_arraylist;
    private String keyword;
    CoordinatorLayout coordinatorLayout;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            listView = (ListView) findViewById(account_list);
            listitems = new ArrayList<String>();
            adapter = new ArrayAdapter<String>(this,
                    R.layout.activity_listview, listitems);
            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(view.getContext(), EditaccActivity.class);
                    intent.putExtra("pos", position);
                    startActivityForResult(intent, 0);
                }
            });

            this.listView.setLongClickable(true);
            this.listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                public boolean onItemLongClick(final AdapterView<?> parent, View v, final int position, long id) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(
                            MainActivity.this);
                    alert.setTitle("Alert!");
                    alert.setMessage("Delete this record?");
                    alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {


                            SharedPreferences sharedPreferences = getSharedPreferences("MyPrefsFile",MODE_PRIVATE);
                            String accusrpd = sharedPreferences.getString("aup", "");

                            String list[] = accusrpd.split(";");
                            List<String> parts = new LinkedList<>(Arrays.asList(list));

                            for(int i=0;i<3;i++)
                                parts.remove(position*3);

                            String strparts = TextUtils.join(";",parts);

                            SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                            editor.putString("aup",strparts);
                            editor.commit();

                            LoadPreferences();

                            Toast.makeText(getApplicationContext(), "Deleted successfully", Toast.LENGTH_SHORT).show();
                        }
                    });
                    alert.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            dialog.dismiss();
                        }
                    });

                    alert.show();

                    return true;
                }
            });
        }

    private void LoadPreferences(){

        listitems.clear();

        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefsFile",MODE_PRIVATE);

        String accusrpd = sharedPreferences.getString("aup","");

        String list[] = accusrpd.split(";");
        List<String > parts= Arrays.asList(list);

        for(int i=0;i<parts.size() && parts.size()>2;i++){
            if(i%3==0){
                listitems.add(parts.get(i));

            }
        }
        adapter = new ArrayAdapter<String>(this,
                R.layout.activity_listview, listitems);
        listView.setAdapter(adapter);
    }

    public void add(View view)
    {
        Intent intent = new Intent(MainActivity.this, Main2Activity.class);
        startActivity(intent);
    }

    @Override
    public void onResume(){
        super.onResume();

        LoadPreferences();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);

        SearchManager searchManager = (SearchManager)
                getSystemService(Context.SEARCH_SERVICE);
        MenuItem searchMenuItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) searchMenuItem.getActionView();

        searchView.setSearchableInfo(searchManager.
                getSearchableInfo(getComponentName()));
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(this);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.rp) {
            Intent intent = new Intent(MainActivity.this, resetpswd.class);
            startActivity(intent);
        }
        if(id == R.id.dr){

            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which){
                        case DialogInterface.BUTTON_POSITIVE:
                            //Do your Yes progress
                            SharedPreferences sharedPreferences = getSharedPreferences("MyPrefsFile",MODE_PRIVATE);
                            sharedPreferences.edit().remove("aup").commit();
                            finish();
                            startActivity(getIntent());
                            break;

                        case DialogInterface.BUTTON_NEGATIVE:
                            //Do your No progress
                            dialog.dismiss();
                            break;
                    }
                }
            };
            AlertDialog.Builder ab = new AlertDialog.Builder(this);
            ab.setTitle("Alert!");ab.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                    .setNegativeButton("No", dialogClickListener).show();
        }
        if(id == R.id.about){
            Intent intent = new Intent(MainActivity.this, AboutActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        adapter.getFilter().filter(newText);
        return true;
    }

    private static final int TIME_INTERVAL = 2000; // # milliseconds, desired time passed between two back presses.
    private long mBackPressed;


    @Override
    public void onBackPressed()
    {
        if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis())
        {
            super.onBackPressed();
            return;
        }
        else { //Toast.makeText(getApplicationContext(), "Tap back again to exit", Toast.LENGTH_SHORT).show();
            coordinatorLayout = (CoordinatorLayout)findViewById(R.id.coordinatorlayout);
            Snackbar.make(coordinatorLayout, "Tap back again to exit", Snackbar.LENGTH_LONG).show(); }

        mBackPressed = System.currentTimeMillis();
    }
}
