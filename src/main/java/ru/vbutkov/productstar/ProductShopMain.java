package ru.vbutkov.productstar;

import org.h2.jdbcx.JdbcDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;

@SpringBootApplication
public class ProductShopMain {

    @Bean
    public DataSource h2DataSource(@Value("${jdbc.url}") String jdbcUrl) {
        JdbcDataSource jdbcDataSource = new JdbcDataSource();
        jdbcDataSource.setURL(jdbcUrl);
        jdbcDataSource.setUser("user");
        jdbcDataSource.setPassword("password");

        return jdbcDataSource;
    }

    @Bean
    public CommandLineRunner cmd(DataSource dataSource) throws IOException {
        return args -> {
            try (InputStream inputStream = this.getClass().getResourceAsStream("/initial.sql")) {
                String sql = new String(inputStream.readAllBytes());
                try (Connection connection = dataSource.getConnection();
                     Statement statement = connection.createStatement()) {

                    statement.executeUpdate(sql);
                    String insertSql = "INSERT INTO product(name, article, bar, price) values (?, ?, ?, ?)";
                    try (PreparedStatement preparedStatement = connection.prepareStatement(insertSql)) {
                        preparedStatement.setString(1, "Товар 1");
                        preparedStatement.setString(2, "1");
                        preparedStatement.setString(3, "1111111111111");
                        preparedStatement.setDouble(4, 125.00);

                        // preparedStatement.executeUpdate();
                    }
                }
            }
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(ProductShopMain.class, args);
    }
}
