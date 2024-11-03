package data_structure.heap

import data_structure.heap.Main.BinaryHeap.swap

import java.io.{ BufferedReader, InputStreamReader }
import scala.annotation.tailrec
import scala.collection.mutable.ArrayBuffer

object Main {

  final class BinaryHeap[T](private val capacity: Int)(val comparator: (T, T) => Boolean) extends Serializable {

    private val heap = new ArrayBuffer[T](capacity)

    def isEmpty: Boolean = heap.isEmpty

    def size: Int = heap.size

    def extract: Option[T] = {
      if (size == 0) {
        None
      } else {
        val root = heap(0)
        BinaryHeap.swap(0, size - 1)(heap)
        heap.remove(size - 1, 1)
        siftDown(0)
        Some(root)
      }
    }

    def insert(value: T): Unit = {
      heap.addOne(value)
      siftUp(size - 1)
    }

    def siftUp(idx: Int): Int = {
      var parent = (idx - 1) / 2
      var cursor = idx
      while (cursor > 0 && comparator(heap(cursor), heap(parent))) {
        swap(cursor, parent)(heap)
        cursor = parent
        parent = (cursor - 1) / 2
      }
      cursor
    }

    def setValue(idx: Int, value: T): Unit = heap(idx) = value

    @tailrec
    private def siftDown(idx: Int): Unit = {
      val left  = 2 * idx + 1
      val right = left + 1
      if (left < size) {
        var next = left
        if (right < next && comparator(heap(right), heap(left))) {
          next = right
        }
        if (comparator(heap(next), heap(idx))) {
          swap(next, idx)(heap)
          siftDown(next)
        }
      }
    }
  }

  object BinaryHeap {

    private def swap[T](i: Int, j: Int)(buffer: ArrayBuffer[T]): Unit = {
      val tmp = buffer(i)
      buffer(i) = buffer(j)
      buffer(j) = tmp
    }

    def apply[T](elems: T*)(comparator: (T, T) => Boolean): BinaryHeap[T] = {
      val heap = new BinaryHeap[T](elems.size)(comparator)
      elems.foreach(heap.insert)
      heap
    }

    def apply[T](comparator: (T, T) => Boolean): BinaryHeap[T] = new BinaryHeap[T](0)(comparator)
  }

  def main(args: Array[String]): Unit = {
    val reader       = new BufferedReader(new InputStreamReader(System.in))
    val heapCapacity = reader.readLine().toInt
    val heap         = new BinaryHeap[Int](heapCapacity)(_ > _)
    reader.readLine().split(" ").foreach(elem => heap.insert(elem.toInt))
    val queryCount = reader.readLine().toInt
    (0 until queryCount).foreach { _ =>
      val Array(idx, value) = reader.readLine().split(" ").map(_.toInt)
      heap.setValue(idx - 1, value)
      println(heap.siftUp(idx - 1) + 1)
    }
  }
}
