## Task 1: Max Non-Overlapping Courses

### Problem Description
Niki is trying to maximize the number of non-overlapping courses he can attend on Wednesday. He has a list of desired courses with their start and end times. Your task is to write a function that calculates the maximum number of non-overlapping courses.

### Function Signature
```java
public static int maxNonOverlappingCourses(int[][] courses)
```

### Examples

| Invocation                                                                                     | Result |
|-----------------------------------------------------------------------------------------------|--------|
| `maxNonOverlappingCourses(new int[][]{{9, 11}, {10, 12}, {11, 13}, {15, 16}})`             | 3      |
| `maxNonOverlappingCourses(new int[][]{{19, 22}, {17, 19}, {9, 12}, {9, 11}, {15, 17}, {15, 17}})` | 4      |
| `maxNonOverlappingCourses(new int[][]{{19, 22}})`                                          | 1      |
| `maxNonOverlappingCourses(new int[][]{{13, 15}, {13, 17}, {11, 17}})`                     | 1      |

### Input Format
- An array of arrays, where each inner array contains two integers representing the start and end times of a course.

## Task 2: Text Justifier 📄

### Problem Description
Text editors, except for the most basic ones, allow various text formatting options, among which alignment (left, right, or justified) is one of the most commonly used features. As part of developing a formatting module, you will create a function that takes an array of strings (words) and a maximum width for printing. The function should return an array of strings representing lines of text, justified by inserting an appropriate number of spaces between words.

Each line of the formatted text must have a length equal to the specified maximum width. The maximum possible number of words must be packed into each line. Lines that can only fit a single word should remain left-aligned. The last line should be left-aligned, with single spaces between words, and filled with spaces to the right to meet the specified width. Each line must start and end with a word (it cannot start or end with a space). Only the last line and lines with a single word may end with a space.

The spaces on each line should be distributed as evenly as possible from left to right: the number of spaces between any two words should vary by at most one, and if any two words are separated by `k` spaces, all words to the left must be separated by at least `k` spaces.

For simplicity, assume that a word is a sequence of one or more arbitrary characters that have graphical representation (i.e., words do not contain spaces, tabs, new line characters, etc.). Also, assume that the given maximum width is a positive number greater than or equal to the length of the longest word, so you do not need to handle any word wrapping.

### Function Signature
```java
public static String[] justifyText(String[] words, int maxWidth) {
    // Your implementation here
}
```
## Examples

1. Given the input `{"The", "quick", "brown", "fox", "jumps", "over", "the", "lazy", "dog."}` and a maximum width of `11`, the output should be:
```arduino
   {"The   quick",
   "brown   fox",
   "jumps  over",
   "the    lazy",
   "dog.       "}
   ```
2. When invoked with {"Science", "is", "what", "we", "understand", "well", "enough", "to", "explain", "to", "a", "computer."} and a maximum width of 20, the output should be:
```arduino
   {"Science  is  what we",
   "understand      well",
   "enough to explain to",
   "a computer.         "}
```
