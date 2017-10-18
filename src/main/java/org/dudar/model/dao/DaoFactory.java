package org.dudar.model.dao;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.*;

/**
 * Abstract class that represents abstract dao factory that can construct
 * various types of concrete Dao factories that support different types of
 * persistent storage access implementation.
 * 
 * Each concrete dao factory that support particular kind of persistent storage access
 * implementation has to extend this abstract class
 * 
 * @author Dmitry
 *
 */
public abstract class DaoFactory {

	private static final Logger LOGGER = LogManager.getLogger(DaoFactory.class);
	private static final String DB_FILE = "/db.properties";
	private static final String DB_FACTORY_CLASS = "factory.class";
	private static DaoFactory daoFactory;
    public static int LIMIT;

	public abstract DaoConnection getConnection();

	public abstract UserDao createUserDao();

	public abstract UserDao createUserDao(DaoConnection connection);

	public abstract BookDescriptionDao createBookDescriptionDao();

	public abstract BookDescriptionDao createBookDescriptionDao(DaoConnection connection);

	public abstract BookInstanceDao createBookInstanceDao();

	public abstract BookInstanceDao createBookInstanceDao(DaoConnection connection);

	public abstract AuthorDao createAuthorDao();

	public abstract AuthorDao createAuthorDao(DaoConnection connection);

	public abstract BookOrderDao createBookOrderDao();

	public abstract BookOrderDao createBookOrderDao(DaoConnection connection);

	/**
	 * Method that returns concrete dao factory that support particular kind of
	 * persistent storage access implementation(JDBC implementation). This factory implementation is loaded
	 * from db.properties file
	 * 
	 * @return DaoFactory concrete dao factory implementation
	 */
	public static DaoFactory getDaoFactory() {
		if (daoFactory == null) {
			try {
				InputStream inputStream = DaoFactory.class.getResourceAsStream(DB_FILE);
				Properties dbProps = new Properties();
				dbProps.load(inputStream);
				String factoryClass = dbProps.getProperty(DB_FACTORY_CLASS);
				LIMIT = Integer.parseInt(dbProps.getProperty("limit"));
				daoFactory = (DaoFactory) Class.forName(factoryClass).newInstance();

			} catch (IOException | IllegalAccessException | InstantiationException | ClassNotFoundException e) {
				LOGGER.error("Can't load inputStream db config file to properties object", e);
				//throw new ServerException(e);
			}
		}
		return daoFactory;
	}
}
