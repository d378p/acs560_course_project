
import DataBaseTest
import SocketServer

#import simpleTest

#Website reference:
#https://docs.python.org/2/library/socketserver.html

class TestTCPHandler(SocketServer.StreamRequestHandler):
    
    
    def handle(self):
        # self.request is socket connected to the client
        self.data = self.rfile.readline().strip()
        print "{} wrote:".format(self.client_address[0])      
       
        
        db = DataBaseTest.DataBaseTest()# instance of child
        db.setDB(self.data) # child calls overridden method 
        db.getDB()
           
        
        result = format(db.getDB()+' ')
        print result
        
        
        self.wfile.write(result)
    

if __name__== "__main__":
    HOST, PORT = "localhost", 9999
    
   

    server = SocketServer.TCPServer((HOST, PORT), TestTCPHandler) 

    server.serve_forever()
    
   
    
    
