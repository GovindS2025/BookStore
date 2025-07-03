package com.org.bookstore_backend.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.io.Serializable;

@Configuration
public class PostgresDBsource implements Serializable,Cloneable {

    private final Environment env;
    private static final long serialVersionUID = 1L;
    private static boolean isInstanceCreated = false;
    // The PostgresDBsource class is a singleton that provides a DataSource instance for PostgreSQL operations.
    private PostgresDBsource instance;

    public PostgresDBsource(Environment env) {
        if (isInstanceCreated == true) {
            throw new RuntimeException("Singleton instance already exists!");
        }
        this.isInstanceCreated = true;
        this.env = env;
        // Assign the current instance to the singleton instance
    }

    // The readResolve method is used to ensure that when the singleton instance is deserialized,
    // it returns the same instance created by the getInstance method.
    // This is necessary to maintain the singleton property during serialization and deserialization.
    protected Object readResolve() {
        return this; // Ensures deserialization returns the same instance
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException("Cannot clone Singleton");
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://localhost:5432/my-db");
        dataSource.setUsername("my-user");
        dataSource.setPassword("user-password");
        return dataSource;
    }
}
