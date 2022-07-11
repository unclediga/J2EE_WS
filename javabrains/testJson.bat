@echo off
echo . . . . . . . . . . .
echo GET /webapi/messages/json 
echo . . . . . . . . . . .
curl -H "Accept:application/json" http://localhost:7778/webapi/messages/json
echo ...
echo ...
echo . . . . . . . . . . .
echo GET /webapi/messages/json/1 
echo . . . . . . . . . . .
curl -get -H "Accept:application/json" http://localhost:7778/webapi/messages/json/1
echo ...
echo ...
echo . . . . . . . . . . .
echo POST /webapi/messages/json 
echo . . . . . . . . . . .
curl -X POST -H "Accept:application/json" -H "Content-Type:application/json" --data @POST_1.json http://localhost:7778/webapi/messages/json
echo ...
echo ...
echo . . . . . . . . . . .
echo PUT /webapi/messages/json/3 
echo . . . . . . . . . . .
curl -X PUT -H "Accept:application/json" -H "Content-Type:application/json" --data @PUT_1.json http://localhost:7778/webapi/messages/json/3
echo ...
echo ...
echo . . . . . . . . . . .
echo DELETE /webapi/messages/json/2 
echo . . . . . . . . . . .
curl -X DELETE http://localhost:7778/webapi/messages/json/2