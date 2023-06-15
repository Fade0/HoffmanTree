package pl.fade;

import java.util.*;

public class Main {
    //mapa do przechowywania ostatecznego stringa z kodem
    private static Map<Character, String> symbolToCode;

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the input string: \n");
        String input = scanner.nextLine();
        input.strip();
        scanner.close();

        System.out.print("input: " + input + "\n");

        buildHuffmanTree(input);
        String encodedString = encodeString(input);
        System.out.println("Huffman Codes:");
        for (Map.Entry<Character, String> entry : symbolToCode.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
        System.out.println("Encoded String: " + encodedString);
    }

    public static void buildHuffmanTree(String input) {
        //Dodawanie do Hashmapy literek, zliczanie ich wystąpień
        Map<Character, Integer> symbolFrequency = new HashMap<>();
        for (char c : input.toCharArray()) {
            symbolFrequency.put(c, symbolFrequency.getOrDefault(c, 0) + 1);
        }

        //Porównywanie Nodeów na podstawie ich wartości aka frequency a potem alfabetu
        Comparator<HuffmanNode> nodeComparator = Comparator.comparingInt((HuffmanNode node) -> node.frequency)
                .thenComparing(node -> node.symbol);

        //przekazywanie do priorityQ nodeComparator jako argumentu
        // pozwala na przypisanie kolejki w odpowiedniej kolejności
        PriorityQueue<HuffmanNode> priorityQueue = new PriorityQueue<>(nodeComparator);


        //wyświetlanie wystąpień w ciągu znaków
        for (Map.Entry<Character, Integer> entry : symbolFrequency.entrySet()) {
            HuffmanNode node = new HuffmanNode(entry.getKey(), entry.getValue());
            priorityQueue.offer(node);
            System.out.println(node.symbol +" "+ node.frequency);
        }


        System.out.println("--------");
        //posortowane w kolejce elementy
        while (priorityQueue.size() > 1) {
            HuffmanNode leftChild = priorityQueue.poll();
            HuffmanNode rightChild = priorityQueue.poll();
            //Wyświetlanie
            System.out.println("leftChild: " + leftChild.symbol + " " + leftChild.frequency);
            System.out.println("rightChild: " + rightChild.symbol + " " + rightChild.frequency);

            HuffmanNode parent = new HuffmanNode('\0', leftChild.frequency + rightChild.frequency);
            parent.left = leftChild;
            parent.right = rightChild;
            System.out.println("parent: " + parent.symbol + " " + parent.frequency + " " + leftChild.symbol + " " + rightChild.symbol);
            priorityQueue.add(parent);
        }

        HuffmanNode root = priorityQueue.poll();
        System.out.println("Root frequency:" + root.frequency);

        symbolToCode = new HashMap<>();
        generateCodes(root, "");
    }
        //Zaczynając od korzenia i początkowego stringa w tym przypadku
    public static void generateCodes(HuffmanNode node, String code) {
        if (node.left == null && node.right == null) {
            symbolToCode.put(node.symbol, code);
            return;
        }

        generateCodes(node.right, code + "0");
        generateCodes(node.left, code + "1");
    }

    public static String encodeString(String input) {
        StringBuilder encodedString = new StringBuilder();
        for (char c : input.toCharArray()) {
            encodedString.append(symbolToCode.get(c));
        }
        return encodedString.toString();
    }
}
