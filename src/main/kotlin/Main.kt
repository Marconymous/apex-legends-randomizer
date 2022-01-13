import java.io.FileWriter
import kotlin.system.exitProcess

fun main() {
    LegendGenerator.run()
}

object LegendGenerator {
    private val legends = readAllLegends()
    private var selectedLegends = readSelectedLegends()

    fun run() {
        while (true) {
            print("What do you want to do [h for help] > ")

            when (readLine()) {
                "h" -> printHelp()
                "a" -> addLegend()
                "d" -> removeLegend()
                "r" -> resetLegends()
                "e" -> exit()
                "l" -> listLegends()
                "g" -> generateLegend()
            }
        }
    }

    private fun generateLegend() {
        val iters = ((Math.random() * 30) + 20).toInt()

        for (i in 0..iters) {
            if (i != iters) {
                print("Generating Legend: ${selectedLegends[(Math.random() * selectedLegends.size).toInt()]} ${".".repeat(i % 3)}")

                Thread.sleep(100)

                print("\r")
                continue
            }

            println("Your legend is > ${selectedLegends[(Math.random() * selectedLegends.size).toInt()]}")
        }

    }

    private fun listLegends() {
        selectedLegends.forEach {
            println("   > $it")
        }
    }

    private fun exit() {
        saveLegends()

        exitProcess(0)
    }

    private fun saveLegends() {
        val fw = FileWriter(this::class.java.getResource("selected-legends.dat")?.file ?: run { error("Could not file File to save legends!"); })

        selectedLegends.forEach {
            println("saving -> $it")
            fw.append(it + "\n")
        }

        fw.flush()
        fw.close()
    }

    private fun resetLegends() {
        selectedLegends = legends.toMutableList()
    }

    private fun removeLegend() {
        println("Available Legends to remove: ${selectedLegends}")
        print("What legend do you want to remove [e to exit] > ")

        val legend = readLine()?.lowercase() ?: ""

        if (legend == "e")
            return

        if (selectedLegends.contains(legend)) {
            selectedLegends.remove(legend)
            println("Removed $legend from your legends!")
            return
        }

        removeLegend()
    }

    private fun addLegend() {
        println("Available Legends to add: ${legends.filter { !selectedLegends.contains(it) }}")
        print("What legend do you want to add [e to exit] > ")

        val legend = readLine()?.lowercase() ?: ""

        if (legend == "e")
            return

        if (legends.contains(legend) && !selectedLegends.contains(legend)) {
            selectedLegends.add(legend)
            println("Added $legend to your legends!")
            return
        }

        addLegend()
    }

    private fun printHelp() {
        println("Apex Legends Randomizer > Help")
        println("   - d | remove a legend from the list!")
        println("   - a | add a legend to the list!")
        println("   - r | reset legend list [add all]")
        println("   - e | exit program")
        println("   - g | generate random legend")
    }


    private fun readAllLegends(): List<String> {
        val fileContent = object {}.javaClass.getResource("legends.dat")?.readText() ?: ""

        return fileContent.split("\n")
    }

    private fun readSelectedLegends(): MutableList<String> {
        val fileContent = object {}.javaClass.getResource("selected-legends.dat")?.readText() ?: ""

        return fileContent.split("\n").toMutableList()
    }
}

