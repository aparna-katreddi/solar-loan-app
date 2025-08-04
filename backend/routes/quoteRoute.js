const express = require('express');
const router = express.Router();
const Quote = require('../models/Quote');

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
    apr,
  } = req.body;

  try {
    // 1. Calculate system price based on panels
    const panelPrice = 500; // $500 per panel
    const basePrice = panelCount * panelPrice;

    // 2. Add battery price if applicable
    let batteryPrice = 0;
    if (solarType === "Solar + Storage") {
      if (batteryOption === "Tesla Powerwall") batteryPrice = 12000;
      else if (batteryOption === "Enphase IQ") batteryPrice = 10000;
    }

    const systemPrice = basePrice + batteryPrice;

    // 3. Calculate tax credit (ITC 30%)
    const taxCredit = 0.30 * systemPrice;

    // 4. Calculate net loan amount after tax credit
    const netLoanAmount = systemPrice - taxCredit;

    // 5. Save the quote to DB
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

    // 6. Calculate monthly rent if loan type
    let monthlyRent = null;
    if (financeType === "loan") {
      const months = parseInt(year);
      const monthlyRate = parseFloat(apr) / 100 / 12;
      monthlyRent = (netLoanAmount * monthlyRate) / (1 - Math.pow(1 + monthlyRate, -months));
    }

    // 7. Send back the response with quote details
    res.json({
      quoteId: newQuote._id,
      monthlyRent: monthlyRent ? monthlyRent.toFixed(2) : null,
      systemPrice: systemPrice.toFixed(2),
      taxCredit: taxCredit.toFixed(2),
      netLoanAmount: netLoanAmount.toFixed(2),
    });
  } catch (err) {
    console.error("❌ Error saving quote:", err);
    res.status(500).json({ message: "Server error" });
  }
});

module.exports = router;
