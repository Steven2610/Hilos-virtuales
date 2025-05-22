import util.*;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws Exception {
        List<String> urls = Utils.loadUrlsFromFile("C:/Users/Usuario/Downloads/Nueva carpeta (3)/url.txt");

        List<Thread> workers = new ArrayList<>();
        List<UrlReader> readers = new ArrayList<>();

        for (String url : urls) {
            UrlReader reader = new UrlReader(url);
            Thread t = new Thread(reader);
            workers.add(t);
            readers.add(reader);
            t.start();
        }

        for (Thread t : workers) {
            t.join();
        }

        Utils.saveResultsToCsv("resultados_modificado.csv", readers);
        System.out.println("Archivo 'resultados_modificado.csv' generado con éxito.");
    }
}
