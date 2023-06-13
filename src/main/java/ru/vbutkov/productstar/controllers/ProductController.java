package ru.vbutkov.productstar.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.vbutkov.productstar.dao.ProductDao;
import ru.vbutkov.productstar.dto.ProductDto;
import ru.vbutkov.productstar.entity.Product;
import ru.vbutkov.productstar.facade.ProductFacade;

import java.sql.SQLException;


@Controller
//@RequestMapping("/products")
public class ProductController {

    private final ProductDao productDao;
    private final ProductFacade productFacade;

    @Autowired
    public ProductController(ProductFacade productFacade, @Qualifier("productDaoImpl") ProductDao productDao) {
        this.productFacade = productFacade;
        this.productDao = productDao;
    }

    @GetMapping("/")
    public String showProducts(Model model) throws SQLException {
        var products = productFacade.getProducts();
        model.addAttribute("products", products);
        return "index";
    }

    @GetMapping("/add")
    public String createForm() {
        return "create-form";
    }

    @PostMapping("/create")
    public String create(ProductDto productDto) {
        Product product = productFacade.mapperProductDtoToProduct(productDto);
        productDao.addProduct(product);
        return "redirect:/";
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable("id") String id) throws SQLException {
        Product product = productDao.getProductById(id);
        productDao.deleteProduct(product);
        return "redirect:/";
    }

    @PatchMapping("/edit/{id}")
    public String editForm(@PathVariable("id") String id, Model model) throws SQLException {
        Product product = productDao.getProductById(id);
        ProductDto productDto = productFacade.mapperProductToProductDto(product);
        model.addAttribute("product", productDto);
        return "edit-form";
    }

    @PatchMapping("/update/{id}")
    public String update(ProductDto productDto, @PathVariable("id") String id) throws SQLException {
        productFacade.updateProduct(productDto, id);
        return "redirect:/";
    }

}
