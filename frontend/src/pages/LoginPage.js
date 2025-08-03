import React, { useState } from "react";
import api from "../api";
import { useNavigate } from "react-router-dom";

function LoginPage() {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const navigate = useNavigate();

  const handleLogin = async () => {
    try {
      const res = await api.post("/api/login", { username, password });
      localStorage.setItem("dealerId", res.data.dealerId);
      navigate("/quote");
    } catch (err) {
      alert("Login failed");
    }
  };

  return (
    <div style={styles.container}>
      <h1 style={styles.title}>🌞 Dealer Portal 🔋 </h1>
      <div style={styles.form}>
        <input
          placeholder="Username"
          style={styles.input}
          onChange={(e) => setUsername(e.target.value)}
        />
        <input
          placeholder="Password"
          type="password"
          style={styles.input}
          onChange={(e) => setPassword(e.target.value)}
        />
        <button style={styles.button} onClick={handleLogin}>
          Login
        </button>
      </div>
    </div>
  );
}

const styles = {
  container: {
    textAlign: "center",
    marginTop: "50px",
    fontFamily: "Arial, sans-serif",
  },
  title: {
    fontSize: "2rem",
    marginBottom: "30px",
  },
  form: {
    display: "inline-block",
    textAlign: "left",
  },
  input: {
    display: "block",
    width: "250px",
    padding: "10px",
    margin: "10px 0",
    fontSize: "1rem",
  },
  button: {
    width: "100%",
    padding: "10px",
    backgroundColor: "#007bff",
    color: "white",
    fontSize: "1rem",
    border: "none",
    cursor: "pointer",
  },
};

export default LoginPage;
