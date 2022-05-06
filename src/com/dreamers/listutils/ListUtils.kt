package com.dreamers.listutils

import com.google.appinventor.components.annotations.SimpleFunction
import com.google.appinventor.components.runtime.AndroidNonvisibleComponent
import com.google.appinventor.components.runtime.ComponentContainer
import com.google.appinventor.components.runtime.util.YailConstants
import com.google.appinventor.components.runtime.util.YailList
import gnu.mapping.ProcedureN

@Suppress("FunctionName")
class ListUtils(container: ComponentContainer) : AndroidNonvisibleComponent(container.`$form`()) {

    private val functionInvoker = FunctionInvoker(form)
    private val proceduresCache: MutableMap<String, ProcedureN> = mutableMapOf()

    /**
     * Get [ProcedureN] using its name. If the procedure doesn't exist in [proceduresCache], it finds the procedure using [FunctionInvoker.lookupProcedure].
     */
    private fun String.findProcedure(): ProcedureN {
        return if (proceduresCache.contains(this)) {
            proceduresCache[this]!!
        } else {
            val procedure = functionInvoker.lookupProcedure(this)
            proceduresCache[this] = procedure
            procedure
        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun <T> ProcedureN.call(vararg args: Any?): T {
        return functionInvoker.call(this, args.toList()) as T
    }

    private fun YailList.dropHeader(): List<Any?> = dropWhile { it == YailConstants.YAIL_HEADER }

    private fun List<Any?>.toYailList() = YailList.makeList(this)

    @SimpleFunction(
        description = "Returns a list containing only elements matching the given predicate. Provides item to the predicate."
    )
    fun Filter(list: YailList, predicate: String): YailList {
        val procedure = predicate.findProcedure()
        return list.dropHeader().filter {
            procedure.call(it)
        }.toYailList()
    }

    @SimpleFunction(
        description = "Returns a list containing only elements matching the given predicate. Provides index & item to the predicate."
    )
    fun FilterIndexed(list: YailList, predicate: String): YailList {
        val procedure = predicate.findProcedure()
        return list.dropHeader().filterIndexed { index, item ->
            procedure.call(index.inc(), item)
        }.toYailList()
    }

    @SimpleFunction(
        description = "Returns a list containing all elements not matching the given predicate. Provides item to the predicate."
    )
    fun FilterNot(list: YailList, predicate: String): YailList {
        val procedure = predicate.findProcedure()
        return list.dropHeader().filterNot {
            procedure.call(it)
        }.toYailList()
    }

    @SimpleFunction(
        description = "Returns a list containing all elements that are not null."
    )
    fun FilterNotNull(list: YailList): YailList {
        return list.dropHeader().filterNotNull().toYailList()
    }

    @SimpleFunction(
        description = "Performs the given action on each element. Provides item to the action."
    )
    fun ForEach(list: YailList, action: String) {
        val procedure = action.findProcedure()
        list.dropHeader().forEach {
            procedure.call(it)
        }
    }

    @SimpleFunction(
        description = "Performs the given action on each element. Provides index & item to the action."
    )
    fun ForEachIndexed(list: YailList, action: String) {
        val procedure = action.findProcedure()
        list.dropHeader().forEachIndexed { index, item ->
            procedure.call(index.inc(), item)
        }
    }

    @SimpleFunction(
        description = "Returns a list containing the results of applying the given transform function to each element in the original collection. Provides item to the action."
    )
    fun Map(list: YailList, action: String): YailList {
        val procedure = action.findProcedure()
        return list.dropHeader().map {
            procedure.call<Any?>(it)
        }.toYailList()
    }

    @SimpleFunction(
        description = "Returns a list containing the results of applying the given transform function to each element in the original collection. Provides index & item to the action."
    )
    fun MapIndexed(list: YailList, action: String): YailList {
        val procedure = action.findProcedure()
        return list.dropHeader().mapIndexed { index, item ->
            procedure.call<Any?>(index.inc(), item)
        }.toYailList()
    }

    @SimpleFunction(
        description = "Returns true if all elements match the given predicate. Provides item to the predicate."
    )
    fun VerifyAll(list: YailList, predicate: String): Boolean {
        val procedure = predicate.findProcedure()
        return list.dropHeader().all {
            procedure.call(it)
        }
    }

    @SimpleFunction(
        description = "Returns true if at least one element matches the given predicate. Provides item to the predicate."
    )
    fun VerifyAny(list: YailList, predicate: String): Boolean {
        val procedure = predicate.findProcedure()
        return list.dropHeader().any {
            procedure.call(it)
        }
    }

    @SimpleFunction(
        description = "Returns true if no elements match the given predicate.. Provides item to the predicate."
    )
    fun VerifyNone(list: YailList, predicate: String): Boolean {
        val procedure = predicate.findProcedure()
        return list.dropHeader().none {
            procedure.call(it)
        }
    }

    @SimpleFunction(
        description = "Returns the first element matching the given predicate, or null if no such element was found. Provides item to the predicate."
    )
    fun Find(list: YailList, predicate: String): Any? {
        val procedure = predicate.findProcedure()
        return list.dropHeader().find {
            procedure.call(it)
        }
    }

    @SimpleFunction(
        description = "Returns the last element matching the given predicate, or null if no such element was found. Provides item to the predicate."
    )
    fun FindLast(list: YailList, predicate: String): Any? {
        val procedure = predicate.findProcedure()
        return list.dropHeader().findLast {
            procedure.call(it)
        }
    }

    @SimpleFunction(
        description = "Returns a list containing all elements except first n elements."
    )
    fun Drop(list: YailList, count: Int): YailList {
        return list.dropHeader().drop(count).toYailList()
    }

    @SimpleFunction(
        description = "Returns a list containing all elements except first elements that satisfy the given predicate. Provides item to the predicate."
    )
    fun DropWhile(list: YailList, predicate: String): YailList {
        val procedure = predicate.findProcedure()
        return list.dropHeader().dropWhile {
            procedure.call(it)
        }.toYailList()
    }

    @SimpleFunction(
        description = "Returns a list containing all elements except last n elements."
    )
    fun DropLast(list: YailList, count: Int): YailList {
        return list.dropHeader().dropLast(count).toYailList()
    }

    @SimpleFunction(
        description = "Returns a list containing all elements except last elements that satisfy the given predicate. Provides item to the predicate."
    )
    fun DropLastWhile(list: YailList, predicate: String): YailList {
        val procedure = predicate.findProcedure()
        return list.dropHeader().dropLastWhile {
            procedure.call(it)
        }.toYailList()
    }

    @SimpleFunction(
        description = "Returns a list containing first n elements."
    )
    fun Take(list: YailList, count: Int): YailList {
        return list.dropHeader().take(count).toYailList()
    }

    @SimpleFunction(
        description = "Returns a list containing first elements satisfying the given predicate. Provides item to the predicate."
    )
    fun TakeWhile(list: YailList, predicate: String): YailList {
        val procedure = predicate.findProcedure()
        return list.dropHeader().takeWhile {
            procedure.call(it)
        }.toYailList()
    }

    @SimpleFunction(
        description = "Returns a list containing last n elements."
    )
    fun TakeLast(list: YailList, count: Int): YailList {
        return list.dropHeader().takeLast(count).toYailList()
    }

    @SimpleFunction(
        description = "Returns a list containing last elements satisfying the given predicate. Provides item to the predicate."
    )
    fun TakeLastWhile(list: YailList, predicate: String): YailList {
        val procedure = predicate.findProcedure()
        return list.dropHeader().takeLastWhile {
            procedure.call(it)
        }.toYailList()
    }

    @SimpleFunction(
        description = "Returns true if the given object is null."
    )
    fun IsNull(obj: Any?): Boolean {
        return obj != null
    }

}
