package util;


import java.io.*;
import java.net.URI;
import java.util.*;

public class Utils {

    public static List<String> loadUrlsFromFile(String filepath) throws IOException {
        Set<String> urls = new LinkedHashSet<>(java.nio.file.Files.readAllLines(new File(filepath).toPath()));
        return new ArrayList<>(urls);
    }

    public static void saveResultsToCsv(String filepath, List<UrlReader> results) throws IOException {
        try (PrintWriter writer = new PrintWriter(filepath)) {
            writer.println("URL;LinksInternos");
            for (UrlReader reader : results) {
                writer.printf("%s;%d%n", reader.getUrl(), reader.getInternalLinksCount());
            }
        }
    }

    public static String extractDomain(String url) {
        try {
            URI uri = new URI(url);
            String host = uri.getHost();
            if (host == null) return "";
            return host.startsWith("www.") ? host.substring(4) : host;
        } catch (Exception e) {
            return "";
        }
    }
}