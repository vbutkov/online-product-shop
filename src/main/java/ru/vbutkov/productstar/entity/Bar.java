package ru.vbutkov.productstar.entity;

import java.util.List;

public class Bar {

    private static long generator = 1;
    private long id;
    private String bar;
    private List<Double> prices = List.of(0.0);
    private List<Article> articles;

    Unit unit = Unit.шт;

    public Bar(String bar, List<Article> articles) {
        this.bar = bar;
        this.articles = articles;
        generator++;
    }

    public Bar(String bar, List<Article> articles, List<Double> prices, Unit unit) {
        this(bar, articles);
        this.prices = prices;
        this.unit = unit;
    }

    public Bar(String bar, List<Article> articles, Unit unit) {
        this(bar, articles);
        this.unit = unit;
    }

    public Bar(String bar, List<Article> articles, List<Double> prices) {
        this(bar, articles);
        this.prices = prices;
    }

    public long getId() {
        return id;
    }

    public String getBar() {
        return bar;
    }

    public List<Double> getPrices() {
        return prices;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setBar(String bar) {
        this.bar = bar;
    }

    public void setPrices(List<Double> prices) {
        this.prices = prices;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    @Override
    public String toString() {
        return "Bar{" +
                "id=" + id +
                ", bar='" + bar + '\'' +
                ", prices=" + prices +
                ", articles=" + articles +
                ", unit=" + unit +
                '}';
    }
}
