package com.audi.demo1;



import java.io.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @program: jvm
 * @author: wnr
 * @Date 2021/1/25
 * 自定义加载器
 */
public class HelloClassLoader extends ClassLoader{

    private String path;
    public HelloClassLoader(String classPath){
        path = classPath;
    }

    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException {
        //这个类class的路径
        String classPath = "src\\com\\audi\\demo1\\Hello.xlass";

        HelloClassLoader myClassLoader = new HelloClassLoader(classPath);
        //类的全称
        String packageNamePath = "com.audi.demo1.Hello";

        //加载Hello这个class文件
        Class<?> hello = myClassLoader.loadClass(packageNamePath);
        System.out.println(hello);

        System.out.println("类加载器是:" + hello.getClassLoader());

        //利用反射获取main方法
        Method method = hello.getDeclaredMethod("main", String[].class);
        Object object = hello.newInstance();
        String[] arg = {"ad"};
        method.invoke(object, (Object) arg);
    }



    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        Class hello = null;
        byte[] classData = loadClassData();

        if (classData!=null){
            // 将class的字节码数组转换成Class类的实例
            hello = defineClass(name, classData, 0, classData.length);
        }

        return hello;
    }

    /**
     * 将class文件转化为字节码数组
     * @return
     * @throws ClassNotFoundException
     */
    private byte[] loadClassData() throws ClassNotFoundException {
        File file = new File(path);
        if (file.exists()){
            FileInputStream in = null;
            ByteArrayOutputStream out = null;
            try {
                in = new FileInputStream(file);
                out = new ByteArrayOutputStream();

                byte[] buffer = new byte[1024];
                int size = 0;
                while ((size = in.read(buffer)) != -1) {
                    out.write(buffer, 0, size);
                }

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    in.close();
                } catch (IOException e) {

                    e.printStackTrace();
                }
            }
            return out.toByteArray();
        }else{
            return null;
        }


    }




}
