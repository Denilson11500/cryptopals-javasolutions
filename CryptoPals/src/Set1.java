
import java.nio.charset.StandardCharsets;

public class Set1 {

    private final char[] digitoshex = {'0', '1', '2', '3', '4', '5', '6', '7',
        '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    private final char[] digitos64 = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H',
        'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P',
        'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X',
        'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f',
        'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',
        'o', 'p', 'q', 'r', 's', 't', 'u', 'v',
        'w', 'x', 'y', 'z', '0', '1', '2', '3',
        '4', '5', '6', '7', '8', '9', '+', '/'};
    //String etaion is used for evaluate decryprion.
    private final String etaoin = "etaoin srhldcumfpgwybvkxjqzETAOINSRHLDCUMFPGWYBVKXJQZ0123456789.?!";

    byte[] HextoByte(String entrada) {
        byte[] binario;
        if (entrada.equals("0")) {
            binario = new byte[1];
            binario[0] = 0;
            System.out.println("Input was zero.");
            return binario;
        }
        char c1, c2;
        int v1, v2;
        int n = entrada.length();
        if (n % 2 == 1) {
            n++;
            entrada = "0" + entrada;
            System.out.println("Warning input had odd digits.");
        }
        n = n / 2;//2 digits Hex have 8 bits == 1 byte
        binario = new byte[n];
        for (int i = 0; i < entrada.length(); i += 2) {
            c1 = entrada.charAt(i);
            c2 = entrada.charAt(i + 1);
            v1 = ValorDoDigito(c1);
            v2 = ValorDoDigito(c2);
            binario[i / 2] = 0;
            binario[i / 2] = (byte) ((v1 << 4) + v2);
        }
        return binario;
    }

    private int ValorDoDigito(char c) {
        char cmin = Character.toLowerCase(c);
        for (int i = 0; i < digitoshex.length; i++) {
            if (cmin == digitoshex[i]) {
                return i;
            }
        }
        System.out.println("Input is not Hexadecimal.");
        return 0;
    }

    String HextoText(String entrada) {
        byte[] binario;
        binario = HextoByte(entrada);
        String res = new String(binario, StandardCharsets.UTF_8);
        return res;
    }

    String BytetoHex(byte[] binario) {
        String res = "";
        for (int i = 0; i < binario.length; i++) {
            res += digitoshex[(binario[i] & 0xF0) >> 4];
            res += digitoshex[(binario[i] & 0x0F)];
        }
        return res;
    }

    String BytetoText(byte[] binario) {
        return HextoText(BytetoHex(binario));
    }

    String HextoBase64(String hex) {
        byte a, b, c;
        int comp;
        byte[] binario = HextoByte(hex);
        String res = "";
        for (int i = 0; i < binario.length; i += 3) {
            comp = ((binario.length - i) == 1) ? 1 : ((binario.length - i) == 2) ? 2 : 3;
            switch (comp) {
                case 1:
                    a = binario[i];
                    b = 0;
                    res += (digitos64[(a & 0xFC) >> 2]);
                    if (hex.length() % 2 == 0) {
                        res += (digitos64[(int) (((a & 0x03) << 4) | (b & 0xF0) >> 4)]);
                    }
                    break;
                case 2:
                    a = binario[i];
                    b = binario[i + 1];
                    c = 0;
                    res += (digitos64[(int) ((a & 0xFC) >> 2)]);
                    res += (digitos64[(int) (((a & 0x03) << 4) | (b & 0xF0) >> 4)]);
                    res += (digitos64[(int) ((b & 0x0F) << 2 | (c & 0xC0) >> 6)]);
                    break;
                default:
                    a = binario[i];
                    b = binario[i + 1];
                    c = binario[i + 2];
                    res += (digitos64[(int) ((a & 0xFC) >> 2)]);
                    res += (digitos64[(int) (((a & 0x03) << 4) | (b & 0xF0) >> 4)]);
                    res += (digitos64[(int) ((b & 0x0F) << 2 | (c & 0xC0) >> 6)]);
                    res += (digitos64[(int) (c & 0x3F)]);
                    break;
            }
        }
        return res;
    }

    byte[] Base64toByte(String entrada) {
        byte[] binario;
        if (entrada.equals("0")) {
            binario = new byte[1];
            binario[0] = 0;
            System.out.println("Input was zero.");
            return binario;
        }
        char c1, c2, c3, c4;
        int v1, v2, v3, v4;
        int n = entrada.length();
        switch (n % 4) {
            case 2:
                n++;
                entrada = entrada + "0";
            case 3:
                n++;
                entrada = entrada + "0";
            case 0:
                break;
            case 1:
            default:
                n += 3;
                System.out.println("Warning input has wrong size.");
                break;
        }
        n = 3 * n / 4;//4 digits base64 will form 3 bytes
        binario = new byte[n];
        for (int i = 0; i < entrada.length(); i += 4) {
            c1 = entrada.charAt(i);
            c2 = entrada.charAt(i + 1);
            c3 = entrada.charAt(i + 2);
            c4 = entrada.charAt(i + 3);
            v1 = Base64value(c1);
            v2 = Base64value(c2);
            v3 = Base64value(c3);
            v4 = Base64value(c4);
            binario[i / 4 * 3] = 0;
            binario[i / 4 * 3] = (byte) ((v1 << 2) | (v2 & 0x30) >> 4);
            binario[i / 4 * 3 + 1] = 0;
            binario[i / 4 * 3 + 1] = (byte) (((v2 & 0x0F) << 4) | ((v3 & 0x3C) >> 2));
            binario[i / 4 * 3 + 2] = 0;
            binario[i / 4 * 3 + 2] = (byte) (((v3 & 0x03) << 6) | (v4 & 0x3F));
        }
        return binario;
    }

