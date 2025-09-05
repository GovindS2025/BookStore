# 🖼️ Local Images Setup Guide

This guide explains how to use local book images from your `C:\Users\govin\Downloads\Java Books` directory instead of AWS S3.

## 🚀 Quick Setup

### 1. Prepare Your Images
1. Copy your book cover images to: `C:\Users\govin\Downloads\Java Books`
2. Use descriptive filenames that match book titles (e.g., `Effective Java.jpg`, `Spring Boot in Action.png`)
3. Supported formats: `.jpg`, `.jpeg`, `.png`, `.gif`, `.bmp`, `.webp`

### 2. Run the Setup Script
```bash
node setup-local-images.js
```
This script will:
- Scan your images directory
- Create a mapping between book titles and image files
- Generate sample book data
- Provide setup instructions

### 3. Start Your Application
```bash
# Start the Spring Boot application
mvn spring-boot:run

# Or with Docker
docker-compose up -d
```

### 4. Assign Images to Books
Use the admin endpoint to automatically assign local images to books:
```bash
POST http://localhost:8080/api/admin/book-images/assign-local-images
```

## 🔧 Configuration

### Application Configuration
The local images feature is configured in `application.yml`:

```yaml
app:
  local-images:
    directory: C:\Users\govin\Downloads\Java Books
    url-prefix: /api/images/local/
    enabled: true
```

### Docker Configuration
For Docker deployment, update `application-docker.yml`:

```yaml
app:
  local-images:
    directory: /app/images
    url-prefix: /api/images/local/
    enabled: true
```

## 📡 API Endpoints

### Public Endpoints
- `GET /api/images/local/{filename}` - Serve a local image
- `GET /api/images/local/list` - List all available images
- `GET /api/images/local/find?title={bookTitle}` - Find matching image for a book title

### Admin Endpoints (Requires ADMIN role)
- `GET /api/admin/book-images/local-images` - Get available local images
- `POST /api/admin/book-images/assign-local-images` - Auto-assign images to books
- `GET /api/admin/book-images/find-matching?title={title}` - Find matching image
- `GET /api/admin/book-images/config` - Get configuration status

## 🎯 How It Works

### Image Matching Algorithm
The system uses a smart matching algorithm to associate book titles with image files:

1. **Exact Match**: Direct filename match (e.g., "Effective Java" → "Effective Java.jpg")
2. **Partial Match**: Filename contains book title or vice versa
3. **Word-based Match**: Individual words from the title match words in the filename
4. **Score-based Selection**: Best match is selected based on a scoring system

### Example Matching
```
Book Title: "Effective Java"
Possible Matches:
- "Effective Java.jpg" (Score: 100 - Exact match)
- "Effective Java Programming.png" (Score: 80 - Contains match)
- "Java Effective Guide.jpg" (Score: 60 - Word-based match)
```

## 🛠️ Manual Setup

### 1. Add Images to Directory
```bash
# Copy your book images to the directory
cp "book-cover.jpg" "C:\Users\govin\Downloads\Java Books\"
```

### 2. Update Book Records
You can manually update book records with local image URLs:
```sql
UPDATE books 
SET image_url = '/api/images/local/Effective Java.jpg' 
WHERE title = 'Effective Java';
```

### 3. Use the Admin Interface
1. Login as admin
2. Go to Books Management
3. Edit a book
4. The system will automatically suggest matching local images

## 🔍 Troubleshooting

### Common Issues

#### Images Not Loading
- Check if the image file exists in the directory
- Verify the filename matches exactly (case-sensitive)
- Ensure the image format is supported
- Check application logs for errors

#### No Images Found
- Verify the directory path in configuration
- Check file permissions
- Ensure images are in supported formats
- Run the setup script to scan for images

#### Permission Errors
- Ensure the application has read access to the images directory
- On Windows, run the application as administrator if needed
- Check file permissions for the images directory

### Debug Commands
```bash
# List all available images
curl http://localhost:8080/api/images/local/list

# Check configuration
curl http://localhost:8080/api/admin/book-images/config

# Find matching image for a book
curl "http://localhost:8080/api/images/local/find?title=Effective Java"
```

## 📊 Performance Considerations

### Image Optimization
- Keep image files under 2MB for better performance
- Use JPG format for photographs, PNG for graphics
- Consider resizing images to standard dimensions (e.g., 300x400px)

### Caching
- Images are served directly by Spring Boot
- Consider adding a reverse proxy (nginx) for better caching
- Use CDN for production deployments

## 🔄 Migration from S3

If you're migrating from AWS S3 to local images:

1. **Download Images**: Download all images from S3 to your local directory
2. **Update Configuration**: Set `app.local-images.enabled=true`
3. **Run Assignment**: Use the auto-assignment endpoint
4. **Verify**: Check that all books have local image URLs
5. **Disable S3**: Set S3 configuration to disabled (optional)

## 🎨 Frontend Integration

The frontend automatically handles local image URLs. The `BookCard` component will display local images when the `imageUrl` starts with `/api/images/local/`.

### Example Frontend Usage
```jsx
// The BookCard component automatically handles local images
<BookCard book={book} />

// Local image URLs are automatically served
// Example: /api/images/local/Effective Java.jpg
```

## 📝 Best Practices

### File Naming
- Use descriptive filenames that match book titles
- Avoid special characters in filenames
- Use consistent naming conventions
- Keep filenames under 100 characters

### Directory Structure
```
C:\Users\govin\Downloads\Java Books\
├── Effective Java.jpg
├── Spring Boot in Action.png
├── Clean Code.jpg
├── Design Patterns.png
└── ...
```

### Image Quality
- Use high-quality images (at least 300x400 pixels)
- Maintain aspect ratio
- Use consistent image dimensions
- Optimize file sizes for web delivery

## 🚀 Production Deployment

### Docker Setup
1. Mount the images directory as a volume:
```yaml
volumes:
  - "C:/Users/govin/Downloads/Java Books:/app/images:ro"
```

2. Update the configuration for the container path:
```yaml
app:
  local-images:
    directory: /app/images
```

### Security Considerations
- Ensure the images directory is not publicly accessible
- Use proper file permissions
- Validate image file types
- Consider image scanning for malware

## 📞 Support

If you encounter issues:
1. Check the application logs
2. Verify configuration settings
3. Test with the provided API endpoints
4. Run the setup script for diagnostics

---

**Happy coding! 📚✨**
