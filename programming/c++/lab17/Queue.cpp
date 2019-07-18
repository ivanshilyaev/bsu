#include "Queue.hpp"

void Queue::Erase() {}

void Queue::Clone(const Queue &Q) {}

Queue::Queue() {}

Queue::Queue(const Queue &Q) { Clone(Q); }

Queue::~Queue() { Erase(); }

Queue& Queue::operator=(const Queue &Q) {
    if (this!=&Q) {
        Erase();
        Clone(Q);
    }
    return *this;
}
