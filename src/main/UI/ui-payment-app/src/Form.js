import React, { useState } from 'react';
import axios from 'axios';

function MyForm(props) {
  const [inputValue, setInputValue] = useState('');
  const [mode, setMode] = useState('pay');
  const [responseData, setResponseData] = useState('');
  
  const { onResponse } = props;

  const handleSubmit = (event) => {
    event.preventDefault();
    const value = (mode === 'pay') ? -inputValue : inputValue;
    axios.post('http://localhost:8008/WebApp_OOP_Lab1_war_exploded/MainServlet', { data: value })
      .then((response) => {
        console.log(response.data); 
        setResponseData(response.data); 
        // onMoneyDataChange(response.data.balance);
        // onResponse(response.data);
        onResponse(response.data);
      })
      .catch((error) => {
        console.error(error); // handle the error if the request fails
      });
  };

  const handleInputChange = (event) => {
    if (!event.target.value.match(/[^0-9]/g)) {
      setInputValue(event.target.value);
    }
  };

  const handleModeChange = (event) => {
    setMode(event.target.value);
  };

  return (
    <div>
      <form onSubmit={handleSubmit}>
        <label>
          Input value:
          <input type="text" value={inputValue} onChange={handleInputChange} />
        </label>
        <label>
          <input type="radio" name="mode" value="pay" checked={mode === 'pay'} onChange={handleModeChange} />
          Pay
        </label>
        <label>
          <input type="radio" name="mode" value="receive" checked={mode === 'receive'} onChange={handleModeChange} />
          Receive
        </label>
        <button type="submit">{mode === 'pay' ? 'Pay' : 'Receive'}</button>
        <p>{responseData}</p>
      </form>
      
    </div>
  );
}

export default MyForm;
