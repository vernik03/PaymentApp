import React, { useState } from "react";
import axios from "axios";

function PasswordModal({ onClose, onUnlock }) {
  const [password, setPassword] = useState("");
  const [passwordText, setpasswordText] = useState("Enter password");

  const handleSubmit = (event) => {
    event.preventDefault();
    axios
      .get("http://localhost:8008/WebApp_OOP_Lab1_war_exploded/CardLockServlet", {
        params: { data: password },
      })
      .then((response) => {
        console.log(response.data);
        if (response.data === "Correct password"){
            onUnlock();
            onClose();
        }
        else{
            setpasswordText(response.data);
        }
      })
      .catch((error) => {
        console.error(error);
      });
  };

  return (
    <div className="modal">
        <p>{passwordText}</p>
      <form onSubmit={handleSubmit}>
        <label htmlFor="password">Password:</label>
        <input
          type="password"
          id="password"
          value={password}
          onChange={(event) => setPassword(event.target.value)}
        />
        <button type="submit">Unlock</button>
      </form>
      <button onClick={onClose}>Close</button>
    </div>
  );
}
export default PasswordModal;