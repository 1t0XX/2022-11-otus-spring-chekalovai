import './css/App.css'
import Main from './components/Main'
import BookList from './components/BookList'
import BookEdit from './components/BookEdit'
import AuthorList from './components/AuthorList'
import AuthorEdit from './components/AuthorEdit'
import GenreList from './components/GenreList'
import GenreEdit from './components/GenreEdit'

import { BrowserRouter as Router, Routes, Route } from 'react-router-dom'

import Header from './components/header/Header'

function App() {
    return (
        <Router>
            <div>
                <Header />

                <Routes>
                    <Route path="/" element={<Main />}></Route>
                    <Route path="/book" element={<BookList />} />
                    <Route path="/book/:id" element={<BookEdit />} />
                    <Route path="/author" element={<AuthorList />} />
                    <Route path="/author/:id" element={<AuthorEdit />} />
                    <Route path="/genre" element={<GenreList />} />
                    <Route path="/genre/:id" element={<GenreEdit />} />
                </Routes>
            </div>
        </Router>
    )
}

export default App
