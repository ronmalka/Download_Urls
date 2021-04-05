package ProducerConsumer;

import UrlsKepper.UrlsKeeperSingleton;

import java.util.concurrent.*;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class ProducerConsumer {


    private int maximum;
    private int depth;
    private int tasksNumber;

    private UrlProducer urlProducer;
    private UrlConsumer urlConsumer;
    private BlockingQueue<Task> queue;

    ExecutorService executorServiceProducers;
    ExecutorService executorServiceConsumers;


    public ProducerConsumer(int maximum, int depth, int tasksNumber) {
        this.maximum = maximum;
        this.depth = depth;
        this.tasksNumber = tasksNumber;

        queue = new ArrayBlockingQueue<>(maximum);

        executorServiceProducers = Executors.newFixedThreadPool(maximum);
        executorServiceConsumers = Executors.newFixedThreadPool(maximum);

        urlProducer = new UrlProducer(queue);
        urlConsumer = new UrlConsumer(queue);

    }


    public void startProduceConsume() {

        for (int i = 0; i < tasksNumber; i++) {
            executorServiceProducers.execute(this::startProduce);
            executorServiceConsumers.execute(this::startConsume);

            try {
                executorServiceProducers.awaitTermination(1000, TimeUnit.MILLISECONDS);
                executorServiceConsumers.awaitTermination(1000, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        executorServiceProducers.shutdown();
        executorServiceConsumers.shutdown();

    }


    private void startProduce() {

        final Supplier<Task> supplier = (() -> {
            String url;
            if (UrlsKeeperSingleton.isEmpty()){
                System.out.println("UrlsKeeperSingleton.isEmpty");
            }
            url = UrlsKeeperSingleton.getAndRemoveUrl();
            if (url.isEmpty()){
                System.out.println("url.isEmpty");
            }
            if (url != null) {
                return new Task(url, maximum, depth);
            }
            return null;
        });

        urlProducer.produce(supplier);

    }

    private void startConsume() {

        final Consumer<Task> consumer = (task) -> {
            if (task != null) {
                task.createAndSaveFile();
            }
        };

        urlConsumer.consume(consumer);

    }

}
