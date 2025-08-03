import React, { useState, useEffect } from "react";
import api from "../api";

function LoanCalculatorPage() {
  const [product, setProduct] = useState("30yr_3.99");
  const [rents, setRents] = useState([]);

  const handleCalculate = async () => {
    const res = await api.post("/api/calculate", {
      quoteId: localStorage.getItem("quoteId"),
      loanProduct: product,
    });
    setRents(res.data.monthlyRents);
  };

  return (
    <div>
      <h2>Loan Calculator</h2>
      <select value={product} onChange={(e) => setProduct(e.target.value)}>
        <option value="30yr_3.99">30yr @ 3.99%</option>
        <option value="20yr_5.99">20yr @ 5.99%</option>
        <option value="10yr_7.99">10yr @ 7.99%</option>
        <option value="5yr_5.99">5yr @ 5.99%</option>
      </select>
      <button onClick={handleCalculate}>Calculate Rents</button>
      <ul>
        {rents.map((rent, index) => (
          <li key={index}>Month {index + 1}: ${rent.toFixed(2)}</li>
        ))}
      </ul>
    </div>
  );
}

export default LoanCalculatorPage;
