
import Employee
import MySQLdb

class DataBaseTest:   
    
      
    def setDB(self, data):
        # connect
        #db = MySQLdb.connect(host="localhost:3306", user="root", passwd="",db="test")
        db = MySQLdb.connect(host="localhost", port=3306, user="root", passwd="",db="test")

        #host="127.0.0.1", port=3306, user="yoruname", passwd="yourpwd", db="test")
        cursor = db.cursor()

        # execute SQL select statement
        cursor.execute("SELECT * FROM SongList")

        #cursor.execute("INSERT into SongList (Song) values (%s)", ("testDavid"))

        # get the number of rows in the resultset
        numrows = int(cursor.rowcount)

        # get and display one row at a time.
#         for x in range(0,numrows):
#         row = cursor.fetchone()
#         print row[0], "-->", row[1]
    
        #sql = """INSERT INTO SongList(Song) VALUES ("testDavid2")"""
        sql = "INSERT INTO SongList(Song) VALUES ('%s')" % (data)

        # Execute the SQL command
        cursor.execute(sql)  

        # commit your changes
        db.commit()
            


   
    def getDB(self):
        # connect
        #db = MySQLdb.connect(host="localhost:3306", user="root", passwd="",db="test")
        db = MySQLdb.connect(host="localhost", port=3306, user="root", passwd="",db="test")

        #host="127.0.0.1", port=3306, user="yoruname", passwd="yourpwd", db="test")
        cursor = db.cursor()

        
        # Prepare SQL query to INSERT a record into the database.
        sql = "SELECT DISTINCT Song from SongList "
        
        try:
            # Execute the SQL command
            cursor.execute(sql)
            # Fetch all the rows in a list of lists.
            results = cursor.fetchall()
            for row in results:
                data = row[0] 
                
            return data    
            
        except:
            print "Error: unable to fecth data"

            # disconnect from server
            db.close()

    
