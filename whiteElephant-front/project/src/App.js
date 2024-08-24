import './styles/App.css';
import { BrowserRouter, Route, Routes } from "react-router-dom";
import LoginPage from "./pages/LoginPage";
import JoinPage from "./pages/JoinPage";
import CreateTeamPage from "./pages/CreateTeamPage";

function App() {
    return (
        <BrowserRouter>
            <Routes>
                <Route path='/' element={<LoginPage />}/>
                <Route path='/join' element={<JoinPage />} />
                <Route path='/create-team' element={<CreateTeamPage />} />
            </Routes>
        </BrowserRouter>
        );
    }

export default App;