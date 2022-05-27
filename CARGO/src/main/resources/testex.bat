@echo off
curl -X get http://localhost:7778/rest/example/get/1
curl -X post http://localhost:7778/rest/example/add
curl -X put http://localhost:7778/rest/example/put
curl -X delete http://localhost:7778/rest/example/del/2