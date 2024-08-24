import React from 'react';
import '../styles/FormStyles.css';
import '../styles/CreateTeamPage.css';

function CreateTeamPage() {
  return (
    <div className="container">
      <form className="form">
        <h2 className="form-title">팀 만들기</h2>
        <div className="form-group">
          <label htmlFor="team-name">팀명</label>
          <input type="text" id="team-name" placeholder="Value" />
        </div>
        <div className="form-group">
          <label htmlFor="password">비밀번호</label>
          <input type="password" id="password" placeholder="Value" />
        </div>
        <div className="form-group">
          <label htmlFor="price-tier">가격대</label>
          <input type="text" id="price-tier" placeholder="Value" />
        </div>
        <button type="submit" className="button">만들기</button>
      </form>
    </div>
  );
}

export default CreateTeamPage;
