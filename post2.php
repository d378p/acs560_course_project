 
<?php
//echo 'Hello ' . htmlspecialchars($_POST["name"]) . '!';

 #****************************************
 # log into existing wamp test database
 #**************************************** 
 
 $dbhost = 'localhost:3306';
 $dbuser = 'root';
 //$dbpass = 'rootpassword';
 $conn = mysqli_connect($dbhost, $dbuser);//, $dbpass
 if(! $conn )
 {
 	die('Could not connect: ' . mysqli_error($conn));
 }
 //echo 'Connected successfully<br />';
 
 mysqli_select_db($conn,'test');
 
 #****************************************
 # delete table SongList 
 #****************************************
 
//  $sql = "DROP TABLE SongList";
//  $retval = mysqli_query($conn, $sql);
//  if(! $retval )
//  {
//  	die('Could not DROP table: ' . mysqli_error($conn));
//  }
//  echo "Table DROPPED successfully<br />"; 
 
 #****************************************
 # create table SongList
 #****************************************
 
//  $sql = "CREATE TABLE SongList( ".
//  		"id INT NOT NULL AUTO_INCREMENT, ".
//  		"Song VARCHAR(100) NOT NULL, ".
//  		"PRIMARY KEY ( id )); ";
//  $retval = mysqli_query($conn, $sql);
//  if(! $retval )
//  {
//  	die('Could not create table: ' . mysql_error($conn));
//  }
//  echo "Table CREATED successfully<br />";
 
 #****************************************
 # insert values to table SongList
 #****************************************
 

$value = htmlspecialchars($_POST["name"]);
 
 $sql = 'INSERT INTO SongList '.
 		'(Song) '.
 		'VALUES ("'.$value.'")'; //was "test1"
 $retval = mysqli_query($conn, $sql);
 if(! $retval )
 {
 	die('Could not INSERT values: ' . mysql_error($conn));
 }
 //echo "INSERT Values successfully<br />";
 mysqli_close($conn);
 
 #***************************
 # get table attributes
 #***************************
 //open DB
 $conn = mysqli_connect($dbhost, $dbuser);//, $dbpass
 mysqli_connect($dbhost, $dbuser);//, $dbpass
 mysqli_select_db($conn, "test");
 //$sql=mysqli_query($conn, "SELECT DISTINCT Song from SongList ");
 $sql=mysqli_query($conn, "SELECT Song FROM SongList ORDER BY id DESC LIMIT 1 ");
 while($row=mysqli_fetch_assoc($sql))
 $output[]=$row;
 //send query back to app
 echo 'Hello ' . json_encode($output) . '!';
 mysqli_close($conn);
 
 
 
 
 
 
 
 //print("Table Values fetched<br />".json_encode($output)); 
 //json_encode($output);
 //$lastKey = Object.keys($output).sort().reverse()[0];
 //$lastValue = json[$lastKey]; 
 //end($output) 
 //$json = json_encode($output,true) 
 //$sql_2=mysqli_query($conn, "SELECT Song FROM SongList ORDER BY id DESC LIMIT 1;");
 
  
?>

 
 
 
 
 
