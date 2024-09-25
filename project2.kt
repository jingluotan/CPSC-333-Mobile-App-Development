// Class: CPSC 333 Mobile App Development (Fall 2024)
// Instructor: Harley Davis
// Student Name: Keyu Chen
// Project: HW2 – Kotlin 2: Rock, Paper, Scissors
// Date: 9/24/2024
package project2

// Bonus: For each of your scenarios (win, loss, tie), have a collection (your choice) of responses (at least 5 each) to
// randomly choose from, e.g., your collection might have “Loss! Better luck next time!” as one choice when the
// player loses and the next round they lose they see “It’s a losing hand, bummer!” randomly chosen from the
// collection
// Enum class for loss replies
enum class replyLoss(val reply:String) {
    Loss1("Loss! Better luck next time!"),
    Loss2("Loss! Do not worry, you can win!"),
    Loss3("Loss! I wish you can win next time"),
    Loss4("It's computer Win. Better luck next time!"),
    Loss5("Oh no! The computer won this round.")
}

// Enum class for win replies
enum class replyWin(val reply:String) {
    Win1("You win! Good job!"),
    Win2("Victory is yours!"),
    Win3("You're on fire!"),
    Win4("Congratulation! You win"),
    Win5("You've crushed it!")
}

// Enum class for tie replies
enum class replyTie(val reply:String) {
    Tie1("No points, it's a tie!"),
    Tie2("It's a draw. Both played equally well!"),
    Tie3("Tie game, no winners this round."),
    Tie4("It's an even match!"),
    Tie5("Both hands are the same, it's a tie!")
}

// GameReply class provides random reply messages based on the game result (win, tie, loss)
class gameReply {

    // Returns a random loss message
    fun LossGame(): replyLoss {
        return replyLoss.values().random()
    }

    // Returns a random win message
    fun WinGame(): replyWin {
        return replyWin.values().random()
    }

    // Returns a random tie message
    fun TieGame(): replyTie {
        return replyTie.values().random()
    }
}

// project starts here
// Enum class representing possible hands (Rock, Paper, Scissors) and their emojis
enum class Hands(val emoji:String) {
    ROCK("\uD83D\uDC8E (Rock)!"),  // Rock emoji
    PAPER("\uD83E\uDDFB (Paper)!"),  // Paper emoji
    SCISSORS("\u2702\uFE0F (Scissors)!") // Scissors emoji
}

// Enum class representing the result of each round (TIE, WIN, LOSS)
enum class RoundResult {
    TIE,
    WIN,
    LOSS
}

// GamePlayer class tracks the player's statistics like wins, losses, ties, and calculates the win-loss ratio
class GamePlayer {

    // Properties to store the player's game stats
    var ties: Int = 0
        set(value) {
            if (value > 0) {
                field = value
            } else {
                throw IllegalArgumentException("Ties cannot be negative.")
            }
        }
    var wins: Int = 0
        set(value) {
            if (value > 0) {
                field = value
            } else {
                throw IllegalArgumentException("Wins cannot be negative.")
            }
        }
    var losses: Int = 0
        set(value) {
            if (value > 0) {
                field = value
            } else {
                throw IllegalArgumentException("Losses cannot be negative.")
            }
        }

    // Calculate the total number of games played
    val totalGamesPlayed: Int
        get() = wins + losses + ties

    // Calculate the win-loss ratio, if losses are zero, return wins as the ratio
    val winLossRatio: Double
        get() {
            return if (losses == 0) wins.toDouble() else wins.toDouble() / losses
        }

    // Initialize a welcome message when the player object is created
    init {
        println("Welcome to the Rock, Paper, Scissors simulator")
    }

    // Method to map the player's hand choice input to the Hands enum
    fun chooseHand(hand: Char): Hands {
        return when (hand) {
            'r' -> Hands.ROCK
            'p' -> Hands.PAPER
            's' -> Hands.SCISSORS
            else -> throw IllegalArgumentException("Invalid hand choice. Please enter ('r', 'p', or 's')")
        }
    }
}

// Game class handles the core logic of the rock-paper-scissors game
class Game() {

    // Randomly select the computer's hand choice
    fun rockPaperScissors(): Hands {
        return Hands.values().random()
    }

    // Determine the game result based on player and computer hand choices
    fun getGameResult(playerChoice: Hands, computerChoice: Hands): RoundResult {
        return when (playerChoice) {
            Hands.ROCK -> when (computerChoice) {
                Hands.ROCK -> RoundResult.TIE
                Hands.PAPER -> RoundResult.LOSS
                Hands.SCISSORS -> RoundResult.WIN
            }
            Hands.PAPER -> when (computerChoice) {
                Hands.ROCK -> RoundResult.WIN
                Hands.PAPER -> RoundResult.TIE
                Hands.SCISSORS -> RoundResult.LOSS
            }
            Hands.SCISSORS -> when (computerChoice) {
                Hands.ROCK -> RoundResult.LOSS
                Hands.PAPER -> RoundResult.WIN
                Hands.SCISSORS -> RoundResult.TIE
            }
        }
    }
}

// Main function, entry point of the program
fun main() {
    // Asking for player's name
    println("Player, what is your name?")
    val name = readLine()!!.toString()
    println("Welcome, $name")

    // Initializing player and game objects
    val player = GamePlayer()
    val game = Game()

    // Asking the player for the number of rounds to play
    var playRound: Int
    while(true) {
        try {
            println("How many rounds should we play?")
            playRound = readLine()!!.toInt()
            break
        } catch (e: NumberFormatException) {
            println("Invalid round choice, please enter only a number")
        }
    }

    // Loop through the number of rounds
    for (i in 1..playRound) {
        println("$name!, choose a hand! Rock, Paper, or Scissors? ('r', 'p', 's')")
        var playerChoice: Hands

        // Loop until the player provides a valid hand choice
        while (true) {
            try {
                val hand = readLine()!!.first().lowercaseChar()
                playerChoice = player.chooseHand(hand)
                break
            } catch (e: IllegalArgumentException) {
                println("Invalid hand choice, try again! Rock, Paper, or Scissors? ('r', 'p', 's')")
            }
        }

        // Computer randomly selects its hand
        val computerChoice = game.rockPaperScissors()
        println("$name throws a ${playerChoice.emoji}, The computer throws a ${computerChoice.emoji}")

        // Determine the result of the round
        val gameResult = game.getGameResult(playerChoice, computerChoice)
        when (gameResult) {
            RoundResult.WIN -> {
                val gameWin = gameReply().WinGame()
                println(gameWin.reply)
                player.wins++  // Increment the player's win counter
            }
            RoundResult.TIE -> {
                val gameTie = gameReply().TieGame()
                println(gameTie.reply)
                player.ties++  // Increment the player's tie counter
            }
            else -> {
                val gameLoss = gameReply().LossGame()
                println(gameLoss.reply)
                player.losses++  // Increment the player's loss counter
            }
        }

        // Prompt to press Enter to continue to the next round
        println("Press 'Enter' to continue...")
        readLine()
    }

    // End of the game summary
    val whiteFlag = "\uD83C\uDFF3"
    println("$whiteFlag That's it! GG! ${player.totalGamesPlayed} rounds played! $whiteFlag")
    println("Game Stats for $name")
    println("----------------------")
    println("Wins: ${player.wins}")
    println("Losses: ${player.losses}")
    println("Ties: ${player.ties}")
    println("Win-Loss Ratio: ${player.winLossRatio}")
}
