import React, { useState } from "react";
import api from "../api";
import { useNavigate } from "react-router-dom";

function LoginPage() {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");
  const navigate = useNavigate();

  const handleLogin = async () => {
    try {
      const res = await api.post("/api/login", { username, password });
      const dealerId = res.data.userId;
      localStorage.setItem("dealerId", dealerId);

      // ✅ Optional: fetch profile
      const profileRes = await api.get(`/api/profile?dealerId=${dealerId}`);
      console.log("✅ Profile:", profileRes.data);

      navigate("/quote"); // Go to quote page after login
    } catch (err) {
      console.error("❌ Login failed:", err);
      setError("Invalid username or password");
    }
  };

  return (
    <div style={styles.container}>
      <h1>☀️ Solar Quote Portal 🔋</h1>
      <div style={styles.form}>
        <input
          placeholder="Username"
          value={username}
          onChange={(e) => setUsername(e.target.value)}
          style={styles.input}
        />
        <input
          placeholder="Password"
          type="password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          style={styles.input}
        />
        <button onClick={handleLogin} style={styles.button}>
          Login
        </button>
        {error && <p style={styles.error}>{error}</p>}
      </div>
    </div>
  );
}

const styles = {
  container: {
    marginTop: "100px",
    textAlign: "center",
    fontFamily: "Arial, sans-serif",
  },
  form: {
    display: "inline-block",
    padding: "20px",
    border: "1px solid #ccc",
    borderRadius: "8px",
    backgroundColor: "#f9f9f9",
    textAlign: "left",
  },
  input: {
    display: "block",
    width: "250px",
    padding: "10px",
    marginBottom: "10px",
    fontSize: "16px",
  },
  button: {
    width: "100%",
    padding: "10px",
    backgroundColor: "#4da6ff",
    color: "#fff",
    fontSize: "16px",
    border: "none",
    borderRadius: "4px",
    cursor: "pointer",
  },
  error: {
    color: "red",
    marginTop: "10px",
    fontSize: "14px",
  },
};

export default LoginPage;
