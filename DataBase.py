

import MySQLdb

class DataBase:  
    
    
    def createUser(self,username, password, email): 
        
        validity='TRUE'       
        return validity
 
 
    def userExists(self, data):       
   
        
        validity=True       
        return validity
 
 
      
    def setDB(self, data):
        # connect        
        db = MySQLdb.connect(host="localhost", port=3306, user="root", passwd="",db="ourmusic")

        #set cursor
        cursor = db.cursor()        
        
        #tokenize data
        str = data;
        #split first string value
        friend =  str.split(' ', 1 )[0];
        song =  str.split(' ', 1 )[1];
        
        #print data
        #print friend
        #print song
        
        #insert into db       
        sql = "INSERT INTO SongList (friend, song) VALUES ('%s', '%s')" % (friend, song);
       
        # Execute the SQL command
        cursor.execute(sql)  

        # commit your changes
        db.commit()
            


   
#     def getDB(self):
#         # connect
#         #db = MySQLdb.connect(host="localhost:3306", user="root", passwd="",db="test")
#         db = MySQLdb.connect(host="localhost", port=3306, user="root", passwd="",db="ourmusic")
# 
#         #host="127.0.0.1", port=3306, user="yoruname", passwd="yourpwd", db="test")
#         cursor = db.cursor()
# 
#         
# #       Prepare SQL query to INSERT a record into the database.
# #       sql = "SELECT DISTINCT Song from SongList "
# 
# # sql = "SELECT Song s, count(*) \
# #                  FROM SongList \
# #                  WHERE friend IN ( 'davidkimmey2602389999','jack') \
# #                  group by Song \
# #                  order by count(*) desc \
# #                  limit 2 ;"
#                  
#         sql = "SELECT Song s, count(*) \
#                  FROM SongList \
#                  WHERE friend IN ( 'davidkimmey2602389999' ) \
#                  group by Song \
#                  order by count(*) desc \
#                  limit 2 ;"
#                 
# #         sql = "SELECT Song s, count(*) \
# #                 FROM SongList;"
#         
#         try:
#             # Execute the SQL command
#             cursor.execute(sql)           
#             
#             # Fetch all the rows in a list of lists.
#             results = cursor.fetchall()
#             #results = cursor.fetchone()
#             #results = cursor.fetchmany(1)
#             data = ''
#             totalData = ''  
#             for list in results:               
#                 for number in list:                                      
#                     data = data+str(number)+"\n" 
#                     #data = data+str(number)+" "                               
#             totalData = data
#             
#             #how to tokenize string
#             tokens = totalData.split('\n')
#             str1 = str(tokens[0])+" "+str(tokens[1])
#             print str1
#                     
#             #print totalData
#             
#             #------------------------------------------------
#                             
# #             for row in results:
# #                 print row[0]
# #                 print row[1]
#               
#                 #data = row
#             #returns a list of lists  
#             #print results
#             
# #             for list in results:
# #                 #data=''
# #                 for number in list:
# #                     #print number
# #                     #print isinstance( number, str)
# #                     data = data+str(number)+" "
# #                    
# #               
# #             totalData = totalData+data
# #             i=0
# #             m=0
# #             while i<results.length:
# #                 while m<results[0].length:
# #                     print results[i][m]
# #                     m +=2
# #             i+=1
#             
# #             #how to combine string and int
# #             str1 = 1
# #             str2 = 'hello'
# #             totalData = str2+'\n'+str(str1)                
# #             print totalData            
# #              
# #             test2 = totalData[0:6:2]
# #             print test2
# #             test2 = totalData[0:3]
# #             print ('test2 = '+test2)
#              
#             
#              
#            
#               
#             return str1#delete  
#             
#         except:
#             print "Error: unable to fecth data"
# 
#             # disconnect from server
#             db.close()

    
