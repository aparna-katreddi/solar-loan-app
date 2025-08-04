const express = require('express');
const mongoose = require('mongoose');
const cors = require('cors');
require('dotenv').config();

const authRoute = require('./routes/authRoute');
const quoteRoute = require('./routes/quoteRoute');

const app = express();

// 🧠 Body parser
app.use(express.json());

// ✅ CORS config
app.use(cors({
  origin: 'http://localhost:3000',
  methods: ['GET', 'POST', 'PUT', 'DELETE', 'OPTIONS'],
  credentials: true
}));

// ✅ Routes
app.use('/api', authRoute);
app.use('/api', quoteRoute);

// Healthcheck
app.get('/', (req, res) => {
  res.send('🌞 Solar Quotes API is up!');
});

// ✅ Connect to MongoDB THEN start the server
const PORT = process.env.PORT || 5001;
const MONGO_URI = process.env.MONGO_URI || 'mongodb://localhost:27017/solar_quotes';

mongoose.connect(MONGO_URI, {
  useNewUrlParser: true,
  useUnifiedTopology: true,
})
  .then(() => {
    console.log('✅ MongoDB connected');
    app.listen(PORT, () => {
      console.log(`🚀 Server running at http://localhost:${PORT}`);
    });
  })
  .catch((err) => {
    console.error('❌ MongoDB connection failed:', err.message);
    process.exit(1); // Exit if DB not connected
  });
