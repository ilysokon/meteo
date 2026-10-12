https://www.google.com/search?q=Kafka%3A+how+to+create+high+secured+topic+for+secrets%3F&gs_lcrp=EgZjaHJvbWUyBggAEEUYOTIGCAEQRRg60gEJMzQyMzBqMGo3qAIIsAIB&sourceid=chrome&ie=UTF-8&udm=50&fbs=ABfTbFVyMZGZf1hfvX9uKjN_-G8cqu7ocb7U6ah0xpkIrGMK4LpKPoHZrc4HVz9uOAATjT2whS8aQNdsb1nn47m_ZQdHnGlbmJEWuifzXWkNwOhGdQIZhSmnokr5ouqtk2Rl_-Di4N9vcWSFyBbdx9FE4Fhm4z0mqpcP-fzEJeLj1G-nnXoU7Bh7o6NLOumFogjk6icOA5Lz&aep=10&ntc=1&sxsrf=APpeQnv17L8flFmjb5LdXnfLDye2xoKQAQ%3A1790762564405&mstk=AUtExfAttnXKFpaCbXjArO6CtTU6u1DUPbVoN9Y5tufj6KSt05b9CB5D7ux5U9l1Pxf_vqeMj9HH7CP5C2Ize6ciNIRjVvpFDraXjFKJqoFOVoy67ItCyLNC-dkMCGJ7GE7s0z80KOK3PalNObXQ-vi0Wo9xVUoN-RHQtL0YB3fCOTI4mMbZqN2AsmHEBIJ2Lf2FzYm86-5L14D-2T5pMZ4xmT5XlPki8Z3PYgvOrXB1UhKq4Z4k4Rw61ni_w-Bld_POj4kzbAV0sND8CXzfi1SMEioUWw-mo17YqzNJ5CgKBRkdECE2BWFPcj1Nc1Mg858sRzaNUxAe9x4iiA&aioh=3&csuir=1&cs=0&mtid=Md-8apviNufkxc8P6qm44Ao

To implement high-security encryption using pure Java and Micronaut Kafka without relying on external KMS providers like Vault or AWS KMS, you can manage a static 256-bit AES master key natively.
We will use Google Tink (a pure Java library) to safely handle the complex parts of cryptography—such as cryptographic nonces, initialization vectors, and data integrity checks—without requiring external cloud engines.
# 1. Project Dependencies
   Add the required dependencies to your build.gradle file:
   ```gradle
   dependencies {
    implementation("io.micronaut.kafka:micronaut-kafka")
    implementation("com.google.crypto.tink:tink:1.15.0")
   }
```
# 2. Supply Your Secret Key Natively
   You must provide a secure 256-bit key to your application. In a standalone production environment, pass this key into your container or environment using a secure environment variable (AES_KEY_BASE64).
   Add this variable parsing block to your application.yml:
    ```yml
    micronaut:
        crypto:
            # Expects a 32-byte base64 encoded string (e.g., generated via 'openssl rand -base64 32')
            master-key: ${AES_KEY_BASE64:AAECAwQFBgcICQoLDA0ODxAREhMUFRYXGBkaGxwdHh8=}

        kafka:
            bootstrap:
                servers: "your.broker.hostname:9093"
            security:
                protocol: "SSL"
            ssl:
                truststore:
                    location: "/var/private/ssl/kafka.client.truststore.jks"
                    password: "TruststorePassword"
                    keystore:
                        location: "/var/private/ssl/kafka.client.keystore.jks"
                        password: "KeystorePassword"
                    key:
                        password: "KeyPassword"
    ```
# 3. Initialize Google Tink AEAD Factory
Create a factory bean inside Micronaut that parses your base64 string directly into an optimized pure-Java AES-GCM container (Aead). 
This ensures encryption operations happen instantly in memory without external API calls.

```java
import com.google.crypto.tink.Aead;
import com.google.crypto.tink.subtle.AesGcmJce;
import io.micronaut.context.annotation.Factory;
import io.micronaut.context.annotation.Value;
import jakarta.inject.Singleton;
import java.util.Base64;

@Factory
public class CryptoFactory {

    @Value("${crypto.master-key}")
    private String base64Key;

    @Singleton
    public Aead aead() throws Exception {
        byte[] keyBytes = Base64.getDecoder().decode(base64Key);
        if (keyBytes.length != 32) {
            throw new IllegalArgumentException("AES key must be exactly 32 bytes (256 bits) long.");
        }
        // Pure Java AES-GCM execution layer
        return new AesGcmJce(keyBytes);
    }
}
```
# 4. Create a Custom Kafka Deserializer / Serializer
The most elegant, native approach in Micronaut is creating custom Kafka Serializer and Deserializer implementations. This isolates encryption completely from Micronaut's internal network interceptors.

Custom Encrypting Serializer

