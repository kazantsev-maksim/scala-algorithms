package heap

import scala.annotation.tailrec
import scala.collection.mutable.ArrayBuffer

final class ArrayBasedBinaryHeap[T](val comparator: (T, T) => Boolean) extends Heap[T] with Serializable {

  private val heap = new ArrayBuffer[T]()

  override def isEmpty: Boolean = heap.isEmpty

  override def size: Int = heap.size

  override def extract: Option[T] = {
    if (size == 0) {
      None
    } else {
      val root = heap(0)
      swap(0, size - 1)
      heap.remove(size - 1, 1)
      siftDown(0)
      Some(root)
    }
  }

  override def insert(value: T): Unit = {
    heap.addOne(value)
    siftUp(size - 1)
  }

  @tailrec
  private def siftUp(idx: Int): Unit = {
    val parent = (idx - 1) / 2
    if (comparator(heap(idx), heap(parent))) {
      swap(idx, parent)
      siftUp(parent)
    }
  }

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
        swap(next, idx)
        siftDown(next)
      }
    }
  }

  private def swap(i: Int, j: Int): Unit = {
    val buffer = heap(i)
    heap(i) = heap(j)
    heap(j) = buffer
  }

}

object ArrayBasedBinaryHeap {

  def apply[T](elems: T*)(comparator: (T, T) => Boolean): ArrayBasedBinaryHeap[T] = {
    val heap = new ArrayBasedBinaryHeap[T](comparator)
    elems.foreach(heap.insert)
    heap
  }

  def apply[T](comparator: (T, T) => Boolean): ArrayBasedBinaryHeap[T] = new ArrayBasedBinaryHeap[T](comparator)
}
