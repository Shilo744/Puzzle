import java.util.Arrays;

public class Node {
    private Node parent;
    private Node left;
    private Node up;
    private Node right;
    private Node down;
    private Tiny[]state;
    private byte location;
    private byte self;
    //self is:
    //1-left
    //2-up
    //3-right
    //4-down

    public Node(Node parent, Tiny[] state, byte location, byte self) {
        this.parent = parent;
        this.state = state;
        this.location = location;
        this.self = self;
    }
    public Node left(){
        byte location=this.location;
            Tiny[]numbers=copy();
        if(location%3!=0){
            numbers[location].setNumber(numbers[location-1].getNumber());
            numbers[location-1].setNumber(0);

            location-=1;
            return new Node(this,numbers,location, (byte) 1);
        }
        return null;
    }
    public Node up(){
        int location=this.location;
        Tiny[]numbers=copy();
        if(location>=3){
            numbers[location].setNumber(numbers[location-3].getNumber());
            numbers[location-3].setNumber(0);
            location-=3;
            return new Node(this,numbers, (byte) location, (byte) 2);
        }
        return null;
    }
    public Node right(){
        int location=this.location;
        Tiny[]numbers=copy();
        if(location%3!=2){
            numbers[location].setNumber(numbers[location+1].getNumber());
            numbers[location+1].setNumber(0);

            location+=1;
            return new Node(this,numbers, (byte) location, (byte) 3);
        }
        return null;
    }
    public Node down(){
        int location=this.location;
        Tiny[]numbers=copy();
        if(location<6){
            numbers[location].setNumber(numbers[location+3].getNumber());
            numbers[location+3].setNumber(0);

            location+=3;
            return new Node(this,numbers, (byte) location, (byte) 4);
        }
        return null;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public void setUp(Node up) {
        this.up = up;
    }

    public void setRight(Node right) {
        this.right = right;
    }

    public void setDown(Node down) {
        this.down = down;
    }
    private Tiny[]copy(){
        Tiny []arr= new Tiny[state.length];
        for (int i = 0; i < state.length; i++) {
            arr[i]=new Tiny(state[i].getNumber());
        }
        return arr;
    }

    public Node getParent() {
        return parent;
    }

    public Tiny[] getState() {
        return state;
    }

    public byte getSelf() {
        return self;
    }

    public byte getLocation() {
        return location;
    }

    @Override
    public String toString() {
        return "Node{" +
                "state=" + Arrays.toString(state) +
                ", location=" + location +
                ", self=" + self +
                '}';
    }
}
