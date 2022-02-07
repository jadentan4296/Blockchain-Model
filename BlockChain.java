import java.util.*;

public class BlockChain {

    public static List<Block> blockChain = new ArrayList<>();

    public static final int difficulty = 6;
    public static void main(String[] args) {

        for(int i=0; i<15; i++) {
            if(blockChain.isEmpty()) {
                blockChain.add(new Block(String.valueOf(i), "0"));
            } else {
                blockChain.add(new Block(String.valueOf(i),blockChain.get(blockChain.size()-1).hash));
            }
            System.out.print("Mining Block " + i + "     ");
            blockChain.get(i).mineBlock(difficulty);
        }
        Block.validChain(blockChain);

    }


}
