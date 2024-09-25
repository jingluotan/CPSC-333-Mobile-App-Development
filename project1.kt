// Class: CPSC 333 Mobile App Development (Fall 2024)
// Instructor: Harley Davis
// Student Name: Keyu Chen
// Project: HW1 – Kotlin Basics – Random Number Ranking Game
// Date: 9/12/2024

package project1

class project1 {
}

fun main()
{
    do
    {
        val range = getNumberRange()
        if (range == null)
        {
            continue
        }
        // create a map to save data. In the beginning, all of the ranks are empty
        val map = mutableMapOf<String, Int?>()
        for (i: Int in 1..10)
        {
            map["Rank $i"] = null
        }
        if(!gameStart(range,map))
        {
            println("You are fail")
        }
        else
        {
            printRanks(map)
            println("You win!")
        }
        // bonus 1: Customize your program to ask the user if they would like to play again. This should happen after they
        //input ‘q’ or if you implement the third bonus task
        println("Do you want to play the game again? (y/n)")
        val choice: String? = readlnOrNull()
        if (choice == null || !choice.equals("y", true))
        {
            println("Thank you for playing")
            break
        }
    }
    while(true)
}

// this function for putting the number range to the game
fun getNumberRange(): IntRange?
{
    try
    {
        // bonus 2: Customize your program to prompt the user for a new range of numeric inputs, e.g., 1-500, 7000-10000,
        //etc. The game will then use that as the basis for random number generation. You also need to make this
        //new range visible on each turn
        println("which Number range you want to play?")
        println("what is your start number")
        val start = readLine()!!.toInt()
        println("what is your end number")
        val end = readLine()!!.toInt()
        if ((end - start) < 10)
        {
            println("The end number must be greater than the start number by 10")
            return null
        }
        val range = start..end
        return range
    }
    catch(e: NumberFormatException)
    {
        println("please put the correct number\n")
        return null
    }
}

// Print out the map
fun printRanks(list: Map<String, Int?>)
{
    for((word, value) in list)
    {
        println("$word: ${value ?: "-"}")
    }
}

// Start game
fun gameStart(range: IntRange, map: MutableMap<String, Int?>): Boolean
{
    var i = 10
    do
    {
        try
        {
            val randomNumber = range.random()
            println("Remaining placements: $i")
            println("Generated number: $randomNumber")
            printRanks(map)

            println("Enter the rank number (1-10) where you want to place a number, or 'q' to quit:")
            val num = readlnOrNull()

            if (num.equals("q", true))
            {
                return false
            }

            if (map["Rank $num"] != null)
            {
                println("A value placement requires an ascending numeric order")
                continue
            }

            if (num == null || num.toInt() !in 1..10)
            {
                println("Valid input is an integer between 1 and 10 or '1")
                continue
            }
            else
            {
                if (!validRank(randomNumber, num.toInt(), map))
                {
                    return false
                }
                map["Rank $num"] = randomNumber
                i--
            }
        }
        catch (e: NumberFormatException)
        {
            println("You can only put on an integer or q for quit")
        }
    }
    while (i > 0)
    return true
}

// Determine if the rank correct
fun validRank(number: Int, rank: Int, list: Map<String, Int?>): Boolean
{
    for(x in 0..(rank) )
    {
        val previousRank = list["Rank $x"]
        if (previousRank != null && previousRank >= number)
        {
            return false
        }
    }

    for(y in 10 downTo (rank))
    {
        val nextRank = list["Rank $y"]
        if(nextRank != null && nextRank <= number)
        {
            return false
        }
    }
    return true
}

