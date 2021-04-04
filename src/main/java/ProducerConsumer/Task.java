package ProducerConsumer;

/*
* The Task class jobs are:
*   1.download the Url content with JSOUP library
*   2.add new URLs to UrlsKeeperSingleton
*   3.make file from the URL content
*   4.pass the file to the consumer to save it on the local device
* */

import java.io.File;

public class Task {

    private String url;
    private File result;

    public Task(String url) {
        this.url = url;
    }

    public void doTask(){
        System.out.println("Start Task with url: " + url);

        downloadContentFromUrl();
        createFile();

    }

    private void downloadContentFromUrl() {
        System.out.println("Task with url: " + url + " download content from url");
    }

    public void saveUrls() {
        System.out.println("Task with url: " + url + " save urls");
    }

    private void createFile() {
        System.out.println("Task with url: " + url + " create file");
    }

    public File getFile() {
        System.out.println("Task with url: " + url + " return file");
        return result;
    }
}