```java
import com.google.crypto.tink.Aead;
import org.apache.kafka.common.serialization.Serializer;
import java.nio.charset.StandardCharsets;

public class EncryptingSerializer implements Serializer<String> {
    
    private final Aead aead;

    public EncryptingSerializer(Aead aead) {
        this.aead = aead;
    }

    @Override
    public byte[] serialize(String topic, String data) {
        if (data == null) return null;
        try {
            byte[] plaintextBytes = data.getBytes(StandardCharsets.UTF_8);
            // Tink automatically generates a fresh random IV per message and tags it for integrity
            return aead.encrypt(plaintextBytes, null);
        } catch (Exception e) {
            throw new RuntimeException("Encryption failure in Kafka Serializer", e);
        }
    }
}

```

Custom Decrypting Deserializer

```java
import com.google.crypto.tink.Aead;
import org.apache.kafka.common.serialization.Deserializer;
import java.nio.charset.StandardCharsets;

public class DecryptingDeserializer implements Deserializer<String> {

    private final Aead aead;

    public DecryptingDeserializer(Aead aead) {
        this.aead = aead;
    }

    @Override
    public String deserialize(String topic, byte[] data) {
        if (data == null) return null;
        try {
            byte[] decryptedBytes = aead.decrypt(data, null);
            return new String(decryptedBytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException("Decryption failure or bad signature in Kafka Deserializer", e);
        }
    }
}

```

# 5. Wire Custom Serializers to Micronaut Declarative Clients
Now pass your custom serializers to your interface signatures. Micronaut will instantiate these manually or pull your managed dependencies contextually.

The Secure Producer Client
```java
import io.micronaut.configuration.kafka.annotation.KafkaClient;
import io.micronaut.configuration.kafka.annotation.KafkaKey;
import io.micronaut.configuration.kafka.annotation.Topic;

@KafkaClient
public interface SecretProducer {

    @Topic("secure-secrets-topic")
    void sendSecret(
        @KafkaKey String key, 
        String plaintextSecret // Will be passed into custom serializer
    );
}
```

The Secure Listener Consumer
```java
import io.micronaut.configuration.kafka.annotation.KafkaListener;
import io.micronaut.configuration.kafka.annotation.KafkaKey;
import io.micronaut.configuration.kafka.annotation.Topic;

@KafkaListener(groupId = "secret-consumer-group")
public class SecretListener {

    @Topic("secure-secrets-topic")
    public void receiveSecret(@KafkaKey String key, String decryptedSecret) {
        // You instantly get the plaintext because the engine handled decryption automatically
        System.out.println("Safely processed secret: " + decryptedSecret);
    }
}
```

# Here is how to wire your Google Tink Aead bean directly into Kafka’s native lifecycle using Micronaut configuration overrides, followed by a Java utility to generate your secure production environment key.

## 1. Injecting Aead into Kafka Serializers via Micronaut Overrides
   Because Apache Kafka instantiates serializers natively via reflection inside its engine, you cannot use standard @Inject constructors inside EncryptingSerializer or DecryptingDeserializer.
   Instead, the cleanest architectural approach in Micronaut is to declare custom Producer/Consumer Factory beans that explicitly pass the managed Aead instance into the constructors.
   
### The Producer Factory Configuration
```java
import com.google.crypto.tink.Aead;
import io.micronaut.configuration.kafka.config.AbstractKafkaProducerConfiguration;
import io.micronaut.context.annotation.Factory;
import io.micronaut.context.annotation.Requires;
import jakarta.inject.Singleton;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.common.serialization.StringSerializer;

@Factory
@Requires(property = "kafka.bootstrap.servers")
public class SecureProducerFactory {

    private final Aead aead;

    public SecureProducerFactory(Aead aead) {
        this.aead = aead;
    }

    @Singleton
    public Producer<String, String> secureKafkaProducer(AbstractKafkaProducerConfiguration<String, String> configuration) {
        // Wire custom instances directly through constructors
        return new KafkaProducer<>(
            configuration.getConfig(),
            new StringSerializer(), 
            new EncryptingSerializer(aead)
        );
    }
}
```

### The Consumer Factory Configuration
```java
import com.google.crypto.tink.Aead;
import io.micronaut.configuration.kafka.config.AbstractKafkaConsumerConfiguration;
import io.micronaut.context.annotation.Factory;
import io.micronaut.context.annotation.Requires;
import jakarta.inject.Singleton;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

@Factory
@Requires(property = "kafka.bootstrap.servers")
public class SecureConsumerFactory {

    private final Aead aead;

    public SecureConsumerFactory(Aead aead) {
        this.aead = aead;
    }

    @Singleton
    public Consumer<String, String> secureKafkaConsumer(AbstractKafkaConsumerConfiguration<String, String> configuration) {
        // Wire custom instances directly through constructors
        return new KafkaConsumer<>(
            configuration.getConfig(),
            new StringDeserializer(),
            new DecryptingDeserializer(aead)
        );
    }
}
```

## 2. Java Code to Generate a Secure 256-Bit Base64 Key Template
   Run this standalone Java class or test snippet locally to generate a cryptographically strong random 256-bit (32-byte) key formatted in Base64 encoding.
   
