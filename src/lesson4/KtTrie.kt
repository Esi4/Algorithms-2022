package lesson4

import java.util.*
import kotlin.collections.ArrayList

/**
 * Префиксное дерево для строк
 */
class KtTrie : AbstractMutableSet<String>(), MutableSet<String> {

    private class Node {
        val children: SortedMap<Char, Node> = sortedMapOf()
    }

    private val root = Node()

    override var size: Int = 0
        private set

    override fun clear() {
        root.children.clear()
        size = 0
    }

    private fun String.withZero() = this + 0.toChar()

    private fun findNode(element: String): Node? {
        var current = root
        for (char in element) {
            current = current.children[char] ?: return null
        }
        return current
    }

    override fun contains(element: String): Boolean =
        findNode(element.withZero()) != null

    override fun add(element: String): Boolean {
        var current = root
        var modified = false
        for (char in element.withZero()) {
            val child = current.children[char]
            if (child != null) {
                current = child
            } else {
                modified = true
                val newChild = Node()
                current.children[char] = newChild
                current = newChild
            }
        }
        if (modified) {
            size++
        }
        return modified
    }

    override fun remove(element: String): Boolean {
        val current = findNode(element) ?: return false
        if (current.children.remove(0.toChar()) != null) {
            size--
            return true
        }
        return false
    }

    /**
     * Итератор для префиксного дерева
     *
     * Спецификация: [java.util.Iterator] (Ctrl+Click по Iterator)
     *
     * Сложная
     */
    override fun iterator(): MutableIterator<String> {
        return TrieIterator()
    }

    inner class TrieIterator internal constructor() : MutableIterator<String> {
        private val stack = LinkedList<String>()
        private var chars = ArrayList<String>()
        private var limit = root.children.size
        private var count = 0
        private var word = ""
        private var currentW = ""

        private fun pass() {
            if (count < limit) {
                word = chars[count]
                findNode(chars[count])?.let { passBranch(it) }
                count++
            }
        }

        private fun passBranch(node: Node) {
            val map = node.children
            for ((key, value) in map) {
                if (key.code == 0) stack.add(word)
                word += key
                passBranch(value)
            }
            if (word.isNotEmpty()) word = word.substring(0, word.length - 1)
        }

        init {
            val map = root.children
            map.forEach { chars.add(it.key.toString()) }
            pass()
        }

        //T = O(const)
        //R = O(1)
        override fun hasNext(): Boolean {
            return stack.isNotEmpty()
        }

        //T = O(const)
        //R = O(2N) N
        override fun next(): String {
            if (stack.peek() == null) throw NoSuchElementException()
            currentW = stack.poll()
            if (stack.peek() == null) pass()
            return currentW
        }

        //T = O(N)
        //R == O(1)
        override fun remove() {
            if (!remove(currentW)) throw IllegalStateException()
        }

    }

}