# FizzBuzz multithreading exercise

One shared `FizzBuzz(n)` instance is used by four threads. Each thread calls its
assigned method once; that method handles all of its tokens from 1 through `n`.
The supplied callbacks perform the actual printing.

| Method | When to call its callback | Output |
| --- | --- | --- |
| `fizz` | Divisible by 3, but not 5 | `fizz` |
| `buzz` | Divisible by 5, but not 3 | `buzz` |
| `fizzbuzz` | Divisible by both 3 and 5 | `fizzbuzz` |
| `number` | Divisible by neither 3 nor 5 | The integer |

Produce exactly `n` tokens in increasing integer order, regardless of scheduling
or arrival order. Run callbacks on their assigned worker threads and finish each
callback before starting the next. All four methods must return when the sequence
is complete, including methods that have no tokens to print.

For `n = 15`, the output is:

```text
[1, 2, fizz, 4, buzz, fizz, 7, 8, fizz, buzz, 11, fizz, 13, 14, fizzbuzz]
```

For `n = 5`, the output is `[1, 2, fizz, 4, buzz]`.
Assume `1 <= n <= 50`, callbacks return normally, and workers are not interrupted
mid-sequence. Recovery from cancellation or callback failure is outside the scope
of this exercise. Each instance represents one sequence.

## Implementation

`FizzBuzz.java` contains a completed semaphore solution. The number semaphore
starts with one permit; the three word semaphores start with zero. The number
thread visits every integer from 1 through `n`, acquiring its permit before
either printing the integer or releasing the appropriate word semaphore.

After a callback finishes, its worker returns a permit to the number semaphore.
This handoff prevents the next token from being dispatched before the previous
callback completes. Each word worker processes only its matching integers, so
it knows how many permits to wait for and can terminate without a shutdown signal.
The loop counters are local to each thread, and `n` is immutable.

## Run the tests

In IntelliJ, open `FizzBuzzTest.java` and run `main`. No JUnit or assertion flags
are required. Alternatively, with a JDK on your PATH, run from the project root:

```powershell
javac -d out src/FizzBuzz/FizzBuzz.java src/FizzBuzz/FizzBuzzTest.java
java -cp out FizzBuzz.FizzBuzzTest
```

The completed implementation should pass all tests.
The runner reports 14 cases, covering examples, boundaries, all 50 allowed input
values, all 24 worker launch orders, delayed workers, slow callbacks, and concurrent
independent instances. It checks exact output order, callback thread identity,
callback overlap, and termination of all workers. Thread launch order does not
guarantee execution order; these tests sample schedules rather than prove correctness.

Each run has a five-second completion timeout. Failures produce a nonzero exit
code. Failed workers are interrupted and use daemon threads so a deadlock cannot
keep the process alive.

For LeetCode submission, remove `package FizzBuzz;` and submit the implementation
with its `Semaphore` and `IntConsumer` imports, without the test runner.
