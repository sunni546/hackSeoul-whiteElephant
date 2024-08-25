import { useNavigate } from 'react-router-dom';
import '../styles/MyPage.css'
import React, { useEffect, useState } from "react";
import axios from 'axios';
import Cookies from 'js-cookie';

const MyPage = () => {
    const [teamList, setTeamList] = useState([]);

    useEffect(() => {
        const config = {
            headers: {
                'Content-Type': 'application/json',
            },
        };

        const userId = Cookies.get('userId');
        axios.get(`http://127.0.0.1:8080/users/${userId}/teams`, config)
        .then((res) => {
            setTeamList(res.data);
        })
    });
    
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
                <div className='make_team'>
                    <p className='title'>내가 만든 팀 모음</p>
                    <div className='collection'>
                        {
                            teamList.filter((team) => team.userRole === 'LEADER').map((team, index) => (
                                <div key={index} className='one_team' onClick={() => {navigate(`/detail/${team.teamId}`)}}>
                                    <p className='team_name'>{team.name}</p>
                                    <p className='member_num'>{team.memberNumber}명</p>
                                </div>
                            ))
                        }
                    </div>
                </div>
                <div className='join_team'>
                    <p className='title'>내가 속한 팀 모음</p>
                    <div className='collection'>
                        {
                            teamList.filter((team) => team.userRole !== null).map((team, index) => (
                                <div key={index} className='one_team' onClick={() => {navigate(`/detail/${team.teamId}`)}}>
                                    <p className='team_name'>{team.name}</p>
                                    <p className='member_num'>{team.memberNumber}명</p>
                                </div>
                            ))
                        }
                    </div>
                </div>
                <div className='finish_team'>
                    <p className='title'>모집 완료 팀 모음</p>
                    <div className='colleciton'>
                        {
                            teamList.filter((team) => team.status !== 'ACTIVE').map((team, index) => (
                                <div key={index} className='one_team' onClick={() => {navigate(`/detail/${team.teamId}`)}}>
                                    <p className='team_name'>{team.name}</p>
                                    <p className='member_num'>{team.memberNumber}명</p>
                                </div>
                            ))
                        }
                    </div>
                </div>
            </div>
        </div>
    )
}

export default MyPage;