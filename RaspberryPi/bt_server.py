import bt_server

server = bt_server.BluetoothSocket(bt_server.RFCOMM)
port = 1
server.bind(("", port))
server.listen(1)
client, addr = server.accept()

while True:
   print(client.recv(1024))   # 한 문자씩 출력됨
