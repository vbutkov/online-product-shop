package ru.vbutkov.productstar.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.vbutkov.productstar.entity.Article;
import ru.vbutkov.productstar.entity.Bar;
import ru.vbutkov.productstar.entity.Product;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ProductDaoImpl implements ProductDao {

    private final DataSource dataSource;

    @Autowired
    public ProductDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Product> getProducts() throws SQLException {
        String SELECT_SQL = "SELECT product_id, name, article, bar, price FROM product";
        List<Product> products = new ArrayList<>();
        try (
                Connection connection = dataSource.getConnection();
                Statement statement = connection.createStatement();
                ResultSet rs = statement.executeQuery(SELECT_SQL);
        ) {
            while (rs.next()) {
                Article article1 = new Article(rs.getString(2), rs.getString(3));
                Bar bar1 = new Bar(rs.getString(4), List.of(article1), List.of(rs.getDouble(5)));
                products.add(new Product(String.valueOf(rs.getInt(1)), article1, bar1));
            }
        }
        return products;
    }

    @Override
    public Product addProduct(Product product) {
        String INSERT_SQL = "INSERT INTO product(name, article, bar, price) values (?, ?, ?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, product.getArticle().getName());
            preparedStatement.setString(2, product.getArticle().getArticle());
            preparedStatement.setString(3, product.getBar().getBar());
            preparedStatement.setDouble(4, product.getBar().getPrices().get(0));

            preparedStatement.executeUpdate();

            try (ResultSet rs = preparedStatement.getGeneratedKeys()) {
                rs.next();
                product.setId(String.valueOf(rs.getInt(1)));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return product;
    }

    @Override
    public void deleteProduct(Product product) {
        String DELETE_BY_ID_SQL = "DELETE FROM product WHERE product_id=?";
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(DELETE_BY_ID_SQL)
        ) {
            preparedStatement.setInt(1, Integer.parseInt(product.getId()));
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Product getProductById(String id) {
        String GET_BY_ID_SQL = "SELECT name, article, bar, price FROM product WHERE product_id=?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_BY_ID_SQL)) {

            preparedStatement.setInt(1, Integer.parseInt(id));
            ResultSet rs = preparedStatement.executeQuery();

            if (!rs.next())
                throw new RuntimeException(String.format("Product with id %d was not found", id));

            Article article1 = new Article(rs.getString(1), rs.getString(2));
            Bar bar1 = new Bar(rs.getString(3), List.of(article1), List.of(rs.getDouble(4)));

            return new Product(id, article1, bar1);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteAllProducts() {
        String DELETE_SQL = "TRUNCATE TABLE product";
        try (
                Connection connection = dataSource.getConnection();
                Statement statement = connection.createStatement();
        ) {
            statement.executeUpdate(DELETE_SQL);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Product updateProduct(Product product) {
        String UPDATE_SQL = "UPDATE product SET name = ?, article = ?, bar = ?, price = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SQL)) {
            preparedStatement.setString(1, product.getArticle().getName());
            preparedStatement.setString(2, product.getArticle().getArticle());
            preparedStatement.setString(3, product.getBar().getBar());
            preparedStatement.setDouble(4, product.getBar().getPrices().get(0));

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return product;
    }

}
