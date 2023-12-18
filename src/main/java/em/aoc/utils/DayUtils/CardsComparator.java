package em.aoc.utils.DayUtils;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class CardsComparator implements Comparator<String> {

  private final Boolean isPart2;

  public CardsComparator(boolean isPart2) {
    this.isPart2 = isPart2;
  }

  @Override
  public int compare(String o1, String o2) {
    if (getType(o1) == getType(o2)) {
      for (int i = 0; i < 5; i++) {
        if (getRank(o1.charAt(i)) > getRank(o2.charAt(i))) {
          return 1;
        }
        if (getRank(o1.charAt(i)) < getRank(o2.charAt(i))) {
          return -1;
        }
      }
    }
    return getType(o1) < getType(o2) ? -1 : 1;
  }

  private int getRank(char charAt) {
    switch (charAt) {
      case '2':
        return 1;
      case '3':
        return 2;
      case '4':
        return 3;
      case '5':
        return 4;
      case '6':
        return 5;
      case '7':
        return 6;
      case '8':
        return 7;
      case '9':
        return 8;
      case 'T':
        return 9;
      case 'J': {
        if (Boolean.TRUE.equals(isPart2)) {
          return 0;
        }
        return 10;
      }
      case 'Q':
        return 11;
      case 'K':
        return 12;
      case 'A':
        return 13;
      default:
        return 0;
    }
  }

  private long getType(String input) {
    Map<Character, Integer> chars = new HashMap<>();
    for (Character ch : input.toCharArray()) {
      chars.putIfAbsent(ch, 0);
      chars.put(ch, chars.get(ch) + 1);
    }
    if (Boolean.TRUE.equals(isPart2) && chars.containsKey('J')) {
      replaceJ(chars);
    }
    if (chars.size() == 1) {
      return 7;
    }
    if (chars.size() == 5) {
      return 1;
    }
    if (chars.size() == 4) {
      return 2;
    }
    int max = 0;
    for (Entry<Character, Integer> entry : chars.entrySet()) {
      if (max < entry.getValue()) {
        max = entry.getValue();
      }
    }
    if (chars.size() == 2) {
      if (max == 4) {
        return 6;
      }
      return 5;
    }
    if (chars.size() == 3) {
      if (max == 3) {
        return 4;
      }
      return 3;
    }
    return 0;
  }

  private void replaceJ(Map<Character, Integer> chars) {
    int max = 0;
    char newMaxChar = 0;
    if (chars.get('J') == 5) {
      return;
    }
    for (Entry<Character, Integer> entry : chars.entrySet()) {
      if (max < entry.getValue() && entry.getKey() != 'J') {
        max = entry.getValue();
        newMaxChar = entry.getKey();
      }
    }
    chars.put(newMaxChar, chars.get(newMaxChar) + chars.get('J'));
    chars.remove('J');
  }
}
