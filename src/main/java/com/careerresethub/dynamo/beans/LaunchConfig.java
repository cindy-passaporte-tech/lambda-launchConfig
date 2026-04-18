package com.careerresethub.dynamo.beans;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;

@SuppressWarnings("unused")
@DynamoDbBean
public class LaunchConfig {
    private String launchId;
    private String startCaptureDate;
    private String startSalesDate;
    private String endSalesDate;
    private String soldOutUrl;

    public String getLaunchId() {
        return launchId;
    }

    public void setLaunchId(String launchId) {
        this.launchId = launchId;
    }

    public String getStartCaptureDate() {
        return startCaptureDate;
    }

    public void setStartCaptureDate(String startCaptureDate) {
        this.startCaptureDate = startCaptureDate;
    }

    public CharSequence getStartSalesDate() {
        return startSalesDate;
    }

    public CharSequence getEndSalesDate() {
        return endSalesDate;
    }

    public String getSoldOutUrl() {
        return soldOutUrl;
    }

    public void setStartSalesDate(String startSalesDate) {
        this.startSalesDate = startSalesDate;
    }

    public void setEndSalesDate(String endSalesDate) {
        this.endSalesDate = endSalesDate;
    }

    public void setSoldOutUrl(String soldOutUrl) {
        this.soldOutUrl = soldOutUrl;
    }
}
