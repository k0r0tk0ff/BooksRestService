package ru.k0r0tk0ff.configuration;

import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;

@Slf4j
@Configuration
@EnableJpaRepositories(basePackages = {"ru.k0r0tk0ff.repository"})
public class DataSourceConfig {

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.driverClassName}")
    private String driverClassName;

    @Value("${spring.datasource.username}")
    private String user;

    @Value("${spring.datasource.password}")
    private String password;

    @Value("${spring.flyway.schemas}")
    private String schemas;

    @Value("${spring.flyway.locations}")
    private String[] locations;

    @Value("${spring.flyway.out-of-order}")
    private Boolean outOfOrder;

    @Value("${schema.recreate:false}")
    private boolean schemaRecreate;

    @Value("${spring.flyway.repair:false}")
    private boolean schemaRepair;

    @Bean(name = "mainDataSource")
    public DataSource getDataSource() {
        try {
            EmbeddedDatabaseBuilder dbBuilder = new EmbeddedDatabaseBuilder();
            return dbBuilder
                    .setType(EmbeddedDatabaseType.H2)
                    .setName("Embedded datasource H2")
                    .build();
        } catch (Exception е) {
            log.error("Cannot create Embedded dataSource !");
            return null;
        }
    }

    @Bean(initMethod = "migrate")
    @DependsOn("mainDataSource")
    public Flyway flyway() {
        Flyway flyway = Flyway.configure()
                .dataSource(getDataSource())
                .schemas(schemas)
                .ignoreFutureMigrations(false)
                .locations(locations)
                .baselineOnMigrate(schemaRecreate)
                .outOfOrder(outOfOrder)
                .load();
        if (schemaRecreate) {
            flyway.clean();
        }
        if (schemaRepair) {
            flyway.repair();
        }
        flyway.migrate();
        return flyway;
    }
}
