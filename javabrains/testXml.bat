@echo off
echo GET /webapi/messages 
echo . . . . . . . . . . .
curl -H "Accepted:application/xml" http://localhost:7778/webapi/messages
echo . . . . . . . . . . .
echo ...
echo ...

echo . . . . . . . . . . .
echo GET /webapi/messages/1 
echo . . . . . . . . . . .
curl -H "Accepted:application/xml" http://localhost:7778/webapi/messages/1
echo . . . . . . . . . . .
echo ...
echo ...
echo . . . . . . . . . . .
echo GET /webapi/messages/2 
echo . . . . . . . . . . .
curl -H "Accepted:application/xml" http://localhost:7778/webapi/messages/2