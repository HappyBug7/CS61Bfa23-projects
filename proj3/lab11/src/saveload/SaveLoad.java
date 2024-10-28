package saveload;

import utils.FileUtils;

public class SaveLoad {

    public static void main(String[] args) {
        // We want to save the number of times that the file has been written to.
        // Make sure to use FileUtils!
        String fileName = "saveandload.txt";

        if (!FileUtils.fileExists(fileName)) {
            FileUtils.writeFile(fileName, "0");
        }

        String content = FileUtils.readFile(fileName);
        int countNum = Integer.parseInt(content);
        countNum++;
        FileUtils.writeFile(fileName, "" + countNum);
        // Make note of where the file is stored and check if the count has been incremented.
    }
}
