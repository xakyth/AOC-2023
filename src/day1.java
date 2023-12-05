import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class day1 {
  public static void main(String[] args) throws IOException {
    List<String> input = Files.readAllLines(Paths.get("src", "day1_input1.txt"));
    System.out.println("part1: " + sumOfCalibrationValues(input));
    System.out.println("part2: " + sumOfCalibrationValuesDigitsAsWords(input));
  }

  public static int sumOfCalibrationValues(List<String> allLines) {
    int totalSum = 0;
    for (String line : allLines) {
      int firstDigit = 0;
      int lastDigit = 0;
      for (int i = 0; i < line.length(); i++) {
        if (Character.isDigit(line.charAt(i))) {
          firstDigit = line.charAt(i) - '0';
          break;
        }
      }
      for (int i = line.length() - 1; i >= 0; i--) {
        if (Character.isDigit(line.charAt(i))) {
          lastDigit = line.charAt(i) - '0';
          break;
        }
      }
      totalSum += firstDigit * 10 + lastDigit;
    }
    return totalSum;
  }

  public static int sumOfCalibrationValuesDigitsAsWords(List<String> allLines) {
    int totalSum = 0;
    String[] digits = new String[] {
        "one", "two", "three",
        "four", "five", "six",
        "seven", "eight", "nine" };

    for (String line : allLines) {
      int[] firstOccurences = new int[] { -1, -1, -1, -1, -1, -1, -1, -1, -1 };
      int[] lastOccurences = new int[] { -1, -1, -1, -1, -1, -1, -1, -1, -1 };
      for (int i = 0; i < 9; i++) {
        firstOccurences[i] = line.indexOf(digits[i]);
        int occ = line.indexOf(Integer.toString(i+1));
        if (firstOccurences[i] == -1) {
          firstOccurences[i] = occ;
        } 
        else {
          if (occ != -1 && occ < firstOccurences[i])
            firstOccurences[i] = occ;
        }
      }
      for (int i = 0; i < 9; i++) {
        lastOccurences[i] = line.lastIndexOf(digits[i]);
        int occ = line.lastIndexOf(Integer.toString(i+1));
        if (lastOccurences[i] == -1) {
          lastOccurences[i] = occ;
        } 
        else {
          if (occ != -1 && occ > lastOccurences[i])
            lastOccurences[i] = occ;
        }
      }

      int temp = line.length();
      int temp2 = 0;
      for (int i = 0; i < firstOccurences.length; i++) {
        if (firstOccurences[i] != -1 && firstOccurences[i] < temp) {
          temp = firstOccurences[i];
          temp2 = i + 1;
        }
      }
      int firstDigit = temp2;
      temp = -1;
      for (int i = 0; i < lastOccurences.length; i++) {
        if (lastOccurences[i] > temp) {
          temp = lastOccurences[i];
          temp2 = i + 1;
        }
      }
      int lastDigit = temp2;
      totalSum += firstDigit * 10 + lastDigit;
    }

    return totalSum;
  }
}
