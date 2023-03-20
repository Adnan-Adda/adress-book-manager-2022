/**
 * Encryption represent an object that can encrypt/decrypt text using cipher shifting algoritm.
 * It has a constructor with cipher key as parameter,
 * and one without that has a default value for cipher key set to zero.
 * There are two method one to encrypt text and one to decrypt text.
 */
public class Encryption {

    // PLAIN_CHARS_SET and CIPHER_CHARS_SET have save length
    private final String PLAIN_CHARS_SET= "abcdefghijklmnopqrstuvwxyz ABCDEFGHIJKLMNOPQRSTUVWXYZ|öäåÖÄÅ,0123456789";
    private final String CIPHER_CHARS_SET="0kaGHIJouABQRSTUjqbw1ylCDEF2rm3däVWXYZv4sKLMNOPt5nöhp6xg7ec8zi9f#$åÄÅÖ,";
    private final int SIZE=PLAIN_CHARS_SET.length();
    private int cipherKey;

    /**
     * creates new encryption object with custom cipher key
     * the key is used to shift characters forward/backward vid encryption/decryption
     * @param cipherKey key used to encrypt/decrypt the text
     */
    public Encryption(int cipherKey){
        setCipherKey(cipherKey);
    }

    /**
     * creates new encryption object with default cipher key set to 0
     */
    public Encryption(){
        this(0);
    }

    /**
     * set cipher key, if key smaller than 0 it will be set to 0
     * @param cipherKey key used to encrypt/decrypt the text
     */
    public void setCipherKey(int cipherKey){
        this.cipherKey = Math.max(cipherKey, 0);
    }

    /**
     * Encrypt a plain text
     * @param plainText text to be encrypted
     * @return encrypted text
     */
    public String encrypt(String plainText){
        StringBuilder stringBuilder = new StringBuilder();
        for (int i=0;i<plainText.length();i++){
            int plainCharIndex = PLAIN_CHARS_SET.indexOf(plainText.charAt(i));
            // modulo SIZE ensure that the key is not bigger than the size of PLAIN_CHARS_SET
            int cipherCharIndex =(plainCharIndex+cipherKey)%SIZE;
            stringBuilder.append(CIPHER_CHARS_SET.charAt(cipherCharIndex));
        }
        return stringBuilder.toString();
    }

    /**
     * Decrypt encrypted text
     * @param cipherText text to be decrypted
     * @return plain text
     */
    public String decrypt(String cipherText){
        StringBuilder stringBuilder = new StringBuilder();
        for (int i=0;i<cipherText.length();i++){
            int cipherCharIndex = CIPHER_CHARS_SET.indexOf(cipherText.charAt(i));
            int plainCharIndex = (cipherCharIndex - (cipherKey % SIZE) + SIZE) % SIZE;
            stringBuilder.append(PLAIN_CHARS_SET.charAt(plainCharIndex));
        }
        return stringBuilder.toString();
    }
}
