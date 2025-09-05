import React, { useState } from 'react';
import { Box, Typography, Container, Paper, TextField, Button, Grid, CircularProgress, Alert } from '@mui/material';
import { useTheme } from '@mui/material/styles';
import axios from 'axios';

const AddBookPage = () => {
    const theme = useTheme();
    const [bookData, setBookData] = useState({
        title: '',
        description: '',
        isbn: '',
        publicationYear: '',
        genre: '',
        price: '',
        quantity: '',
        publisher: '',
        authors: ''
    });
    const [selectedFile, setSelectedFile] = useState(null);
    const [previewUrl, setPreviewUrl] = useState(null);
    const [status, setStatus] = useState('');
    const [loading, setLoading] = useState(false);

    const handleInputChange = (e) => {
        const { name, value } = e.target;
        setBookData({ ...bookData, [name]: value });
    };

    const handleFileChange = (e) => {
        const file = e.target.files[0];
        setSelectedFile(file);
        if (file) {
            setPreviewUrl(URL.createObjectURL(file));
        } else {
            setPreviewUrl(null);
        }
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        setLoading(true);
        setStatus('');

        if (!selectedFile) {
            setStatus('error: Please select an image file.');
            setLoading(false);
            return;
        }

        const formData = new FormData();
        formData.append('imageFile', selectedFile); // The key 'imageFile' must match your DTO field
        formData.append('title', bookData.title);
        formData.append('description', bookData.description);
        formData.append('isbn', bookData.isbn);
        formData.append('publicationYear', bookData.publicationYear);
        formData.append('genre', bookData.genre);
        formData.append('price', bookData.price);
        formData.append('quantity', bookData.quantity);
        formData.append('publisher', bookData.publisher);
        formData.append('authors', bookData.authors);

        try {
            // Updated endpoint to match the backend controller
            await axios.post('http://localhost:8080/api/books', formData, {
                headers: {
                    // No need to set Content-Type; axios handles it for FormData
                },
            });
            setStatus('success: Book added successfully!');
            setBookData({
                title: '', description: '', isbn: '', publicationYear: '', genre: '', price: '', quantity: '', publisher: '', authors: ''
            });
            setSelectedFile(null);
            setPreviewUrl(null);
        } catch (error) {
            console.error('Upload error:', error);
            setStatus(`error: Failed to add book. ${error.response?.data?.message || error.message}`);
        } finally {
            setLoading(false);
        }
    };

    return (
        <Container maxWidth="md" sx={{ mt: 5 }}>
            <Paper elevation={5} sx={{ p: { xs: 3, md: 6 }, borderRadius: 2, backgroundColor: theme.palette.background.paper }}>
                <Typography variant="h3" gutterBottom sx={{ fontWeight: 'bold', textAlign: 'center' }}>
                    Add New Book
                </Typography>
                <Box component="form" onSubmit={handleSubmit} sx={{ mt: 4 }}>
                    <Grid container spacing={2}>
                        <Grid item xs={12} sm={6}><TextField fullWidth margin="normal" label="Book Title" name="title" value={bookData.title} onChange={handleInputChange} required /></Grid>
                        <Grid item xs={12} sm={6}><TextField fullWidth margin="normal" label="Description" name="description" value={bookData.description} onChange={handleInputChange} required /></Grid>
                        <Grid item xs={12} sm={6}><TextField fullWidth margin="normal" label="ISBN" name="isbn" value={bookData.isbn} onChange={handleInputChange} required /></Grid>
                        <Grid item xs={12} sm={6}><TextField fullWidth margin="normal" label="Publication Year" name="publicationYear" type="number" value={bookData.publicationYear} onChange={handleInputChange} required /></Grid>
                        <Grid item xs={12} sm={6}><TextField fullWidth margin="normal" label="Genre" name="genre" value={bookData.genre} onChange={handleInputChange} required /></Grid>
                        <Grid item xs={12} sm={6}><TextField fullWidth margin="normal" label="Price" name="price" type="number" value={bookData.price} onChange={handleInputChange} required /></Grid>
                        <Grid item xs={12} sm={6}><TextField fullWidth margin="normal" label="Quantity" name="quantity" type="number" value={bookData.quantity} onChange={handleInputChange} required /></Grid>
                        <Grid item xs={12} sm={6}><TextField fullWidth margin="normal" label="Publisher" name="publisher" value={bookData.publisher} onChange={handleInputChange} required /></Grid>
                        <Grid item xs={12} sm={6}><TextField fullWidth margin="normal" label="Authors" name="authors" value={bookData.authors} onChange={handleInputChange} required /></Grid>
                    </Grid>
                    <Box sx={{ mt: 3, mb: 3 }}>
                        <Typography variant="subtitle1" gutterBottom>Book Cover Image</Typography>
                        <Button variant="contained" component="label">Select Image<input type="file" hidden onChange={handleFileChange} accept="image/*" /></Button>
                        {selectedFile && <Typography variant="body2" sx={{ ml: 2, display: 'inline' }}>{selectedFile.name}</Typography>}
                        {previewUrl && (<Box sx={{ mt: 2, textAlign: 'center' }}><img src={previewUrl} alt="Preview" style={{ maxWidth: '100%', maxHeight: '300px', objectFit: 'contain' }} /></Box>)}
                    </Box>
                    <Button type="submit" fullWidth variant="contained" sx={{ mt: 3, mb: 2 }} disabled={loading}>
                        {loading ? <CircularProgress size={24} color="inherit" /> : 'Add Book'}
                    </Button>
                    {status && (
                        <Alert severity={status.split(':')[0]} sx={{ mt: 2 }}>
                            {status.split(':')[1]}
                        </Alert>
                    )}
                </Box>
            </Paper>
        </Container>
    );
};

export default AddBookPage;