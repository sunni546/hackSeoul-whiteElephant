import React from "react";
import { Link } from "react-router-dom";

const JoinPage = () => {
	return (
		<>
			<div className="container">
	      <form className="form">
	        <h2 className="form-title">회원가입</h2>
	        <div className="form-group">
	          <label htmlFor="name">이름</label>
	          <input type="text" id="name" placeholder="Value" />
	        </div>
	        <div className="form-group">
	          <label htmlFor="email">이메일</label>
	          <input type="email" id="email" placeholder="Value" />
	        </div>
	        <div className="form-group">
	          <label htmlFor="password">비밀번호</label>
	          <input type="password" id="password" placeholder="Value" />
	        </div>
	        <div className="form-group">
	          <label htmlFor="confirm-password">비밀번호 확인</label>
	          <input type="password" id="confirm-password" placeholder="Value" />
	        </div>
	        <div className="form-group">
	          <label htmlFor="phone">전화번호</label>
	          <input type="tel" id="phone" placeholder="Value" />
	        </div>
	        <button type="submit" className="button">가입</button>
	        <Link to="/" className="link">로그인으로 돌아가기</Link>
	      </form>
	    </div>
		</>
	)
}

export default JoinPage;