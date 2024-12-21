package FinalManagement.Controller;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

public class MongoDBBase {
    protected static final String CONNECTION_STRING = "mongodb://localhost:27017/";
    protected static final String DATABASE_NAME = "BasisDataProject";

    protected MongoClient getMongoClient() {
        return MongoClients.create(CONNECTION_STRING);
    }

    protected MongoDatabase getDatabase(MongoClient mongoClient) {
        return mongoClient.getDatabase(DATABASE_NAME);
    }

    protected List<Document> findDocuments(String collectionName, Document filter) {
        List<Document> documents = new ArrayList<>();
        try (MongoClient mongoClient = getMongoClient()) {
            MongoCollection<Document> collection = getDatabase(mongoClient).getCollection(collectionName);
            try (MongoCursor<Document> cursor = collection.find(filter).iterator()) {
                while (cursor.hasNext()) {
                    documents.add(cursor.next());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return documents;
    }

    protected void insertDocument(String collectionName, Document document) {
        try (MongoClient mongoClient = getMongoClient()) {
            MongoDatabase database = getDatabase(mongoClient);
            MongoCollection<Document> collection = database.getCollection(collectionName);
            collection.insertOne(document);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected Document findFirstDocument(String collectionName, Document filter) {
        try (MongoClient mongoClient = getMongoClient()) {
            MongoCollection<Document> collection = getDatabase(mongoClient).getCollection(collectionName);
            return collection.find(filter).first();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected void updateDocument(String collectionName, Document filter, Document update) {
        try (MongoClient mongoClient = getMongoClient()) {
            MongoCollection<Document> collection = getDatabase(mongoClient).getCollection(collectionName);
            collection.updateOne(filter, update);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
