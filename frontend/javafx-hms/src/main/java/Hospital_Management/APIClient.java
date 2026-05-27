package Hospital_Management;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class APIClient {

    private static final String BASE_URL = "https://medilink-06px.onrender.com";

    private static final HttpClient client = HttpClient.newHttpClient();

    public static String get(
            String endpoint)
            throws IOException,
            InterruptedException {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL+ endpoint))
                .GET()
                .build();

        return client.send(
                request,
                HttpResponse.BodyHandlers
                        .ofString())
                .body();
    }

}
