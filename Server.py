
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
        subject=decoded["subject"]               
        if subject == 'NEW_USER':                
                username = decoded["username"];                        
                password = decoded["password"];                             
                email = decoded["email"];                
                validity=database.createUser(username, password, email);  
        elif subject == 'LOGIN':                
                username = decoded["username"];                      
                password = decoded["password"];                
                validity=database.checkCredentials(username, password);       
        elif subject == 'INIT':                
                validity=database.getSongs(username);     
        elif subject == 'QUERY':                
                validity=database.getTopTen(decoded);    
        elif subject == 'ADD_SONG':                             
                validity=database.addSong(decoded);        
        elif subject == 'ADD_FRIEND':                              
                validity=database.addFriend(decoded);     
        #must convert result to string
        #before writing    
        self.wfile.write(str(validity))         


if __name__== "__main__":
    HOST, PORT = "localhost", 9999   
    
    server = SocketServer.TCPServer((HOST, PORT), TestTCPHandler)
    print 'Waiting on connection:'
    server.serve_forever()
  
    
    
   
    
    
