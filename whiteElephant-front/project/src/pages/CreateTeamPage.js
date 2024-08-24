import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import Cookies from 'js-cookie';
import '../styles/FormStyles.css';
import '../styles/CreateTeamPage.css';

function CreateTeamPage() {
  const [formData, setFormData] = useState({
    name: '',
    password: '',
    minPrice: '',
    maxPrice: ''
  });

  const navigate = useNavigate();

  const config = {
    headers: {
      'Content-Type': 'application/json',
    },
  };

  const handleChange = (e) => {
    const { id, value } = e.target;
    setFormData({
      ...formData,
      [id]: value,
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    const { name, password, minPrice, maxPrice } = formData;

    try {
      const userId = Cookies.get('userId')

      const res = await axios.post(
        `http://127.0.0.1:5001/users/${userId}/teams`,
        {
          name,
          password,
          minPrice,
          maxPrice
        },
        config
      );

      console.log('팀이 생성되었습니다.', res.data);

      const teamId = res.data.teamId;

      navigate(`/detail/${teamId}`);
    } catch (error) {
      if (axios.isAxiosError(error)) {
        console.error('오류가 발생했습니다.', error.message);
      }
    }
  };

  return (
    <div className="container">
      <form className="form" onSubmit={handleSubmit}>
        <h2 className="form-title">팀 만들기</h2>
        <div className="form-group">
          <label htmlFor="name">팀명</label>
          <input 
            type="text" 
            id="name" 
            placeholder="Value" 
            value={formData.name} 
            onChange={handleChange} 
          />
        </div>
        <div className="form-group">
          <label htmlFor="password">비밀번호</label>
          <input 
            type="password" 
            id="password" 
            placeholder="Value" 
            value={formData.password} 
            onChange={handleChange} 
          />
        </div>
        <div className="form-group">
          <label htmlFor="minPrice">최소 가격</label>
          <input 
            type="text" 
            id="minPrice" 
            placeholder="Value" 
            value={formData.minPrice} 
            onChange={handleChange} 
          />
        </div>
        <div className="form-group">
          <label htmlFor="maxPrice">최대 가격</label>
          <input 
            type="text" 
            id="maxPrice" 
            placeholder="Value" 
            value={formData.maxPrice} 
            onChange={handleChange} 
          />
        </div>
        <button type="submit" className="button">만들기</button>
      </form>
    </div>
  );
}

export default CreateTeamPage;
