const express = require('express');
const router = express.Router();
const User = require('../models/User');

// Test route to confirm API is reachable
router.post('/ping', (req, res) => {
  console.log('✅ Received /api/ping request');
  res.json({ message: 'pong' });
});

// POST /api/login
router.post('/login', async (req, res) => {
  console.log('➡️ Login request received');
  console.log('Headers:', req.headers);
  console.log('Body:', req.body);

  const { username, password } = req.body;

  try {
    if (!username || !password) {
      return res.status(400).json({ message: 'Username and password are required' });
    }

    const user = await User.findOne({ username });

    if (!user || user.password !== password) {
      return res.status(401).json({ message: 'Invalid credentials' });
    }

    res.json({ message: 'Login successful', userId: user._id });
  } catch (err) {
    console.error('❌ Login error:', err);
    res.status(500).json({ message: 'Server error' });
  }
});

module.exports = router;
