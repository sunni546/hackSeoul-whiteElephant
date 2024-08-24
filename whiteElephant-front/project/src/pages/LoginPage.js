import React from "react";
import { Link } from "react-router-dom";
import '../styles/LoginForm.css';
import '../styles/FormStyles.css';

const LoginPage = () => {
  return (
    <>
      <div className="container">
        <form className="form">
          <div className="form-group">
            <label htmlFor="email">이메일</label>
            <input type="email" id="email" placeholder="Value" />
          </div>
          <div className="form-group">
            <label htmlFor="password">비밀번호</label>
            <input type="password" id="password" placeholder="Value" />
          </div>
          <button type="submit" className="button">로그인</button>
          <a href="/forgot-password" className="link">비밀번호 잊으셨습니까?</a>
          <Link to="join" className="link">회원가입</Link>
        </form>
      </div>
    </>
  )
}

export default LoginPage;
