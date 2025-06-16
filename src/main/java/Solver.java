import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Stack;

public class Solver {
    private LinkedList <Node> allNodes=new LinkedList<>();
    private ArrayList<Tiny[]> allBoards=new ArrayList<>();
    Stack <Byte> moves=new Stack<>();
    private boolean bfs=true;
    private int bestScore=0;

    public void solveIt(Node parent){
        if(bfs){
            int tryNum=0;
            while (!victory(parent)){
                tryNum++;
                System.out.println(tryNum);
                Node left = parent.left();
                Node up = parent.up();
                Node right = parent.right();
                Node down = parent.down();

                switch (parent.getSelf()){
                    case 1->right=null;
                    case 2->down=null;
                    case 3->left=null;
                    case 4->up=null;
                }
                addNode(left,parent);
                addNode(up,parent);
                addNode(right,parent);
                addNode(down,parent);
                parent=allNodes.pop();
            }
            if(victory(parent)){
                printMoves(parent);
            }
        }
    }
    public void addNode(Node node,Node parent){
        if (node != null && !checkIfExist(node.getState())) {
            byte score=score(node);
            if(score>bestScore){
                bestScore=score;
            }
            if(bestScore<score+8){
            parent.setLeft(parent.left());
            allNodes.add(node);
            allBoards.add(node.getState());
            }
        }
    }
    public byte score(Node node){
        byte score=0;
        for (int i = 0; i < node.getState().length-1; i++) {
            int current=node.getState()[i].getNumber();
            if(current==i+1){
                score+=3;
            }else if ((current==i && i!=0 && i!=3 && i!=6)|| (current==i+2 && i!=2 && i!=5 && i!=9)){
                score+=2;
            }else {
             if ((current==(i+1)+3) || i>2&& (current==(i+1)-3)){
                    score+=2;
                }

        }
        }
        return score;
    }
    public static boolean victory(Node parent){
        Tiny []arr=parent.getState();
        for (int i = 0; i < arr.length-1; i++) {
            if(arr[i].getNumber()!=i+1){
                return false;
            }
        }
        return true;
    }
    private boolean checkIfExist(Tiny []arr2){
        for (Tiny[]arr1: allBoards) {
            if(checkEven(arr1,arr2)){
                return true;
            }
        }
        return false;
    }
    public boolean checkEven(Tiny[] arr1, Tiny[] arr2){
        for (int i = 0; i < arr1.length - 1; i++) {
            if(arr1[i].getNumber()!=arr2[i].getNumber()){
                return false;
            }
        }
        return true;
    }
    public void printMoves(Node node){
        if(node.getSelf()!=0){
            moves.push(node.getSelf());
            switch (node.getSelf()){
                case 1-> System.out.print("right ");
                case 2-> System.out.print("down ");
                case 3-> System.out.print("left ");
                case 4-> System.out.print("up ");
            }
            printMoves(node.getParent());
        }
    }
}
