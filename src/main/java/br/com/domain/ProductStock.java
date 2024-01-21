package br.com.domain;

public class ProductStock {

    private Long idProduct;
    private int qtdStock;

    public Long getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(Long idProduct) {
        this.idProduct = idProduct;
    }

    public int getQtdStock() {
        return qtdStock;
    }

    public void setQtdStock(int qtdStock) {
        this.qtdStock = qtdStock;
    }
}
