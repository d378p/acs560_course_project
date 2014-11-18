
import DataBaseTest
import Employee
import SocketServer
#import simpleTest

#Website reference:
#https://docs.python.org/2/library/socketserver.html

class TestTCPHandler(SocketServer.StreamRequestHandler):
    
    
    def handle(self):
        # self.request is socket connected to the client
        self.data = self.rfile.readline().strip()
        print "{} wrote:".format(self.client_address[0])
        print self.data
        self.wfile.write(self.data.upper())
        #test
        emp1 = Employee.Employee("Zara", 2000)
        
        db = DataBaseTest.DataBaseTest()# instance of child
        db.getDB("sara") # child calls overridden method
        
   
        

    

if __name__== "__main__":
    HOST, PORT = "localhost", 9999
    
   

    server = SocketServer.TCPServer((HOST, PORT), TestTCPHandler)

    server.serve_forever()
    
    

    
    
