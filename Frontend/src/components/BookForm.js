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
  Stack,
  FormControlLabel,
  Checkbox,
} from '@mui/material';
import { styled, keyframes } from '@mui/system';

const fadeIn = keyframes`
  from {
    opacity: 0;
    transform: translateY(30px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
`;

const AnimatedPaper = styled(Paper)(({ theme }) => ({
  animation: `${fadeIn} 0.8s ease-out`,
  padding: theme.spacing(4),
  marginTop: theme.spacing(4),
  backgroundColor: '#f3f4ff',
  borderRadius: theme.shape.borderRadius,
}));

const BookForm = () => {
  const { id } = useParams();
  const navigate = useNavigate();
  const [book, setBook] = useState({
    title: '',
    author: '',
    isbn: '',
    publicationYear: '',
    price: '',
    publisher: '',
    borrowed: false,
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
    const { name, value, type, checked } = e.target;
    setBook(prev => ({
      ...prev,
      [name]: type === 'checkbox' ? checked : value,
    }));
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    const request = id
      ? axiosInstance.put(`/${id}`, book)
      : axiosInstance.post('/save', book);

    request
      .then(() => navigate('/'))
      .catch(error => console.error('Error saving book:', error));
  };

  return (
    <Container maxWidth="sm">
      <AnimatedPaper elevation={3}>
        <Typography variant="h5" gutterBottom sx={{ color: '#4a148c' }}>
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
              color="secondary"
            />
            <TextField
              label="Author"
              name="author"
              value={book.author}
              onChange={handleChange}
              required
              fullWidth
              color="secondary"
            />
            <TextField
              label="ISBN"
              name="isbn"
              value={book.isbn}
              onChange={handleChange}
              required
              fullWidth
              color="secondary"
            />
            <TextField
              label="Publication Year"
              name="publicationYear"
              type="number"
              value={book.publicationYear}
              onChange={handleChange}
              required
              fullWidth
              color="secondary"
            />
            <TextField
              label="Price"
              name="price"
              type="number"
              value={book.price}
              onChange={handleChange}
              required
              fullWidth
              color="secondary"
            />
            <TextField
              label="Publisher"
              name="publisher"
              value={book.publisher}
              onChange={handleChange}
              required
              fullWidth
              color="secondary"
            />
            <FormControlLabel
              control={
                <Checkbox
                  checked={book.borrowed}
                  onChange={handleChange}
                  name="borrowed"
                  color="secondary"
                />
              }
              label="Borrowed"
            />
            <Button
              variant="contained"
              color="secondary"
              type="submit"
              sx={{ fontWeight: 'bold' }}
            >
              Save
            </Button>
          </Stack>
        </Box>
      </AnimatedPaper>
    </Container>
  );
};

export default BookForm;
