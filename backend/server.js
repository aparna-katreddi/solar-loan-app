const express = require('express');
const mongoose = require('mongoose');
const cors = require('cors');
require('dotenv').config();

console.log('🔧 Starting server setup...');
console.log('🔁 Registering auth routes');
const authRoute = require('./routes/authRoute');
console.log('📦 Registering quote routes');
const quoteRoute = require('./routes/quoteRoute');

const app = express();

app.use(express.json());
console.log('🧠 JSON body parser middleware applied.');

// ✅ Updated: Proper CORS setup for production (Render)
const corsOptions = {
  origin: function(origin, callback) {
    console.log(`🌐 CORS check for origin: ${origin}`);
    const allowedOrigins = [
      'http://localhost:3000',
      'https://solar-loan-app-frontend.onrender.com'
    ];
    if (!origin || allowedOrigins.includes(origin)) {
      return callback(null, true);
    }
    const msg = 'CORS policy does not allow this origin.';
    console.warn(`❌ ${msg} Origin: ${origin}`);
    return callback(new Error(msg), false);
  },
  methods: ['GET', 'POST', 'PUT', 'DELETE', 'OPTIONS'],
  credentials: true
};

app.use(cors(corsOptions));

// ✅ Ensures preflight (OPTIONS) requests return correct headers
app.options('*', cors(corsOptions));
console.log('⚙️ CORS preflight handling enabled.');

app.use('/api', authRoute);
console.log('🛣️ Auth routes registered at /api');

app.use('/api', quoteRoute);
console.log('🛣️ Quote routes registered at /api');

app.get('/', (req, res) => {
  console.log('🏠 Healthcheck endpoint hit');
  res.send('🌞 Solar Quotes API is up!');
});

const PORT = process.env.PORT || 5001;
const MONGO_URI = process.env.MONGO_URI || 'mongodb://localhost:27017/solar_quotes';

console.log(`🔗 Connecting to MongoDB at: ${MONGO_URI}`);

mongoose.connect(MONGO_URI)
  .then(() => {
    console.log('✅ MongoDB connected successfully');
    app.listen(PORT, () => {
      console.log(`🚀 Server running at http://localhost:${PORT}`);
    });
  })
  .catch((err) => {
    console.error('❌ MongoDB connection failed:', err.message);
    process.exit(1);
  });
