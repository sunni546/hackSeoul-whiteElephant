import './styles/App.css';
import { BrowserRouter, Route, Routes } from "react-router-dom";
import LoginPage from "./pages/LoginPage";
import JoinPage from "./pages/JoinPage";

function App() {
    return (
        <BrowserRouter>
            <Routes>
                <Route path='/' element={<LoginPage />}/>
                <Route path='/join' element={<JoinPage />} />
            </Routes>
        </BrowserRouter>
        );
    }

export default App;