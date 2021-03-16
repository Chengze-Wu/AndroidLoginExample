# AndroidLoginExample
## Instructions
1. Create a new folder(any name) on your local machine or AWS VM.
2. Copy the login_example.py to your new folder. 
3. Try to run it and get the ip address. (Make sure to have the outside network access permission)
4. Open the login_example.py and change following code to your own MongoDB uri.  
```
app.config['MONGO_URI'] = 'mongodb+srv://admin:admin@cluster0.zhcnd.mongodb.net/myFirstDatabase?retryWrites=true&w=majority' 
```
 
5. Open the Constents.kt in Android project and modify the url with your ip address.
6. Try register and login on Android emulator