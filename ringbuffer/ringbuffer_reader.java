package ringbuffer;

import java.util.Optional;

public final class ringbuffer_reader<T> {

    private final ringbuffer<T> buffer;
    private long nextSeq;
    private long missed = 0;

    ringbuffer_reader(ringbuffer<T> buffer, long startSeq) {
        this.buffer = buffer;
        this.nextSeq = startSeq;
    }

    public Optional<T> read() {
        long oldest = buffer.oldestSeq();
        long newestExclusive = buffer.currentWriteSeq();

        if (nextSeq < oldest) {
            missed += (oldest - nextSeq);
            nextSeq = oldest;
        }

        if (nextSeq >= newestExclusive) {
            return Optional.empty();
        }

        Optional<T> out = buffer.readAt(nextSeq);
        if (out.isPresent()) nextSeq++;
        return out;
    }

    public long getMissedCount() {
        return missed;
    }
}