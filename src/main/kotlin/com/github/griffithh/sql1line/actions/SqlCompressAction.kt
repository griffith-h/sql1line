package com.github.griffithh.sql1line.actions

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.command.WriteCommandAction

class SqlCompressAction : AnAction() {
    override fun actionPerformed(event: AnActionEvent) {
        val project = event.project ?: return
        val editor = event.getData(CommonDataKeys.EDITOR) ?: return
        val document = editor.document

        val selectionModel = editor.selectionModel
        if (!selectionModel.hasSelection()) return

        val selectedText = selectionModel.selectedText ?: return
        val compressedText = selectedText.replace(Regex("\\s+"), " ").trim()

        val start = selectionModel.selectionStart
        val end = selectionModel.selectionEnd

        WriteCommandAction.runWriteCommandAction(project) {
            document.replaceString(start, end, compressedText)
            editor.selectionModel.removeSelection()
        }
    }

    override fun update(event: AnActionEvent) {
        val editor = event.getData(CommonDataKeys.EDITOR)
        event.presentation.isEnabledAndVisible = editor?.selectionModel?.hasSelection() == true
    }
}