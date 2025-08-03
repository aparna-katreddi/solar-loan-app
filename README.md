# solar-loan-app

## MongoDB is NoSQL Database

## Step-by-Step: Install MongoDB on macOS
### Step 1: Install Homebrew (skip if already installed)
### Step 2: Install MongoDB Community Edition
`brew tap mongodb/brew`
`brew install mongodb-community@6.0`
### Step 3: Start MongoDB
`brew services start mongodb/brew/mongodb-community`
### Step4: To stop it
`brew services stop mongodb/brew/mongodb-community`
### Step 4: Verify It’s Running
`ps aux | grep -v grep | grep mongod`
`mongosh`
### You should see 
Current Mongosh Log ID:	688d288bfff3b7b25443b1e7
Connecting to:		mongodb://127.0.0.1:27017/?directConnection=true&serverSelectionTimeoutMS=2000&appName=mongosh+2.5.6
Using MongoDB:		6.0.25
Using Mongosh:		2.5.6


### Error Troubleshoot If services are not started
#### Error: Formula `mongodb-community` is not installed.
### Step-by-Step Fix
### 🔄 1. Retap the MongoDB repo
`brew untap mongodb/brew || true`
`brew tap mongodb/brew`

## To create database 
### After mongosh step, Wait until you see the shell prompt like: 
test>
### Switch to your database:
`use solar_quotes`
### You will see:
switched to db solar_quotes
### Insert a test user document: Paste this full line (don’t split it):
`db.users.insertOne({ username: "dealer1", password: "password123" });`
### You should see output like:
`{
acknowledged: true,
insertedId: ObjectId("64d3abc456....")
}`
### ####################################################
## To view the data and insert more rows follow below steps
## 1. MongoDB Compass (Official GUI)
Download: https://www.mongodb.com/try/download/compass
Add New connection:
Connection string: mongodb://localhost:27017
Click Connect
You will see: solar_quotes database
Inside it: users, quotes collections

### Add Test User via Compass (Optional)
1.Navigate to solar_quotes > users
2.lick Add Data → Insert Document
3.Paste:
`{
"username": "dealer1",
"password": "password123"
}`
4.Click Insert
### ####################################################
# Now database is ready,Run backend server ,should run this command from 'backend/' folder
1. If You Don’t Have backend/package.json, Create One:Inside backend folder :
  `npm init -y` 
2. Install required backend dependencies
   `npm install express mongoose cors dotenv body-parser`
3. create server.js file inside backend folder
4. Start backend server
    `node server.js`
5. You should see:
   ✅ Connected to MongoDB
   🚀 Server is running on port 5000
### ####################################################
# Now start frontend : Inside frontend folder
1. `npm install`
2. `npm install axios react-router-dom`
3. `npm start`
4. # If above do not work and it throws error react-scripts do not exist
5. `npm install react-scripts --save-dev `
6. `npm start`
7. front end `http://localhost:3000`

### ####################################################
✅ Summary: What’s Working
🔌 MongoDB installed and connected
🖥️ Backend (server.js) is up and running on http://localhost:5000
🌐 Frontend (React) is running on http://localhost:3000



