# App Shell Команды

_r, R, run_: Начать тестирование
_y, Y, yes_: Согласиться на обработку личных данных
_n, N, no_: Не согласиться на обработку личных данных
_quit_ Завершение работы

# Описание параметров в файле application.yaml

**_properties:_**

В тестировании 3 языка RU EN BY
за выбор отвечает параметр

**_locale: be_BY_**

Количество баллов необходимых для успешного прохождения теста

**_needScoreForPass: 3_**

Название фвйла с фопросами формируется из 3 параметров

Имя файла **_filename: "Questions\_" _**

Локаль **_locale: be_BY_**

Расширение файла **_fileExtension: ".csv"_**

например Questions_be_BY.csv

**_AOP:_**

Для включения аспекта который переведет все сообщения в верхний регистр

**_aspectUpperCase: false_**
