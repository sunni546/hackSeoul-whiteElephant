import { useNavigate } from 'react-router-dom';
import '../styles/MyPage.css'
import React from "react";

const MyPage = () => {
    const navigate = useNavigate()
    return (
        <div className='mypage'>
            <div className='mypage_header'>
                <p className='name' onClick={() => {navigate('/main')}}>whiteElephant</p>
                <div className='btn_collection'>
                    <button className='mypage_btn' onClick={() => {navigate('/mypage')}}>마이페이지</button>
                    <button className='logout_btn' onClick={() => {navigate('/')}}>로그아웃</button>
                </div>
            </div>
            <div className='mypage_body'>
                <div className='user_info'>
                    <div className='img'>이미지</div>
                    <p className='user_name'>유저 이름</p>
                </div>
            </div>
            <div className='team_collection'>
                <p className='title'>내가 만든 팀 모음</p>
                <p className='title'>내가 속한 팀 모음</p>
            </div>
        </div>
    )
}

export default MyPage;