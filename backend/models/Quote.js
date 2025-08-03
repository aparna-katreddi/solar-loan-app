const mongoose = require('mongoose');

const quoteSchema = new mongoose.Schema({
  dealerId: String,
  firstName: String,
  lastName: String,
  address: String,
  panel: String,
  panelCount: Number,
  state: String,
  financeType: String,
  solarType: String,
  batteryOption: String,
  term: Number,
  apr: Number,
  systemPrice: Number,
  taxCredit: Number,
  netLoanAmount: Number,
});

module.exports = mongoose.model('Quote', quoteSchema);
