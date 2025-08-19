const express = require('express');
const mongoose = require('mongoose');
const cors = require('cors');
require('dotenv').config();

const authRoute = require('./routes/authRoute');
const quoteRoute = require('./routes/quoteRoute');

const app = express();

app.use(express.json());

const allowedOrigins = [
  'http://localhost:3000',
  'https://solar-loan-app-frontend.onrender.com'
];

app.use(cors({
  origin: function(origin, callback) {
    if(!origin) return callback(null, true);
    if(allowedOrigins.indexOf(origin) === -1) {
      const msg = 'CORS policy does not allow this origin.';
      return callback(new Error(msg), false);
    }
    return callback(null, true);
  },
  methods: ['GET', 'POST', 'PUT', 'DELETE', 'OPTIONS'],
  credentials: true
}));

app.options('*', cors()); // handle preflight OPTIONS requests

app.use('/api', authRoute);
app.use('/api', quoteRoute);

app.get('/', (req, res) => {
  res.send('🌞 Solar Quotes API is up!');
});

const PORT = process.env.PORT || 5001;
const MONGO_URI = process.env.MONGO_URI || 'mongodb://localhost:27017/solar_quotes';

mongoose.connect(MONGO_URI)
  .then(() => {
    console.log('✅ MongoDB connected');
    app.listen(PORT, () => {
      console.log(`🚀 Server running at http://localhost:${PORT}`);
    });
  })
  .catch((err) => {
    console.error('❌ MongoDB connection failed:', err.message);
    process.exit(1);
  });
