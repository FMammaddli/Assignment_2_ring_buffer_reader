package ringbuffer;

import java.util.Optional;

public final class ringbuffer<T> {

    private final Object[] data;
    private final int capacity;
    private long writeSeq = 0;

    public ringbuffer(int capacity) {
        if (capacity <= 0) throw new IllegalArgumentException("Capacity must be > 0");
        this.capacity = capacity;
        this.data = new Object[capacity];
    }

    public synchronized void write(T item) {
        if (item == null) throw new IllegalArgumentException("Can't be null!");

        int i = (int) (writeSeq % capacity);
        data[i] = item;
        writeSeq++;
    }

    public synchronized ringbuffer_reader<T> createReader() {
        return new ringbuffer_reader<>(this, oldestSeqUnsafe());
    }

    synchronized long currentWriteSeq() {
        return writeSeq;
    }

    synchronized long oldestSeq() {
        return oldestSeqUnsafe();
    }

    private long oldestSeqUnsafe() {
        long oldest = writeSeq - capacity;
        return Math.max(0, oldest);
    }

    synchronized Optional<T> readAt(long seq) {
        long oldest = oldestSeqUnsafe();
        if (seq < oldest || seq >= writeSeq) return Optional.empty();

        int i = (int) (seq % capacity);
        @SuppressWarnings("unchecked")
        T value = (T) data[i];
        return Optional.ofNullable(value);
    }
}