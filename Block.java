import java.util.Date;
import java.util.List;
import java.security.MessageDigest;

public class Block {

    public String previousHash;
    public String data;
    private long timeStamp;
    public String hash;
    private int nonce;

    public Block(String data, String previousHash) {
        this.previousHash = previousHash;
        this.data = data;
        this.timeStamp = new Date().getTime();
        this.hash = calculateHash();
    }

    public String calculateHash() {
        String calculatedHash = sha256(
            previousHash
            + Long.toString(timeStamp)
            + data
            + Integer.toString(nonce)
        );
        return calculatedHash;
    }

    public String sha256(String input){
        try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");	        
			
            byte[] bytes = digest.digest(input.getBytes("UTF-8"));

            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < bytes.length; i++) {
				String hex = Integer.toHexString(0xff & bytes[i]);
				if(hex.length() == 1) hexString.append('0');
				hexString.append(hex);
			}
            return hexString.toString();
		} 
        catch(Exception e) {
			throw new RuntimeException(e);
		}
    }

    public void mineBlock(int difficulty) {
        String target = new String(new char[difficulty]).replace('\0', '0');

        while(!hash.substring(0, difficulty).equals(target)) {
			nonce ++;
			hash = calculateHash();
		}
        System.out.println("Block mined:    " + hash);
        System.out.println();
    }

    public static Boolean validChain(List<Block> blockChain) {
        Block currentBlock;
        Block previousBlock;

        for(int i=1; i<blockChain.size(); i++) {
            currentBlock = blockChain.get(i);
            previousBlock = blockChain.get(i-1);

            if(!currentBlock.hash.equals(currentBlock.calculateHash())) {
                System.out.println("CURRENT HASH INVALID");
                return false;
            }

            if(!currentBlock.previousHash.equals(previousBlock.hash)) {
                System.out.println("PREVIOUS HASH INVALID");
                return false;
            }
        }
        System.out.println("VALID CHAIN");
        return true;
    }
}
