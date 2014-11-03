package com.example.myfirstapp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import android.support.v7.app.ActionBarActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class DisplayMessageActivity extends ActionBarActivity {

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);
       Context myApp =  getApplicationContext();

//        if (savedInstanceState == null) {
//            getSupportFragmentManager().beginTransaction()
//                .add(R.id.container, new PlaceholderFragment()).commit();
//        }
        
     // Get the message from the intent
        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        // Create the text view
        TextView textView = new TextView(this);
        textView.setTextSize(40);
        textView.setText(message);

        // Set the text view as the activity layout
        setContentView(textView); 
        
        //create file
        //File file = new File(myApp.getFilesDir(), filename);
        
        String fileName = "myfile";
        String string = "Hello world!";
        FileOutputStream outputStream;

        try {
          outputStream = openFileOutput(fileName, Context.MODE_PRIVATE);
          outputStream.write(string.getBytes());
          outputStream.close();
        } catch (Exception e) {
          e.printStackTrace();
        }//end try catch 
        
        //try and read file
        StringBuffer datax = new StringBuffer("");
        try {
            FileInputStream fIn = openFileInput ( fileName ) ;
            InputStreamReader isr = new InputStreamReader ( fIn ) ;
            BufferedReader buffreader = new BufferedReader ( isr ) ;

            String readString = buffreader.readLine ( ) ;
            while ( readString != null ) {
                datax.append(readString);
                readString = buffreader.readLine ( ) ;
            }

            isr.close ( ) ;
        } catch ( IOException ioe ) {
            ioe.printStackTrace ( ) ;
        }
        System.out.println("fileContents = "+datax.toString());
        
        textView.setText("fileContents = "+datax.toString());

        // Set the text view as the activity layout
        setContentView(textView); 
        
        
        
        
    }//end onCreate method

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }//end if
        return super.onOptionsItemSelected(item);
    }//end onOptionsItemSelected

    /**
     * A placeholder fragment containing a simple view.
     */
//    public static class PlaceholderFragment extends Fragment {
//
//        public PlaceholderFragment() { }
//
//        @Override
//        public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                  Bundle savedInstanceState) {
//              View rootView = inflater.inflate(R.layout.fragment_display_message,
//                      container, false);
//              return rootView;
//        }
//    }

}//end DisplayMessageActivity class
