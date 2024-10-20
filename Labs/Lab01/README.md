# Course Schedule Challenges

This repository contains solutions to various coding challenges related to course scheduling. Below are the detailed problem descriptions.

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

