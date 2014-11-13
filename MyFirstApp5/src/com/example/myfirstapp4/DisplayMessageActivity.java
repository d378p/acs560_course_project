package com.example.myfirstapp4;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.support.v7.app.ActionBarActivity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcel;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class DisplayMessageActivity extends ActionBarActivity {
	
	//field
	String message = null;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_message);
//		
//		 // Get the message from the intent
//	    Intent intent = getIntent();
//	    String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
//
//	    // Create the text view
//	    TextView textView = new TextView(this);
//	    textView.setTextSize(40);
//	    textView.setText(message);
//
//	    // Set the text view as the activity layout
//	    setContentView(textView);
		
		 Context myApp =  getApplicationContext();

//       if (savedInstanceState == null) {
//           getSupportFragmentManager().beginTransaction()
//               .add(R.id.container, new PlaceholderFragment()).commit();
//       }
       
		 /////////////////////////////////////
		 // Get the message from the intent
		 ////////////////////////////////////
		 
       Intent intent = getIntent();
       String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
       this.message = message;
       // Create the text view
       TextView textView = new TextView(this);
       textView.setTextSize(40);
       //sets the message string to text intered into the view
       textView.setText(message);       
       

       // Set the text view as the activity layout
       setContentView(textView); 
       
       /////////////////////////////////////////////       
       // Write and read file with content
       //////////////////////////////////////////////
       
       
       //create file
       //File file = new File(myApp.getFilesDir(), filename);
       
       String fileName = "myfile";
       //String string = "Hello world!";
       FileOutputStream outputStream;

       //write file
       try {
         outputStream = openFileOutput(fileName, Context.MODE_PRIVATE);
         outputStream.write(message.getBytes());//was string
         outputStream.close();
       } catch (Exception e) {
         e.printStackTrace();
       }//end try catch 
       
       //read file
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
       //System.out.println("fileContents = "+datax.toString());
       
       textView.setText("fileContents = "+datax.toString());

       // Set the text view as the activity layout
       setContentView(textView); 
      
       
     
       /////////////////////////////////////////////       
       // DB create table and store content
       //////////////////////////////////////////////
       
       //web example
       //http://www.techrepublic.com/blog/software-engineer/browse-sqlite-data-on-the-android-emulator/
     
     final String SAMPLE_DB_NAME = "TrekBook";
   	 final String SAMPLE_TABLE_NAME = "Info";
   	
      SQLiteDatabase sampleDB =  this.openOrCreateDatabase(SAMPLE_DB_NAME, MODE_PRIVATE, null);
       
      //remove existing table
      //sampleDB.execSQL("DROP TABLE IF EXISTS "+SAMPLE_TABLE_NAME+" ;", null);
       
		sampleDB.execSQL("CREATE TABLE IF NOT EXISTS " +
               SAMPLE_TABLE_NAME +
               " (LastName VARCHAR, FirstName VARCHAR," +
               " Rank VARCHAR);");
       sampleDB.execSQL("INSERT INTO " +
               SAMPLE_TABLE_NAME +
               " Values ('a','b', '"+message+" ');");
       
       sampleDB.close();
       
       Toast.makeText(this, "DB Created!", Toast.LENGTH_LONG).show();         
       
       
       /////////////////////////////////////
       // Open DB and read stored content
       /////////////////////////////////////
       
       SQLiteDatabase sampleDB2 =  this.openOrCreateDatabase(SAMPLE_DB_NAME, MODE_PRIVATE, null);     
     
     Cursor result=sampleDB2.rawQuery("SELECT LastName, FirstName, Rank FROM "+SAMPLE_TABLE_NAME+" ;",null);
     
       
     // Iterate over cursor to build string
     String LastNameDB = null;
     String FirstNameDB = null;
     String RankDB = null;
     String total = "";
     result.moveToFirst();
    while(!result.isAfterLast()){
   	  LastNameDB = result.getString(0);
   	  FirstNameDB = result.getString(1);
   	  RankDB = result.getString(2);
     	total = total + LastNameDB+" "+FirstNameDB+" "+RankDB+"\n";
     	
     	result.moveToNext();
    }
    
    //Toast.makeText(this, "DB Created!"+" "+total, Toast.LENGTH_LONG).show();    
     
     result.close();
     
     sampleDB2.close();
     
     textView.setText("DB values = \n"+total);

     // Set the text view as the activity layout
     setContentView(textView); 
     
     
     /////////////////////////////////////////////////////////////
     // Read server file with web browser
     /////////////////////////////////////////////////////////////
     
//   //works fine
//   //for local host on android the path is "http://10.0.2.2/hello.php"
//   //Uri theUri = Uri.parse("10.0.2.2/hello.php");//does not work
//   Uri theUri = Uri.parse("http://10.0.2.2/hello.php");//works
//   Intent LaunchBrowserIntent = new Intent(Intent.ACTION_VIEW,theUri);
//   startActivity(LaunchBrowserIntent);
     
     
     //---------------------------------------------------------------------------
     
     /////////////////////////////////////////////////////////////
     // Use HTTP Post to send content to server and store content
     // into mySQL using Post2.php script
     /////////////////////////////////////////////////////////////
     
     //new HttpGetDemo().execute(textView); 
     
     new HttpPostDemo().execute(textView);  
     
     

		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.display_message, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	
	/////////////////////////////////////////////////////////////
	// Http class
	/////////////////////////////////////////////////////////////
	
	public class HttpPostDemo extends AsyncTask<TextView, Void, String> 
	{
		TextView textView;
		
		@Override
		protected String doInBackground(TextView... params) 	
		{
			this.textView = params[0];
			BufferedReader inBuffer = null;
			String url = "http://10.0.2.2/post2.php";
			String result = "fail";
			try {
				HttpClient httpClient = new DefaultHttpClient();
				HttpPost request = new HttpPost(url);
				List<NameValuePair> postParameters = 
					new ArrayList<NameValuePair>();
				postParameters.add(new BasicNameValuePair("name", message));
				//postParameters.add(new BasicNameValuePair("operandb", "6"));
				//postParameters.add(new BasicNameValuePair("answer", "11"));
				UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(
						postParameters);

				request.setEntity(formEntity);
				HttpResponse httpResponse = httpClient.execute(request);
				inBuffer = new BufferedReader(
					new InputStreamReader(
						httpResponse.getEntity().getContent()));

				StringBuffer stringBuffer = new StringBuffer("");
				String line = "";
				String newLine = System.getProperty("line.separator");
				while ((line = inBuffer.readLine()) != null) {
					stringBuffer.append(line + newLine);
				}
				inBuffer.close();

				result = stringBuffer.toString();
				
			} catch(Exception e) {
				// Do something about exceptions
				result = e.getMessage();
			} finally {
				if (inBuffer != null) {
					try {
						inBuffer.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			return result;
		}
		
		protected void onPostExecute(String page)
		{    	
	    	textView.setText(page);      	
		}
		
	}
		
}
