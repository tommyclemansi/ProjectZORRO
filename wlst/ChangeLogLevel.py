# tcleyman
'''
created by tcleyman

 - create project and make sure WLS is a targetted server
 - add WLST facet
 - create script through template 
   or
 - right click on a bean

this is a sample of a WLS online command. 
change log level of a server

'''
#Conditionally import wlstModule only when script is executed with jython
if __name__ == '__main__': 
    from wlstModule import *#@UnusedWildImport

print 'starting the script ....'
username = 'weblogic'
password = 'welcome1'
url='t3://localhost:7001'

connect(username,password,url)


edit()
startEdit()




try:
    save()
    activate(block="true")
    print "script returns SUCCESS"   
except Exception, e:
    print e 
    print "Error while trying to save and/or activate!!!"
    dumpStack()
    raise 
    
