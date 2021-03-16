from flask import Flask, render_template, url_for, request, session, redirect, flash
from flask_pymongo import PyMongo
from werkzeug.security import check_password_hash, generate_password_hash


app = Flask(__name__)


app.config['MONGO_URI'] = 'mongodb+srv://admin:admin@cluster0.zhcnd.mongodb.net/myFirstDatabase?retryWrites=true&w=majority'
mongo = PyMongo(app)

@app.route('/')
def index():
    if 'username' in session:
        return 'You are logged in as ' + session['username']
        
    return render_template('index.html')
 
@app.route('/login', methods=['POST'])
def login():
    users = mongo.db.users
    login_user = users.find_one({'name' : request.form['username']})
    password = request.form['pass']
    error = None
        
    if login_user is None:
        error = 'Incorrect Username'
    #elif not check_password_hash(login_user['password'], password):
    elif login_user['password'] == password:
        error = 'Incorrect password'
        
    if error is None:
        session.clear()
        session['username'] = request.form['username']
        return redirect(url_for('index'))
                
    flash(error)
        
    return render_template('index.html')
    
@app.route('/register', methods=['POST','GET'])
def register():
    if request.method == 'POST':
        users = mongo.db.users
        existing_user = users.find_one({'name' : request.form['username']})
        
        if existing_user is None:
            hashpass = generate_password_hash(request.form['pass'])
            users.insert({'name' : request.form['username'], 'password' : request.form['pass']})
            session['username'] = request.form['username']
            return redirect(url_for('index'))
            
        return 'That username already exists!'
    
    if request.method == 'GET':
        return render_template('register.html')
            
    return ''    
#
# This part serves as Android auth
# url should be with /api/
#
@app.route('/api/login', methods=['POST'])
def android_login():
    users = mongo.db.users
    android_user = users.find_one({'name' : request.form['username']})
    password = request.form['pass']
    error = None
    if android_user is None:
        error = 'Incorrect Username'
    #elif not check_password_hash(android_user['password'], password):
    elif not android_user['password'] == password:
        error = 'Incorrect password'
        
    if error is None:
        session.clear()
        session['username'] = request.form['username']
        return 'You login as ' + session['username']
    
    return error
        
    

    
@app.route('/api/register', methods=['POST'])
def android_register():
    users = mongo.db.users
    existing_user = users.find_one({'name' : request.form['username']})
    username = request.form['username']
    password = request.form['pass']
    error = None
    
    if not username:
        error = 'Username is required.'
    elif not password:
        error = 'Password is required.'
    elif existing_user:
        error = 'That username already exists!'
        
    if error is None:
        users.insert({'name' : request.form['username'], 'password' : request.form['pass']})
        session['username'] = request.form['username']
        return 'Successful registered!'
        
    return error
    
    
    
if __name__ == '__main__':
    app.secret_key = 'dev'
    app.run(host='0.0.0.0',debug=True)