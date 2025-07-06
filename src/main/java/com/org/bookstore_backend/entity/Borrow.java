package com.org.bookstore_backend.entity;
import java.util.Set;
import jakarta.persistence.*;
import lombok.Setter;
import org.apache.catalina.User;
//import com.org.bookstore_backend.entity.Fine;
import java.time.LocalDate;

@Entity
@Table(name="borrow")
public class Borrow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "borrowDate")
    private LocalDate borrowDate;

    @Setter
    @Column(name = "returnDate")
    private LocalDate returnDate;



//    @OneToMany(mappedBy = "borrow")
//    private Set<Fine> fines;



    public Borrow(int id, Book book, User user, LocalDate borrowDate, LocalDate returnDate) {
        this.id = id;
        this.book = book;
        this.user = user;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
    }

    public Borrow(Book book, User user, LocalDate borrowDate, LocalDate returnDate) {
        this.book = book;
        this.user = user;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
    }

    public Borrow() {
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(LocalDate borrowDate) {
        this.borrowDate = borrowDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    @Override
    public String toString() {
        return "Borrow{" +
                "id=" + id +
                ", book=" + book +
                ", user=" + user +
                ", borrowDate=" + borrowDate +
                ", returnDate=" + returnDate +
                '}';
    }
}