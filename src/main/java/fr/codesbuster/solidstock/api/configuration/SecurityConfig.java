package fr.codesbuster.solidstock.api.configuration;


import fr.codesbuster.solidstock.api.entity.*;
import fr.codesbuster.solidstock.api.repository.*;
import fr.codesbuster.solidstock.api.security.JwtAuthenticationEntryPoint;
import fr.codesbuster.solidstock.api.security.JwtAuthenticationFilter;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Configuration
@EnableMethodSecurity
@SecurityScheme(
        name = "Bear Authentication",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
public class SecurityConfig {

    private final List<RoleEntity> roles = new ArrayList<>();

    private final UserDetailsService userDetailsService;
    private final JwtAuthenticationEntryPoint authenticationEntryPoint;
    private final JwtAuthenticationFilter authenticationFilter;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private QuantityTypeRepository quantityTypeRepository;
    @Autowired
    private ProductFamilyRepository productFamilyRepository;
    @Autowired
    private VATRepository vatRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private SupplierRepository supplierRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private OwnerCompanyRepository ownerCompanyRepository;


    public SecurityConfig(UserDetailsService userDetailsService,
                          JwtAuthenticationEntryPoint authenticationEntryPoint,
                          JwtAuthenticationFilter authenticationFilter) {
        this.userDetailsService = userDetailsService;
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.authenticationFilter = authenticationFilter;
    }

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @PostConstruct
    public void init() throws IOException {

        if (quantityTypeRepository.findByUnit("kg").isEmpty()) {
            QuantityTypeEntity quantityType = new QuantityTypeEntity();
            quantityType.setName("Kilogramme");
            quantityType.setUnit("kg");
            quantityType.setDescription("Unité de mesure de masse");
            quantityTypeRepository.save(quantityType);
        }

        if (quantityTypeRepository.findByUnit("l").isEmpty()) {
            QuantityTypeEntity quantityType = new QuantityTypeEntity();
            quantityType.setName("Litre");
            quantityType.setUnit("l");
            quantityType.setDescription("Unité de mesure de volume");
            quantityTypeRepository.save(quantityType);
        }

        if (quantityTypeRepository.findByUnit("unit").isEmpty()) {
            QuantityTypeEntity quantityType = new QuantityTypeEntity();
            quantityType.setName("Unité");
            quantityType.setUnit("unit");
            quantityType.setDescription("Unité de mesure de quantité");
            quantityTypeRepository.save(quantityType);
        }

        if (quantityTypeRepository.findByUnit("m").isEmpty()) {
            QuantityTypeEntity quantityType = new QuantityTypeEntity();
            quantityType.setName("Mètre");
            quantityType.setUnit("m");
            quantityType.setDescription("Unité de mesure de longueur");
            quantityTypeRepository.save(quantityType);
        }

        if (productFamilyRepository.findByName("Vin Rouge").isEmpty()) {
            ProductFamilyEntity productFamily = new ProductFamilyEntity();
            productFamily.setName("Vin Rouge");
            productFamily.setDescription("Vin rouge");
            productFamilyRepository.save(productFamily);
        }

        if (productFamilyRepository.findByName("Vin Blanc").isEmpty()) {
            ProductFamilyEntity productFamily = new ProductFamilyEntity();
            productFamily.setName("Vin Blanc");
            productFamily.setDescription("Vin blanc");
            productFamilyRepository.save(productFamily);
        }

        if (productFamilyRepository.findByName("Vin Rosé").isEmpty()) {
            ProductFamilyEntity productFamily = new ProductFamilyEntity();
            productFamily.setName("Vin Rosé");
            productFamily.setDescription("Vin rosé");
            productFamilyRepository.save(productFamily);
        }

        if (productFamilyRepository.findByName("Champagne").isEmpty()) {
            ProductFamilyEntity productFamily = new ProductFamilyEntity();
            productFamily.setName("Champagne");
            productFamily.setDescription("Champagne");
            productFamilyRepository.save(productFamily);
        }

        if (productFamilyRepository.findByName("Spiritueux").isEmpty()) {
            ProductFamilyEntity productFamily = new ProductFamilyEntity();
            productFamily.setName("Spiritueux");
            productFamily.setDescription("Spiritueux");
            productFamilyRepository.save(productFamily);
        }

        if (productFamilyRepository.findByName("Bières").isEmpty()) {
            ProductFamilyEntity productFamily = new ProductFamilyEntity();
            productFamily.setName("Bières");
            productFamily.setDescription("Bières");
            productFamilyRepository.save(productFamily);
        }

        if (productFamilyRepository.findByName("Cidres").isEmpty()) {
            ProductFamilyEntity productFamily = new ProductFamilyEntity();
            productFamily.setName("Cidres");
            productFamily.setDescription("Cidres");
            productFamilyRepository.save(productFamily);
        }

        if (productFamilyRepository.findByName("Softs").isEmpty()) {
            ProductFamilyEntity productFamily = new ProductFamilyEntity();
            productFamily.setName("Softs");
            productFamily.setDescription("Softs");
            productFamilyRepository.save(productFamily);
        }

        if (productFamilyRepository.findByName("Eaux").isEmpty()) {
            ProductFamilyEntity productFamily = new ProductFamilyEntity();
            productFamily.setName("Eaux");
            productFamily.setDescription("Eaux");
            productFamilyRepository.save(productFamily);
        }

        if (productFamilyRepository.findByName("Cafés").isEmpty()) {
            ProductFamilyEntity productFamily = new ProductFamilyEntity();
            productFamily.setName("Cafés");
            productFamily.setDescription("Cafés");
            productFamilyRepository.save(productFamily);
        }

        if (productFamilyRepository.findByName("Thés").isEmpty()) {
            ProductFamilyEntity productFamily = new ProductFamilyEntity();
            productFamily.setName("Thés");
            productFamily.setDescription("Thés");
            productFamilyRepository.save(productFamily);
        }

        if (productFamilyRepository.findByName("Infusions").isEmpty()) {
            ProductFamilyEntity productFamily = new ProductFamilyEntity();
            productFamily.setName("Infusions");
            productFamily.setDescription("Infusions");
            productFamilyRepository.save(productFamily);
        }

        if (vatRepository.findByRate(20).isEmpty()) {
            VATEntity vat = new VATEntity();
            vat.setRate(20);
            vat.setDescription("TVA à 20%");
            vat.setPercentage("20%");
            vatRepository.save(vat);
        }

        if (vatRepository.findByRate(5.5).isEmpty()) {
            VATEntity vat = new VATEntity();
            vat.setRate(5.5);
            vat.setDescription("TVA à 5.5%");
            vat.setPercentage("5.5%");
            vatRepository.save(vat);
        }

        if (vatRepository.findByRate(10).isEmpty()) {
            VATEntity vat = new VATEntity();
            vat.setRate(10);
            vat.setDescription("TVA à 10%");
            vat.setPercentage("10%");
            vatRepository.save(vat);
        }

        if (vatRepository.findByRate(2.1).isEmpty()) {
            VATEntity vat = new VATEntity();
            vat.setRate(2.1);
            vat.setDescription("TVA à 2.1%");
            vat.setPercentage("2.1%");
            vatRepository.save(vat);
        }

        if (vatRepository.findByRate(0).isEmpty()) {
            VATEntity vat = new VATEntity();
            vat.setRate(0);
            vat.setDescription("TVA à 0%");
            vat.setPercentage("0%");
            vatRepository.save(vat);
        }

        if (vatRepository.findByRate(13).isEmpty()) {
            VATEntity vat = new VATEntity();
            vat.setRate(13);
            vat.setDescription("TVA à 13%");
            vat.setPercentage("13%");
            vatRepository.save(vat);
        }

        if (vatRepository.findByRate(7).isEmpty()) {
            VATEntity vat = new VATEntity();
            vat.setRate(7);
            vat.setDescription("TVA à 7%");
            vat.setPercentage("7%");
            vatRepository.save(vat);
        }

        if (vatRepository.findByRate(15).isEmpty()) {
            VATEntity vat = new VATEntity();
            vat.setRate(15);
            vat.setDescription("TVA à 15%");
            vat.setPercentage("15%");
            vatRepository.save(vat);
        }

        if (vatRepository.findByRate(17).isEmpty()) {
            VATEntity vat = new VATEntity();
            vat.setRate(17);
            vat.setDescription("TVA à 17%");
            vat.setPercentage("17%");
            vatRepository.save(vat);
        }

        if (supplierRepository.findByCompanyName("Château Latour").isEmpty()) {
            SupplierEntity supplier = new SupplierEntity();
            supplier.setCompanyName("Château Latour");
            supplier.setStreetNumber("14");
            supplier.setAddress("Rue Saint-Guillaume");
            supplier.setZipCode("33250");
            supplier.setCity("Pauillac");
            supplier.setHomePhone("05 56 73 19 80");
            supplier.setWorkPhone("05 56 73 19 80");
            supplier.setSiren("775 726 520");
            supplier.setSiret("775585585858959");
            supplier.setRib("FR76 3004 1000 0100 0008 8134 116");
            supplier.setFax("05 56 73 19 81");
            supplier.setCountry("France");
            supplier.setEmail("latour@chateau.com");
            supplier.setWebsite("https://www.chateau-latour.com/");
            supplierRepository.save(supplier);
        }

        if (supplierRepository.findByCompanyName("Château Margaux").isEmpty()) {
            SupplierEntity supplier = new SupplierEntity();
            supplier.setCompanyName("Château Margaux");
            supplier.setStreetNumber("1");
            supplier.setAddress("Rue de la Fonderie");
            supplier.setZipCode("33250");
            supplier.setCity("Margaux");
            supplier.setHomePhone("05 57 88 83 83");
            supplier.setEmail("chateau@margaux.com");
            supplier.setWebsite("https://www.chateau-margaux.com/");
            supplier.setRcs(595945);
            supplier.setSiren("775 726 520");
            supplier.setSiret("775585 595 858 959");
            supplier.setRib("FR76 3005 1000 0100 0008 8134 116");
            supplier.setFax("05 57 88 83 84");
            supplier.setCountry("France");
            supplierRepository.save(supplier);
        }

        if (supplierRepository.findByCompanyName("Château Lafite Rothschild").isEmpty()) {
            SupplierEntity supplier = new SupplierEntity();
            supplier.setCompanyName("Château Lafite Rothschild");
            supplier.setStreetNumber("1");
            supplier.setAddress("Rue de la Fonderie");
            supplier.setZipCode("33250");
            supplier.setCity("Pauillac");
            supplier.setHomePhone("05 56 73 19 80");
            supplier.setEmail("lafite@test.com");
            supplier.setWebsite("https://www.lafite.com/");
            supplier.setRcs(595945);
            supplier.setSiren("775 726 520");
            supplier.setSiret("775585 595 858 959");
            supplier.setRib("FR76 3004 1000 0100 0008 8134 116");
            supplier.setFax("05 56 73 19 81");
            supplier.setCountry("France");
            supplierRepository.save(supplier);
        }

        if (supplierRepository.findByCompanyName("Château Mouton Rothschild").isEmpty()) {
            SupplierEntity supplier = new SupplierEntity();
            supplier.setCompanyName("Château Mouton Rothschild");
            supplier.setStreetNumber("5");
            supplier.setAddress("Rue de la Fonderie");
            supplier.setZipCode("33250");
            supplier.setCity("Pauillac");
            supplier.setHomePhone("05 56 73 19 80");
            supplier.setEmail("mouton@rothschild.com");
            supplier.setWebsite("https://www.mouton-rothschild.com/");
            supplier.setRcs(595945);
            supplier.setSiren("775 726 520");
            supplier.setSiret("775585 595 858 959");
            supplier.setRib("FR76 3001 1000 0100 0008 8134 116");
            supplier.setFax("05 56 73 19 81");
            supplier.setCountry("France");
            supplierRepository.save(supplier);
        }

        if (supplierRepository.findByCompanyName("Château d'Yquem").isEmpty()) {
            SupplierEntity supplier = new SupplierEntity();
            supplier.setCompanyName("Château d'Yquem");
            supplier.setAddress("33210 Sauternes");
            supplier.setHomePhone("05 56 73 19 80");
            supplier.setEmail("lolo@lali.com");
            supplier.setWebsite("https://www.yquem.com/");
            supplier.setRcs(595945);
            supplier.setSiren("775 726 520");
            supplier.setSiret("775585 595 858 959");
            supplier.setRib("FR76 3005 1000 0100 0008 8134 116");
            supplier.setFax("05 56 73 19 81");
            supplier.setCountry("France");
            supplierRepository.save(supplier);
        }


        if (productRepository.findByBarCode("123456789").isEmpty()) {
            ProductEntity product = new ProductEntity();
            product.setName("Château Latour 2010");
            product.setDescription("Château Latour 2010");
            product.setBarCode("123456789");
            product.setBuyPrice(1000.0);
            product.setSellPrice(1500.0);
            product.setMinimumStockQuantity(10);
            product.setSupplier(supplierRepository.findByCompanyName("Château Latour").get());
            product.setVat(vatRepository.findByRate(20).get());
            product.setQuantityType(quantityTypeRepository.findByUnit("unit").get());
            product.setProductFamily(productFamilyRepository.findByName("Vin Rouge").get());
            productRepository.save(product);
        }

        if (productRepository.findByBarCode("253456789").isEmpty()) {
            ProductEntity product = new ProductEntity();
            product.setName("Château Margaux 2010");
            product.setDescription("Château Margaux 2010");
            product.setBarCode("253456789");
            product.setBuyPrice(1000.0);
            product.setSellPrice(1500.0);
            product.setMinimumStockQuantity(10);
            product.setSupplier(supplierRepository.findByCompanyName("Château Margaux").get());
            product.setVat(vatRepository.findByRate(20).get());
            product.setQuantityType(quantityTypeRepository.findByUnit("unit").get());
            product.setProductFamily(productFamilyRepository.findByName("Vin Rouge").get());
            productRepository.save(product);
        }

        if (productRepository.findByBarCode("323456789").isEmpty()) {
            ProductEntity product = new ProductEntity();
            product.setName("Château Lafite Rothschild 2010");
            product.setDescription("Château Lafite Rothschild 2010");
            product.setBarCode("323456789");
            product.setBuyPrice(1000.0);
            product.setSellPrice(1500.0);
            product.setMinimumStockQuantity(10);
            product.setSupplier(supplierRepository.findByCompanyName("Château Lafite Rothschild").get());
            product.setVat(vatRepository.findByRate(20).get());
            product.setQuantityType(quantityTypeRepository.findByUnit("unit").get());
            product.setProductFamily(productFamilyRepository.findByName("Vin Rouge").get());
            productRepository.save(product);
        }

        if (productRepository.findByBarCode("423456789").isEmpty()) {
            ProductEntity product = new ProductEntity();
            product.setName("Les Terrasses du Diable 2010");
            product.setDescription("Les Terrasses du Diable 2010");
            product.setBarCode("423456789");
            product.setBuyPrice(1000.0);
            product.setSellPrice(1600.0);
            product.setMinimumStockQuantity(10);
            product.setSupplier(supplierRepository.findByCompanyName("Château Mouton Rothschild").get());
            product.setVat(vatRepository.findByRate(20).get());
            product.setQuantityType(quantityTypeRepository.findByUnit("unit").get());
            product.setProductFamily(productFamilyRepository.findByName("Vin Blanc").get());
            productRepository.save(product);
        }

        if (productRepository.findByBarCode("523456789").isEmpty()) {
            ProductEntity product = new ProductEntity();
            product.setName("Château d'Yquem 2010");
            product.setDescription("Château d'Yquem 2010");
            product.setBarCode("523456789");
            product.setBuyPrice(1000.0);
            product.setSellPrice(1500.0);
            product.setMinimumStockQuantity(10);
            product.setSupplier(supplierRepository.findByCompanyName("Château d'Yquem").get());
            product.setVat(vatRepository.findByRate(20).get());
            product.setQuantityType(quantityTypeRepository.findByUnit("unit").get());
            product.setProductFamily(productFamilyRepository.findByName("Vin Blanc").get());
            productRepository.save(product);
        }

        if (productRepository.findByBarCode("623456789").isEmpty()) {
            ProductEntity product = new ProductEntity();
            product.setName("Château d'Yquem 2010");
            product.setDescription("Château d'Yquem 2010");
            product.setBarCode("623456789");
            product.setBuyPrice(1000.0);
            product.setSellPrice(1500.0);
            product.setMinimumStockQuantity(10);
            product.setSupplier(supplierRepository.findByCompanyName("Château d'Yquem").get());
            product.setVat(vatRepository.findByRate(20).get());
            product.setQuantityType(quantityTypeRepository.findByUnit("unit").get());
            product.setProductFamily(productFamilyRepository.findByName("Spiritueux").get());
            productRepository.save(product);
        }

        if (productRepository.findByBarCode("723456789").isEmpty()) {
            ProductEntity product = new ProductEntity();
            product.setName("Château d'Yquem 2010");
            product.setDescription("Château d'Yquem 2010");
            product.setBarCode("723456789");
            product.setBuyPrice(1000.0);
            product.setSellPrice(1500.0);
            product.setMinimumStockQuantity(10);
            product.setSupplier(supplierRepository.findByCompanyName("Château d'Yquem").get());
            product.setVat(vatRepository.findByRate(20).get());
            product.setQuantityType(quantityTypeRepository.findByUnit("unit").get());
            product.setProductFamily(productFamilyRepository.findByName("Bières").get());
            productRepository.save(product);
        }

        if (productRepository.findByBarCode("823456789").isEmpty()) {
            ProductEntity product = new ProductEntity();
            product.setName("Château d'Yquem 2010");
            product.setDescription("Château d'Yquem 2010");
            product.setBarCode("823456789");
            product.setBuyPrice(1000.0);
            product.setSellPrice(1500.0);
            product.setMinimumStockQuantity(10);
            product.setSupplier(supplierRepository.findByCompanyName("Château d'Yquem").get());
            product.setVat(vatRepository.findByRate(20).get());
            product.setQuantityType(quantityTypeRepository.findByUnit("unit").get());
            product.setProductFamily(productFamilyRepository.findByName("Cidres").get());
            productRepository.save(product);
        }

        if (productRepository.findByBarCode("923456789").isEmpty()) {
            ProductEntity product = new ProductEntity();
            product.setName("Château d'Yquem 2010");
            product.setDescription("Château d'Yquem 2010");
            product.setBarCode("923456789");
            product.setBuyPrice(1000.0);
            product.setSellPrice(1500.0);
            product.setMinimumStockQuantity(10);
            product.setSupplier(supplierRepository.findByCompanyName("Château d'Yquem").get());
            product.setVat(vatRepository.findByRate(20).get());
            product.setQuantityType(quantityTypeRepository.findByUnit("unit").get());
            product.setProductFamily(productFamilyRepository.findByName("Softs").get());
            productRepository.save(product);
        }

        if (customerRepository.findByCompanyName("CGT de Lyon").isEmpty()) {
            CustomerEntity customer = new CustomerEntity();
            customer.setCompanyName("CGT de Lyon");
            customer.setStreetNumber("14");
            customer.setAddress("Rue Saint-Guillaume");
            customer.setZipCode("33250");
            customer.setCity("Pauillac");
            customer.setHomePhone("05 56 73 19 80");
            customer.setWorkPhone("05 56 73 19 80");
            customer.setSiren("775 726 520");
            customer.setSiret("775585585858959");
            customer.setRib("FR76 3004 1000 0100 0008 8134 116");
            customer.setFax("05 56 73 19 81");
            customer.setCountry("France");
            customer.setEmail("cgt@lyon.fr");
            customerRepository.save(customer);
        }

        if (customerRepository.findByCompanyName("CGT de Paris").isEmpty()) {
            CustomerEntity customer = new CustomerEntity();
            customer.setCompanyName("CGT de Paris");
            customer.setStreetNumber("14");
            customer.setAddress("Rue Saint-Guillaume");
            customer.setZipCode("33250");
            customer.setCity("Pauillac");
            customer.setHomePhone("05 56 73 19 80");
            customer.setWorkPhone("05 56 73 19 80");
            customer.setSiren("775 726 520");
            customer.setSiret("775585585858959");
            customer.setRib("FR76 3004 1000 0100 0008 8134 116");
            customer.setFax("05 56 73 19 81");
            customer.setCountry("France");
            customer.setEmail("test@test.com");
            customerRepository.save(customer);
        }

        if (customerRepository.findByCompanyName("CGT de Marseille").isEmpty()) {
            CustomerEntity customer = new CustomerEntity();
            customer.setCompanyName("CGT de Marseille");
            customer.setStreetNumber("14");
            customer.setAddress("Rue Saint-Guillaume");
            customer.setZipCode("33250");
            customer.setCity("Pauillac");
            customer.setHomePhone("05 56 73 19 80");
            customer.setWorkPhone("05 56 73 19 80");
            customer.setSiren("775 726 520");
            customer.setSiret("775585585858959");
            customer.setRib("FR76 3004 1000 0100 0008 8134 116");
            customer.setFax("05 56 73 19 81");
            customer.setCountry("France");
            customer.setEmail("marseillelatrick@cgt.com");
            customerRepository.save(customer);
        }

        createRoleIfNotFound("USER");
        createRoleIfNotFound("CUSTOMER_MANAGER");
        createRoleIfNotFound("STOCK_MANAGER");
        createRoleIfNotFound("PRODUCT_MANAGER");
        createRoleIfNotFound("SUPPLIER_MANAGER");
        createRoleIfNotFound("ESTIMATE_MANAGER");
        createRoleIfNotFound("ORDER_MANAGER");
        createRoleIfNotFound("INVOICE_MANAGER");
        createRoleIfNotFound("USER_MANAGER");
        createRoleIfNotFound("ADMIN");
        createRoleIfNotFound("WEB_USER");


        if (userRepository.findByEmail("admin.admin@admin.admin").isEmpty()) {
            UserEntity user = new UserEntity();
            user.setLastName("DUPOND");
            user.setFirstName("Pierre");
            user.setEmail("admin.admin@admin.admin");
            user.setPassword(passwordEncoder().encode("admin"));
            user.setRoles(roles);
            userRepository.save(user);
        }

        if (userRepository.findByEmail("user@user.user").isEmpty()) {
            UserEntity user = new UserEntity();
            user.setLastName("MICHEL");
            user.setFirstName("Pascal");
            user.setEmail("user@user.user");
            user.setPassword(passwordEncoder().encode("test"));
            List<RoleEntity> roles = new ArrayList<>();
            roles.add(roleRepository.findByName("USER").orElse(null));
            user.setRoles(roles);
            user.setCustomer(customerRepository.findByCompanyName("CGT de Lyon").get());
            user.setDefaultPage("Produits");

            userRepository.save(user);
        }

        if (ownerCompanyRepository.findById(Long.valueOf(1)).isEmpty()) {
            OwnerCompanyEntity ownerCompanyEntity = new OwnerCompanyEntity();
            ownerCompanyEntity.setCompanyName("Auchan");
            ownerCompanyEntity.setOwnerName("Breuillard Clément");
            ownerCompanyEntity.setSiret("12345678901234");
            ownerCompanyEntity.setSiren("123456789");
            ownerCompanyEntity.setRcs(123456789);
            ownerCompanyEntity.setStreetNumber("205");
            ownerCompanyEntity.setStreetName("Avenue du chemin");
            ownerCompanyEntity.setZipCode("69000");
            ownerCompanyEntity.setCity("Lyon");
            ownerCompanyEntity.setCountry("France");
            ownerCompanyEntity.setEmail("email@email.com");
            ownerCompanyEntity.setPhone("04 04 04 04 04");
            ownerCompanyEntity.setIban("FR76 4561 4561 4561 253");
            File image = new File(Objects.requireNonNull(getClass().getClassLoader().getResource("images/negosud.png")).getPath());
            ownerCompanyEntity.setImage(Files.readAllBytes(image.toPath()));
            ownerCompanyRepository.save(ownerCompanyEntity);
        }
    }

    private void createRoleIfNotFound(String roleName) {
        roleRepository.findByName(roleName).orElseGet(() -> {
            RoleEntity role = new RoleEntity();
            role.setName(roleName);
            role = roleRepository.save(role);
            roles.add(role);
            return role;
        });
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf().disable()
                .authorizeHttpRequests((authorize) ->
                        //authorize.anyRequest().authenticated()
                        authorize.requestMatchers("/api/v1/auth/**").permitAll()
                                .requestMatchers("/swagger-ui/**").permitAll()
                                .requestMatchers("/v3/api-docs/**").permitAll()
                                .requestMatchers("/api/v1/web/**").permitAll()
                                .anyRequest().authenticated()

                ).exceptionHandling(exception -> exception
                        .authenticationEntryPoint(authenticationEntryPoint)
                ).sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );

        http.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
