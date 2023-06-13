package ru.vbutkov.productstar.entity;

public class Product {
    String id;
    Article article;
    Bar bar;

    public Product(String id, Article article, Bar bar) {
        this.id = id;
        this.article = article;
        this.bar = bar;
    }

    public Product(Article article, Bar bar) {
        this.article = article;
        this.bar = bar;
    }

    public Article getArticle() {
        return article;
    }

    public Bar getBar() {
        return bar;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id='" + id + '\'' +
                ", article=" + article +
                ", bar=" + bar +
                '}';
    }
}
