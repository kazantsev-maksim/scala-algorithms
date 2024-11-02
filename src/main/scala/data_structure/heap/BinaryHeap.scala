package data_structure.heap

import data_structure.heap.BinaryHeap.swap

import scala.annotation.tailrec
import scala.collection.mutable.ArrayBuffer

sealed class BinaryHeap[T](private val capacity: Int)(implicit val ord: Ordering[T]) extends Heap[T] with Serializable {

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
    if (ord.lt(heap(idx), heap(parent))) {
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
      if (right < next && ord.lt(heap(right), heap(left))) {
        next = right
      }
      if (ord.lt(heap(next), heap(idx))) {
        swap(next, idx)(heap)
        siftDown(next)
      }
    }
  }
}

object BinaryHeap {

  private def swap[T](i: Int, j: Int)(buffer: ArrayBuffer[T]): Unit = {
    val tmp = buffer(i)
    buffer.updated(i, buffer(j))
    buffer.updated(j, tmp)
  }

  def apply[T](elems: T*)(implicit ord: Ordering[T]): BinaryHeap[T] = {
    val heap = new BinaryHeap[T](elems.size)
    elems.foreach(heap.insert)
    heap
  }

  def apply[T](implicit ord: Ordering[T]): BinaryHeap[T] = new BinaryHeap[T](0)
}
