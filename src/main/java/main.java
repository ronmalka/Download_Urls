import ProducerConsumer.ProducerConsumer;
import UrlsKepper.UrlsKeeperSingleton;

public class main {

    public static void main(String[] args) {
        UrlsKeeperSingleton.getInstance();
        String url = "https://www.ynetnews.com"; /*args[0];*/
        int maximum = 5; /*Integer.parseInt(args[1]);*/
        int depth = 5; /*Integer.parseInt(args[2]);*/
        boolean uniq = true; /*Boolean.parseBoolean(args[3]);*/

        init(url, uniq);
        doTask(depth, maximum);
        int i;
    }

    private static void doTask(int depth, int maximum) {
        for (int i = 0; i < depth; i++) {

            if(UrlsKeeperSingleton.isEmpty()) break;

            int tasksNumber = (int) Math.pow(maximum, i);
            ProducerConsumer producerConsumer = new ProducerConsumer(maximum, i, tasksNumber);
            producerConsumer.startProduceConsume();
        }

    }

    private static void init(String url, boolean uniq) {
        UrlsKeeperSingleton.setUniq(uniq);
        UrlsKeeperSingleton.addUrl(url);
    }
}
