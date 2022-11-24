import bluetooth

serverMACAddress = 'xx:xx:xx:xx:xx:x'
port = 3
sock = bluetooth.BluetoothSocket(bluetooth.RFCOMM)
sock.connect((serverMACAddress, port))

while 1:
   text = raw_input() # Note change to the old (Python 2) raw_input
   if text == "quit":
       break
   sock.send(text)

sock.close()