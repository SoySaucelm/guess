package com.ezfun.guess.packagedemo;

import java.lang.annotation.Annotation;

import static com.ezfun.guess.packagedemo.PkgConst.PACAKGE_CONST;

/**
 * @author SoySauce
 * @date 2019/12/3
 */
//@java.lang.Deprecated
public class Client {
    public void run(){
        System.out.println("run>>>");
    }
    public void say(){
        System.out.println("say>>>");
    }

    public static void main(String[] args) {
        //可以通过I/O操作或配置项获得包名
        String pkgName = "com.ezfun.guess.packagedemo";
        Package pkg = Package.getPackage(pkgName);
        //获得包上的注解
        Annotation[] annotations = pkg.getAnnotations();
        //遍历注解数组
        for(Annotation an:annotations){
            if(an instanceof PkgAnnotation){
                System.out.println(((PkgAnnotation) an).value());
                System.out.println("Hi,I'm the PkgAnnotation");

            }
        }
        System.out.println(PACAKGE_CONST);


    }


}
