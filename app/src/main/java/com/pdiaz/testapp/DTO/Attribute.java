package com.pdiaz.testapp.DTO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by diiaz94 on 04-04-2017.
 */
public class Attribute {
    @SerializedName("product.displayName")
    @Expose
    private List<String> productDisplayName;

    @SerializedName("sku.thumbnailImage")
    @Expose
    private List<String> skuThumbnailImage;

    @SerializedName("sku.list_Price")
    @Expose
    private List<String> skuPromoPrice;

    @SerializedName("ancestorCategories.displayName")
    @Expose
    private List<String> ancestorCategoriesDisplayName;

    public Attribute(List<String> productDisplayName, List<String> skuThumbnailImage, List<String> skuPromoPrice) {
        this.productDisplayName = productDisplayName;
        this.skuThumbnailImage = skuThumbnailImage;
        this.skuPromoPrice = skuPromoPrice;
    }

    public String getProductDisplayName() {
        return  productDisplayName == null?"":productDisplayName.get(0);
    }

    public void setProductDisplayName(List<String> productDisplayName) {
        this.productDisplayName = productDisplayName;
    }

    public String getSkuThumbnailImage() {
        return skuThumbnailImage == null?"":skuThumbnailImage.get(0);
    }

    public void setSkuThumbnailImage(List<String> skuThumbnailImage) {
        this.skuThumbnailImage = skuThumbnailImage;
    }

    public String getSkuPromoPrice() {
        return skuPromoPrice == null?"":skuPromoPrice.get(0);
    }

    public void setSkuPromoPrice(List<String> skuPromoPrice) {
        this.skuPromoPrice = skuPromoPrice;
    }

    public String getAncestorCategoriesDisplayName() {
        return ancestorCategoriesDisplayName == null?"":ancestorCategoriesDisplayName.get(0);
    }

    public void setAncestorCategoriesDisplayName(List<String> ancestorCategoriesDisplayName) {
        this.ancestorCategoriesDisplayName = skuPromoPrice;
    }

}
