package ru.vbutkov.productstar.dto;

public class ProductDto {

    private String id;
    private final String name;
    private final String bar;
    private final String article;
    private final String unit;
    private final double price;

    public ProductDto(String id, String name, String bar, String article, String unit, double price) {
        this.id = id;
        this.name = name;
        this.bar = bar;
        this.article = article;
        this.unit = unit;
        this.price = price;
    }

    public String getArticle() {
        return article;
    }

    public String getName() {
        return name;
    }

    public String getBar() {
        return bar;
    }

    public String getUnit() {
        return unit;
    }

    public double getPrice() {
        return price;
    }

    public String getId() {
        return id;
    }
}
