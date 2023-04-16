# Laufzeitumgebung (LZU)



# ComponentAssembler - Komponentenlebenszyklus und Konstruktion

Die `ComponentAssembler`-Klasse im bereitgestellten Code scheint ein Komponentenmodell zu implementieren, das einigen der Kriterien von Crnkovic's Framework entspricht, insbesondere in den Bereichen Komponentenlebenszyklus und Konstruktion.

## Lebenszyklus
Die `ComponentAssembler`-Klasse stellt Methoden zum Erstellen und Stoppen von Komponenteninstanzen bereit sowie zur Überprüfung des Zustands von Komponenten. Die Methode `createInstance()` erstellt einen neuen Thread, um die `start()`-Methode der Komponente aufzurufen, was als Startphase des Komponentenlebenszyklus betrachtet werden kann. Ebenso ruft die Methode `stopInstance()` die `stop()`-Methode der Komponente auf, was die Stoppphase des Komponentenlebenszyklus darstellt. 
Die Methode `deleteComponent()` stellt sicher, dass eine Komponente gestoppt ist, bevor sie aus der Map entfernt wird, was dem Lebenszyklusmuster entspricht, eine Komponente zuerst zu stoppen, bevor sie gelöscht wird.

## Konstruktion
Die `ComponentAssembler`-Klasse verwendet Java-Reflexion, um Komponentenklassen dynamisch aus JAR-Dateien zu laden und Instanzen zu erstellen. Die Methode `addComponent()` erstellt einen neuen Klassenlader für die Komponente und lädt die Hauptklasse der Komponente mit der Methode `loadClass()`. Anschließend erstellt sie eine neue Instanz der Hauptklasse mit Reflexion, was als Konstruktionsphase der Komponente betrachtet werden kann.

Insgesamt implementiert die `ComponentAssembler`-Klasse grundlegende Konzepte des Komponentenlebenszyklus und der Konstruktion gemäß Crnkovic's Framework.
