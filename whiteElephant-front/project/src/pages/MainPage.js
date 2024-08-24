import '../styles/MainPage.css'
import React from "react";

const MainPage = () => {
    const items = Array(6).fill(null);

    return (
        <div className='main'>
            <div className='main_header'>
                <p className='name'>whiteElephant</p>
                <div className='btn_collection'>
                    <button className='mypage_btn'>마이페이지</button>
                    <button className='logout_btn'>로그아웃</button>
                </div>
            </div>
            <div className='main_body'>
                <div className='title_box'>
                    <p className='title'>목록</p>
                </div>
                <div className='team_collection'>
                    {
                        items.map((_, index) => (
                            <div className='one_team'>
                                <div className='team_info'>
                                    <p className='team_name'>팀명</p>
                                    <p className='person'>참가 인원 : n명</p>
                                </div>
                                <div className='team_leader'>
                                    <p>팀장 이름</p>
                                </div>
                            </div>
                        ))}
                </div>
            </div>
        </div>
    )
}

export default MainPage;