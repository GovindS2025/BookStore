package com.org.bookstore_backend.repo;

import com.org.bookstore_backend.entity.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;

/*public interface PublisherRepository extends MongoRepository<Publisher, String> {
    // This interface will automatically provide CRUD operations for Publisher entities.
    // No additional methods are needed unless custom queries are required.
}*/
public interface PublisherRepo extends JpaRepository<Publisher, String> {
    // This interface will automatically provide CRUD operations for Publisher entities.
    // No additional methods are needed unless custom queries are required.
}

