import { useNavigate } from 'react-router-dom';
import '../styles/MyPage.css'
import React, { useEffect, useState } from "react";
import axios from 'axios';
import Cookies from 'js-cookie';

const RequestModal = ({userName, receiverName, clickModal}) => {
    return (
        <div className='modal'>
            <div className='modal_box'>
                <p className='alarm'>{userName}님은 {receiverName}님께 선물을 보내면 됩니다!</p>
                <div className='middle_box'>
                    <p className='move_link'>선물을 보내기 위해 쿠팡으로 이동!</p>
                    <a className='link' href='https://coupang.com/' target='_blank' rel="noopener noreferrer">쿠팡 이동하기</a>
                </div>
                <button className='modal_btn' onClick={clickModal}>창 끄기</button>
            </div>
        </div>
    )
}

const MyPage = () => {
    const [teamList, setTeamList] = useState({
        teamCompletedDtos: [],
        teamLeaderDtos: [],
        teamMemberDtos: []
      });
    
    const config = {
        headers: {
            'Content-Type': 'application/json',
        },
    };

    const navigate = useNavigate()
    const userId = Cookies.get('userId');
    const [showModal, setShowModal] = useState(false);
    const [userName, setUserName] = useState('');
    const [receiverName, setReceiverName] = useState('');

    useEffect(() => {
        const config = {
            headers: {
                'Content-Type': 'application/json',
            },
        };

        const userId = Cookies.get('userId');
        axios.get(`http://127.0.0.1:8080/users/${userId}/teams/me`, config)
        .then((res) => {
            setTeamList(res.data);
        });
        
        axios.get(`http://127.0.0.1:8080/users/${userId}`, config)
        .then((res) => {
            setUserName(res.data.name);
        });
    }, []);

    const clickModal = () => {
        setShowModal(!showModal);
    }

    const handleReceiver = (teamId) => {
        axios.get(`http://127.0.0.1:8080/users/${userId}/teams/${teamId}/receiver`, config)
        .then((res) => {
            setReceiverName(res.data.userName);
            console.log(res);
            clickModal();
        });
    }

    return (
        <>
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
                        <p className='user_name'>{userName}</p>
                    </div>
                </div>
                <div className='team_collection'>
                    <div className='make_team'>
                        <p className='title'>내가 만든 팀 모음</p>
                        <div className='collection'>
                            {
                                teamList.teamLeaderDtos.map((team, index) => (
                                    <div key={index} className='one_team' onClick={() => {navigate(`/detail/${team.teamId}`)}}>
                                        <p className='team_name'>{team.name}</p>
                                        <p className='member_num'>{team.memberNumber}명</p>
                                    </div>
                                ))
                            }
                        </div>
                    </div>
                    <div className='join_team'>
                        <p className='title'>멤버로 속한 팀 모음</p>
                        <div className='collection'>
                            {
                                teamList.teamMemberDtos.map((team, index) => (
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
                                teamList.teamCompletedDtos.map((team, index) => (
                                    <div key={index} className='one_team' onClick={() => handleReceiver(team.teamId)}>
                                        <p className='team_name'>{team.name}</p>
                                        <p className='member_num'>{team.memberNumber}명</p>
                                    </div>
                                ))
                            }
                        </div>
                    </div>
                </div>
            </div>
            {showModal && <RequestModal userName={userName} receiverName={receiverName} clickModal={clickModal}/>}
        </>
    )
}

export default MyPage;