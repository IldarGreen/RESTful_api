package com.vanesabo.backend;

import com.vanesabo.backend.model.*;
import com.vanesabo.backend.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HexFormat;
import java.util.List;

@SpringBootApplication
public class BackendApplication {

    private static final Logger log = LoggerFactory.getLogger(BackendApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }

    @Autowired
    BookRepository bookRepository;

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    ClientRepository clientRepository;

    // Run this if app.db.init.enabled = true
    @Bean
    @ConditionalOnProperty(prefix = "app", name = "db.init.enabled", havingValue = "true")
    public CommandLineRunner demoCommandLineRunner(ImagesRepository imagesRepository, ProductRepository productRepository, SupplierRepository supplierRepository) {
        return args -> {

            System.out.println("Running.....");

            Book b1 = new Book("Book A",
                    BigDecimal.valueOf(9.99),
                    LocalDate.of(2023, 8, 31));
            Book b2 = new Book("Book B",
                    BigDecimal.valueOf(19.99),
                    LocalDate.of(2023, 7, 31));

            bookRepository.saveAll(List.of(b1, b2));

            AddressEntity a1 = new AddressEntity("Франция", "Леон", "Вьен");
            AddressEntity a2 = new AddressEntity("Россия", "Сакт-Петербург", "Лунная");

            addressRepository.saveAll(List.of(a1, a2));

            ClientEntity cl1 = new ClientEntity("Кэлен", "Амнелл", "2000-04-05",
                    "F", "2014-05-06", a1);
            ClientEntity cl2 = new ClientEntity("Йон", "Тихий", "1940-01-02",
                    "M", "2024-05-07", a2);
            ClientEntity cl3 = new ClientEntity("Йон2", "Тихий", "1940-01-02",
                    "M", "2024-05-07", a2);
            ClientEntity cl4 = new ClientEntity("Йо3", "Тихий", "1940-01-02",
                    "M", "2024-05-07", a2);
            ClientEntity cl5 = new ClientEntity("Йон4", "Тихий", "1940-01-02",
                    "M", "2024-05-07", a2);
            ClientEntity cl6 = new ClientEntity("Йон5", "Тихий", "1940-01-02",
                    "M", "2024-05-07", a2);

            clientRepository.saveAll(List.of(cl1, cl2, cl3, cl4, cl5, cl6));

            SupplierEntity sp1 = new SupplierEntity("Жан-Жак Клошар", a1, "+33686579015");
            SupplierEntity sp2 = new SupplierEntity("Иван Иванов", a2, "+89686579015");

            supplierRepository.saveAll(List.of(sp1, sp2));

//            byte[] CDRIVES = HexFormat.of().parseHex("e04fd020ea3a6910a2d808002b30309d");
//            byte[] myvar = "Any String you want".getBytes();
//            byte[] CDRIVES = "\u00e0\u004f\u00d0\u0020\u00ea\u003a\u0069\u0010\u00a2\u00d8\u0008\u0000\u002b\u0030\u0030\u009d".getBytes();
            byte[] image1 = HexFormat.of().parseHex("e04fd020ea3a6910a2d808002b30309d");
            byte[] image2 = HexFormat.of().parseHex("e03fd020ea3a6910a2d808002b30309d");

            ProductEntity pr1 = new ProductEntity("Богарт", "Духи", new BigDecimal(1000), 10, "2024-05-10", sp1, null);
            ProductEntity pr2 = new ProductEntity("Невская политра 30", "Краски", new BigDecimal(1500), 4, "2024-05-10", sp2, null);
            ProductEntity pr3 = new ProductEntity("Невская политра 10", "Краски", new BigDecimal(500), 5, "2024-05-10", sp2, null);

            ImagesEntity im1 = new ImagesEntity(image1, List.of(pr1));
            ImagesEntity im2 = new ImagesEntity(image2, List.of(pr2, pr3));

            imagesRepository.saveAll(List.of(im1, im2));

            pr1.setImage(im1);
            pr2.setImage(im2);
            pr3.setImage(im2);

            productRepository.saveAll(List.of(pr1, pr2, pr3));

        };
    }

}