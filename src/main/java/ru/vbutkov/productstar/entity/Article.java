package ru.vbutkov.productstar.entity;

import java.util.Objects;

public class Article {

    private static long generatorId = 1;
    private long id;
    private String name;
    private String article;
    private byte type = 1;

    public Article(String name, String article) {
        this.name = name;
        this.article = article;
        generatorId++;
    }

    public Article(String name, String article, byte type) {
        this(name, article);
        this.type = type;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getArticle() {
        return article;
    }

    public byte getType() {
        return type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setArticle(String article) {
        this.article = article;
    }

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", article='" + article + '\'' +
                ", type=" + type +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Article article1 = (Article) o;
        return id == article1.id && type == article1.type && Objects.equals(name, article1.name) && Objects.equals(article, article1.article);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, article, type);
    }
}
