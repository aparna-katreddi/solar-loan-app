import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import api from "../api";

function QuoteDetailsPage() {
  const { id: quoteId } = useParams();
  const [quote, setQuote] = useState(null);
  const [error, setError] = useState(null);

  useEffect(() => {
    async function fetchQuote() {
      try {
        const res = await api.get(`/api/quote/${quoteId}`);
        setQuote(res.data);
      } catch (err) {
        setError("Failed to fetch quote details.");
      }
    }

    fetchQuote();
  }, [quoteId]);

  if (error) return <p style={{ color: "red" }}>{error}</p>;
  if (!quote) return <p>Loading...</p>;

  return (
    <div style={{ padding: "40px", textAlign: "center" }}>
      <h2>📄 Quote Summary</h2>
      <p><strong>Quote ID:</strong> {quoteId}</p>
      <p><strong>First Name:</strong> {quote.firstName}</p>
      <p><strong>Last Name:</strong> {quote.lastName}</p>
      <p><strong>System Price:</strong> ${quote.systemPrice}</p>
      <p><strong>Tax Credit:</strong> ${quote.taxCredit}</p>
      <p><strong>Net Loan Amount:</strong> ${quote.netLoanAmount}</p>
      <p><strong>Estimated Monthly Rent:</strong> ${quote.monthlyRent}</p>
    </div>
  );
}

export default QuoteDetailsPage;
