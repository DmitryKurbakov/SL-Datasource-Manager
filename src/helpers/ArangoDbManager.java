package helpers;

import com.arangodb.ArangoConfigure;
import com.arangodb.ArangoDriver;
import com.arangodb.ArangoException;
import com.arangodb.entity.CollectionEntity;
import com.arangodb.entity.CollectionsEntity;
import data_object.DataSource;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by budae on 10.10.2016.
 */
public class ArangoDbManager {

    static final private String USER = "root";
    static final private String PASSWORD = "zuwa45we";

    private ArangoConfigure configure;
    private ArangoDriver arangoDriver;

    public ArangoDbManager() {

        this.configure = new ArangoConfigure();
        configure.setUser("root");
        configure.setPassword("zuwa45we");
        configure.init();
        arangoDriver = new ArangoDriver(configure);
    }

    public boolean createDocument(String database, String collection, DataSource dataSource) {

        try {
            arangoDriver.setDefaultDatabase(database);
            arangoDriver.createDocument(collection, dataSource);
            System.out.println("Document created");
            return true;
        } catch (ArangoException e) {
            System.out.println("Failed to create document. " + e.getMessage());
            return false;
        }
    }

    public DataSource readDocument(String database, String collection, String id) {

        arangoDriver.setDefaultDatabase(database);
        DataSource dataSource = null;
        try {
            dataSource = arangoDriver.getDocument(collection, id, DataSource.class).getEntity();
        } catch (ArangoException e) {
            System.out.println("Failed to get document. " + e.getMessage());
        }
        return dataSource;
    }

    public boolean updateDocument(String database, String collection, DataSource dataSource, String id) {

        arangoDriver.setDefaultDatabase(database);
        try {
            arangoDriver.updateDocument(arangoDriver.getDocument(collection, id, DataSource.class)
                    .getDocumentHandle(), dataSource);
            return true;
        } catch (ArangoException e) {
            System.out.println("Failed to update document. " + e.getMessage());
            return false;
        }
    }

    public boolean deleteDocument(String database, String collection, String id) {

        arangoDriver.setDefaultDatabase(database);
        try {
            arangoDriver.deleteDocument(arangoDriver.getDocument(collection, id, DataSource.class)
                    .getDocumentHandle());
            return true;
        } catch (ArangoException e) {
            System.out.println("Failed to delete document. " + e.getMessage());
            return false;
        }
    }

    public List<List<List<DataSource>>> readDatabases() {

        List<List<List<DataSource>>> allDatabases = new ArrayList<>();
        try {
            List<String> databases = arangoDriver.getDatabases(true).getResult();
            for (String databaseName : databases) {
                allDatabases.add(readCollections(databaseName));
            }

        } catch (ArangoException e) {
            e.printStackTrace();
        }

        return allDatabases;
    }

    public List<List<DataSource>> readCollections(String database) {

        arangoDriver.setDefaultDatabase(database);
        List<List<DataSource>> allColections = new ArrayList<>();
        try {
            CollectionsEntity collections = arangoDriver.getCollections(true);
            for (Object collectionName : collections.getNames().values().toArray()) {
                allColections.add(readDocuments(database, ((CollectionEntity) collectionName).getName()));
            }
        } catch (ArangoException e) {
            System.out.println(e.getMessage());
        }

        return allColections;
    }

    public List<DataSource> readDocuments(String database, String collection) {

        arangoDriver.setDefaultDatabase(database);
        List<DataSource> allDocuments = new ArrayList();

        try {
            List<String> documents = arangoDriver.getDocuments(collection);
            for (String documentName : documents) {
                allDocuments.add(readDocument(database, collection, documentName));
            }
        } catch (ArangoException e) {
            e.printStackTrace();
        }

        return allDocuments;
    }
}
