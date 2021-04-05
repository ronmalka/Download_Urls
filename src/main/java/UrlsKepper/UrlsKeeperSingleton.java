package UrlsKepper;


import java.util.HashSet;
import java.util.Iterator;

public class UrlsKeeperSingleton {

    private static HashSet<String> urlsSetToProduce;
    private static HashSet<String> urlsSetProduced;
    private static UrlsKeeperSingleton INSTANCE = null;
    private static boolean uniq = true; //set default as true

    private UrlsKeeperSingleton() {
        urlsSetToProduce = new HashSet<>();
        urlsSetProduced = new HashSet<>();
    }

    public static UrlsKeeperSingleton getInstance() {
        if (INSTANCE == null) {
            synchronized (UrlsKeeperSingleton.class) {
                if (INSTANCE == null) {
                    INSTANCE = new UrlsKeeperSingleton();
                }

            }
        }
        return INSTANCE;
    }

    public static boolean addUrl(String url) {


        if (uniq && urlsSetProduced.contains(url)) return false;
        urlsSetToProduce.add(url);
        urlsSetProduced.add(url);
        return true;
    }

    public static String getAndRemoveUrl() {
        Iterator<String> iterator = urlsSetToProduce.iterator();
        if (iterator.hasNext()) {
            String url = iterator.next();
            iterator.remove();
            return url;
        }

        return null;
    }

    public static void setUniq(boolean uniq) {
        UrlsKeeperSingleton.uniq = uniq;
    }

    public static boolean isEmpty() {

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return urlsSetToProduce.isEmpty();
    }


}
