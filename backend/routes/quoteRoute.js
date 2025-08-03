const express = require('express');
const router = express.Router();
const Quote = require('../models/Quote');

// POST /api/quote
router.post('/quote', async (req, res) => {
  const {
    dealerId,
    firstName,
    lastName,
    address,
    panel,
    panelCount,
    state,
    financeType,
    solarType,
    batteryOption,
    year,
    apr
  } = req.body;

  try {
    // 1. System price logic
    const panelPrice = 500; // Example: $500 per panel
    const basePrice = panelCount * panelPrice;

    // 2. Add battery price if storage is included
    let batteryPrice = 0;
    if (solarType === "Solar + Storage") {
      if (batteryOption === "Tesla Powerwall") batteryPrice = 12000;
      else if (batteryOption === "Enphase IQ") batteryPrice = 10000;
    }

    const systemPrice = basePrice + batteryPrice;

    // 3. Apply tax incentive (e.g., 30% ITC)
    const taxCredit = 0.30 * systemPrice;
    const netLoanAmount = systemPrice - taxCredit;

    // 4. Save quote to DB
    const newQuote = new Quote({
      dealerId,
      firstName,
      lastName,
      address,
      panel,
      panelCount,
      state,
      financeType,
      solarType,
      batteryOption,
      term: financeType === "loan" ? year : null,
      apr: financeType === "loan" ? apr : null,
      systemPrice,
      taxCredit,
      netLoanAmount,
    });

    await newQuote.save();

    // 5. Calculate monthly rent (only if loan)
    let monthlyRent = null;
    if (financeType === "loan") {
      const months = parseInt(year);
      const monthlyRate = parseFloat(apr) / 100 / 12;
      monthlyRent = (netLoanAmount * monthlyRate) / (1 - Math.pow(1 + monthlyRate, -months));
    }

    // 6. Send response
    res.json({
      quoteId: newQuote._id,
      monthlyRent: monthlyRent ? monthlyRent.toFixed(2) : null,
      systemPrice,
      taxCredit: taxCredit.toFixed(2),
      netLoanAmount: netLoanAmount.toFixed(2),
    });
  } catch (err) {
    console.error("❌ Error saving quote:", err);
    res.status(500).json({ message: "Server error" });
  }
});

module.exports = router;
