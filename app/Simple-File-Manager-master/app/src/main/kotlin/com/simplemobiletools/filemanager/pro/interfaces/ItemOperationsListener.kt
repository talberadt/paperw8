package com.simplemobiletools.filemanager.pro.interfaces

import com.simplemobiletools.commons.models.FileDirItem
import java.util.*

interface ItemOperationsListener {
    fun refreshFragment()

    fun deleteFiles(files: ArrayList<FileDirItem>)

    fun selectedPaths(paths: ArrayList<String>)

    fun searchQueryChanged(text: String)

    fun setupDateTimeFormat()

    fun setupFontSize()

    fun toggleFilenameVisibility()

    fun increaseColumnCount()

    fun reduceColumnCount()

    fun finishActMode()
}
