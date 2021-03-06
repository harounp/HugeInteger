import java.util.Random;
/**
 * @author Pierre Haroun
 */
public class HugeInteger {
    private boolean sign;
    private int num[];
    private int top=-1;
    
    public HugeInteger(String val){        
        sign=val.charAt(0)=='-';
        int x = sign?1:0;
        top=val.length() - x - 1;
        num= new int[val.length()-x];
        int j=0;
        for (int i=top ;i>=0 ;i--){
            num[i]=val.charAt(j+x)-48;
            j++;
        }
    }
    public HugeInteger(int n){
        top=n-1;
        num= new int[n];
        Random generator = new Random(); 
        if(n>0){
            num[top]=generator.nextInt(8) + 1;
        }
        for(int i=top-1 ;i>=0 ;i--){
            num[i]=generator.nextInt(9);
        }
        sign=generator.nextInt(1)==1;
    }
    public HugeInteger add(HugeInteger h){
        HugeInteger sum = new HugeInteger("0");        
        if(sign==h.sign){
            int x = top>=h.top?top+1:h.top+1;
            int X[]= top>=h.top?num:h.num;
            int y = top<h.top?top:h.top;
            int Y[]= top<h.top?num:h.num;
            sum.top= x;
            sum.sign=sign;
            sum.num= new int[x+1];
            int carry = 0;            
            for (int i =0; i<=y; i++){
                sum.num[i]= (carry + X[i] + Y[i])%10;
                carry = (carry + X[i] + Y[i])/10;
            }
            for (int i=y+1;i<x;i++){
                sum.num[i]= (carry + X[i])%10;
                carry = (carry + X[i])/10;
            }
            sum.num[x]=carry;
        }
        else{            
            int x = top>=h.top?top+1:h.top+1;
            int X[]= top>=h.top?num:h.num;
            int y = top<h.top?top:h.top;
            int Y[]= top<h.top?num:h.num;            
            sum.top=0;
            sum.num= new int[x+1];
            int borrow=0;
            for(int i=0; i<y; i++){
                if(Y[i]>X[i]){
                    borrow=1;
                    X[i+1]=X[i+1]-1;
                }
                sum.num[i]= borrow*10 + X[i] - Y[i];
                sum.top++;
            }
            for(int i=y;i<x;i++){
                sum.num[i]=X[i];
                sum.top++;
            }
            if(num[top]==0)
            {
                sum.sign= true;
            }
            else{
                sum.sign= x-1==top;
            }
        }        
        return sum;
    }
    public HugeInteger subtract(HugeInteger h){
        HugeInteger diff = new HugeInteger("0");                
        h.sign=!h.sign;
        diff = this.add(h);        
        h.sign=!h.sign;
        return diff;
        
    }
    public HugeInteger multiply(HugeInteger h)
    {
        HugeInteger prod= new HugeInteger("0");
        int x=top;
        int y=h.top;        
        prod.num = new int [x+y+2];               
        for(int i=0;i<=x;i++)
        {
            for(int j=0;j<=y;j++)
            {
                prod.num[i+j]+= (num[i]*h.num[j]);  
                prod.num[i+j+1]+= prod.num[i+j]/10;
                prod.num[i+j] = prod.num[i+j]%10;                             
            }
        }
        prod.top=x+y;
        prod.sign = sign ^ h.sign;        
        return prod;
    }
    public int compareTo(HugeInteger h){
        if(!(sign) && h.sign){
            return 1;
        }
        if(sign && !(h.sign)){
            return -1;
        }
        if(sign && h.sign){
            int x= top;
            int y= h.top;
            if (x==y){
                while(x>=0){
                    if(num[x]==h.num[x]){
                        x--;
                    }
                    else{
                        return num[x]<h.num[x]?1:-1;
                    }
                }
            }
            else{
                return x<y?1:-1;
            }
        }
        if(!(sign) && !(h.sign)){
            int x= top;
            int y= h.top;
            if (x==y){
                while(x>=0){
                    if(num[x]==h.num[x]){
                        x--;
                    }
                    else{
                        return num[x]>h.num[x]?1:-1;
                    }
                }
            }
            else{
                return x>y?1:-1;
            }
        }
        return 0;
     }
                
    
    @Override
    public String toString(){
        String out = new String();
        for(int i=top;i>0;i--) {
            if(num[top]==0){
                top--;                
            }
            else{
                break;
            }
        }       
        if(sign && num[top]!=0){
            out+="-";
        }
        for(int i=top;i>=0;i--){
            out+=num[i];
        }
        out+="\n";
        return out;
    }    
}
