# Building H2O exercise

Implement the constructor, `hydrogen`, and `oxygen` in `H2O.java`.
The starter deliberately has no synchronization. Add fields, imports, and helper
methods as needed, keeping both public method signatures and callback lines.

## Required behavior

One shared `H2O` object receives calls from separate hydrogen and oxygen threads.
Each thread calls its method once and supplies a callback that outputs its atom.

- Form each molecule from exactly two hydrogen threads and one oxygen thread.
- Wait for a complete set of three threads before running any of its callbacks.
  For example, a lone oxygen must wait for two hydrogens, and two hydrogens must
  wait for an oxygen.
- Call each supplied callback exactly once, on the thread that supplied it.
- Finish all three callbacks for one molecule before starting callbacks for
  the next molecule. Callbacks within a molecule may execute in any order.
- Allow every thread to finish once enough atoms have arrived. Do not rely on
  arrival order, thread names, sleeps, or busy waiting to enforce correctness.
- Keep separate `H2O` instances independent and support successive molecules
  using the same instance.

Use Java synchronization primitives of your choice. The `water` string belongs
to the test harness; your class does not receive it or know the molecule count.
Assume callbacks return normally. Recovery after a callback throws or a worker
is interrupted mid-molecule is outside this exercise's scope; keep the declared
`InterruptedException` in both methods.

## Examples and constraints

| Input | One valid output | Other valid outputs |
| --- | --- | --- |
| `HOH` | `HHO` | `HOH`, `OHH` |
| `OOHHHH` | `HHOHHO` | `HOHHHO`, `OHHHOH`, `HHOOHH` |

Divide the output into consecutive groups of three. Each must contain two `H`s
and one `O`. Having the correct totals alone is insufficient: `HHHOOH` is invalid.
The input describes the atoms to launch, not their actual scheduling order.

- `water.length == 3 * n`, with `1 <= n <= 20`.
- Each character is `H` or `O`.
- There are exactly `2 * n` hydrogens and `n` oxygens.

## Run the tests

In IntelliJ, open `H2OTest.java` and run `main`. No JUnit or assertion flags are
required. Alternatively, run from the project root with a JDK on your PATH:

```powershell
javac -d out src/H2O/H2O.java src/H2O/H2OTest.java
java -cp out H2O.H2OTest
```

The runner reports 13 cases and exits unsuccessfully if any fail. Failures are
expected until you solve the exercise; some output-only cases may pass by chance
with the starter. Tests accept all valid atom orders and cover both examples,
incomplete arrivals, maximum-size inputs, shuffled launches, repeated use,
independent instances, and a deliberately delayed callback.

Each batch has a five-second completion timeout. Failed workers are interrupted
and run as daemon threads so a deadlock cannot keep the test process alive.
The waiting tests use short observation windows after workers signal entry;
concurrency testing samples schedules and cannot prove correctness. Review why
your synchronization preserves the rules under every scheduling order.

For LeetCode submission, remove `package H2O;` and submit only the implementation
and any helpers it requires.
