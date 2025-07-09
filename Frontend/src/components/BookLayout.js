import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { Link, useNavigate } from 'react-router-dom';

const BookLayout = () => {
  const [books, setBooks] = useState([]);
  const [searchQuery, setSearchQuery] = useState('');
  const navigate = useNavigate();

  // Axios instance with Basic Auth
  const axiosInstance = axios.create({
    baseURL: '/api/books',
    headers: {
      Authorization: 'Basic ' + btoa('admin:password'),
    },
  });

  // Fetch books
  useEffect(() => {
    fetchBooks();
  }, []);

  const fetchBooks = () => {
    axiosInstance.get('')
      .then(response => setBooks(response.data))
      .catch(error => console.error('Error fetching books:', error));
  };

  // Delete a book
  const handleDelete = (id) => {
    axiosInstance.delete(`/${id}`)
      .then(() => {
        setBooks(books.filter(book => book.id !== id));
      })
      .catch(error => console.error('Error deleting book:', error));
  };

  // Search filter logic
  const filteredBooks = books.filter(book =>
    book.title.toLowerCase().includes(searchQuery.toLowerCase()) ||
    book.author.toLowerCase().includes(searchQuery.toLowerCase()) ||
    book.isbn.toLowerCase().includes(searchQuery.toLowerCase())
  );

  return (
    <div>
      <h2>Book Library</h2>

      {/* Search Bar */}
      <div style={{ marginBottom: '1rem' }}>
        <input
          type="text"
          placeholder="Search by title, author or ISBN"
          value={searchQuery}
          onChange={(e) => setSearchQuery(e.target.value)}
          style={{ padding: '0.5rem', width: '250px' }}
        />
        <button
          onClick={() => navigate('/add')}
          style={{ marginLeft: '1rem', padding: '0.5rem 1rem' }}
        >
          Add New Book
        </button>
      </div>

      {/* Book Table */}
      <table border="1" cellPadding="8" cellSpacing="0">
        <thead>
          <tr>
            <th>Title</th>
            <th>Author</th>
            <th>ISBN</th>
            <th>Year</th>
            <th>Price</th>
            <th>Publisher</th>
            <th>Borrowed</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {filteredBooks.length > 0 ? (
            filteredBooks.map(book => (
              <tr key={book.id}>
                <td>{book.title}</td>
                <td>{book.author}</td>
                <td>{book.isbn}</td>
                <td>{book.publicationYear}</td>
                <td>{book.price}</td>
                <td>{book.publisher}</td>
                <td>{book.borrowed ? 'Yes' : 'No'}</td>
                <td>
                  <Link to={`/view/${book.id}`} style={{ marginRight: '8px' }}>Read</Link>
                  <Link to={`/edit/${book.id}`} style={{ marginRight: '8px' }}>Update</Link>
                  <button onClick={() => handleDelete(book.id)}>Delete</button>
                </td>
              </tr>
            ))
          ) : (
            <tr>
              <td colSpan="8">No books found.</td>
            </tr>
          )}
        </tbody>
      </table>
    </div>
  );
};

export default BookLayout;
