package com.beingknow.eatit2020.ModelResponse;

public class CafeCategory
{

    private String server_url_image;
    private String name;

    public CafeCategory(String server_url_image, String name) {
        this.server_url_image = server_url_image;
        this.name = name;
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
