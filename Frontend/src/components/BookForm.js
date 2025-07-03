import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import axios from 'axios';
import {
  Container,
  Typography,
  TextField,
  Button,
  Box,
  Paper,
  Stack
} from '@mui/material';

const BookForm = () => {
  const { id } = useParams();
  const navigate = useNavigate();
  const [book, setBook] = useState({
    title: '',
    author: '',
    isbn: '',
    publicationYear: ''
  });

  const axiosInstance = axios.create({
    baseURL: '/api/books',
    headers: {
      Authorization: 'Basic ' + btoa('admin:password'),
    },
  });

  useEffect(() => {
    if (id) {
      axiosInstance.get(`/${id}`)
        .then(response => setBook(response.data))
        .catch(error => console.error('Error fetching book:', error));
    }
  }, [id]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setBook({ ...book, [name]: value });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    const request = id
      ? axiosInstance.put(`/${id}`, book)
      : axiosInstance.post('/', book);

    request
      .then(() => navigate('/'))
      .catch(error => console.error('Error saving book:', error));
  };

  return (
    <Container maxWidth="sm">
      <Paper elevation={3} sx={{ padding: 4, marginTop: 4 }}>
        <Typography variant="h5" gutterBottom>
          {id ? 'Edit Book' : 'Add Book'}
        </Typography>
        <Box component="form" onSubmit={handleSubmit} noValidate>
          <Stack spacing={3}>
            <TextField
              label="Title"
              name="title"
              value={book.title}
              onChange={handleChange}
              required
              fullWidth
            />
            <TextField
              label="Author"
              name="author"
              value={book.author}
              onChange={handleChange}
              required
              fullWidth
            />
            <TextField
              label="ISBN"
              name="isbn"
              value={book.isbn}
              onChange={handleChange}
              required
              fullWidth
            />
            <TextField
              label="Publication Year"
              name="publicationYear"
              type="number"
              value={book.publicationYear}
              onChange={handleChange}
              required
              fullWidth
            />
            <Button variant="contained" color="primary" type="submit">
              Save
            </Button>
          </Stack>
        </Box>
      </Paper>
    </Container>
  );
};

export default BookForm;
