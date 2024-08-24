import { useNavigate } from 'react-router-dom';
import '../styles/MainPage.css'
import React, { useEffect, useState } from "react";
import Cookies from "js-cookie";
import axios from 'axios';

const MainPage = () => {
    const navigate = useNavigate();
    const [items, setItems] = useState([]); // 팀 정보
    const itemsPerPage = 6;
    const [currentPage, setCurrentPage] = useState(1);
    const totalPages = Math.ceil(items.length / itemsPerPage);
    const currentItems = items.slice((currentPage - 1) * itemsPerPage, currentPage * itemsPerPage);
    useEffect(() => {
        const config = {
            headers: {
                'Content-Type': 'application/json',
            },
        };
        const userId = Cookies.get('userId');

        axios.get(`http://127.0.0.1:8080/users/${userId}/teams`, config)
        .then((res) => {
            setItems(res.data);
        })
    }, []);

    const goToPage = (page) => {
        setCurrentPage(page);
    };

    const renderPagination = () => {
        let pages = [];

        for (let i = 1; i <= totalPages; i++) {
            pages.push(
                <button
                    key={i}
                    onClick={() => goToPage(i)}
                    className={`pagination__link ${i === currentPage ? 'pagination__link--active' : ''}`}
                >
                    {i}
                </button>
            );
        }
        return pages;
    };

    return (
        <div className='main'>
            <div className='main_header'>
                <p className='name' onClick={() => {navigate('/main')}}>whiteElephant</p>
                <div className='btn_collection'>
                    <button className='mypage_btn' onClick={() => {navigate('/mypage')}}>마이페이지</button>
                    <button className='logout_btn' onClick={() => {navigate('/')}}>로그아웃</button>
                </div>
            </div>
            <div className='main_body'>
                <div className='title_box'>
                    <p className='title'>목록</p>
                    <button className='make_team' onClick={() => {navigate('/create-team')}}>팀 생성</button>
                </div>
                <div className='team_collection'>
                    {
                        currentItems.map((item, index) => (
                            <div key={index} className='one_team' onClick={() => {navigate(`/detail/${item.teamId}`)}}>
                                <div className='team_info'>
                                    <p className='team_name'>{item.name}</p>
                                    <p className='person'>참가 인원 : {item.memberNumber}명</p>
                                </div>
                                <div className='team_leader'>
                                    <p>{item.leaderName}</p>
                                </div>
                            </div>
                        ))}
                </div>
                <div className="pagination">
                    <button
                        onClick={() => goToPage(currentPage - 1)}
                        className={`pagination__link ${currentPage === 1 ? 'pagination__link--disabled' : ''}`}
                        disabled={currentPage === 1}
                    >
                        ←
                    </button>
                    {renderPagination()}
                    <button
                        onClick={() => goToPage(currentPage + 1)}
                        className={`pagination__link ${currentPage === totalPages ? 'pagination__link--disabled' : ''}`}
                        disabled={currentPage === totalPages}
                    >
                        →
                    </button>
                </div>
            </div>
        </div>
    )
}

export default MainPage;