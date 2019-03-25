package app.Entities;

public class B {
    public Bankaccount ba;
    public B(Bankaccount ba){
        this.ba = ba;
    }
    @Override
    public String toString(){
        return ba.getType();
    }
}
