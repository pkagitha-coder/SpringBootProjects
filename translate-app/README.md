#Translate APP
Supports following REST APIs 
- /users/register API is used to register USER. This api takes JSON payload name,email and password. Email address should be unique. No Authentication required for this API

Authentication Required for below APIs(Use Basic Authentication)
- /users/<emailaddress> API is fetch the user details. This api needs username and password.    
- /translate/<srcLang>/to/<destLang> API is used to translate text. This API takes text to be translated as response body.


#How To Run Application
Use following command to run this app
```
mvn clean package spring-boot:run
```
    
