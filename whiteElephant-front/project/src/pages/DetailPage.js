import '../styles/DetailPage.css';

import React, { useState } from "react";
import {useParams} from 'react-router-dom';

import axios from 'axios';
import Cookies from "js-cookie";

const DetailPage = () => {
    const { teamId } = useParams();
    const config = {
        headers: {
            'Content-Type': 'application/json',
        },
    };

    const [pageStatus, setPageStatus] = useState({
        loaded: false,
        isInTeam: false,
    });

    const [teamInfo, setTeamInfo] = useState({});
    const userId = Cookies.get('userId');

    if (pageStatus.loaded === false && teamId !== undefined) {
        try {
            axios.get(`http://127.0.0.1:8080/users/${userId}/teams/${teamId}`,config)
            .then((res) => {
                let isInTeam = false;
                if (res.data.userRole !== null) {
                    isInTeam = true;
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

    const itemsPerPage = 6;
    const [currentPage, setCurrentPage] = useState(1);
    const [totalPages, setTotalPages] = useState(0);
    const [currentItems, setCurrentItems] = useState([]);
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

    const handleNavigation = (path) => {
        window.location.href = path;
      };

    const handleDeleteTeam = () => {
        axios.get(`http://127.0.0.1:8080/users/${userId}`, config)
        .then((res) => {
            if (teamInfo.teamDto.leaderName === res.data.name) {
                axios.delete(`http://127.0.0.1:8080/users/${userId}/teams/${teamId}`, config);
                handleNavigation('/main');
            } else {
                alert("리더가 아니므로 팀을 삭제 시킬 수 없습니다!")
            }
        })
    }

    const handleCompleteTeam = () => {
        axios.get(`http://127.0.0.1:8080/users/${userId}`, {}, config)
        .then((res) => {
            if (teamInfo.teamDto.leaderName === res.data.name) {
                if (teamInfo.memberDtos.length > 1){
                    axios.patch(`http://127.0.0.1:8080/users/${userId}/teams/${teamId}`, config);
                    handleNavigation('/mypage');
                } else {
                    alert("팀 멤버가 2명 이상이 아닙니다");
                }
            } else {
                alert("리더가 아니므로 팀을 완성 시킬 수 없습니다!");
            }
        })
    }

    let body;

    if (!pageStatus.loaded) {
        body = (
            <p>로딩 중</p>
        );
    } else {
        if (pageStatus.isInTeam) {
            if (teamInfo.teamDto === undefined) {
                axios.get(`http://127.0.0.1:8080/users/${userId}/teams/${teamId}/details`, {}, config)
                .then((res) => {
                    setTeamInfo(res.data);
                    setTotalPages(Math.ceil(res.data.memberDtos.length / itemsPerPage));
                    setCurrentItems(res.data.memberDtos?.slice((currentPage - 1) * itemsPerPage, currentPage * itemsPerPage));
                })
            }

            body = (
                <>
                    <div className='title_box'>
                        <div className='team_info'>
                            <p className='team_name'>{teamInfo.teamDto?.name}</p>
                            <p className='buy_price'>({teamInfo.teamDto?.minPrice} ~ {teamInfo.teamDto?.maxPrice})</p>
                        </div>
                        <div className='btn_collection'>
                            <button className='btn' onClick={handleDeleteTeam}>팀 삭제하기</button>
                            <button className='btn' onClick={handleCompleteTeam}>팀 완성하기</button>
                        </div>
                    </div>
                    <div className='detail_info'>
                        <p className='member_num'>멤버({teamInfo.teamDto?.memberNumber}명)</p>
                        <div className='member_collection'>
                            {currentItems.map((item, index) => (
                                <div className='one_member' key={index}>
                                    <div className='member_img'></div>
                                    <p className='name'>{item.userName}</p>
                                    <p className='role'>{item.userRole === "LEADER" ? "LEADER" : ""}</p>
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
                        `http://127.0.0.1:8080/users/${userId}/teams/join`,
                        { teamId: teamId, 
                            password: password },
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
                <p className='name' onClick={() => {handleNavigation('/main')}}>whiteElephant</p>
                <div className='btn_collection'>
                    <button className='mypage_btn' onClick={() => {handleNavigation('/mypage')}}>마이페이지</button>
                    <button className='logout_btn' onClick={() => {handleNavigation('/')}}>로그아웃</button>
                </div>
            </div>
            <div className='detail_body'>
                {body}
            </div>
        </div>
    );
};

export default DetailPage;