package edu.neu.cs5500.fantastix;

import edu.neu.cs5500.fantastix.core.*;
import edu.neu.cs5500.fantastix.data.*;
import edu.neu.cs5500.fantastix.resources.*;
import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.jersey.setup.JerseyEnvironment;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.federecio.dropwizard.swagger.SwaggerBundle;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Service extends Application<ServiceConfiguration> {
	private static final Logger LOGGER = LoggerFactory.getLogger(ServiceConfiguration.class);
	
    public static void main(String[] args) throws Exception {
        new Service().run(args);
    }

    @Override
    public String getName() {
        return "Fantastix";
    }

    private final HibernateBundle<ServiceConfiguration> hibernateBundle = new HibernateBundle<ServiceConfiguration>(Renter.class, Manager.class, Booking.class, Feedback.class, Property.class, PropertyImage.class) {
        @Override
        public DataSourceFactory getDataSourceFactory(ServiceConfiguration configuration) {
            return configuration.getDataSourceFactory();
        }

    };

    @Override
    public void initialize(Bootstrap<ServiceConfiguration> bootstrap) {
    	LOGGER.info("Initializing configuration");
        bootstrap.addBundle(new MigrationsBundle<ServiceConfiguration>() {
            @Override
            public DataSourceFactory getDataSourceFactory(ServiceConfiguration configuration) {
                return configuration.getDataSourceFactory();
            }
        });
        bootstrap.addBundle(hibernateBundle);
        bootstrap.addBundle(new SwaggerBundle<ServiceConfiguration>() {
            @Override
            protected SwaggerBundleConfiguration getSwaggerBundleConfiguration(ServiceConfiguration configuration) {
                return configuration.swaggerBundleConfiguration;
            }
        });
    }

    @Override
    public void run(ServiceConfiguration configuration, Environment environment) throws Exception {

    	LOGGER.info("Starting the Application");

        final JerseyEnvironment env = environment.jersey();

        final RenterDAO renterDAO = new RenterDAO(hibernateBundle.getSessionFactory());
        final ManagerDAO managerDAO = new ManagerDAO(hibernateBundle.getSessionFactory());
        final FeedbackDAO feedbackDAO = new FeedbackDAO(hibernateBundle.getSessionFactory());
        final BookingDAO bookingDAO = new BookingDAO(hibernateBundle.getSessionFactory());
        final PropertyDAO propertyDAO = new PropertyDAO(hibernateBundle.getSessionFactory());
        final PropertyImageDAO propertyImageDAO = new PropertyImageDAO(hibernateBundle.getSessionFactory());

        final RenterResource renterResource = new RenterResource(renterDAO, bookingDAO, feedbackDAO);
        final ManagerResource managerResource = new ManagerResource(managerDAO, propertyDAO, bookingDAO, feedbackDAO, propertyImageDAO);
        final PropertyResource propertyResource = new PropertyResource(propertyDAO, propertyImageDAO);

        env.register(renterResource);
        env.register(managerResource);
        env.register(propertyResource);
    }
}