    private int Base64value(char c) {
        for (int i = 0; i < digitos64.length; i++) {
            if (c == digitos64[i]) {
                return i;
            }
        }
        return 0;
    }

    String Base64toHex(String base64) {
        return BytetoHex(Base64toByte(base64));
    }

    String FixedXor(String a, String b) {
        if (a.length() != b.length()) {
            System.out.println("Input strings are not the same size.");
            return "0";
        }
        byte[] byte1 = HextoByte(a);
        byte[] byte2 = HextoByte(b);
        byte[] res = new byte[byte1.length];
        for (int i = 0; i < byte1.length; i++) {
            res[i] = (byte) (byte1[i] ^ byte2[i]);
        }
        return BytetoHex(res);
    }

    char chave;
    int minscore;

    String DecodeSingleByteXor(String entrada) {
        byte[] byte1;
        char c;
        int valor, score, max = 0x7FFFFFFF;
        byte1 = HextoByte(entrada);
        byte[] teste = new byte[byte1.length];
        byte[] res = new byte[byte1.length];
        for (c = 0; c < 255; c++) {
            score = 0;
            for (int i = 0; i < teste.length; i++) {
                teste[i] = (byte) (byte1[i] ^ c);
                valor = etaoin.indexOf(teste[i]);
                if (valor >= 0) {
                    score += valor;
                } else {
                    score += 255;
                }
            }
            if (score < max) {
                chave = c;
                max = score;
                minscore = score;
                res = teste.clone();
            }
        }
        return BytetoHex(res);
    }

    String TexttoHex(String text) {
        String res = "";
        char[] ch = text.toCharArray();
        int aux;
        for (int i = 0; i < ch.length; i++) {
            aux = (int) ch[i];
            if (aux <= 0x0F) {
                res += "0";
            }
            res += Integer.toHexString(aux);
        }
        return res;
    }

    byte[] TexttoByte(String text) {
        return HextoByte(TexttoHex(text));
    }

    String BytetoBase64(byte[] entrada) {
        return HextoBase64(BytetoHex(entrada));
    }

    String RepeatKeyXor(String text, String key) {
        byte[] hex;
        byte[] c;
        hex = TexttoByte(text);
        c = TexttoByte(key);
        byte[] res = new byte[hex.length];
        for (int i = 0; i < hex.length; i++) {
            res[i] = (byte) (hex[i] ^ c[(i % c.length)]);
        }
        return BytetoHex(res);
    }

    int distance(String a, String b) {
        if (a.length() != b.length()) {
            System.out.println("Strings have different sizes.");
        }
        byte[] byte1;
        byte[] byte2;
        byte1 = TexttoByte(a);
        byte2 = TexttoByte(b);

        return bytedistance(byte1, byte2);
    }

    private int bytedistance(byte[] byte1, byte[] byte2) {
        int res = 0;
        byte aux;
        int min = (byte1.length < byte2.length) ? byte1.length : byte2.length;
        for (int i = 0; i < min; i++) {
            aux = (byte) (byte1[i] ^ byte2[i]);
            if ((aux & 0x01) == 0x01) {
                res++;
            }
            if ((aux & 0x02) == 0x02) {
                res++;
            }
            if ((aux & 0x04) == 0x04) {
                res++;
            }
            if ((aux & 0x08) == 0x08) {
                res++;
            }
            if ((aux & 0x10) == 0x10) {
                res++;
            }
            if ((aux & 0x20) == 0x20) {
                res++;
            }
            if ((aux & 0x40) == 0x40) {
                res++;
            }
            if ((aux & 0x80) == 0x80) {
                res++;
            }
        }
        return res;
    }

    int keysize;

    String DecryptReapatKeyXor(String entrada) {
        String res = "", aux;
        byte[] hex, a, b;
        hex = HextoByte(entrada);
        int KEYMIN = 2, KEYMAX = 40, tkeysize, i, k = 0;
        a = new byte[KEYMAX];
        b = new byte[KEYMAX];
        int[] dist = new int[KEYMAX - KEYMIN + 1];
        double[] ndist = new double[KEYMAX - KEYMIN + 1];
        double ndistmin = Double.MAX_VALUE;
        for (tkeysize = KEYMIN; tkeysize <= KEYMAX; tkeysize++) {
            for (i = 0; i < tkeysize; i++) {
                a[i] = hex[i];
                b[i] = hex[tkeysize + i];
            }
            dist[tkeysize - KEYMIN] = bytedistance(a, b);
            ndist[tkeysize - KEYMIN] = (double) dist[tkeysize - KEYMIN] / tkeysize;
            if (ndist[tkeysize - KEYMIN] < ndistmin) {
                ndistmin = ndist[tkeysize - KEYMIN];
                keysize = tkeysize;
            }
        }
        System.out.println(keysize);
        System.out.println(ndistmin);
        aux = "";
        for (i = 0; i < entrada.length(); i += keysize) {
            aux += entrada.substring(i, i + 1);
        }
        //aux = RepeatKeyXor(aux);
        System.out.println(HextoText(aux));
        return res;
    }

}
