public class TextJustifier {
    public static String lastLineJustify(String[] words, int padding, int countWords, int beg, int end){
        int spacesAfter = padding - (countWords - 1);//removing the single spaces

        StringBuilder result = new StringBuilder(words[beg]);
        for (int i = beg + 1; i < end; i++) {
            result.append(" " + words[i]);
        }

        for (int i = 0; i < spacesAfter; i++) {
            result.append(" ");
        }
        return result.toString();
    }

    public static String justify(String[] words, int padding, int countWords, int beg, int end){
        int spaces = padding / (countWords - 1);
        int extraSpaces = padding % (countWords - 1);
        StringBuilder result = new StringBuilder(words[beg]);

        for (int i = beg + 1; i < end; i++) {
            for (int j = 0; j < spaces; j++) {
                result.append(" ");
            }
            if(extraSpaces != 0){//distributing the extra spaces evenly in between the first words
                result.append(" ");
                --extraSpaces;
            }

            result.append(words[i]);
        }
        return result.toString();
    }
    
    public static String[] justifyText(String[] words, int maxWidth){
        StringBuilder resultBuilder = new StringBuilder();
        
        int beg = 0;
        int wordsLen = words.length;
        while(beg < wordsLen){
            int end = beg + 1;//points to the next word after the beginning of the line
            int lineLength = words[beg].length();

            while(end < wordsLen && (lineLength + (end - beg - 1) + words[end].length()) < maxWidth){
                lineLength += words[end].length();
                end++;
            }

            int padding = maxWidth - lineLength;
            int countWords = end - beg;

            if(countWords == 1 || end >= wordsLen){//last line
                resultBuilder.append(lastLineJustify(words,padding,countWords,beg,end) + '\n');
            }else{
                resultBuilder.append(justify(words,padding,countWords,beg,end)+ '\n');
            }
            beg = end;
        }
        String[] result = resultBuilder.toString().split("\n");
        return result;
    }
}
