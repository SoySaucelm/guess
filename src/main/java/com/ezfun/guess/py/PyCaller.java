package com.ezfun.guess.py;

/**
 * Created by liming on 2019/1/21.
 */

import org.springframework.util.FileCopyUtils;

import java.io.*;
import java.util.concurrent.TimeUnit;

class PyCaller {
    private static final String DATA_SWAP = "temp.txt";
    private static final String PY_URL = System.getProperty("user.dir") + "\\src\\main\\java\\com\\ezfun\\guess\\py\\test.py";
    public static void writeImagePath(String path) throws IOException {
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(new FileWriter(new File(DATA_SWAP)));
        } catch (IOException e) {
            e.printStackTrace();
        }

        pw.print(path);
        pw.close();
        FileWriter fileWriter = new FileWriter(new File(DATA_SWAP));
        fileWriter.write("afewfw222",1,3);
        fileWriter.close();
    }

    public static String readAnswer() {
        BufferedReader br;
        String answer = null;
        try {
            br = new BufferedReader(new FileReader(new File(DATA_SWAP)));
            answer = br.readLine();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return answer;
    }
    public static String input2String(InputStream input){
        StringBuilder stringBuilder = new StringBuilder();
        try {
//            URL url = new URL("http","baike.baidu.com",80,"/link?url=wW841T-yTu4c-dPTa15dhIw3Rg3ElGfhDL6nXZ0dcdrLuhDS1V70-CK6znTIXBjChhRIVWN0wiI0yOmMGH77UmlEdcm_xII8LpT0hKBr6oi4BG-RP3kr0ieZRksMM_Qf");
//             = url.openStream();   // 打开输入流
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(input));
            boolean firstLine = true;
            String line = null; ;
            while((line = bufferedReader.readLine()) != null){
                if(!firstLine){
                    stringBuilder.append(System.getProperty("line.separator"));
                }else{
                    firstLine = false;
                }
                stringBuilder.append(line);
            }
            System.out.println(stringBuilder.toString());
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return stringBuilder.toString() ;
    }

    public static void execPy3() throws IOException {
        Process  process;
        BufferedReader reader;
        try {
            ProcessBuilder pbuilder=new ProcessBuilder("python",PY_URL);
            pbuilder.redirectErrorStream(true);
            process=pbuilder.start();
            InputStream in = process.getInputStream();
//            byte[] re=new byte[1024];
//            while (in.read(re)!= -1) {
////                System.out.println("==>"+new String(re));
//            }
//            System.out.println(new String(re));
            reader=new BufferedReader(new InputStreamReader(in));
            String line=null;
            StringBuilder sb = new StringBuilder();
            while((line=reader.readLine())!=null){
//                System.out.println(line);
                sb.append(line);
            }
//            in.close();
            if(process.isAlive()){
                process.waitFor();
            }
            System.out.println(sb.toString());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public static void execPy2() throws IOException {
        Process  process = Runtime.getRuntime().exec("python " + PY_URL);
        try {
            //获取进程的标准输入流
            final InputStream is1 = process.getInputStream();
            //获取进城的错误流
            final InputStream is2 = process.getErrorStream();
            StringBuffer sb1 = new StringBuffer();
            StringBuffer sb2 = new StringBuffer();
            //启动两个线程，一个线程负责读标准输出流，另一个负责读标准错误流
            new Thread() {
                @Override
                public void run() {
                    BufferedReader br1 = new BufferedReader(new InputStreamReader(is1));
                    try {
                        String line1 = null;
                        while ((line1 = br1.readLine()) != null) {
//                            if (line1 != null){}
                            sb1.append(line1);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    finally{
                        try {
                            is1.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }.start();

            new Thread() {
                @Override
                public void  run() {
                    BufferedReader br2 = new  BufferedReader(new  InputStreamReader(is2));
                    try {
                        String line2 = null ;
                        while ((line2 = br2.readLine()) !=  null ) {
//                            if (line2 != null){}
                            sb2.append(line2);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    finally{
                        try {
                            is2.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }.start();

            //可能导致进程阻塞，甚至死锁
            boolean ret = process.waitFor(10,TimeUnit.SECONDS);
            System.out.println(sb1.toString());
            System.out.println(sb2.toString());
            System.out.println("return value:"+ret);
            System.out.println(process.exitValue());
            process.destroy();
//            logger.info("event:{}", "RunExeForWindows",process.exitValue());
            byte[] bytes = new byte[process.getInputStream().available()];
            process.getInputStream().read(bytes);
            System.out.println(new String(bytes));


//            logger.info("event:{}", "RunExeForWindows",new String(bytes));
        }catch (Exception ex){
            ex.printStackTrace();
            try{
                process.getErrorStream().close();
                process.getInputStream().close();
                process.getOutputStream().close();
            }
            catch(Exception ee){}
        }

    }

    public static void execPy() {
        Process proc = null;
        OutputStream outputStream=null;
        InputStream inputStream = null;

        System.out.println(PY_URL);

        try {
            proc = Runtime.getRuntime().exec("python " + PY_URL);
            proc.waitFor();
//            proc.
//            outputStream = proc.getOutputStream();
//            outputStream.write("1".getBytes());
//            outputStream.flush();
            System.out.println("1111111");
            outputStream = new FileOutputStream("b2.txt");
//                System.out.println(proc.isAlive());
            inputStream = proc.getInputStream();
//            OutputStream outputStream1 = proc.getOutputStream();
//            outputStream1.
            String s = input2String(inputStream);
            System.out.println(s);
            Reader in=new InputStreamReader(inputStream);
            String s1 = FileCopyUtils.copyToString(in);
//            IOUtils.copy(inputStream, outputStream);
//            new ByteArrayInputStream(inputStream)
//            String s = inputStream.toString();
            System.out.println(s1);
            FileCopyUtils.copy(inputStream, outputStream);
//            FileCopyUtils.copyToString(new FileReader());
            System.out.println("end");
//            outputStream.is
//            final File file = new File("b2.txt");
//            final FileInputStream fis = new FileInputStream(file);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
//            try {
//                inputStream.close();
//                outputStream.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
        }
    }

    // 测试码
    public static void main(String[] args) throws IOException, InterruptedException {
        execPy3();
//        String url = "https://github.com/";
//        RestTemplate restTemplate = new RestTemplate();
//        ResponseEntity forEntity = restTemplate.getForEntity(url, String.class);
//
//        String result = restTemplate.getForObject(url, String.class);
//        System.out.println(result);
//        writeImagePath("C:\\work\\myCode\\java\\guess\\img\\1.jpg");
//        execPy();
//        System.out.println(readAnswer());
//        byte[] decode = Base64.getDecoder().decode("aHR0cHMlM0EvL2pxLnFxLmNvbS8lM0Zfd3YlM0QxMDI3JTI2ayUzRDVSSDdmdFQ=");
////        https//3A//jq.qq.com/3F_wv/3D1027/26k/3D5RH7ftT
//        String s = new String(decode);
//        System.out.println(URLDecoder.decode(s));

    }
}
