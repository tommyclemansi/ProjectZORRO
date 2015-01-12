
#Conditionally import wlstModule only when script is executed with jython
if __name__ == '__main__': 
    from wlstModule import *#@UnusedWildImport

def getRunningServerNames():
    domainConfig()
    return cmo.getServers()

def monitorRunningServers():
    print 'starting the script ....'
    username = 'weblogic'
    password = 'welcome1'
    url='t3://localhost:7001'
    connect(username,password,url)
    
    serverNames = getRunningServerNames()
    domainRuntime()
    for name in serverNames:
        print 'Now checking '+name.getName()
        try:
            cd("/ServerRuntimes/"+name.getName())
        except WLSTException,e:
            # this typically means the server is not active, just ignore
            print e
            pass
        print cmo.getName()
        print cmo.getSocketsOpenedTotalCount()
        cmo = cd('serverRuntime:/JVMRuntime/AdminServer')



if __name__== "main":
    monitorRunningServers()
    
