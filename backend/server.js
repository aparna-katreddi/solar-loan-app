const express = require('express');
const mongoose = require('mongoose');
const cors = require('cors');
require('dotenv').config();

console.log('🔧 Starting server setup...');

const authRoute = require('./routes/authRoute');
const quoteRoute = require('./routes/quoteRoute');

const app = express();

app.use(express.json());
console.log('🧠 JSON body parser middleware applied.');

const allowedOrigins = [
  'http://localhost:3000',
  'https://solar-loan-app-frontend.onrender.com'
];

app.use(cors({
  origin: function(origin, callback) {
    console.log(`🌐 CORS check for origin: ${origin}`);
    if(!origin) return callback(null, true);
    if(allowedOrigins.indexOf(origin) === -1) {
      const msg = 'CORS policy does not allow this origin.';
      console.warn(`❌ ${msg} Origin: ${origin}`);
      return callback(new Error(msg), false);
    }
    return callback(null, true);
  },
  methods: ['GET', 'POST', 'PUT', 'DELETE', 'OPTIONS'],
  credentials: true
}));

app.options('*', cors()); // handle preflight OPTIONS requests
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
