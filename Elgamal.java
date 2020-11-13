import java.math.*;
import java.util.*;
import java.security.*;
import java.io.*;
import java.util.Scanner;
import java.nio.charset.Charset;
import java.math.BigInteger;

public class Elgamal
{
    static Random rand = new SecureRandom();
    public static void main(String[] args)
    {

        BigInteger p, e1, e2, d;
        System.out.print("Enter your message: ");
        Scanner sc=new Scanner(System.in);
        String str=sc.nextLine();

  //keys generation

        p = BigInteger.probablePrime(128, rand);
        BigInteger maxLimit = new BigInteger(p.toString());
        BigInteger minLimit = new BigInteger("1");
        BigInteger bigInteger = maxLimit.subtract(minLimit);
        int len = maxLimit.bitLength();

  //secret key generation
        d = new BigInteger(len, rand);
        if (d.compareTo(minLimit) < 0)
          d = d.add(minLimit);
        if (d.compareTo(bigInteger) >= 0)
            d = d.mod(bigInteger).add(minLimit);
        // public key calculation
        e1 = new BigInteger("3");
        e2 = e1.modPow(d, p);
        System.out.println("The public keys are :");
        System.out.println("p = " + p);
        System.out.println("e1 = " + e1);
        System.out.println("e2 = " + e2);
        System.out.println("The secret key is = " + d);


        BigInteger Ptext= new BigInteger(str.getBytes(Charset.forName("US-ASCII")));

        // Encryption

        BigInteger[] call=encryption(e1,e2,p,Ptext);
        BigInteger c1=call[0];
        BigInteger c2=call[1];
        System.out.println("Plaintext = " + Ptext);
        System.out.println("cipher text 1 (C1) = " + c1);
        System.out.println("cipher text 2 (C2) = " + c2);

        BigInteger dectext=decryption(c1,c2,d,p);
        System.out.println("Alice decodes: " + dectext);
        String dec = new String(dectext.toByteArray(),Charset.forName("ascii"));
            String finalMessage = dec;
            System.out.println("finalMessage after decryption is: "+finalMessage);
    }

    //This whole part is main method with generation of keys,calling  the encryption and decryption methods and the messages are displayed here//

    // encryption method//

    public static BigInteger[] encryption(BigInteger e1,BigInteger e2,BigInteger p,BigInteger Ptext){
        BigInteger r = new BigInteger(128, rand);
        BigInteger c1 = e1.modPow(r, p);
        BigInteger c2 = Ptext.multiply(e2.modPow(r, p)).mod(p);
        return new BigInteger[] {c1,c2};
    }

    //decryption method//

    public static BigInteger decryption(BigInteger c1,BigInteger c2,BigInteger d,
    BigInteger p){
        BigInteger c1modp = c1.modPow(d, p);
        BigInteger c1modpInv = c1modp.modInverse(p);
        BigInteger dectext1 = c1modpInv.multiply(c2).mod(p);
        return dectext1;
    }
}
