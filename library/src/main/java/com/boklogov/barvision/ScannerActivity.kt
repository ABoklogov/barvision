package com.boklogov.barvision

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions

class ScannerActivity : AppCompatActivity() {
    // Константа для ключа результата
    companion object {
        const val EXTRA_BARCODE_TEXT = "barcode_text"
        const val EXTRA_BARCODE_FORMAT = "barcode_format"
    }

    // Регистрируем контракт для запуска сканера и получения результата
    private val barcodeLauncher = registerForActivityResult(ScanContract()) { result ->
        if (result.contents == null) {
            // Пользователь отменил сканирование
            Toast.makeText(this, "Сканирование отменено", Toast.LENGTH_SHORT).show()
            setResult(RESULT_CANCELED)
            finish()
        } else {
            // Штрихкод успешно отсканирован!
            val barcodeText = result.contents
            val barcodeFormat = result.formatName
            
            // передаем результат
            val intent = Intent().apply {
                putExtra(EXTRA_BARCODE_TEXT, barcodeText)
                putExtra(EXTRA_BARCODE_FORMAT, barcodeFormat)
            }
            setResult(RESULT_OK, intent)
            finish() // Закрываем активити после сканирования
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Настраиваем параметры сканирования
        val options = ScanOptions()
        options.setPrompt("Наведите камеру на штрихкод") // Текст подсказки
        options.setBeepEnabled(true) // Включать звук при успешном сканировании
        options.setOrientationLocked(false) // Разрешить смену ориентации
        options.setDesiredBarcodeFormats(ScanOptions.ALL_CODE_TYPES) // Сканировать все типы

        // Запускаем сканер
        barcodeLauncher.launch(options)
    }
}