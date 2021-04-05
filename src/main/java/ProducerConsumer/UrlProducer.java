package ProducerConsumer;

import java.util.concurrent.BlockingQueue;
import java.util.function.Supplier;

public class UrlProducer {
    private BlockingQueue<Task> queue;

    public UrlProducer(BlockingQueue<Task> queue) {
        this.queue = queue;
    }

    public void produce(Supplier<Task> supplier) {
        final Task task = supplier.get();

        if (task != null) {
            task.doTask();

            try {
                queue.put(task);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
