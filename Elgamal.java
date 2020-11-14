import java.math.*;
import java.util.*;
import java.security.*;
import java.io.*;
import java.util.Scanner;
import java.nio.charset.Charset;
import java.math.BigInteger;

//Main Method
public class Elgamal
{
    static Random random = new SecureRandom();
    public static void main(String... args)
    {

        BigInteger p, a1, a2, d;
        System.out.print("Enter your message: ");
        Scanner sc=new Scanner(System.in);
        String str=sc.nextLine();

  //keys

        p = BigInteger.probablePrime(128, random);
        BigInteger maxLim = new BigInteger(p.toString());
        BigInteger minLim = new BigInteger("1");
        BigInteger bigInteger = maxLim.subtract(minLim);
        int len = maxLim.bitLength();

  //secret key
        d = new BigInteger(len, random);
        if (d.compareTo(minLim) < 0)
          d = d.add(minLim);
        if (d.compareTo(bigInteger) >= 0)
            d = d.mod(bigInteger).add(minLim);
        // public key
        a1 = new BigInteger("3");
        a2 = a1.modPow(d, p);
        System.out.println("The public keys for Elgamal PKC are :");
        System.out.println("p :- " + p);
        System.out.println("A1 :- " + a1);
        System.out.println("A2 :-  " + a2);
        System.out.println("The secret key is = " + d);


        BigInteger Ptext= new BigInteger(str.getBytes(Charset.forName("US-ASCII")));

        // Encryption

        BigInteger[] call=encryption(a1,a2,p,Ptext);
        BigInteger c1=call[0];
        BigInteger c2=call[1];
        System.out.println("Plaintext = " + Ptext);
        System.out.println("cipher text 1 (C1) = " + c1);
        System.out.println("cipher text 2 (C2) = " + c2);

        BigInteger dtext=decryption(c1,c2,d,p);
        System.out.println("Alice decodes: " + dtext);
        String dec = new String(dtext.toByteArray(),Charset.forName("ascii"));
            String Message = dec;
            System.out.println("Message after decryption is: "+Message);
    }

    // encryption method//

    public static BigInteger[] encryption(BigInteger e1,BigInteger e2,BigInteger p,BigInteger Ptext){
        BigInteger r = new BigInteger(128, random);
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
