package ProducerConsumer;

import java.util.concurrent.BlockingQueue;
import java.util.function.Consumer;

public class UrlConsumer {

    private BlockingQueue<Task> queue;

    public UrlConsumer(BlockingQueue<Task> queue) {
        this.queue = queue;
    }

    public void consume(Consumer<Task> consumer) {

        try {

            consumer.accept(queue.take());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

}
