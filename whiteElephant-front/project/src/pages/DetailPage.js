import '../styles/DetailPage.css';

import React, { useState } from "react";
import { useParams, useNavigate} from 'react-router-dom';
import axios from 'axios';
import Cookies from "js-cookie";

const DetailPage = () => {
    const { teamId } = useParams();
    
    const navigate = useNavigate();

    const config = {
        headers: {
            'Content-Type': 'application/json',
        },
    };

    const [pageStatus, setPageStatus] = useState({
        loaded: true,
        isInTeam: true,
    });

    const [teamInfo, setTeamInfo] = useState({});

  const userId = Cookies.get('userId')

    if (pageStatus.loaded === false) {
        try {
          axios.get(
            `http://127.0.0.1:5001/users/${userId}/teams`,
            {},
            config
          ).then((res) => {
              let isInTeam = false;

              for (let i = 0; res.data.length; i++) {
                if (res.data[i].id == teamId) {
                    isInTeam = true;
                    break;
                }
              }

                setPageStatus({
                    loaded: true,
                    isInTeam: isInTeam,
                });
            })
      } catch (error) {
          if (axios.isAxiosError(error)) {
            console.error('오류가 발생했습니다.', error.message);
          }
        }
    }

    const items = Array(67).fill(null); // Assume we have 67 members.
    const itemsPerPage = 10;
    const [currentPage, setCurrentPage] = useState(1);

    const totalPages = Math.ceil(items.length / itemsPerPage);

    const currentItems = items.slice((currentPage - 1) * itemsPerPage, currentPage * itemsPerPage);

    const [formData, setFormData] = useState({
        password: ''
    });

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

    let body;

    if (!pageStatus.loaded) {
        body = (
            <p>로딩 중</p>
        );
    } else {
        if (pageStatus.isInTeam) {
            if (teamInfo.teamDto === undefined) {
              axios.get(
                `http://127.0.0.1:5001/users/${userId}/teams/${teamId}/details`,
                {},
                config
              ).then((res) => {
                  setTeamInfo(res.data);
              }

            body = (
                <>
                    <div className='title_box'>
                        <div className='team_info'>
                            <p className='team_name'>{res.data.name}</p>
                            <p className='buy_price'>({res.data.minPrice} ~ {res.data.maxPrice})</p>
                        </div>
                        <button className='join_btn'>팀 합류하기</button>
                    </div>
                    <div className='detail_info'>
                        <p className='member_num'>멤버({res.data.memberNumber}명)</p>
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
                </>
            )
        } else {
            const handleChange = (e) => {
                const { id, value } = e.target;
                setFormData({
                    ...formData,
                    [id]: value,
                });
            };

            const handleSubmit = async (e) => {
                e.preventDefault(); // Prevents page refresh

                const { password } = formData;

                try {
                    const res = await axios.post(
                        `/users/${userId}/teams/${teamId}`,
                        { password },
                        config
                    );

                      console.log('팀 가입했습니다.', res.data);

                    setPageStatus({
                        ...pageStatus,
                        isInTeam: true
                    });
                } catch (error) {
                    if (axios.isAxiosError(error)) {
                        console.error('로그인 시 오류가 발생했습니다.', error.message);
                    }
                }
            };


            body = (
                <div className="container">
                  <form className="form" onSubmit={handleSubmit}>
                    <h2 className="form-title">팀 가입</h2>
                    <div className="form-group">
                      <label htmlFor="password">비밀번호</label>
                      <input type="password" id="password" placeholder="Value" value={formData.password} onChange={handleChange} />
                    </div>
                    <button type="submit" className="button">가입</button>
                  </form>
                </div>
            );
        }
    }

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
                {body}
            </div>
        </div>
    );
};

export default DetailPage;