import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class day7 {
  public static void main(String[] args) throws IOException {
    List<String> mini = Files.readAllLines(Paths.get("src", "day7_input_mini.txt"));
    List<String> input = Files.readAllLines(Paths.get("src", "day7_input.txt"));
    System.out.println("part 1 mini: " + part1(mini));
    System.out.println("part1: " + part1(input));
  }

  public static int part1(List<String> lines) {
    int total = 0;
    class Hand {
      int bid;
      String rawCards;
      int[] cards;
      int type;

      public Hand(String input) {
        cards = new int[14];
        String[] split = input.split(" ");
        for (Character card : split[0].toCharArray()) {
          cards[mapCard(card)]++;
        }
        rawCards = split[0];
        bid = Integer.parseInt(split[1]);
        int pairs = 0;
        int triples = 0;
        type = -1;
        for (int cardCount : cards) {
          switch (cardCount) {
            case 5:
              type = 6;
              break;
            case 4:
              type = 5;
              break;
            case 3:
              triples++;
              break;
            case 2:
              pairs++;
              break;
            default:
              break;
          }
        }
        if (type == -1) {
          if (pairs > 0 && triples > 0)
            type = 4;
          else if (pairs == 0 && triples > 0)
            type = 3;
          else if (pairs == 2 && triples == 0)
            type = 2;
          else if (pairs == 1 && triples == 0)
            type = 1;
          else
            type = 0;
        }
      }

      public int mapCard(char card) {
        if (Character.isDigit(card)) {
          return card - '2';
        }
        switch (card) {
          case 'T':
            return 9;
          case 'J':
            return 10;
          case 'Q':
            return 11;
          case 'K':
            return 12;
          case 'A':
            return 13;
          default:
            return -1;
        }
      }
    }
    List<Hand> handList = new ArrayList<>();
    for (String inputHand : lines) {
      handList.add(new Hand(inputHand));
    }
    handList.sort((Hand h1, Hand h2) -> {
      int res = Integer.compare(h1.type, h2.type);
      if (res != 0) return res;
      for (int i = 0; i < h1.rawCards.length(); i++) {
        res = Integer.compare(h1.mapCard(h1.rawCards.charAt(i)), h2.mapCard(h2.rawCards.charAt(i)));
        if (res != 0) return res;
      }
      return 0;
    });
    for (int i = 0; i < handList.size(); i++) {
      total += (i+1) * handList.get(i).bid;
    }
    return total;
  }
}
