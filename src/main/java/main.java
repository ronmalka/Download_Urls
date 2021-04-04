import ProducerConsumer.ProducerConsumer;
import UrlsKepper.UrlsKeeperSingleton;

public class main {

    private static ProducerConsumer producerConsumer;
    private final static UrlsKeeperSingleton urlsKeeperSingleton = UrlsKeeperSingleton.getInstance();

    public static void main(String[] args) {

        String url = "https://www.ynetnews.com" ; /*args[0];*/
        int maximum = 5 ; /*Integer.parseInt(args[1]);*/
        int depth = 2; /*Integer.parseInt(args[2]);*/
        boolean uniq = true; /*Boolean.parseBoolean(args[3]);*/

        init(url,maximum,depth,uniq);
        doTask(depth, maximum);
    }

    private static void doTask(int depth, int maximum){

        for (int i = 0; i < depth; i++){
            if (urlsKeeperSingleton.isEmpty()) break;
            int tasksNumber = (int) Math.pow(maximum,i);
            producerConsumer.createProducers(tasksNumber);
            producerConsumer.createConsumer(tasksNumber);
            producerConsumer.startProduceConsume();
            producerConsumer.clear();

        }

    }

    private static void init(String url, int maximum, int depth, boolean uniq) {
        urlsKeeperSingleton.setUniq(uniq);
        urlsKeeperSingleton.addUrl(url);
        producerConsumer = new ProducerConsumer(maximum,depth);
    }
}
