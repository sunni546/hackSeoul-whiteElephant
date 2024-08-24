import '../styles/DetailPage.css';

import React, { useState } from "react";

const DetailPage = () => {
    const items = Array(67).fill(null); // Assume we have 67 members.
    const itemsPerPage = 10;
    const [currentPage, setCurrentPage] = useState(1);

    const totalPages = Math.ceil(items.length / itemsPerPage);

    const currentItems = items.slice((currentPage - 1) * itemsPerPage, currentPage * itemsPerPage);

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
                    <p className='member_num'>멤버({items.length}명)</p>
                    <div className='member_collection'>
                        {currentItems.map((_, index) => (
                            <div className='one_member' key={index}>
                                <div className='member_img'></div>
                                <p className='name'>멤버 이름{(currentPage - 1) * itemsPerPage + index}</p>
                                <p className='role'>역할 (팀장만)</p>
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
        </div>
    );
};

export default DetailPage;