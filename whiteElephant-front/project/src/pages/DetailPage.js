import '../styles/DetailPage.css'
import React from "react";

const DetailPage = () => {
    const items = Array(11).fill(null);

    return (
        <div className='detail'>
            <div className='detail_header'>
                <p className='name'>whiteElephant</p>
                <div className='btn_collection'>
                    <button className='mypage_btn'>마이페이지</button>
                    <button className='logout_btn'>로그아웃</button>
                </div>
            </div>
            <div className='detail_body'>
                <div className='title_box'>
                    <div className='team_info'>
                        <p className='team_name'>팀명</p>
                        <p className='buy_price'>(최소금액 ~ 최대금액)</p>
                    </div>
                    <button className='join_btn'>팀 합류하기</button>
                </div>
                <div className='detail_info'>
                    <p className='member_num'>멤버(n명)</p>
                    <div className='member_collection'>
                    {
                        items.map((_, index) => (
                            <div className='one_member'>
                                <div className='member_img'></div>
                                <p className='name'>멤버 이름{index}</p>
                                <p className='role'>역할 (팀장만)</p>
                            </div>
                    ))}
                    </div>
                </div>
            </div>
        </div>
    )
}

export default DetailPage;