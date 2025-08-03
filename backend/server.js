const express = require('express');
const mongoose = require('mongoose');
const cors = require('cors');
require('dotenv').config();

const authRoute = require('./routes/authRoute');
const quoteRoute = require('./routes/quoteRoute');

const app = express();

// 🧠 Body parser FIRST
app.use(express.json());

// ✅ CORS setup
app.use(cors({
  origin: 'http://localhost:3000',
  methods: ['GET', 'POST', 'PUT', 'DELETE', 'OPTIONS'],
  credentials: true
}));

// ✅ MongoDB Connection
mongoose.connect('mongodb://localhost:27017/solar_quotes', {
  useNewUrlParser: true,
  useUnifiedTopology: true,
});

mongoose.connection.on('connected', () => {
  console.log('✅ Connected to MongoDB');
});

mongoose.connection.on('error', (err) => {
  console.error('❌ MongoDB connection error:', err);
});

// ✅ API routes
app.use('/api', authRoute);
app.use('/api', quoteRoute);

// Root route
app.get('/', (req, res) => {
  res.send('API is working!');
});

// ✅ Start server
const PORT = process.env.PORT || 5001;
app.listen(PORT, () => {
  console.log(`🚀 Server is running on http://localhost:${PORT}`);
});
