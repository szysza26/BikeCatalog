package com.github.szysza26.bikecatalog.dto;

public class SearchBikeRequest {
    private int pageNumber = 0;
    private int pageSize = 12;
    private String sort = "createdAt.DESC";
    private String name = "";
    private Long brand= null;
    private Long category = null;

    public SearchBikeRequest() {
    }

    public SearchBikeRequest(int pageNumber, int pageSize, String sort, String name, Long brand, Long category) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.sort = sort;
        this.name = name;
        this.brand = brand;
        this.category = category;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getBrand() {
        return brand;
    }

    public void setBrand(Long brand) {
        this.brand = brand;
    }

    public Long getCategory() {
        return category;
    }

    public void setCategory(Long category) {
        this.category = category;
    }
}
