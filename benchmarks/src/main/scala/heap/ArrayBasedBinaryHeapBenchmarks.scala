package heap

import org.openjdk.jmh.annotations.{ Benchmark, OutputTimeUnit, Scope, Setup, State }

import java.util.concurrent.TimeUnit
import scala.collection.mutable.ListBuffer

@State(Scope.Benchmark)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
object ArrayBasedBinaryHeapBenchmarks {

  private val maxHeap: ArrayBasedBinaryHeap[Int] = new ArrayBasedBinaryHeap[Int](_ > _)

  private val testData = new ListBuffer[Int]()

  @Setup
  def setup(): Unit = (1000 to 1).foreach(id => testData.append(id))

  @Benchmark
  def insertBenchmark(): Unit = testData.foreach(id => maxHeap.insert(id))

  def main(args: Array[String]): Unit = org.openjdk.jmh.Main.main(args)
}