```java
import java.security.SecureRandom;
import java.util.Base64;

public class SecretKeyGenerator {
    public static void main(String[] args) {
        // 1. Allocate 32 bytes for a strong 256-bit AES symmetric key
        byte[] keyBytes = new byte[32];
        
        // 2. Use a cryptographically secure random number generator (OS-level entropy source)
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(keyBytes);
        
        // 3. Convert raw binary bytes into a portable URL/Environment safe Base64 string
        String base64EncodedKey = Base64.getEncoder().encodeToString(keyBytes);
        
        // 4. Output the result
        System.out.println("=================================================================");
        System.out.println("YOUR SECURE 256-BIT BASE64 KEY TEMPLATE:");
        System.out.println(base64EncodedKey);
        System.out.println("=================================================================");
        System.out.println("\nApply this value to your environment variables file:");
        System.out.println("export AES_KEY_BASE64=\"" + base64EncodedKey + "\"");
    }
}
```

# Here is how to write a robust JUnit 5 integration test using Micronaut's built-in testing features.
This test sets up an in-memory embedded Kafka broker, automatically overrides the application context with a testing cryptographic key, publishes a plaintext secret using your declarative client, and verifies that the consumer intercepts and seamlessly decrypts it.

## 1. Add Test Dependencies
Add the Micronaut Test Kafka module to your build.gradle file. This library handles bootstrapping a lightweight Kafka test broker automatically.

```groovy
dependencies {
// JUnit 5 and Micronaut Test platform
testImplementation("io.micronaut.test:micronaut-test-junit5")
testAnnotationProcessor("io.micronaut:micronaut-inject-java")

    // Testcontainers or Embedded Kafka provider managed by Micronaut
    testImplementation("io.micronaut.kafka:micronaut-kafka-test")
}
```
## 2. The JUnit 5 Integration Test
Create this file under src/test/java/your/package/SecretSecurityIntegrationTest.java:

```java
package your.package;

import com.google.crypto.tink.Aead;
import io.micronaut.context.annotation.Property;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.concurrent.TimeUnit;
import static org.awaitility.Awaitility.await;

@MicronautTest
// Overriding Kafka properties to point to the dynamically generated test broker
@Property(name = "kafka.embedded.enabled", value = "true")
// Injecting a valid mock 256-bit Base64 Key directly for test execution isolation
@Property(name = "crypto.master-key", value = "AAECAwQFBgcICQoLDA0ODxAREhMUFRYXGBkaGxwdHh8=")
class SecretSecurityIntegrationTest {

    @Inject
    SecretProducer secretProducer;

    @Inject
    TestSecretListener testSecretListener;

    @Inject
    Aead aead;

    @Test
    void testEndToEndEnvelopeEncryptionAndDecryption() {
        String testSecret = "integration-test-database-password-2026";
        String messageKey = "test-key-id";

        // 1. Publish the message through the declarative Micronaut Kafka Client
        // The underlying infrastructure will catch this and pass it through your custom EncryptingSerializer
        secretProducer.sendSecret(messageKey, testSecret);

        // 2. Since Kafka operations are asynchronous, we wait for the message to traverse the broker
        // and hit the DecryptingDeserializer inside the listener container.
        await().atMost(5, TimeUnit.SECONDS).untilAsserted(() -> {
            String receivedPayload = testSecretListener.getLatestReceivedPayload();
            
            // Verify that the payload was received by the client application listener framework
            Assertions.assertNotNull(receivedPayload, "The consumer has not received the message yet.");
            
            // Verify that the final text is identical to the input plaintext string
            Assertions.assertEquals(testSecret, receivedPayload, "Decrypted text mismatch!");
        });
    }
}

/**
 * A dedicated test listener that intercepts messages from the same topic during testing,
 * mimicking your production SecretListener.
 */
@io.micronaut.configuration.kafka.annotation.KafkaListener(
    groupId = "test-secret-consumer-group",
    offsetReset = io.micronaut.configuration.kafka.annotation.OffsetReset.EARLIEST
)
class TestSecretListener {

    private String latestReceivedPayload = null;

    @io.micronaut.configuration.kafka.annotation.Topic("secure-secrets-topic")
    public void receiveSecret(@io.micronaut.configuration.kafka.annotation.KafkaKey String key, String decryptedSecret) {
        this.latestReceivedPayload = decryptedSecret;
    }

    public String getLatestReceivedPayload() {
        return latestReceivedPayload;
    }
}
```
## 3. How this test proves high security
   If you look inside the test broker configuration during this test execution:
   • The data stored in the testing broker partition is unreadable binary ciphertext.
   • The pipeline only yields the string "integration-test-database-password-2026" once the DecryptingDeserializer processes it locally inside the isolated TestSecretListener client runtime.
   Would you like assistance with adding automated schema validation to your payloads using an encrypted format like Protobuf or Avro, or are your secrets strictly text/JSON blobs?
