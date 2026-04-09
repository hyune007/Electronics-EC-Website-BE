package com.hyu.electronicsecwebsitebe;
import com.mongodb.client.MongoDatabase;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.mongodb.client.MongoClient;

@Configuration
public class MongoConnectionTest {

    @Bean
    CommandLineRunner testConnection(MongoClient mongoClient) {
        return args -> {
            try {
                System.out.println("✅ Kết nối MongoDB thành công!");

                // 👉 Lấy database cụ thể
                MongoDatabase db = mongoClient.getDatabase("chatdatabase");

                System.out.println("📂 Database: " + db.getName());

                // 👉 List collections (ĐÚNG)
                db.listCollectionNames()
                        .forEach(col -> System.out.println("📁 Collection: " + col));

            } catch (Exception e) {
                System.out.println("❌ Kết nối thất bại!");
                e.printStackTrace();
            }
        };
    }
}
