package com.beingknow.eatit2020.ModelResponse;

public class FoodCategoryResponse
{
    private int id;
    private String server_url_image;
    private String name;

    public FoodCategoryResponse(int id, String server_url_image, String name) {
       this.id = id;
        this.server_url_image = server_url_image;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getServer_url_image() {
        return server_url_image;
    }

    public void setServer_url_image(String server_url_image) {
        this.server_url_image = server_url_image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
