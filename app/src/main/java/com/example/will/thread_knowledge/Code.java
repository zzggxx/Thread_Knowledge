package com.example.will.thread_knowledge;

public class Code {

    /**
     * 普通代码块：在方法或语句中出现的{}就称为普通代码块。
     * 普通代码块和一般的语句执行顺序由他们在代码中出现的次序决定--“先出现先执行”
     */
    public void init() {
        {
            System.out.println("普通代码块中的普通代码块");
        }
        System.out.println("普通代码块");
    }

    /**
     * 构造块：直接在类中定义且没有加static关键字的代码块称为{}构造代码块。
     * 构造代码块在创建对象时被调用，每次创建对象都会被调用，并且构造代码块的执行次序优先于类构造函数
     */ {
        System.out.println("Code的构造块");
    }

    /**
     * 静态代码块:在java中使用static关键字声明的代码块。静态块用于初始化类，为类的属性初始化。每个静态代码块只会执行一次。
     * 由于JVM在加载类时会执行静态代码块，所以静态代码块先于主方法执行。如果类中包含多个静态代码块，那么将按照"先定义的代码先执行，后定义的代码后执行"。
     * 注意：1 静态代码块不能存在于任何方法体内。2 静态代码块不能直接访问静态实例变量和实例方法，需要通过类的实例对象来访问。
     */
    static {
        System.out.println("Code的静态代码块");
    }

    public Code() {
        System.out.println("Code的构造方法");
    }
}

