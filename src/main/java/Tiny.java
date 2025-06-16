import java.util.Arrays;

public class Tiny {
    boolean []booleans;

    public Tiny(int number) {
        setNumber(number);
    }
    public int getNumber(){
        int number=0;
        for (int i = 0; i < booleans.length; i++) {
            if(booleans[i]){
                number+=Math.pow(2,i);
            }
        }
        return number;
    }
    public void setNumber(int number){
        int size=1;
        int otherNum=number;
        while (number>=1){
            size++;
            number/=2;
        }
        booleans=new boolean[size];
        int index=0;
        while (otherNum!=0){
            if(otherNum%2==0){
                booleans[index]=false;
            }else {
                booleans[index]=true;
            }
            index++;
            otherNum/=2;
        }
    }
    @Override
    public String toString() {
        return getNumber()+"";
    }
}
