package com.soufang.vo.assess;

public class AddAssessDetailVo {

    private Long productId ;
    private Integer assessType ;
    private String assessContent;
    private String productColor;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getAssessType() {
        return assessType;
    }

    public void setAssessType(Integer assessType) {
        this.assessType = assessType;
    }

    public String getAssessContent() {
        return assessContent;
    }

    public void setAssessContent(String assessContent) {
        this.assessContent = assessContent;
    }

    public String getProductColor() {
        return productColor;
    }

    public void setProductColor(String productColor) {
        this.productColor = productColor;
    }
}
