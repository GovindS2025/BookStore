import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import axios from 'axios';
import {
  Container,
  Typography,
  Paper,
  Box,
  Button,
  CircularProgress,
  Stack
} from '@mui/material';
import { styled, keyframes } from '@mui/system';

const fadeIn = keyframes`
  from {
    opacity: 0;
    transform: translateY(20px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
`;

const AnimatedPaper = styled(Paper)(({ theme }) => ({
  animation: `${fadeIn} 0.5s ease-out`,
  padding: theme.spacing(4),
  marginTop: theme.spacing(4),
  backgroundColor: '#fff8e1',
  borderRadius: theme.shape.borderRadius,
}));

const ViewBook = () => {
  const { id } = useParams();
  const navigate = useNavigate();
  const [book, setBook] = useState(null);
  const [loading, setLoading] = useState(true);

  const axiosInstance = axios.create({
    baseURL: '/api/books',
    headers: {
      Authorization: 'Basic ' + btoa('admin:password'),
    },
  });

  useEffect(() => {
    axiosInstance.get(`/${id}`)
      .then(response => {
        setBook(response.data);
        setLoading(false);
      })
      .catch(error => {
        console.error('Error fetching book:', error);
        setLoading(false);
      });
  }, [id]);

  if (loading) {
    return (
      <Container maxWidth="sm" sx={{ textAlign: 'center', marginTop: 10 }}>
        <CircularProgress color="secondary" />
      </Container>
    );
  }

  if (!book) {
    return (
      <Container maxWidth="sm">
        <Typography variant="h6" color="error">
          Book not found.
        </Typography>
      </Container>
    );
  }

  return (
    <Container maxWidth="sm">
      <AnimatedPaper elevation={3}>
        <Typography variant="h5" gutterBottom sx={{ color: '#6a1b9a' }}>
          Book Details
        </Typography>
        <Box>
          <Stack spacing={2}>
            <Typography><strong>Title:</strong> {book.title}</Typography>
            <Typography><strong>Author:</strong> {book.author}</Typography>
            <Typography><strong>ISBN:</strong> {book.isbn}</Typography>
            <Typography><strong>Publication Year:</strong> {book.publicationYear}</Typography>
            <Typography><strong>Price:</strong> ${book.price}</Typography>
            <Typography><strong>Publisher:</strong> {book.publisher}</Typography>
            <Typography><strong>Borrowed:</strong> {book.borrowed ? 'Yes' : 'No'}</Typography>
          </Stack>
        </Box>
        <Box mt={4}>
          <Button variant="outlined" onClick={() => navigate('/')}>
            Back to Library
          </Button>
        </Box>
      </AnimatedPaper>
    </Container>
  );
};

export default ViewBook;
