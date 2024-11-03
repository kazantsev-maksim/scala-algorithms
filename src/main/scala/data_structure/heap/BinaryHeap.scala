package data_structure.heap

import data_structure.heap.BinaryHeap.swap

import scala.annotation.tailrec
import scala.collection.mutable.ArrayBuffer

final class BinaryHeap[T](private val capacity: Int)(val comparator: (T, T) => Boolean)
    extends Heap[T]
    with Serializable {

  private val heap = new ArrayBuffer[T](capacity)

  override def isEmpty: Boolean = heap.isEmpty

  override def size: Int = heap.size

  override def extract: Option[T] = {
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

  override def insert(value: T): Unit = {
    heap.addOne(value)
    siftUp(size - 1)
  }

  @tailrec
  private def siftUp(idx: Int): Unit = {
    val parent = (idx - 1) / 2
    if (comparator(heap(idx), heap(parent))) {
      swap(idx, parent)(heap)
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
