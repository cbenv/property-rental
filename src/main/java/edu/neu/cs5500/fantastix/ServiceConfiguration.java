package edu.neu.cs5500.fantastix;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.db.DatabaseConfiguration;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class ServiceConfiguration extends Configuration {

    private static final String ENVIRONMENT = "heroku"; // heroku or local

    @JsonProperty("swagger")
    public SwaggerBundleConfiguration swaggerBundleConfiguration;

    @Valid
    @NotNull
    private DataSourceFactory database = new DataSourceFactory();

    @JsonProperty("database")
    public DataSourceFactory getDataSourceFactory() {
        if (ENVIRONMENT.equals("local")) {
            return database;
        }
        DatabaseConfiguration databaseConfiguration = DatabaseConfig.create(System.getenv("DATABASE_URL"));
        return (DataSourceFactory) databaseConfiguration.getDataSourceFactory(null);
    }

    @JsonProperty("database")
    public void setDataSourceFactory(DataSourceFactory dataSourceFactory) {
        this.database = dataSourceFactory;
    }
}
