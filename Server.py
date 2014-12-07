
import DataBase
import SocketServer
import json


#Website reference:
#https://docs.python.org/2/library/socketserver.html

class TestTCPHandler(SocketServer.StreamRequestHandler):
    
    
    def handle(self): 
        
        db = DataBase.DataBase()      
        
        #get data
        self.data = self.rfile.readline()
        #trim end off data
        self.data = self.data[2:-3]        
        print self.data
                
        keyAndValue =  self.data.split(',')[0];
        subject = keyAndValue.split(':')[1];
        print 'subject = '+subject
        
        print subject is 'NEW_USER'
        
        if subject == 'NEW_USER':
                keyAndValue =  self.data.split(',')[1];
                username = keyAndValue.split(':')[1];
                print 'username = '+username
        
                keyAndValue =  self.data.split(',')[2];
                password = keyAndValue.split(':')[1];
                print 'password = '+password
        
                keyAndValue =  self.data.split(',')[3];
                email = keyAndValue.split(':')[1];
                print 'email = '+ email
                
                validity = db.createUser(username, password, email)
                validity='TRUE'
                print 'validity = '+validity
        
                #must convert result to string
                #before writing
                self.wfile.write(str(validity))
     
     

if __name__== "__main__":
    HOST, PORT = "localhost", 9999   

    server = SocketServer.TCPServer((HOST, PORT), TestTCPHandler) 

    server.serve_forever()
    
   
    
    
