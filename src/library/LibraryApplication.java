package library;



import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import library.core.Book;
import library.health.TemplateHealthCheck;
import library.jdbi.BookDAO;
import library.resources.BookResource;

/**
 * 
 * @author Seva Meyer
 * main class running the whole application 
 *
 */
public class LibraryApplication extends Application<LibraryConfiguration> {

	public static void main(String[] args) throws Exception {
		new LibraryApplication().run(new String[]{"server", "test.yml"}); //can be args instead
	}


	@Override
	public String getName() {
		return "hello backend";
	}

	 private final HibernateBundle<LibraryConfiguration> hibernate = new HibernateBundle<LibraryConfiguration>(Book.class){ //more entities can be added separated with a coma
	     @Override  
		 public DataSourceFactory getDataSourceFactory(LibraryConfiguration configuration) {
	            return configuration.getDataSourceFactory();
	        }
	    };

	@Override
	public void initialize(Bootstrap<LibraryConfiguration> bootstrap) {

        bootstrap.addBundle(new AssetsBundle("/webapp", "/", "index.html", "static"));
        
        bootstrap.addBundle(new MigrationsBundle<LibraryConfiguration>() {
            @Override
            public DataSourceFactory getDataSourceFactory(LibraryConfiguration configuration) {
                return configuration.getDataSourceFactory();
            }
        });
        
        bootstrap.addBundle(hibernate);
	}

	@Override
	public void run(LibraryConfiguration configuration,
			Environment environment) {

        final BookDAO dao = new BookDAO(hibernate.getSessionFactory());

		
		final TemplateHealthCheck healthCheck = new TemplateHealthCheck(
				configuration.getTemplate());


		environment.healthChecks().register("template", healthCheck); //register the health check
		environment.jersey().register(new BookResource(dao));
	}

}
