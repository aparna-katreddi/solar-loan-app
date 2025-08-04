// App.js
import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import LoginPage from "./pages/LoginPage";
import CreateQuotePage from "./pages/CreateQuotePage";
import LoanPage from "./pages/LoanPage";

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<LoginPage />} />
        <Route path="/quote" element={<CreateQuotePage />} />
        <Route path="/loan" element={<LoanPage />} />
      </Routes>
    </Router>
  );
}

export default App;
