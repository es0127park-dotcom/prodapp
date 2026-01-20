package server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class MyServer {
    public static void main(String[] args) {
        try {
            // 1. 20000번 포트로 대기중...
            ServerSocket ss = new ServerSocket(20000);
            Socket socket = ss.accept();

            // 2. 새로운 소켓에 버퍼달기 (BR, BW)
            InputStream in = socket.getInputStream();
            InputStreamReader ir = new InputStreamReader(in);
            BufferedReader br = new BufferedReader(ir);

            OutputStream out = socket.getOutputStream();
            OutputStreamWriter ow = new OutputStreamWriter(out);
            BufferedWriter bw = new BufferedWriter(ow);

            while(true){
                // 1. 클라이언트로부터 받은 메시지
                String line = br.readLine(); // 엔터키까지 읽기

                // 2. 파싱 (json string -> object)

                // 3. 서비스호출 (상품상세)

                // 4. 응답
                bw.write("ok");
                bw.write("\n");
                bw.flush();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
