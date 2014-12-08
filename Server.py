
import DataBase
import SocketServer
import json


#Website reference:
#https://docs.python.org/2/library/socketserver.html

class TestTCPHandler(SocketServer.StreamRequestHandler):
    
    
    #server   
    def handle(self):
        #get database
        database = DataBase.DataBase()        
        #get data
        self.data = self.rfile.readline()        
        decoded = json.loads(self.data)
        username = "" 
        # pretty printing of json-formatted string
        #print json.dumps(decoded, sort_keys=True, indent=4) 
        #print "subject = ", decoded["subject"]
        subject=decoded["subject"]
               
        #print subject == 'NEW_USER'         
        if subject == 'NEW_USER':
                
                username = decoded["username"];
                #print 'username = '+username         
                password = decoded["password"];
                #print 'password = '+password               
                email = decoded["email"];
                #print 'email = '+ email
                
                validity=database.createUser(username, password, email);       
                #print 'validity = '+validity
        
        elif subject == 'LOGIN':
                
                username = decoded["username"];
                print 'username = '+username         
                password = decoded["password"];
                print 'password = '+password  
                
                validity=database.checkCredentials(username, password);       
                print 'validity = '+validity
                
        elif subject == 'INIT':
                
                validity=database.getSongs(username);               
         
        #must convert result to string
        #before writing    
        self.wfile.write(str(validity))         

if __name__== "__main__":
    HOST, PORT = "localhost", 9999   
    
    server = SocketServer.TCPServer((HOST, PORT), TestTCPHandler) 
    print 'Waiting on connection:'
    server.serve_forever()
    
   
    
    
