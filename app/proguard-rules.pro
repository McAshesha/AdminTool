# Добавьте сюда правила ProGuard для конкретного проекта.
# Управлять набором прикладных файлов конфигурации можно с помощью
# Настройка proguardFiles в build.gradle.
#
# Более подробную информацию см.
# http://developer.android.com/guide/developing/tools/proguard.html

# Если ваш проект использует WebView с JS, раскомментируйте следующее
# и укажите полное имя класса в интерфейсе JavaScript
# сорт:
#-keepclassmembers класс fqcn.of.javascript.interface.for.webview {
# публичный *;
#}

# Раскомментируйте это, чтобы сохранить информацию о номере строки для
# отладка трассировки стека.
#-keeppattributes SourceFile,LineNumberTable

# Если вы сохраните информацию о номере строки, раскомментируйте ее, чтобы
# скрыть исходное имя файла.
#-renamesourcefileattribute SourceFile