import React, { useState } from "react";
import api from "../api";
import { useNavigate } from "react-router-dom";

function CreateQuotePage() {
  const [firstName, setFirstName] = useState("");
  const [lastName, setLastName] = useState("");
  const [address, setAddress] = useState("");
  const [panel, setPanel] = useState("Maxeon 425");
  const [count, setCount] = useState(1);
  const [state, setState] = useState("CA");
  const [type, setType] = useState("loan");
  const [solarType, setSolarType] = useState("PV Only");
  const [batteryOption, setBatteryOption] = useState("No Storage");
  const [year, setYear] = useState("5");
  const [apr, setApr] = useState("3.99");

  const navigate = useNavigate();

  const handleSubmit = async () => {
    const res = await api.post("/api/quote", {
      dealerId: localStorage.getItem("dealerId"),
      firstName,
      lastName,
      address,
      panel,
      panelCount: count,
      state,
      financeType: type,
      solarType,
      batteryOption: solarType === "PV Only" ? "No Storage" : batteryOption,
      year: type === "loan" ? year : null,
      apr: type === "loan" ? apr : null,
    });
    localStorage.setItem("quoteId", res.data.quoteId);
    localStorage.setItem("monthlyRent", res.data.monthlyRent);
    navigate("/loan");
  };

  return (
    <div style={{ display: "flex", justifyContent: "center", padding: "40px", backgroundColor: "#f5f8fa", minHeight: "100vh" }}>
      <div style={{
        backgroundColor: "#fff",
        padding: "30px 40px",
        borderRadius: "12px",
        boxShadow: "0 4px 10px rgba(0,0,0,0.1)",
        width: "100%",
        maxWidth: "700px"
      }}>
        <h2 style={{ textAlign: "center", marginBottom: "30px" }}>Create Quote</h2>

        {/* First Name / Last Name */}
        <div style={{ display: "flex", gap: "30px", marginBottom: "10px" }}>
          <div style={{ flex: 1 }}>
            <label style={{ fontWeight: "bold", color: "#1d4ed8" }}>First Name:</label>
            <input
              type="text"
              value={firstName}
              onChange={(e) => setFirstName(e.target.value)}
              style={{ width: "100%", padding: "8px" }}
            />
          </div>
          <div style={{ flex: 1 }}>
            <label style={{ fontWeight: "bold", color: "#1d4ed8" }}>Last Name:</label>
            <input
              type="text"
              value={lastName}
              onChange={(e) => setLastName(e.target.value)}
              style={{ width: "100%", padding: "8px" }}
            />
          </div>
        </div>

        {/* Address / State */}
        <div style={{ display: "flex", gap: "30px", marginBottom: "25px" }}>
          <div style={{ flex: 2 }}>
            <label style={{ fontWeight: "bold", color: "#1d4ed8" }}>Address:</label>
            <textarea
              value={address}
              onChange={(e) => setAddress(e.target.value)}
              style={{ width: "100%",height:"25px", padding: "8px",marginTop:"5x",resize:"vertical",overflow:"auto" }}
            />
          </div>
          <div style={{ flex: 1 }}>
            <label style={{ fontWeight: "bold", color: "#1d4ed8" }}>State:</label>
            <select value={state} onChange={(e) => setState(e.target.value)} style={{ width: "100%", padding: "8px" }}>
              <option>CA</option>
              <option>NY</option>
              <option>IL</option>
              <option>TX</option>
            </select>
          </div>
        </div>

        {/* Panel / Count */}
        <div style={{ display: "flex", gap: "20px", marginBottom: "20px" }}>
          <div style={{ flex: 1 }}>
            <label style={{ fontWeight: "bold", color: "#1d4ed8" }}>Panel Type:</label>
            <select value={panel} onChange={(e) => setPanel(e.target.value)} style={{ width: "100%", padding: "8px" }}>
              <option>Maxeon 425</option>
              <option>Waree 400</option>
              <option>Qcells 405</option>
            </select>
          </div>

          <div style={{ flex: 1 }}>
            <label style={{ fontWeight: "bold", color: "#1d4ed8" }}>Panel Count:</label>
            <input
              type="number"
              min="1"
              value={count}
              onChange={(e) => setCount(e.target.value)}
              style={{ width: "100%", padding: "8px" }}
            />
          </div>
        </div>

        {/* Solar Type / Battery Option */}
        <div style={{ display: "flex", gap: "20px", marginBottom: "20px" }}>
          <div style={{ flex: 1 }}>
            <label style={{ fontWeight: "bold", color: "#1d4ed8" }}>Solar Type:</label>
            <select
              value={solarType}
              onChange={(e) => setSolarType(e.target.value)}
              style={{ width: "100%", padding: "8px" }}
            >
              <option>PV Only</option>
              <option>Solar + Storage</option>
            </select>
          </div>

          <div style={{ flex: 1 }}>
            <label style={{ fontWeight: "bold", color: "#1d4ed8" }}>Battery Option:</label>
            <select
              value={batteryOption}
              onChange={(e) => setBatteryOption(e.target.value)}
              disabled={solarType === "PV Only"}
              style={{
                width: "100%",
                padding: "8px",
                backgroundColor: solarType === "PV Only" ? "#e5e7eb" : "white",
                color: solarType === "PV Only" ? "#6b7280" : "#000"
              }}
            >
              <option>No Storage</option>
              <option>Tesla Powerwall</option>
              <option>Enphase IQ</option>
            </select>
          </div>
        </div>

        {/* Finance Type */}
        <div style={{ marginBottom: "20px" }}>
          <label style={{ fontWeight: "bold", color: "#1d4ed8" }}>Finance Type:</label>
          <select value={type} onChange={(e) => setType(e.target.value)} style={{ width: "100%", padding: "8px" }}>
            <option value="loan">Loan</option>
            <option value="lease">Lease</option>
          </select>
        </div>

        {/* Year & APR dropdowns (only if Loan is selected) */}
        {type === "loan" && (
          <div style={{ display: "flex", gap: "20px", marginBottom: "20px" }}>
            <div style={{ flex: 1 }}>
              <label style={{ fontWeight: "bold", color: "#1d4ed8" }}>Term (Years):</label>
              <select value={year} onChange={(e) => setYear(e.target.value)} style={{ width: "100%", padding: "8px" }}>
                <option>5</option>
                <option>10</option>
                <option>15</option>
                <option>20</option>
                <option>25</option>
              </select>
            </div>
            <div style={{ flex: 1 }}>
              <label style={{ fontWeight: "bold", color: "#1d4ed8" }}>APR (%):</label>
              <select value={apr} onChange={(e) => setApr(e.target.value)} style={{ width: "100%", padding: "8px" }}>
                <option>3.99</option>
                <option>4.99</option>
                <option>6.54</option>
                <option>7.99</option>
                <option>9.99</option>
                <option>10.54</option>
              </select>
            </div>
          </div>
        )}

        <div style={{ display: "flex", justifyContent: "center" }}>
          <button
            onClick={handleSubmit}
            style={{
              padding: "10px 30px",
              backgroundColor: "#1d4ed8",
              color: "#fff",
              border: "none",
              borderRadius: "6px",
              fontSize: "16px",
              cursor: "pointer"
            }}
          >
            Next
          </button>
        </div>
      </div>
    </div>
  );
}

export default CreateQuotePage;
