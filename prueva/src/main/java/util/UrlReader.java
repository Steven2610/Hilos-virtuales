package util;
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.regex.*;

public class UrlReader implements Runnable {
    private final String url;
    private int internalLinks = 0;

    public UrlReader(String url) {
        this.url = url;
    }

    @Override
    public void run() {
        try {
            URL urlObj = new URL(url);
            BufferedReader reader = new BufferedReader(new InputStreamReader(urlObj.openStream()));
            StringBuilder html = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                html.append(line);
            }

            reader.close();

            String domain = Utils.extractDomain(url); // o Procesos.getDomainName(url) si decides renombrar

            Set<String> internalUrls = new HashSet<>();

            Pattern pattern = Pattern.compile("href=[\"'](http[s]?://[^\"']+)[\"']");
            Matcher matcher = pattern.matcher(html.toString());

            while (matcher.find()) {
                String foundUrl = matcher.group(1);
                if (foundUrl.contains(domain)) {
                    internalUrls.add(foundUrl);
                }
            }

            internalLinks = internalUrls.size();
            System.out.println("Procesado: " + url + " -> " + internalLinks + " links internos.");
        } catch (Exception e) {
            internalLinks = -1;
            System.err.println("Error procesando URL " + url + ": " + e.getMessage());
        }
    }

    public String getUrl() {
        return url;
    }

    public int getInternalLinksCount() {
        return internalLinks;
    }
}