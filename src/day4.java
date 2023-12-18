import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class day4 {
  public static void main(String[] args) throws IOException {
    List<String> mini = Files.readAllLines(Paths.get("src", "day4_input_mini.txt"));
    List<String> input = Files.readAllLines(Paths.get("src", "day4_input.txt"));
    System.out.println("part1: " + part1(input));
    System.out.println("part2 mini: " + part2(mini));
    System.out.println("part2: " + part2(input));
  }

  private static int part2(List<String> input) {
    int total = 0;
    class Card {
      int amount;
      int coincidingCount;
      int cardNumber;
      public Card(String card) {
        this.cardNumber = Integer.parseInt(card.substring(5, card.indexOf(':')).trim());
        coincidingCount = 0;
        String[] split = card.substring(card.indexOf(':') + 1, card.indexOf('|')).trim().split("  ?");
        List<Integer> winningNumbers = new ArrayList<>();
        for (String s : split) winningNumbers.add(Integer.parseInt(s));
        List<Integer> cardNumbers = new ArrayList<>();
        split = card.substring(card.indexOf('|') + 1).trim().split("  ?");
        for (String s : split) cardNumbers.add(Integer.parseInt(s));
        for (int n : cardNumbers) {
          if (winningNumbers.contains(n)) {
            coincidingCount++;
          }
        }
        amount = 1;
      }
    }
    List<Card> cards = new ArrayList<>();
    for (String line : input) {
      cards.add(new Card(line));
    }
    for (int i = 0; i < cards.size(); i++) {
      Card card = cards.get(i);
      total += card.amount;
      for (int j = i + 1; j < cards.size() && j < i + 1 + card.coincidingCount; j++) {
        cards.get(j).amount += card.amount;
      }
    }
    return total;
  }

  public static int part1(List<String> lines) {
    int total = 0;
    HashSet<Integer> winningNumbers = new HashSet<>();
    for (String line : lines) {
      winningNumbers.clear();
      String[] winning = line.substring(line.indexOf(':') + 2, line.indexOf('|')).trim().split("  ?");
      for (int i = 0; i < winning.length; i++) {
        winningNumbers.add(Integer.parseInt(winning[i].trim()));
      }
      String[] myNumbers = line.substring(line.indexOf('|') + 2).trim().split("  ?");
      int cnt = 0;
      for (int i = 0; i < myNumbers.length; i++) {
        if (winningNumbers.contains(Integer.parseInt(myNumbers[i].trim())))
          cnt++;
      }
      total += cnt > 0 ? 1 << cnt - 1 : 0;
    }
    return total;
  }
}
