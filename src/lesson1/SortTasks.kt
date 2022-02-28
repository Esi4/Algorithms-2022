@file:Suppress("UNUSED_PARAMETER")

package lesson1

import java.io.File
import kotlin.math.roundToInt

/**
 * Сортировка времён
 *
 * Простая
 * (Модифицированная задача с сайта acmp.ru)
 *
 * Во входном файле с именем inputName содержатся моменты времени в формате ЧЧ:ММ:СС AM/PM,
 * каждый на отдельной строке. См. статью википедии "12-часовой формат времени".
 *
 * Пример:
 *
 * 01:15:19 PM
 * 07:26:57 AM
 * 10:00:03 AM
 * 07:56:14 PM
 * 01:15:19 PM
 * 12:40:31 AM
 *
 * Отсортировать моменты времени по возрастанию и вывести их в выходной файл с именем outputName,
 * сохраняя формат ЧЧ:ММ:СС AM/PM. Одинаковые моменты времени выводить друг за другом. Пример:
 *
 * 12:40:31 AM
 * 07:26:57 AM
 * 10:00:03 AM
 * 01:15:19 PM
 * 01:15:19 PM
 * 07:56:14 PM
 *
 * В случае обнаружения неверного формата файла бросить любое исключение.
 */
fun sortTimes(inputName: String, outputName: String) {
    TODO()
}

/**
 * Сортировка адресов
 *
 * Средняя
 *
 * Во входном файле с именем inputName содержатся фамилии и имена жителей города с указанием улицы и номера дома,
 * где они прописаны. Пример:
 *
 * Петров Иван - Железнодорожная 3
 * Сидоров Петр - Садовая 5
 * Иванов Алексей - Железнодорожная 7
 * Сидорова Мария - Садовая 5
 * Иванов Михаил - Железнодорожная 7
 *
 * Людей в городе может быть до миллиона.
 *
 * Вывести записи в выходной файл outputName,
 * упорядоченными по названию улицы (по алфавиту) и номеру дома (по возрастанию).
 * Людей, живущих в одном доме, выводить через запятую по алфавиту (вначале по фамилии, потом по имени). Пример:
 *
 * Железнодорожная 3 - Петров Иван
 * Железнодорожная 7 - Иванов Алексей, Иванов Михаил
 * Садовая 5 - Сидоров Петр, Сидорова Мария
 *
 * В случае обнаружения неверного формата файла бросить любое исключение.
 */
fun sortAddresses(inputName: String, outputName: String) {
    TODO()
}

/**
 * Сортировка температур
 *
 * Средняя
 * (Модифицированная задача с сайта acmp.ru)
 *
 * Во входном файле заданы температуры различных участков абстрактной планеты с точностью до десятых градуса.
 * Температуры могут изменяться в диапазоне от -273.0 до +500.0.
 * Например:
 *
 * 24.7
 * -12.6
 * 121.3
 * -98.4
 * 99.5
 * -12.6
 * 11.0
 *
 * Количество строк в файле может достигать ста миллионов.
 * Вывести строки в выходной файл, отсортировав их по возрастанию температуры.
 * Повторяющиеся строки сохранить. Например:
 *
 * -98.4
 * -12.6
 * -12.6
 * 11.0
 * 24.7
 * 99.5
 * 121.3
 *
 * T = O(N)
 * R = O(N)
 */
fun sortTemperatures(inputName: String, outputName: String) {
    val inputStream = File(inputName).readLines()
    val result = File(outputName).bufferedWriter()
    val list = mutableListOf<Int>()

    for (i in inputStream) {
        list.add((i.toDouble() * 10).toInt() + 2730)
    }

    val res = countingSort(list.toIntArray(), 7730)
    for (i in res) {
        val ans = i.toDouble() / 10 - 273
        val r = (ans * 100).roundToInt() / 100.0
        result.write("$r")
        result.newLine()
    }
    result.close()
}

/**
 * Сортировка последовательности
 *
 * Средняя
 * (Задача взята с сайта acmp.ru)
 *
 * В файле задана последовательность из n целых положительных чисел, каждое в своей строке, например:
 *
 * 1
 * 2
 * 3
 * 2
 * 3
 * 1
 * 2
 *
 * Необходимо найти число, которое встречается в этой последовательности наибольшее количество раз,
 * а если таких чисел несколько, то найти минимальное из них,
 * и после этого переместить все такие числа в конец заданной последовательности.
 * Порядок расположения остальных чисел должен остаться без изменения.
 *
 * 1
 * 3
 * 3
 * 1
 * 2
 * 2
 * 2
 *
 * R = O(N)
 * T = O(N log N)
 */
fun sortSequence(inputName: String, outputName: String) {
    val inputStream = File(inputName).readLines()
    val result = File(outputName).bufferedWriter()
    val maxRepeats = mutableListOf<Int>()
    val map = mutableMapOf<Int, Int?>()
    var count = 0

    for (i in inputStream) {
        if (!map.containsKey(i.toInt())) {
            map[i.toInt()] = 1
        } else {
            val keyPlus = map[i.toInt()]?.plus(1)
            map[i.toInt()] = keyPlus
            if (keyPlus != null && keyPlus > count) {
                maxRepeats.clear()
                count = keyPlus
                maxRepeats.add(i.toInt())
            }
            if (keyPlus != null && keyPlus == count) {
                maxRepeats.add(i.toInt())
            }
        }
    }

    maxRepeats.sort()
    val max = if (maxRepeats.isNotEmpty()) maxRepeats[0] else -1

    for (number in inputStream) {
        if (number.toInt() != max) {
            result.write(number)
            result.newLine()
        }
    }
    if (max > 0) {
        for (f in 1..map[max]!!) {
            result.write(max.toString())
            result.newLine()
        }
    }
    result.close()
}

/**
 * Соединить два отсортированных массива в один
 *
 * Простая
 *
 * Задан отсортированный массив first и второй массив second,
 * первые first.size ячеек которого содержат null, а остальные ячейки также отсортированы.
 * Соединить оба массива в массиве second так, чтобы он оказался отсортирован. Пример:
 *
 * first = [4 9 15 20 28]
 * second = [null null null null null 1 3 9 13 18 23]
 *
 * Результат: second = [1 3 4 9 9 13 15 20 23 28]
 */
// T = O(N log N)
// R = O(N)
fun <T : Comparable<T>> mergeArrays(first: Array<T>, second: Array<T?>) {
    var x = 0
    while (second[x] == null) {
        second[x] = first[x]
        x++
    }

    second.sort()
}

