package br.com.file_transformer.service;

import br.com.file_transformer.model.Order;
import br.com.file_transformer.model.Product;
import br.com.file_transformer.model.TransactionRegister;
import br.com.file_transformer.model.User;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


public class ConversionServiceTest {

    @Test
    public void shouldConvertALineToEntities(){
        //Given
        String line = "0000000070                              Palmer Prosacco00000007530000000003     1836.7420210308";
        ConversionService conversionService = new ConversionService();

        //When
        conversionService.populateEntities(line);

        //Then
        List<TransactionRegister> transactionRegister = conversionService.getTransactionRegisters();
        assertNotNull(transactionRegister);
        assertEquals("Palmer Prosacco",transactionRegister.get(0).getUserName());
    }

    @Test
    public void shouldConvertRecordsToEntitiesNormalized(){
        //Given
        List<String> lines =
                List.of("0000000088                             Dr. Felisa Boyle00000009380000000000       213.720210709",
                        "0000000026                       Miss Gaylord Hettinger00000002900000000003     1334.0420211024",
                        "0000000088                             Dr. Felisa Boyle00000009380000000001       215.720210709",
                        "0000000026                       Miss Gaylord Hettinger00000003000000000003     1334.0420211024");

        ConversionService conversionService = new ConversionService();
        for (String line : lines) {
            conversionService.populateEntities(line);
        }

        //When
        List<User> normalizedRecord = conversionService.normalize();

        //Then
        assertEquals(2,normalizedRecord.size());
        User user1 = normalizedRecord.get(0);
        assertEquals(88,user1.getUserId());
        assertEquals("Dr. Felisa Boyle",user1.getName());

        List<Order> orders = user1.getOrders();
        assertEquals(1,orders.size());

        Order order1 = orders.get(0);
        assertEquals(938,order1.getOrderId());
        assertEquals("20210709",order1.getDate());
        assertEquals("429.4",order1.getTotal());

        List<Product> products = order1.getProducts();
        assertEquals(2,products.size());

        Product product1 = products.get(0);
        assertEquals(0,product1.getProductId());
        assertEquals("213.7",product1.getValue());

        Product product2 = products.get(1);
        assertEquals(1,product2.getProductId());
        assertEquals("215.7",product2.getValue());

        User user2 = normalizedRecord.get(1);
        assertEquals(26,user2.getUserId());
        assertEquals("Miss Gaylord Hettinger",user2.getName());

         orders = user2.getOrders();
        assertEquals(2,orders.size());

        order1 = orders.get(0);
        assertEquals(300,order1.getOrderId());
        assertEquals("20211024",order1.getDate());
        assertEquals("1334.04",order1.getTotal());

        products = order1.getProducts();
        assertEquals(1,products.size());

        product1 = products.get(0);
        assertEquals(3,product1.getProductId());
        assertEquals("1334.04",product1.getValue());

        Order order2 = orders.get(1);
        assertEquals(290,order2.getOrderId());
        assertEquals("20211024",order2.getDate());
        assertEquals("1334.04",order2.getTotal());

        products = order2.getProducts();
        assertEquals(1,products.size());

        product1 = products.get(0);
        assertEquals(3,product1.getProductId());
        assertEquals("1334.04",product1.getValue());

    }

}
