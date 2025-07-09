import React from 'react';
import { BrowserRouter as Router, Route, Routes, NavLink } from 'react-router-dom';

import BookLayout from './components/BookLayout';
import BookForm from './components/BookForm';
import ViewBook from './components/ViewBook';

import './App.css';

function App() {
  return (
    <Router>
      <div className="App">
        {/* Navigation Bar */}
        <nav className="nav">
          <NavLink to="/" end className="nav-link">
            Home
          </NavLink>
          <NavLink to="/add" className="nav-link">
            Add Book
          </NavLink>
        </nav>

        {/* Route Definitions */}
        <Routes>
          <Route path="/" element={<BookLayout />} />            {/* List + Search */}
          <Route path="/add" element={<BookForm />} />           {/* Add Book */}
          <Route path="/edit/:id" element={<BookForm />} />      {/* Edit Book */}
          <Route path="/view/:id" element={<ViewBook />} />      {/* View Book */}
        </Routes>
      </div>
    </Router>
  );
}

export default App;
