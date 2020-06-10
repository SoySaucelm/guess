package com.ezfun.guess.multithread.case005;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

/**
 * @author SoySauce
 * @date 2019/10/29
 */
public class ClientSocket {
    public static void main(String[] args) {
        Socket client = new Socket();
        PrintWriter writer = null;
        BufferedReader reader = null;
        try {
            client.connect(new InetSocketAddress("localhost", 8000));
            writer = new PrintWriter(client.getOutputStream(), true);
            reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
            while (true) {
                Scanner scanner = new Scanner(System.in);
                String content = scanner.nextLine();
                writer.println("hi server:" + content);
                System.out.println("from server: " + reader.readLine());
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            System.out.println("finally");
            try {
                if (writer != null) {
                    writer.close();
                }
                if (reader != null) {
                    reader.close();
                }
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
