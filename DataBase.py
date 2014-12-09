

import MySQLdb

class DataBase:
      
    def createUser(self, username, password, email):
        # connect        
        db = MySQLdb.connect(host="localhost", port=3306, user="root", passwd="", db="ourmusic")
        # set cursor
        cursor = db.cursor()                 
        exists = self.userExists(username, password, email)        
        if exists != True:
            # insert into db       
            sql = "INSERT INTO LoginList (username, password, email) VALUES ('%s','%s','%s')" % (username, password, email);       
            # Execute the SQL command
            cursor.execute(sql)
            # create user table
            sql = "CREATE TABLE %s (id INT NOT NULL AUTO_INCREMENT,friendUsername VARCHAR(40) NOT NULL,friendFirst VARCHAR(40) NOT NULL,friendLast VARCHAR(40) NOT NULL,PRIMARY KEY ( id ));" % (username);
            # Execute the SQL command
            cursor.execute(sql)            
            # commit your changes
            db.commit()           
            exist = True             
        elif exists == True:
            exist = False;         
        db.close()           
        validity = '{"validity":%s,"subject":"NEW_USER","type":"RESPONSE"}'% (exist)         
        return validity
    
    def userExists(self, username, password, email):
            # connect
            db = MySQLdb.connect(host="localhost", port=3306, user="root",passwd="",db="ourmusic")               
            cursor = db.cursor()                         
            sql = "SELECT username s, count(*) FROM LoginList where username='%s' and password='%s'and email='%s';" % (username,password,email)
            str1=""
            try:
                # Execute the SQL command
                cursor.execute(sql)            
                # Fetch all the rows in a list of lists.
                results = cursor.fetchall()               
                data = ''
                totalData = ''  
                for list in results:               
                    for number in list:                                      
                        data = data+str(number)+"\n"                                                  
                        totalData = data            
                        #how to tokenize string
                        tokens = totalData.split('\n')
                        str1 = str(tokens[0])                        
            except:
                print "Error: unable to fecth data"
                # disconnect from server
                db.close()       
            if str1 == username:
                validity = True
            else:
                validity = False             
            return validity
                
    def userExists2(self, username, password):
            # connect
            db = MySQLdb.connect(host="localhost", port=3306, user="root",passwd="",db="ourmusic")               
            cursor = db.cursor()                         
            sql = "SELECT username s, count(*) FROM LoginList where username='%s' and password='%s';" % (username,password)                 
            str1=""
            try:
                # Execute the SQL command
                cursor.execute(sql)           
                # Fetch all the rows in a list of lists.
                results = cursor.fetchall()               
                data = ''
                totalData = ''  
                for list in results:               
                    for number in list:                                      
                        data = data+str(number)+"\n"                                                  
                        totalData = data            
                        #how to tokenize string
                        tokens = totalData.split('\n')
                        str1 = str(tokens[0])    
            except:
                print "Error: unable to fecth data"
                # disconnect from server
                db.close()       
            if str1 == username:
                validity = True
            else:
                validity = False              
            return validity
               
    def checkCredentials(self, username, password):                
        exists = self.userExists2(username, password)                
        if exists==True:
            validity = '{"validity":true}'
        else:
            validity = '{"validity":false}'                           
        return validity
    
    def getSongs(self,username):
            # connect       
            db = MySQLdb.connect(host="localhost", port=3306, user="root", passwd="",db="ourmusic")        
            cursor = db.cursor()                         
            sql = "SELECT * FROM SongList WHERE friend IN ( '%s' )" % (username); 
            # Execute the SQL command
            cursor.execute(sql)               
            data = ''
            totalData = ''
            songJson = ''
            count=0
            str1=''
            str2=''           
            # Fetch all the rows in a list of lists.
            results = cursor.fetchall()            
            if results==None:
                songJson = '{"songs":[{"songName":"%s","albumName":"%s","artistName":"%s"}]' % ('noSong', 'noAlbum', 'noArtist')                
            else:                
                str1 = '{"songs":"['                             
                for list in results:                     
                    count=count+1
                    data=''                                  
                    for number in list:                
                        data = data+str(number)+" "           
                    #how to tokenize string
                    #tokens = totalData.split('\n')
                    tokens = data.split(' ')                        
                    str1 = str1+'{"songName":"'+str(tokens[2])+'","albumName":"'+str(tokens[3])+'","artistName":"'+str(tokens[4])+'"}'
                    if count==len(results):
                        str2 = str1+']"}'
                    else:      
                        str2 = str1+','
                        str1=''
                    songJson = songJson+str2
            # disconnect from server
            db.close()             
            return songJson     

    def getTopTen(self,decoded):
        # complete after server
        # issue is resolved
        # so code can be tested out
        
        username = decoded["username"];  
        # connect       
        db = MySQLdb.connect(host="localhost", port=3306, user="root", passwd="",db="ourmusic")       
        cursor = db.cursor()
        
        sql = "SELECT Song s, count(*) \
                         FROM SongList \
                         WHERE friend IN ( '%S') \
                         group by Song \
                         order by count(*) desc \
                         limit 10 ;" %(username)
                         
        try:
            # Execute the SQL command
            cursor.execute(sql)      
            # Fetch all the rows in a list of lists.
            results = cursor.fetchall()
            #results = cursor.fetchone()
            #results = cursor.fetchmany(1)
            data = ''
            totalData = ''  
            for list in results:               
                for number in list:                                      
                    data = data+str(number)+"\n" 
                    #data = data+str(number)+" "                               
            totalData = data
            
            #how to tokenize string
            tokens = totalData.split('\n')
            str1 = str(tokens[0])+" "+str(tokens[1])
            # disconnect from server
            db.close()     
            return str1      
        except:
            print "Error: unable to fecth data"
                       
       
            
    def addSong(self, decoded):
        # complete after server
        # issue is resolved
        # so code can be tested
        
        # connect                      
        songName = decoded["songName"];                        
        albumName = decoded["albumName"];                             
        artistName = decoded["artistName"];               
        db = MySQLdb.connect(host="localhost", port=3306, user="root", passwd="", db="ourmusic")
        # set cursor
        cursor = db.cursor()      
        # insert into db       
        sql = "INSERT INTO LoginList (songName, albumName, artistName) VALUES ('%s','%s','%s')" % (songName, albumName, artistName);       
        # Execute the SQL command
        cursor.execute(sql)        
        # commit your changes
        db.commit()       
        validity = '{"validity":true}'       
        return validity 
          
    
    def addFriend(self, decoded):
        # complete after server
        # issue is resolved
        # so code can be tested
        
        # connect 
        username = decoded["username"];
        friendUsername = decoded["friendUsername"];
        firstName = decoded["firstName"];
        lastName = decoded["lastName"];            
        db = MySQLdb.connect(host="localhost", port=3306, user="root", passwd="", db="ourmusic")
        # set cursor
        cursor = db.cursor()      
        # insert into db       
        sql = "INSERT INTO %s (friendUsername, firstName, lastName) VALUES ('%s','%s','%s')" % (username, friendUsername,firstName,lastName);       
        # Execute the SQL command
        cursor.execute(sql)          
        # commit your changes
        db.commit()       
        validity = '{"validity":true}'       
        return validity 
    
    
    
