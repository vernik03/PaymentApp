import React, { useState, useEffect } from 'react';
import MyForm from './Form';
import PasswordModal from './Modal';
import axios from 'axios';

function LockButton({ lockStatusText, lockStatusChange }) {
  return <button onClick={lockStatusChange}>{lockStatusText}</button>;
}

function Cards() {

  const [CardIdValue, setCardIdValue] = useState('1');  
  const [CardNumberValue, setCardNumberValue] = useState('');
  const [MoneyData, setMoneyData] = useState('');
  const [IsBlocked, setIsBlocked] = useState(false);
  const [isModalOpen, setIsModalOpen] = useState(false);

  const fetchData = (value = CardIdValue) => {  
    axios.get('http://localhost:8008/WebApp_OOP_Lab1_war_exploded/MainServlet', { 
      params: { data: value } 
    })
    .then((response) => {
      console.log(response.data.cardNumber);
      console.log(response.data.balance);
      setCardNumberValue(cardNumberFormat(response.data.cardNumber));
      setMoneyData(response.data.balance);
      setIsBlocked(response.data.isBlocked);
    })
    .catch((error) => {
      console.error(error);
    });
  };

  const cardNumberFormat = (cardNumber) => {
    for (let i = 0; i < cardNumber.length; i++) {
      if (i % 5 === 0 && i !== 0) {
        cardNumber = cardNumber.slice(0, i-1) + ' ' + cardNumber.slice(i-1);
      }
    }
    return cardNumber;
  };


  const handleInputChange = (event) => {
    const { value } = event.target;
    setCardIdValue(value);
    fetchData(value);
  };

  const handleResponse = (responseData) => {
    console.log(responseData + ' from Cards');
    fetchData();
  };

  useEffect(() => {
    fetchData();
  });

  const lockStatusChange = () => {  
    if (IsBlocked) {
      setIsModalOpen(true);
    }
    axios.post('http://localhost:8008/WebApp_OOP_Lab1_war_exploded/CardLockServlet', 
      {  data: CardIdValue   }
    )
      .then((response) => {
        console.log(response.data);
        if (response.data === 'Unblocked successfully') {
          if (isModalOpen) {
            setIsModalOpen(false);
          }
        }
        fetchData();
      })
      .catch((error) => {
        console.error(error);
      });
  };

  const handleUnlock = () => {
    setIsBlocked(false);
  };
  
  const lockStatusText = IsBlocked ? 'Unlock' : 'Lock';

  return (
    <div>
        <input type="radio" id="cardChoice1" name="creditcard" value="1" checked={CardIdValue === '1'} onChange={handleInputChange} />
        <label htmlFor="cardChoice1">Card1</label>
        <input type="radio" id="cardChoice2" name="creditcard" value="2" checked={CardIdValue === '2'} onChange={handleInputChange} />
        <label htmlFor="cardChoice2">Card2</label>
        <input type="radio" id="cardChoice3" name="creditcard" value="3" checked={CardIdValue === '3'} onChange={handleInputChange} />
        <label htmlFor="cardChoice3">Card3</label>
        <LockButton lockStatusText={lockStatusText} lockStatusChange={lockStatusChange} />
        {isModalOpen && (
          <PasswordModal onClose={() => setIsModalOpen(false)} onUnlock={handleUnlock} />
        )}
        <p>Selected card: {CardNumberValue}</p>
        <p>{MoneyData} гривень</p>
        <MyForm onResponse={handleResponse} /> 
      </div>
  );
}

export default Cards;