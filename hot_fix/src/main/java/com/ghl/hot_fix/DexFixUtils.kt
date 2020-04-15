package com.ghl.hot_fix

import android.content.Context
import android.util.Log
import dalvik.system.DexClassLoader
import dalvik.system.PathClassLoader
import java.io.File
import java.lang.reflect.Array
import java.util.*

const val DEX_DIR = "odex"

/**
 * User : wy
 * Date : 2016/12/13
 */
object FixDexUtils {
    private val loadDex = HashSet<File>()
    fun loadFixedDex(mContext: Context?) {
        if (mContext == null) {
            return
        }

        //遍历所有修复的dex
        val fileDir = mContext.getDir(DEX_DIR, Context.MODE_PRIVATE)
        val listFiles = fileDir.listFiles()
        Log.i("你好", "listFiles  " + listFiles.size)
        for (f in listFiles) {
            if (f.name.startsWith("classes") && f.name.endsWith(".dex")) {
                loadDex.add(f) //存入集合
                Log.i("你好", "存入集合")
            }
        }
        //dex合并
        doDexInject(mContext, fileDir, loadDex)
    }

    private fun doDexInject(appContext: Context, fileDir: File, loadDex: HashSet<File>) {
        val optimizeDir = fileDir.absolutePath + File.separator + "opt_dex"
        Log.i("你好", "optimizeDir   $optimizeDir")
        try {
            val fopt = File(optimizeDir)
            if (!fopt.exists()) {
                fopt.mkdirs()
            }

            //1.加载应用程序的dex
            val pathLoad = appContext.classLoader as PathClassLoader
            for (f in loadDex) {
                Log.i("你好", "循环")
                //2.加载指定的修复的dex文件。
                val classLoader = DexClassLoader(f.absolutePath, fopt.absolutePath, null, pathLoad)
                //3.合并
                val dexObj = getPathList(classLoader)
                val pathObj = getPathList(pathLoad)
                val mDexElementsList = getDexElements(dexObj)
                val pathDexElementsList = getDexElements(pathObj)

                //合并完成
                val dexElements = combineArray(mDexElementsList, pathDexElementsList)
                //重写给PathList里面的lement[] dexElements;赋值
                val pathList = getPathList(pathLoad)
                setField(pathList, pathList.javaClass, "dexElements", dexElements)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @Throws(Exception::class)
    private fun getPathList(baseDexClassLoader: Any): Any {
        return getField(baseDexClassLoader, Class.forName("dalvik.system.BaseDexClassLoader"), "pathList")
    }

    @Throws(Exception::class)
    private fun getDexElements(obj: Any): Any {
        return getField(obj, obj.javaClass, "dexElements")
    }

    @Throws(Exception::class)
    private fun getField(obj: Any, cl: Class<*>, filed: String): Any {
        val localField = cl.getDeclaredField(filed)
        localField.isAccessible = true
        return localField[obj]
    }

    @Throws(Exception::class)
    private fun setField(obj: Any, cl: Class<*>, field: String, value: Any) {
        val localField = cl.getDeclaredField(field)
        localField.isAccessible = true
        localField[obj] = value
    }

    /**
     * 两个数组合并
     * @param arrayLhs
     * @param arrayRhs
     * @return
     */
    private fun combineArray(arrayLhs: Any, arrayRhs: Any): Any {
        val localClass = arrayLhs.javaClass.componentType
        val i = Array.getLength(arrayLhs)
        val j = i + Array.getLength(arrayRhs)
        val result = Array.newInstance(localClass, j)
        for (k in 0 until j) {
            if (k < i) {
                Array.set(result, k, Array.get(arrayLhs, k))
            } else {
                Array.set(result, k, Array.get(arrayRhs, k - i))
            }
        }
        return result
    }

    init {
        Log.i("你好", "loadDex 清除")
        loadDex.clear()
    }
}