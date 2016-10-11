package helpers;

import com.arangodb.ArangoConfigure;
import com.arangodb.ArangoDriver;
import com.arangodb.ArangoException;
import data_object.DataSource;

/**
 * Created by budae on 10.10.2016.
 */
public class ArangoDbManager {

    final private String USER = "root";
    final private String PASSWORD = "zuwa45we";

    private String collection;

    private ArangoConfigure configure;
    private ArangoDriver arangoDriver;

    public ArangoDbManager(String database, String collection) {

        this.collection = collection;
        this.configure = new ArangoConfigure();
        configure.setUser("root");
        configure.setPassword("zuwa45we");
        configure.setDefaultDatabase(database);
        configure.init();
        arangoDriver = new ArangoDriver(configure);
    }

    public boolean createDocument(DataSource dataSource) {

        try {
            arangoDriver.createDocument(collection, dataSource);
            System.out.println("Document created");
            return true;
        } catch (ArangoException e) {
            System.out.println("Failed to create document. " + e.getMessage());
            return false;
        }
    }

    public DataSource readDocument(int id) {

        DataSource dataSource = null;
        try {
            dataSource = arangoDriver.getDocument(collection, id, DataSource.class).getEntity();
        } catch (ArangoException e) {
            System.out.println("Failed to get document. " + e.getMessage());
        }
        return dataSource;
    }

    public boolean updateDocument(DataSource dataSource, int id) {

        try {
            arangoDriver.updateDocument(arangoDriver.getDocument(collection, id, DataSource.class)
                    .getDocumentHandle(), dataSource);
            return true;
        } catch (ArangoException e) {
            System.out.println("Failed to update document. " + e.getMessage());
            return false;
        }
    }

    public boolean deleteDocument(int id) {

        try {
            arangoDriver.deleteDocument(arangoDriver.getDocument(collection, id, DataSource.class)
                    .getDocumentHandle());
            return true;
        } catch (ArangoException e) {
            System.out.println("Failed to delete document. " + e.getMessage());
            return false;
        }
    }
}
