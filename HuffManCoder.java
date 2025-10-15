 import java.util.*;
 import java.util.HashMap;
 import java.util.Map;
 import java.util.PriorityQueue;



// --------------------- Node Class ---------------------
class Node implements Comparable<Node> {
    Character data;
    int cost; // frequency
    Node left;
    Node right;

    public Node(Character data, int cost) {
        this.data = data;
        this.cost = cost;
        this.left = null;
        this.right = null;
    }

    @Override
    public int compareTo(Node other) {
        return this.cost - other.cost;
    }
}
// --------------------- HuffmanCoder Class ---------------------
public class HuffManCoder {
    HashMap<Character, String> encoder;  // For encoding
    HashMap<String, Character> decoder;  // For decoding

    // 🔹 Constructor
    public HuffManCoder(String feeder) throws Exception {
        // Step 1: Frequency map
        HashMap<Character, Integer> fmap = new HashMap<>();
        for (int i = 0; i < feeder.length(); i++) {
            char cc = feeder.charAt(i);
            fmap.put(cc, fmap.getOrDefault(cc, 0) + 1);
        }

        // Step 2: Min-Heap
        PriorityQueue<Node> minHeap = new PriorityQueue<>();

        for (Map.Entry<Character, Integer> entry : fmap.entrySet()) {
            Node node = new Node(entry.getKey(), entry.getValue());
            minHeap.add(node);
        }

        // Step 3: Build Huffman Tree
        while (minHeap.size() > 1) {
            Node first = minHeap.poll();
            Node second = minHeap.poll();

            Node newNode = new Node(null, first.cost + second.cost);
            newNode.left = first;
            newNode.right = second;

            minHeap.add(newNode);
        }

        // Step 4: Get root
        Node root = minHeap.poll();

        // Step 5: Initialize maps
        encoder = new HashMap<>();
        decoder = new HashMap<>();

        initEncoderDecoder(root, "");
    }

    // 🔹 Recursive method to fill encoder & decoder
    private void initEncoderDecoder(Node root, String path) {
        if (root == null)
            return;

        // Leaf node condition
        if (root.left == null && root.right == null && root.data != null) {
            encoder.put(root.data, path);
            decoder.put(path, root.data);
            return;
        }

        initEncoderDecoder(root.left, path + "0");
        initEncoderDecoder(root.right, path + "1");
    }

    // 🔹 Encode a string using the encoder map
    public String encode(String source) {
        StringBuilder sb = new StringBuilder();
        for (char c : source.toCharArray()) {
            sb.append(encoder.get(c));
        }
        return sb.toString();
    }

    // 🔹 Decode a Huffman binary string
    public String decode(String encodedStr) {
        StringBuilder sb = new StringBuilder();
        String key = "";
        for (int i = 0; i < encodedStr.length(); i++) {
            key += encodedStr.charAt(i);
            if (decoder.containsKey(key)) {
                sb.append(decoder.get(key));
                key = "";
            }
        }
        return sb.toString();
    }

    // 🔹 Main Method to test
    public static void main(String[] args) throws Exception {
        HuffManCoder coder = new HuffManCoder("AABBCD");

        System.out.println("Encoder Map: " + coder.encoder);
        System.out.println("Decoder Map: " + coder.decoder);

        String encoded = coder.encode("ABCD");
        System.out.println("Encoded: " + encoded);

        String decoded = coder.decode(encoded);
        System.out.println("Decoded: " + decoded);
    }
}
