import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { Link } from 'react-router-dom';

const BookLayout = () => {
  const [books, setBooks] = useState([]);

  // Configure axios with Basic Auth
  const axiosInstance = axios.create({
    baseURL: '/api/books',
    headers: {
      Authorization: 'Basic ' + btoa('admin:password'), // Encode username:password
    },
  });

  useEffect(() => {
    axiosInstance.get('')
      .then(response => setBooks(response.data))
      .catch(error => console.error('Error fetching books:', error));
  }, []);

  const handleDelete = (id) => {
    axiosInstance.delete(`/${id}`)
      .then(() => setBooks(books.filter(book => book.id !== id)))
      .catch(error => console.error('Error deleting book:', error));
  };

  return (
    <div>
      <h2>Book Library</h2>
      <table>
        <thead>
          <tr>
            <th>Title</th>
            <th>Author</th>
            <th>ISBN</th>
            <th>Year</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {books.map(book => (
            <tr key={book.id}>
              <td>{book.title}</td>
              <td>{book.author}</td>
              <td>{book.isbn}</td>
              <td>{book.publicationYear}</td>
              <td>
                <Link to={`/edit/${book.id}`}>Edit</Link>
                <button onClick={() => handleDelete(book.id)}>Delete</button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default BookLayout;