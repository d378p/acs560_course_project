package com.example.myfirstapp4;

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
import java.net.URL;
import java.net.URLConnection;

import android.support.v7.app.ActionBarActivity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class DisplayMessageActivity extends ActionBarActivity {

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
       
    // Get the message from the intent
       Intent intent = getIntent();
       String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

       // Create the text view
       TextView textView = new TextView(this);
       textView.setTextSize(40);
       //sets the message string to text intered into the view
       textView.setText(message);

       // Set the text view as the activity layout
       setContentView(textView); 
       
       //create file
       //File file = new File(myApp.getFilesDir(), filename);
       
       String fileName = "myfile";
       //String string = "Hello world!";
       FileOutputStream outputStream;

       try {
         outputStream = openFileOutput(fileName, Context.MODE_PRIVATE);
         outputStream.write(message.getBytes());//was string
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
       //System.out.println("fileContents = "+datax.toString());
       
       textView.setText("fileContents = "+datax.toString());

       // Set the text view as the activity layout
       setContentView(textView); 
       
       /////////////////////////////////////////////       

       
       //web example
       //http://www.techrepublic.com/blog/software-engineer/browse-sqlite-data-on-the-android-emulator/
       final String SAMPLE_DB_NAME = "TrekBook";
   	final String SAMPLE_TABLE_NAME = "Info";
       SQLiteDatabase sampleDB =  this.openOrCreateDatabase(SAMPLE_DB_NAME, MODE_PRIVATE, null);
		sampleDB.execSQL("CREATE TABLE IF NOT EXISTS " +
               SAMPLE_TABLE_NAME +
               " (LastName VARCHAR, FirstName VARCHAR," +
               " Rank VARCHAR);");
       sampleDB.execSQL("INSERT INTO " +
               SAMPLE_TABLE_NAME +
               " Values ('a','b', '"+message+" ');");
       sampleDB.close();
       Toast.makeText(this, "DB Created!", Toast.LENGTH_LONG).show();  
       
       
       
       
       /////////////////////
       // get data
       SQLiteDatabase sampleDB2 =  this.openOrCreateDatabase(SAMPLE_DB_NAME, MODE_PRIVATE, null);
       
     
     //remove existing table
     //sampleDB2.rawQuery("DROP TABLE IF EXISTS "+SAMPLE_TABLE_NAME+" ;", null);
     Cursor result=sampleDB2.rawQuery("SELECT LastName, FirstName, Rank FROM "+SAMPLE_TABLE_NAME+" ;",null);
     
     /*
      * try to turn this off its is a state issue
      */
     //sampleDB2.close();
     
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
     
     textView.setText("DB values = \n"+total);

     // Set the text view as the activity layout
     setContentView(textView); 
     
     
     /////////////////////////////////////////////////////////////
     
//   //works fine
//   //for local host on android the path is "http://10.0.2.2/hello.php"
//   //Uri theUri = Uri.parse("10.0.2.2/hello.php");//does not work
//   Uri theUri = Uri.parse("http://10.0.2.2/hello.php");//works
//   Intent LaunchBrowserIntent = new Intent(Intent.ACTION_VIEW,theUri);
//   startActivity(LaunchBrowserIntent);
   
   
     
 //---------------------------------------------------------------------------
       /*
        * does not work.....crap!!!
        */
       String inputLine ="";
       
       try {
		//URL u = new URL("www.google.com");//works does not like the http
		URL u = new URL("http://engr.ipfw.edu/~zchen");//works does not like the http
    	//URL u = new URL("http://10.0.2.2/hello.php");//does not work
    	//URL u = new URL("10.0.2.2/hello.php");//works does not like the  http
		URLConnection connection = u.openConnection();		
		InputStream in = connection.getInputStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		//after call to getInputStream
		HttpURLConnection httpConnection = (HttpURLConnection)connection;
		int code = httpConnection.getResponseCode();
		boolean done = false;
		while(!done){
			String input = reader.readLine();			
			if (input==null){
				done=true;
			}else{
				//inputLine = inputLine+input+"\n";
				
			}
			
			textView.setText("Code values = \n"+code);
			
		}
		
	} catch (MalformedURLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
       
       
    
       
//---------------------------------------------------------------- 

//       
//       URL url;
//	try {
//		url = new URL("http://10.0.2.2/hello.php");
//	
//       HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
//       httpCon.setDoOutput(true);
//       httpCon.setRequestMethod("PUT");
//       OutputStreamWriter out = new OutputStreamWriter(
//           httpCon.getOutputStream());
//       out.write("Resource content");
//       out.close();
//       httpCon.getInputStream();
//       
//	} catch (MalformedURLException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	} catch (ProtocolException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	} catch (IOException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	}
       
       
       
//       String url = "http://10.0.2.2/hello.php";
//       
//       String USER_AGENT = "Mozilla/5.0";
//       
//       try {
//       
//		URL obj;
//		
//		obj = new URL(url);
//		
//		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
//
//		// optional default is GET
//		con.setRequestMethod("GET");
//
//		//add request header
//		con.setRequestProperty("User-Agent", USER_AGENT);
//
////		int responseCode = con.getResponseCode();
////		System.out.println("\nSending 'GET' request to URL : " + url);
////		System.out.println("Response Code : " + responseCode);
//
//		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
//		
////		String inputLine;
////		StringBuffer response = new StringBuffer();
////
////		while ((inputLine = in.readLine()) != null) {
////			response.append(inputLine);
////		}
////		in.close();
////
////		//print result
////		System.out.println(response.toString());
////       
//		} catch (MalformedURLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//       
       
       
//       HttpURLConnection con = null;
//       URL url;
//       
//	try {
//		
//		url = new URL("http://10.0.2.2/hello.php");
//		con  = (HttpURLConnection) url.openConnection();
//		con.setReadTimeout(10000/*milliseconds*/);
//		con.setConnectTimeout(15000/*milliseconds*/);
//		con.setRequestMethod("GET");
//		con.setDoInput(true);		
//		con.connect();
//		
//		if(Thread.interrupted()) throw new InterruptedException();
//		
//		BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream(),"UTF-8"));
//		String payload = reader.readLine();
//		reader.close();
//		
//	} catch (MalformedURLException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	} catch (IOException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	} catch (InterruptedException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	}
       
       
       
     
     
//     //test client code from HW7 ***** does not work as is    
//     URL oracle;
//	try {
//		oracle = new URL("http://10.0.2.2/hello.php");
//		URLConnection myConnection;
//		myConnection = oracle.openConnection();
//		BufferedReader in;
//		in = new BufferedReader(new InputStreamReader(
//				myConnection.getInputStream()));
//		String inputLine;
//		while ((inputLine = in.readLine()) != null) {
//			//System.out.println(inputLine);
//			textView.setText("php values = \n"+inputLine);
//		}
//		in.close();
//	} catch (MalformedURLException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();			
//	}//end try catch blocks     
     
		
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
	
		
}
