package heap

trait Heap[T] {

  def isEmpty: Boolean

  def size: Int

  def extract: Option[T]

  def insert(value: T): Unit
}
