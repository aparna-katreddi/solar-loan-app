// App.js
import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import LoginPage from "./pages/LoginPage";
import CreateQuotePage from "./pages/CreateQuotePage";
import QuoteDetailsPage from "./pages/QuoteDetailsPage";

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<LoginPage />} />
        <Route path="/quote" element={<CreateQuotePage />} />
        <Route path="/quote/:id" element={<QuoteDetailsPage />} />
      </Routes>
    </Router>
  );
}

export default App;
