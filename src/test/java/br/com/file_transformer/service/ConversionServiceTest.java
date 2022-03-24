package br.com.file_transformer.service;

import br.com.file_transformer.model.TransactionRegister;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


public class ConversionServiceTest {

    @Test
    public void shouldConvertALineToEntity(){
        //Given
        String line = "0000000070                              Palmer Prosacco00000007530000000003     1836.7420210308";
        ConversionService conversionService = new ConversionService();

        //When
        conversionService.toModel(line);
        List<TransactionRegister> transactionRegister = conversionService.getTransactionRegisters();

        //Then
        assertNotNull(transactionRegister);
        assertEquals("Palmer Prosacco",transactionRegister.get(0).getUserName());
    }

    //TODO: adicionar mais testes
    //TODO: separar teste por responsabilidades

//    @Test
//    public void shouldConvertMultipliesLineToEntitiesNormalized() throws IOException {
//        //Given
//        List<String> lines =
//                List.of("0000000088                             Dr. Felisa Boyle00000009380000000000       213.720210709",
//                        "0000000026                       Miss Gaylord Hettinger00000002900000000003     1334.0420211024",
//                        "0000000088                             Dr. Felisa Boyle00000009380000000000       213.720210709",
//                        "0000000026                       Miss Gaylord Hettinger00000003000000000003     1334.0420211024");
//
//                        ConversionService conversionService = new ConversionService();
//
//        //When
//        for (String line : lines) {
//            conversionService.toModel(line);
//        }
//
//        List<TransactionRegister> transactionRegisters = new ArrayList<>();
//        for (String line : lines) {
//            transactionRegisters = conversionService.toModel(line);
//        }
//        System.out.println(transactionRegisters);
//        System.out.println(conversionService.normalize(transactionRegisters));
//    }








}
