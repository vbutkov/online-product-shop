package ru.vbutkov.productstar.dao;

import ru.vbutkov.productstar.entity.Product;

import java.util.List;

public interface ProductDao {

    List<Product> getProducts();
    void addProduct(Product product);
    void deleteProduct(Product product);
}
