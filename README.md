# Ring Buffer Assignment

## Overview
This project implements a ring buffer with the following:
- single writer
- multiple independent readers where each reader has its own position
- overwrite behavior when the buffer is full 

## Design (Main Responsibilities)

### RingBuffer<T>
- Owns the fixed array storage and the capacity
- Tracks the global write sequence number (writeSeq)
- write(item) stores at index writeSeq % capacity and increments writeSeq
- createReader() creates an independent RingBufferReader starting at the oldest available item

### RingBufferReader<T>
- Keeps its own nextSeq 
- read() reads independently without removing items for other readers
- If nextSeq is older than the buffer's oldest available data then the reader skips forward and counts missed items

### Pictures of Diagrams are included in the repo.
