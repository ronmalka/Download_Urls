package ProducerConsumer;

/*
 * The Task class jobs are:
 *   1.download the Url content with JSOUP library
 *   2.add new URLs to UrlsKeeperSingleton
 *   3.make file from the URL content
 *   4.pass the file to the consumer to save it on the local device
 * */

import UrlsKepper.UrlsKeeperSingleton;
import org.apache.commons.validator.routines.UrlValidator;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class Task {

    private final String LINK_QUERY = "a[href]";
    private final String LINK_ATTR = "abs:href";
    private final String BASE_PATH = "src/main/output/";
    private final String PATH_REGEX = "[\\\\/:*?\"<>| ]";

    private int maximum, depth;
    private String url;
    private String result;
    private Document doc;


    public Task(String url, int maximum, int depth) {
        this.url = url;
        this.maximum = maximum;
        this.depth = depth;
    }

    public void doTask() {
        System.out.println("Start Task with url: " + url);

        try {
            doc = Jsoup.connect(url).get();
        } catch (Exception e) {
            e.printStackTrace();
        }

        downloadContentFromUrl();
        saveUrls();

    }

    private void downloadContentFromUrl() {

        System.out.println("start Task with url: " + url + " download content from url");
        result = String.valueOf(doc);

    }

    private boolean isValid(String url) {
        UrlValidator defaultValidator = new UrlValidator();
        return defaultValidator.isValid(url) && !url.isEmpty();
    }

    private void saveUrls() {
        System.out.println("start Task with url: " + url + " save urls");

        Elements links = doc.select(LINK_QUERY);
        int insertedLinks = 0;
        for (Element link : links) {

            if (insertedLinks >= maximum) break;

            String newUrl = link.attr(LINK_ATTR);
            if (isValid(newUrl) && UrlsKeeperSingleton.addUrl(newUrl)) {
                System.out.println("save url " + newUrl);
                insertedLinks++;
            }
        }


    }

    public void createAndSaveFile() {
        System.out.println("start Task with url: " + url + " create file");

        String pathStr = BASE_PATH + parsePath();

        Path path = Paths.get(pathStr);

        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            writer.write(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String parsePath() {

        return depth + url.replaceAll(PATH_REGEX, "_") + ".html";
    }

}
