package ProducerConsumer;

import UrlsKepper.UrlsKeeperSingleton;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class ProducerConsumer {


    private int maximum;
    private int depth;

    private List<UrlProducer> urlProducers;
    private List<UrlConsumer> urlConsumers;
    private BlockingQueue<Task> queue;

    private final static UrlsKeeperSingleton urlsKeeperSingleton = UrlsKeeperSingleton.getInstance();

    public ProducerConsumer(int maximum, int depth) {
        this.maximum = maximum;
        this.depth = depth;
        queue = new ArrayBlockingQueue<>(maximum);
        urlProducers = new ArrayList<>();
        urlConsumers = new ArrayList<>();
    }

    public void createProducers(int tasksNumber) {
        int num = Math.min(maximum, tasksNumber);

        for (int i = 0; i < num; i++) {

            System.out.println("producer urlProducers size " + urlProducers.size());
            UrlProducer urlProducer = new UrlProducer(queue);
            urlProducers.add(urlProducer);
        }
    }

    public void createConsumer(int tasksNumber) {

        int num = Math.min(maximum, tasksNumber);

        for (int i = 0; i < num; i++) {

            UrlConsumer urlConsumer = new UrlConsumer(queue);
            urlConsumers.add(urlConsumer);
        }
    }

    public void startProduceConsume() {
        startProduce();
        startConsume();
    }


    private void startProduce() {
        for (UrlProducer urlProducer : urlProducers){

            final Supplier<Task> supplier =( () -> {
                String url;
                synchronized (UrlsKeeperSingleton.class){
                    url = urlsKeeperSingleton.getAndRemoveUrl();
                }
                if (url != null){
                    return new Task(url);
                }
                return null;
            });
            new Thread(() -> {
                for (int i = 0; i < urlProducers.size(); i++) {
                    urlProducer.produce(supplier);
                }
            }).start();
        }

    }

    private void startConsume() {
        for (UrlConsumer urlConsumer : urlConsumers){
            final Consumer<Task> consumer = (task) ->{
                if (task != null){
                    urlConsumer.saveFile(task.getFile());
                }
            };
            new Thread(() -> {
                for (int i = 0; i < urlConsumers.size(); i++) {
                    urlConsumer.consume(consumer);
                }
            }).start();
        }
    }

    public void clear() {
        urlProducers.clear();
        urlConsumers.clear();
    }
}
