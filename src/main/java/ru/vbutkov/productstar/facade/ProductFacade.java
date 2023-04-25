package ru.vbutkov.productstar.facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vbutkov.productstar.dao.ProductDao;
import ru.vbutkov.productstar.dto.ProductDto;
import ru.vbutkov.productstar.entity.Article;
import ru.vbutkov.productstar.entity.Bar;
import ru.vbutkov.productstar.entity.Product;
import ru.vbutkov.productstar.entity.Unit;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductFacade {

    private final ProductDao productDao;
    private final int DEFAULT_INDEX_PRICE = 0;

    @Autowired
    public ProductFacade(ProductDao productDao) {
        this.productDao = productDao;
    }

    public List<ProductDto> getProducts() {
        var products = productDao.getProducts();
        return products.stream()
                .map(this::mapperProductToProductDto
                ).collect(Collectors.toList());
    }

    private ProductDto mapperProductToProductDto(Product product) {
        return new ProductDto(
                product.getId(),
                product.getArticle().getName(),
                product.getBar().getBar(),
                product.getArticle().getArticle(),
                product.getBar().getUnit().name(),
                product.getBar().getPrices().get(DEFAULT_INDEX_PRICE));
    }


    public void createProduct(ProductDto productDto) {
        Article article = new Article(productDto.getName(), productDto.getArticle());
        Bar bar = new Bar(productDto.getBar(), List.of(article), List.of(productDto.getPrice()), Unit.valueOf(productDto.getUnit()));
        productDao.addProduct(new Product(UUID.randomUUID().toString(), article, bar));
    }

    public void deleteProduct(String id) {
        Product product = getProduct(id);
        productDao.deleteProduct(product);
    }

    private Product getProduct(String id) {
        Product product = productDao.getProducts().stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElseThrow(RuntimeException::new);

        return product;
    }

    public ProductDto getProductDto(String id) {
        Product product = getProduct(id);
        return mapperProductToProductDto(product);
    }

    public void updateProduct(ProductDto productDto, String id) {
        System.out.println(id);
        Product product = getProduct(id);
        System.out.println(product);
        product.getArticle().setArticle(productDto.getArticle());
        product.getArticle().setName(productDto.getName());
        product.getBar().setBar(productDto.getBar());
        product.getBar().setPrices(List.of(productDto.getPrice()));
        product.getBar().setUnit(Unit.valueOf(productDto.getUnit()));
    }
}
