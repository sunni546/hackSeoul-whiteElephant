import './styles/App.css';
import { BrowserRouter, Route, Routes } from "react-router-dom";
import LoginPage from "./pages/LoginPage";
import JoinPage from "./pages/JoinPage";
import MainPage from './pages/MainPage';
import MyPage from './pages/MyPage';
import DetailPage from './pages/DetailPage';
import CreateTeamPage from "./pages/CreateTeamPage";

function App() {
    return (
        <BrowserRouter>
            <Routes>
                <Route path='/' element={<LoginPage />} />
                <Route path='/join' element={<JoinPage />} />
                <Route path='/main' element={<MainPage />} />
                <Route path='/mypage' element={<MyPage />} />
                <Route path='/detail' element={<DetailPage />} />
                <Route path='/create-team' element={<CreateTeamPage />} />
            </Routes>
        </BrowserRouter>
        );
    }

export default App;