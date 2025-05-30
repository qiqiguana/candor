package original;

class FruitDistribution {
    /**
     * * In this task, you will be given a string that represents a number of apples and oranges
     * that are distributed in a basket of fruit this basket contains
     * apples, oranges, and mango fruits. Given the string that represents the total number of
     * the oranges and apples and an integer that represent the total number of the fruits
     * in the basket return the number of the mango fruits in the basket.
     * For example:
     * fruitDistribution("5 apples and 6 oranges", 19) ->19 - 5 - 6 = 8
     * fruitDistribution("0 apples and 1 oranges",3) -> 3 - 0 - 1 = 2
     * fruitDistribution("2 apples and 3 oranges", 100) -> 100 - 2 - 3 = 95
     * fruitDistribution("100 apples and 1 oranges",120) -> 120 - 100 - 1 = 19
     *
     * > fruitDistribution("5 apples and 6 oranges", 19)
     * 8
     * > fruitDistribution("5 apples and 6 oranges", 21)
     * 10
     * > fruitDistribution("0 apples and 1 oranges", 3)
     * 2
     * > fruitDistribution("1 apples and 0 oranges", 3)
     * 2
     * > fruitDistribution("2 apples and 3 oranges", 100)
     * 95
     * > fruitDistribution("2 apples and 3 oranges", 5)
     * 0
     * > fruitDistribution("1 apples and 100 oranges", 120)
     * 19
     */
    public static int fruitDistribution(String s, int n) {
        int apples = 0;
        int oranges = 0;
        int mangoes = 0;
        String[] words = s.split(" ");
        for (int i = 0; i < words.length; i++) {
            if (words[i].equals("apples")) {
                apples = Integer.parseInt(words[i - 1]);
            }
            if (words[i].equals("oranges")) {
                oranges = Integer.parseInt(words[i - 1]);
            }
        }
        mangoes = n - apples - oranges;
        return mangoes;
    }


}