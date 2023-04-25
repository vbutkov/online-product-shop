package ru.vbutkov.productstar.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.vbutkov.productstar.dto.ProductDto;
import ru.vbutkov.productstar.facade.ProductFacade;


@Controller
//@RequestMapping("/products")
public class ProductController {
    private final ProductFacade productFacade;
    @Autowired
    public ProductController(ProductFacade productFacade) {
        this.productFacade = productFacade;
    }

    @GetMapping("/")
    public String showProducts(Model model) {
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
        productFacade.createProduct(productDto);
        return "redirect:/";
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable("id") String id) {
        productFacade.deleteProduct(id);
        return "redirect:/";
    }

    @PatchMapping("/edit/{id}")
    public String editForm(@PathVariable("id") String id, Model model) {
        var productDto = productFacade.getProductDto(id);
        model.addAttribute("product", productDto);
        return "edit-form";
    }

    @PatchMapping("/update/{id}")
    public String update(ProductDto productDto, @PathVariable("id") String id) {
//        delete(productDto.getId());
//        create(productDto);
        productFacade.updateProduct(productDto, id);
        return "redirect:/";
    }

}
