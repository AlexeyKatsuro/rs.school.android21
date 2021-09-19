package com.alexeykatsuro.task4_storage.data

import com.alexeykatsuro.task4_storage.data.entities.Animal

fun animalsList(): List<Animal> {
    return listOf(
        Animal(id = 1, name = "Дарман", age = 1, breed = "Алабай"),
        Animal(id = 2, name = "Цезарь", age = 9, breed = "Доберман"),
        Animal(id = 3, name = "Одиссей", age = 2, breed = "Лабрадор"),
        Animal(id = 4, name = "Эмир", age = 3, breed = "Маламут"),
        Animal(id = 5, name = "Терра", age = 5, breed = "Мопс"),
        Animal(id = 6, name = "Эльба", age = 7, breed = "Немецкая овчарка"),
        Animal(id = 7, name = "Герда", age = 4, breed = "Пекинес"),
        Animal(id = 8, name = "Девид", age = 2, breed = "Питбуль"),
        Animal(id = 9, name = "Френк", age = 6, breed = "Ротвейлер"),
        Animal(id = 10, name = "Чип", age = 9, breed = "Стаффордширский терьер"),
        Animal(id = 11, name = "Спарки", age = 12, breed = "Такса"),
        Animal(id = 12, name = "Элвис", age = 7, breed = "Хаски"),
        Animal(id = 13, name = "Марли", age = 8, breed = "Цвергпинчер"),
        Animal(id = 14, name = "Тото", age = 4, breed = "Шарпей"),
        Animal(id = 15, name = "Дейзи", age = 1, breed = "Шпиц"),
        Animal(id = 16, name = "Олли", age = 10, breed = "Французский бульдог"),
    )
}