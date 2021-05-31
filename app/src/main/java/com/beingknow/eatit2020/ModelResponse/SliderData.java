package com.beingknow.eatit2020.ModelResponse;

public class SliderData {

    // image url is used to
    // store the url of image
    private String server_url_image;

    // Constructor method.
    public SliderData(String server_url_image) {
        this.server_url_image = server_url_image;
    }

    // Getter method
    public String getImgUrl() {
        return server_url_image;
    }

    // Setter method
    public void setImgUrl(String server_url_image) {
        this.server_url_image = server_url_image;
    }
}
