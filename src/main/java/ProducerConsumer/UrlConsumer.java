package ProducerConsumer;

import java.io.File;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

public class UrlConsumer {

    private BlockingQueue<Task> queue;

    public UrlConsumer(BlockingQueue<Task> queue) {
        this.queue = queue;
    }

    public void consume(Consumer<Task> consumer) {
        try {
                System.out.println("consume queue size" + queue.size());
                consumer.accept(queue.take());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void saveFile(File file) {
        System.out.println("Consumer save file with path" );
    }
}
