package com.hb_215.helloworld;

public class PostClass {
    int a;
    int b;
    String c;
    String d;
    
    /** 
     * @return String
     */
    public String getPost() {
        return String.format("a:%d, b:%d, c:%s, d:%s", this.a, this.b, this.c, this.d);
    }

    @Override
    public String toString() {
        String result = String.format("a:%d, b:%d, c:%s, d:%s", this.a, this.b, this.c, this.d);
        return result;
    }

    public int getA() {
        return this.a;
    }

    public int getB() {
        return this.b;
    }

    public String getC() {
        return this.c;
    }

    public String getD() {
        return this.d;
    }
}
