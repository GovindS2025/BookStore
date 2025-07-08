import React from 'react';
import { BrowserRouter as Router, Route, Routes, Link } from 'react-router-dom';
import BookLayout from './components/BookLayout';
import BookForm from './components/BookForm';
import './App.css';

function App() {
  return (
    <Router>
      <div className="App">
        <nav>
          <Link to="/">Home</Link> | <Link to="/add">Add Book</Link>
        </nav>
        <Routes>
          <Route path="/" element={<BookLayout />} />
          <Route path="/add" element={<BookForm />} />
          <Route path="/edit/:id" element={<BookForm />} />
        </Routes>
      </div>
    </Router>
  );
}

export default App;