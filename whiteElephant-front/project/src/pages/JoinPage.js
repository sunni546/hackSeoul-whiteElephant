import React, { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import axios from 'axios';
import Cookies from 'js-cookie';

const JoinPage = () => {
		const [formData, setFormData] = useState({
				name: '',
				email: '',
				password: '',
				confirmPassword: '',
				phone: ''
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

				const { name, email, password, confirmPassword, phone } = formData;

				if (password !== confirmPassword) {
						alert("비밀번호가 일치하지 않습니다");
						return;
				}

				try {
						const res = await axios.post(
								"http://127.0.0.1:5001/users/join",
								{
										name,
										email,
										password,
										anotherPassword: confirmPassword,
										phone
								},
								config
						);
						console.log('회원가입이 완료되었습니다.', res.data);
						navigate('/'); // 로그인 페이지
				} catch (error) {
						if (axios.isAxiosError(error)) {
								console.error('회원가입 시 오류가 발생했습니다.', error.message);
						}
				}
		};

	return (
        <div className="container">
            <form className="form" onSubmit={handleSubmit}>
                <h2 className="form-title">회원가입</h2>
                <div className="form-group">
                    <label htmlFor="name">이름</label>
                    <input type="text" id="name" placeholder="Value" value={formData.name} onChange={handleChange} />
                </div>
                <div className="form-group">
                    <label htmlFor="email">이메일</label>
                    <input type="email" id="email" placeholder="Value" value={formData.email} onChange={handleChange} />
                </div>
                <div className="form-group">
                    <label htmlFor="password">비밀번호</label>
                    <input type="password" id="password" placeholder="Value" value={formData.password} onChange={handleChange} />
                </div>
                <div className="form-group">
                    <label htmlFor="confirm-password">비밀번호 확인</label>
                    <input type="password" id="confirmPassword" placeholder="Value" value={formData.confirmPassword} onChange={handleChange} />
                </div>
                <div className="form-group">
                    <label htmlFor="phone">전화번호</label>
                    <input type="tel" id="phone" placeholder="Value" value={formData.phone} onChange={handleChange} />
                </div>
                <button type="submit" className="button">가입</button>
                <Link to="/" className="link">로그인으로 돌아가기</Link>
            </form>
        </div>
    );
}

export default JoinPage;