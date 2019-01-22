
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

class Set1Challenges {
    
    Set1 set1 = new Set1();
    
    public void challenge1() {
        String a = "49276d206b696c6c696e6720796f757220627261696e206c696b65206120706f69736f6e6f7573206d757368726f6f6d";

        System.out.println(a);
        System.out.println("Hidden phrase!");
        System.out.println(set1.HextoText(a));
        System.out.println("To base64:");
        System.out.println(set1.HextoBase64(a));
    }

    public void challenge2() {
        String a = "1c0111001f010100061a024b53535009181c";
        String b = "686974207468652062756c6c277320657965";
        String c = set1.FixedXor(a, b);

        System.out.println(a);
        System.out.println(b);
        System.out.println("Hidden phrase!");
        System.out.println(set1.HextoText(b));
        System.out.println("Xor of the strings:");
        System.out.println(c);
        System.out.println("Hidden phrase!");
        System.out.println(set1.HextoText(c));
    }

    public void challenge3() {
        String a = "1b37373331363f78151b7f2b783431333d78397828372d363c78373e783a393b3736";
        String b = set1.DecodeSingleByteXor(a);

        System.out.println(a);
        System.out.println("Solution in Hex:");
        System.out.println(b);
        System.out.println("The key is: " + set1.chave);
        System.out.println("Readable solution:");
        System.out.println(set1.HextoText(b));
    }

    public void challenge4() throws FileNotFoundException {
        File fil = new File("4.txt");
        Scanner arq;
        arq = new Scanner(fil);
        String a, b, res = "";
        char key = 0x00;
        int score = Integer.MAX_VALUE;

        while (arq.hasNextLine()) {
            a = arq.nextLine();
            b = set1.DecodeSingleByteXor(a);
            if (set1.minscore < score) {
                score = set1.minscore;
                res = b;
                key = set1.chave;
            }
        }
        arq.close();
        System.out.println("Solution in Hex:");
        System.out.println(res);
        System.out.println("The key is: " + key);
        System.out.println("Human readable:");
        System.out.println(set1.HextoText(res));
    }

    public void challenge5() {
        String a = "Burning 'em, if you ain't quick and nimble\nI go crazy when I hear a cymbal";
        String key = "ICE";
        System.out.println("Encrypt:");
        System.out.println(a);
        System.out.println("Under the key \"" + key + "\", using repating-key XOR.");
        String res = set1.RepeatKeyXor(a, key);
        System.out.println(res);
    }

    public void challenge6() throws FileNotFoundException {
        String a = "this is a test";
        String b = "wokka wokka!!!";

        System.out.println("Calculate Hamming distance:");
        System.out.println(a);
        System.out.println(b);
        System.out.println("Distance: " + set1.distance(a, b));

        File fil = new File("6.txt");
        Scanner arq;
        arq = new Scanner(fil);
        String file = "";

        while (arq.hasNextLine()) {
            a = arq.nextLine();
            file += a;
        }
        arq.close();
        a = set1.Base64toHex(file);
//        System.out.println(file);
//        System.out.println(file.length());
//        System.out.println(a);
//        System.out.println(a.length());
        b = set1.DecryptReapatKeyXor(a);
        System.out.println(b);
    }
}
