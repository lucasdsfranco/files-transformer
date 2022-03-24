package br.com.file_transformer.model;

public class TransactionRegister {

    String userId;
    String userName;
    String orderId;
    String orderTotal;
    String orderDate;
    String productId;
    String productValue;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderTotal() {
        return orderTotal;
    }

    public void setOrderTotal(String orderTotal) {
        this.orderTotal = orderTotal;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductValue() {
        return productValue;
    }

    public void setProductValue(String productValue) {
        this.productValue = productValue;
    }

    @Override
    public String toString() {
        return "TransactionRegister{" +
                "userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", orderId='" + orderId + '\'' +
                ", orderTotal='" + orderTotal + '\'' +
                ", orderDate='" + orderDate + '\'' +
                ", productId='" + productId + '\'' +
                ", productValue='" + productValue + '\'' +
                '}'+'\n';
    }
}
