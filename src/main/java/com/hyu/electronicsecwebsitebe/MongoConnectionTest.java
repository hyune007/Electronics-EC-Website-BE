package com.hyu.electronicsecwebsitebe;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

@Component
public class MongoConnectionTest implements CommandLineRunner {

    private final MongoTemplate mongoTemplate;

    public MongoConnectionTest(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public void run(String... args) throws Exception {

        System.out.println("=== TEST CONNECT MONGODB ===");

        try {
            // Ping DB
            mongoTemplate.getDb().runCommand(new org.bson.Document("ping", 1));

            System.out.println("✅ MongoDB connected successfully!");

        } catch (Exception e) {
            System.out.println("❌ MongoDB connection failed!");
            e.printStackTrace();
        }
    }
}
