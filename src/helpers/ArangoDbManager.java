package helpers;

import com.arangodb.ArangoConfigure;
import com.arangodb.ArangoDriver;
import com.arangodb.ArangoException;
import com.arangodb.entity.BaseDocument;
import data_object.DataSource;

/**
 * Created by budae on 10.10.2016.
 */
public class ArangoDbManager {

    final private String DATABASE = "integration";
    final private String COLLECTION = "datasources";
    final private String USER = "root";
    final private String PASSWORD = "zuwa45we";
    private ArangoConfigure configure;
    private ArangoDriver arangoDriver;

    public ArangoDbManager(ArangoConfigure configure) {

        this.configure = new ArangoConfigure();
        configure.setUser("root");
        configure.setPassword("zuwa45we");
        configure.setDefaultDatabase("integration");
        configure.init();
        arangoDriver = new ArangoDriver(configure);
    }

    public void createDocument(DataSource dataSource) {

        try {
            arangoDriver.createDocument(COLLECTION, dataSource);
            System.out.println("Document created");
        } catch (ArangoException e) {
            System.out.println("Failed to create document. " + e.getMessage());
        }
    }
}
