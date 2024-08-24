import React, { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import axios from 'axios';
import Cookies from "js-cookie";
import '../styles/LoginPage.css';
import '../styles/LoginForm.css';
import '../styles/FormStyles.css';

const LoginPage = () => {
    const [formData, setFormData] = useState({
        email: '',
        password: ''
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
        e.preventDefault(); // Prevents page refresh

        const { email, password } = formData;

        try {
            const res = await axios.post(

                "http://127.0.0.1:8080/users/login",

                {
                    email,
                    password
                },
                config
            );

            if (res.data.userId !== undefined) {
              console.log('로그인되었습니다.', res.data);

              axios.defaults.headers.common["Authorization"] = `Bearer ${res.data.userId}`;
              Cookies.set("userId", res.data.userId, {expires: 1});

              navigate('/main'); // Redirect to a dashboard or home page after successful login

            } else {
              alert(res.data.result);
            }
        } catch (error) {
            if (axios.isAxiosError(error)) {
                console.error('로그인 시 오류가 발생했습니다.', error.message);
            }
        }
    };

    return (
        <div className="container">
            <form className="form" onSubmit={handleSubmit}>
                <div className="form-group">
                    <label htmlFor="email">이메일</label>
                    <input type="email" id="email" placeholder="Value" value={formData.email} onChange={handleChange} />
                </div>
                <div className="form-group">
                    <label htmlFor="password">비밀번호</label>
                    <input type="password" id="password" placeholder="Value" value={formData.password} onChange={handleChange} />
                </div>
                <button type="submit" className="button">로그인</button>
                <a href="/forgot-password" className="link">비밀번호 잊으셨습니까?</a>
                <Link to="join" className="link">회원가입</Link>
            </form>
        </div>
    );
}

export default LoginPage;