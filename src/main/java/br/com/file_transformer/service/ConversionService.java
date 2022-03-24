package br.com.file_transformer.service;


import br.com.file_transformer.model.Order;
import br.com.file_transformer.model.Product;
import br.com.file_transformer.model.TransactionRegister;
import br.com.file_transformer.model.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toSet;

public class ConversionService {

    Set<User> users = new HashSet<>();
    Set<Order> orders = new HashSet<>();
    Set<Product> products = new HashSet<>();
    List<TransactionRegister> transactionRegisters = new ArrayList<>();

    public List<TransactionRegister> getTransactionRegisters() {
        return transactionRegisters;
    }

    public List<TransactionRegister> toModel(String line) {

        String userId = StringUtils.stripStart(line.substring(0, 10),"0");
        String userName = StringUtils.stripStart(line.substring(10, 55)," ");
        String orderId = StringUtils.stripStart(line.substring(55, 65),"0");
        String productId = StringUtils.stripStart(line.substring(65, 75),"0");
        productId = productId.isEmpty() ? "0":productId;
        String productValue = StringUtils.stripStart(line.substring(75, 87)," ");
        String orderDate = StringUtils.stripStart(line.substring(87, 95),"0");

        TransactionRegister transactionRegister = new TransactionRegister();

        Product product = new Product();
        product.setProductId(Integer.valueOf(productId));
        product.setValue(productValue);
        products.add(product);

        Order order = new Order();
        order.setOrderId(Integer.valueOf(orderId));
        order.setDate(orderDate);
        orders.add(order);

        User user = new User();
        user.setName(userName);
        user.setUserId(Integer.valueOf(userId));
        users.add(user);

        transactionRegister.setUserId(userId);
        transactionRegister.setUserName(userName);
        transactionRegister.setOrderId(orderId);
        transactionRegister.setOrderDate(orderDate);
        transactionRegister.setProductId(productId);
        transactionRegister.setProductValue(productValue);

        this.transactionRegisters.add(transactionRegister);
        return transactionRegisters;
    }

    public User findUserById(Integer userId){
        return this.users.stream().filter(u -> u.getUserId().compareTo(userId)==0).findFirst().get();
    }
    public Order findOrderById(Integer orderId){
        return this.orders.stream().filter(o -> o.getOrderId().compareTo(orderId)==0).findFirst().get();
    }
    public Product findProductById(Integer productId){
        return this.products.stream().filter(p -> p.getProductId().compareTo(productId)==0).findFirst().get();
    }

    public String normalize(List<TransactionRegister> transactionRegisters) {
//TODO: passar os for para processamento com streams
        return convertToJson(convertToNormalizedObjects(normalizeIds(transactionRegisters)));
    }

    private String convertToJson(List<User> usuarios) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(usuarios);
    }

    private List<User> convertToNormalizedObjects
            (Map<String, Map<String, Set<String>>> normalizedIds) {

        List<User> users = new ArrayList<>();
        for (Map.Entry<String, Map<String, Set<String>>> userIdEntry : normalizedIds.entrySet()) {

            User user = findUserById(Integer.valueOf(userIdEntry.getKey()));
            List<Order> orders = new ArrayList<>();

            Map<String, Set<String>> ordersMap = userIdEntry.getValue();
            for (Map.Entry<String, Set<String>> orderEntry : ordersMap.entrySet()) {

                Order order = findOrderById(Integer.valueOf(orderEntry.getKey()));
                List<Product> products = new ArrayList<>();

                Double orderTotal = 0.0;
                for (String productId : orderEntry.getValue()) {

                    Product productReturned = findProductById(Integer.valueOf(productId));

                    products.add(productReturned);
                    orderTotal = BigDecimal.valueOf(orderTotal + Double.valueOf(productReturned.getValue())).setScale(2, RoundingMode.HALF_UP).doubleValue();
                }

                order.setProducts(products);
                order.setTotal(String.valueOf(orderTotal));
                orders.add(order);
            }

            user.setOrders(orders);
            users.add(user);
        }
        return users;
    }

    private Map<String, Map<String, Set<String>>> normalizeIds(List<TransactionRegister> transactionRegisters) {
        return transactionRegisters.stream().collect(
                Collectors.groupingBy(TransactionRegister::getUserId,
                        Collectors.groupingBy(TransactionRegister::getOrderId,
                                Collectors.mapping(TransactionRegister::getProductId, toSet()))));
    }


}
