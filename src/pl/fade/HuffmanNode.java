package pl.fade;

class HuffmanNode {
    char symbol;
    int frequency;
    HuffmanNode left;
    HuffmanNode right;

    HuffmanNode(char symbol, int frequency) {
        this.symbol = symbol;
        this.frequency = frequency;
        this.left = null;
        this.right = null;
    }
}