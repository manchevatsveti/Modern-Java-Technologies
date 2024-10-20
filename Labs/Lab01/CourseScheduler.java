
public class CourseScheduler {
    public static void selectionSortCourses(int[][] courses) {
        for (int i = 0; i < courses.length; i++) {
            int minIndex = i;
            for (int j = i+1; j < courses.length; j++) {
                if (courses[minIndex][1] > courses[j][1]){//prioritising the courses that end earlier
                    minIndex = j;
                }
            }
            if(minIndex != i){
                int[] temp = courses[i];
                courses[i] = courses[minIndex];
                courses[minIndex] = temp;
            }
        }
    }
    public static int maxNonOverlappingCourses(int[][] courses){
        if(courses.length == 0) return 0;

        selectionSortCourses(courses);
        int count = 1;
        int currEnd = courses[0][1];//the end of the earliest  course

        for (int i = 1; i < courses.length; i++) {
            if(courses[i][0] >= currEnd){
                count++;
                currEnd = courses[i][1];
            }
        }
        return count;
    }
}
