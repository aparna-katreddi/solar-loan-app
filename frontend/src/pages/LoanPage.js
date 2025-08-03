import React from "react";

function LoanPage() {
  const quoteId = localStorage.getItem("quoteId");
  const monthlyRent = localStorage.getItem("monthlyRent");

  return (
    <div style={{ padding: "40px", textAlign: "center" }}>
      <h2>📄 Loan Summary</h2>
      <p><strong>Quote ID:</strong> {quoteId}</p>
      <p><strong>Estimated Monthly Payment:</strong> ${monthlyRent}</p>
    </div>
  );
}

export default LoanPage;
