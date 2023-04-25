package ru.vbutkov.productstar.entity;

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
}
