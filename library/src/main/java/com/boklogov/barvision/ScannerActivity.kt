package com.boklogov.barvision

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions

class ScannerActivity : AppCompatActivity() {
    // Регистрируем контракт для запуска сканера и получения результата
    private val barcodeLauncher = registerForActivityResult(ScanContract()) { result ->
        if (result.contents == null) {
            // Пользователь нажал "Назад" или закрыл сканер
            Toast.makeText(this, "Сканирование отменено", Toast.LENGTH_SHORT).show()
        } else {
            // Штрихкод успешно отсканирован!
            val barcodeText = result.contents
            val barcodeFormat = result.formatName
            // Здесь вы можете обработать результат: сохранить, отправить в API и т.д.
            Toast.makeText(this, "Отсканировано: $barcodeText (Формат: $barcodeFormat)", Toast.LENGTH_LONG).show()
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