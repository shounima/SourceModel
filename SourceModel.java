////////////////////////////////
// Name: Shounima Naeem       //
// Assignment: CS1331 - hw2   //
// Due Date: 09/18/2018       //
////////////////////////////////

import java.util.Scanner;
import java.io.File;


public class SourceModel {
    private static String modelName;
    private static String fileName;
    private static double[][] array;
    private static double[] probability;
    private static String[] name;

    public static void main(String[] args) throws Exception {

        modelName = args[args.length - 1];
        probability = new double[args.length];

        double probSum = 0;

        for (int i = 0; i < args.length - 1; i++) {
            fileName = args[i];
            SourceModel ob = new SourceModel(modelName, fileName);
            probability[i] = probability(modelName);
            probSum += probability[i];

        }

        String mostLikely = args[0];
        double most = 0;
        System.out.println("Analyzing: " + modelName);

        for (int i = 0; i < args.length - 1; i++) {
            fileName = args[i];
            name = fileName.split("\\.");
            probability[i] = probability[i] / probSum;
            if (probability[i] > most) {
                most = probability[i];
                mostLikely = name[0];
            }

            System.out.printf("Probability that the test string is %s: %.2f\n",
                name[0], probability[i]);
        }

        System.out.println("Test string is most likely " + mostLikely);

    }

    public SourceModel(String modelName, String fileName) throws Exception {


        File file = new File(fileName);
        Scanner scan = new Scanner(file);
        array = new double[26][26];

        name = fileName.split("\\.");
        System.out.print("Training " + name[0] + " model ... ");
        modelName.toLowerCase();

        //counts the instances of the letters
        while (scan.hasNext()) {

            String word = scan.next().toLowerCase();
            word = word.replaceAll("[^a-zA-Z]", "");
            char previous;
            char current;
            int row = 0;
            int column = 0;
            for (int i = 1; i < word.length(); i++) {
                previous = word.charAt(i - 1);
                current = word.charAt(i);
                column = current - 97;
                row = previous - 97;
                array[row][column] = array[row][column] + 1;
            }

        }


    //makes an array of the sums from the matrix
        double[] sums = new double[26];
        sums[0] = 0;
        for (int i = 0; i < 26; i++) {
            for (int j = 0; j < 26; j++) {
                sums[i] = sums[i] + array[i][j];
            }
        }

    //makes a probability matrix

        for (int k = 0; k < 26; k++) {
            for (int l = 0; l < 26; l++) {
                if (sums[k] == 0) {
                    array[k][l] = 0.01;
                } else {
                    double num = array[k][l] / sums[k];
                    array[k][l] = num;
                }
                if (array[k][l] == 0) {
                    array[k][l] = 0.01;
                }
            }
        }


        System.out.print(" done\n");
    }

    public static String getName() {
        name = SourceModel.fileName.split("\\.");
        return name[0];
    }

    public static double probability(String test) {

        double prob = 1.0;
        char prev;
        char current;
        int row = 0;
        int column = 0;
        int index = 0;

        test = test.replaceAll("[^a-zA-Z]", "").toLowerCase();

        while (index < test.length() - 1) {
            prev = test.charAt(index);
            current = test.charAt(index + 1);
            row = prev - 97;
            column = current - 97;
            prob = prob * array[row][column];


            index++;
        }
        return prob;
    }

    public String toString() {
        System.out.println("Model: " + name[0]);
        String line = "  ";
        int letters = 97;
        while (letters < 123) {
            line += (char) letters + "    ";
            letters++;
        }
        line += "\n";
        for (int i = 0; i < 26; i++) {
            line += (char) (i + 97) + " ";
            for (int j = 0; j < 26; j++) {
                String result = String.format("%.2f", array[i][j]);
                line += result + " ";
            }

            line += "\n";
        }
        return line;
    }
}
