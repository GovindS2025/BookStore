/*
package com.org.bookstore_backend.controller;
import com.org.bookstore_backend.services.UrlShorteningService; // ⭐ Import the INTERFACE
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*") // Adjust in production
public class UrlShortenerController {

    private static final Logger logger = LoggerFactory.getLogger(UrlShortenerController.class);

    private final UrlShorteningService urlShorteningService; // ⭐ Inject the INTERFACE

    @Autowired
    public UrlShortenerController(UrlShorteningService urlShorteningService) { // ⭐ Constructor takes the INTERFACE
        this.urlShorteningService = urlShorteningService;
    }

    */
/**
     * Endpoint to shorten a long URL.
     * Expects a JSON body like: {"longUrl": "http://example.com/very/long/path"}
     * Returns a JSON body like: {"shortUrl": "http://localhost:8080/abcDE1"}
     *//*

    @PostMapping("/api/shorten")
    public ResponseEntity<Map<String, String>> shortenUrl(@RequestBody Map<String, String> request) {
        String longUrl = request.get("longUrl");
        if (longUrl == null || longUrl.trim().isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Long URL is required."));
        }

        try {
            String shortCode = urlShorteningService.shortenUrl(longUrl);
            String fullShortUrl = urlShorteningService.getFullShortUrl(shortCode);
            Map<String, String> response = new HashMap<>();
            response.put("shortUrl", fullShortUrl);
            logger.info("Successfully shortened {} to {}", longUrl, fullShortUrl);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            logger.error("Error shortening URL {}: {}", longUrl, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to shorten URL: " + e.getMessage()));
        }
    }

    */
/**
     * Endpoint for redirection. When a user accesses the short URL,
     * this method retrieves the original long URL and redirects the browser.
     * This endpoint should be at the root of your application, not under /api.
     *//*

    @GetMapping("/{shortCode}")
    public RedirectView redirectFromShortUrl(@PathVariable String shortCode) {
        try {
            String longUrl = urlShorteningService.retrieveLongUrl(shortCode);
            logger.info("Redirecting short code {} to {}", shortCode, longUrl);
            RedirectView redirectView = new RedirectView();
            redirectView.setUrl(longUrl);
            redirectView.setStatusCode(HttpStatus.FOUND); // 302 Found
            return redirectView;
        } catch (IllegalArgumentException e) {
            logger.warn("Attempted to access non-existent short code: {}", shortCode);
            // Redirect to a custom error page or homepage if short code not found
            RedirectView redirectView = new RedirectView();
            redirectView.setUrl("/error/404-short-url"); // You might want to create this page
            redirectView.setStatusCode(HttpStatus.NOT_FOUND); // 404 Not Found
            return redirectView;
        } catch (Exception e) {
            logger.error("Error during redirection for short code {}: {}", shortCode, e.getMessage());
            RedirectView redirectView = new RedirectView();
            redirectView.setUrl("/error/server-error"); // You might want to create this page
            redirectView.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR); // 500 Internal Server Error
            return redirectView;
        }
    }
}
*/
